package com.pratclot.orderly.tools

import com.pratclot.orderly.BUILD_FILE_NAME
import com.pratclot.orderly.OrderlyPluginAbstraction
import com.pratclot.orderly.data.ProjectType
import com.pratclot.orderly.tools.ResourceLoader.copyFileFromResources
import org.gradle.api.Project
import java.io.File

fun createFileBuildGradle(
    file: File,
    projectType: ProjectType,
) {
    when (projectType) {
        ProjectType.ANDROID_APP -> TODO()
        ProjectType.ANDROID_LIB -> "android-library/$BUILD_FILE_NAME.tpl"
        ProjectType.JAVA_LIB -> "java-library/$BUILD_FILE_NAME.tpl"
    }.let {
        copyFileFromResources(
            it,
            file
        )
    }
}

fun createManifestFile(
    file: File,
    projectType: ProjectType,
    modifier: String.() -> String,
) {
    val pathToManifest = when (projectType) {
        ProjectType.ANDROID_LIB -> "android-library/AndroidManifest.xml"
        ProjectType.ANDROID_APP -> "android-app/AndroidManifest.xml"
        else -> ""
    }
    copyFileFromResources(
        pathToManifest,
        file,
        modifier,
    )
}

const val ORDERLY_SETTINGS_GRADLE = "orderly.gradle"
const val SETTINGS_GRADLE = "settings.gradle"

fun Project.linkSubprojectInSettings(subprojectName: String) {
    linkSubprojectInSettings(subprojectName, rootDir)
}

fun linkSubprojectInSettings(subprojectName: String, rootDir: File) {
    val settingsFile = rootDir.resolve(ORDERLY_SETTINGS_GRADLE)
    if (!settingsFile.exists()) settingsFile.createNewFile()

    linkSubproject(subprojectName, settingsFile)
}

fun linkSubproject(subprojectName: String, settingsFile: File) {
    val includeStatement = "include('$subprojectName')"
    if (settingsFile.hasNoLine(includeStatement)) {
        settingsFile.appendText(includeStatement)
        settingsFile.appendText("\n")
    }
}

fun settingsFileHasNoLinkToOrderlySettings(settingsFile: File): Boolean {
    return settingsFile.hasNoLine("apply from: '$ORDERLY_SETTINGS_GRADLE'")
}

fun linkOrderlySettingsFile(settingsFile: File) {
    settingsFile.appendText("apply from: '$ORDERLY_SETTINGS_GRADLE'")
    settingsFile.appendText("\n")
}

fun File.hasNoLine(match: String): Boolean {
    return bufferedReader().use {
        var line = it.readLine()
        while (line != null) {
            if (line == match) {
                it.close()
                return@use false
            }
            line = it.readLine()
        }
        it.close()
        true
    }
}

fun String.convertToGradlePathNotation() = replace('/', ':')

fun Project.isLayerApiFeature() = with(path) {
    (startsWith(":api:") && (endsWith(":mock") || endsWith(":live")))
}

fun Project.isApiCommonLive() = with(path) {
    equals(":api:common-live")
}

fun Project.isApiCommon() = with(path) {
    equals(":api:common")
}

fun Project.isCommonKotlin() = with(path) {
    equals(":common:kotlin")
}

fun Project.isCommonAndroid() = with(path) {
    equals(":common:android")
}

fun Project.isJavaLibrary() = with(plugins) {
//    This does not work because no plugins appear to be applied at `afterEvaluate` stage!
//    hasPlugin("java-library")
    (isLayerCommon() && !isCommonAndroid()) || isLayerApi() || isLayerRepository() || isLayerUsecase() || isDomain() || isDto()
}

fun Project.isAndroidLibrary() = with(plugins) {
//    This does not work because no plugins appear to be applied at `afterEvaluate` stage!
//    hasPlugin("com.android.library")
    isCommonAndroid() || isLayerScreen()
}

fun Project.isAndroidApp() = with(plugins) {
//    hasPlugin("com.android.application")
    isApp()
}

fun Project.isDto() = with(path) {
    equals(":dto")
}

fun Project.isDomain() = with(path) {
    equals(":domain")
}

fun Project.isApp() = with(path) {
    equals(":app")
}

fun Project.isApiLive() = with(path) {
    startsWith(":api:") && endsWith(":live")
}

fun Project.isLayerCommon() = with(path) {
    startsWith(":common:")
}

fun Project.isLayerScreen() = with(path) {
    startsWith(":screen:")
}

fun Project.isLayerApi() = with(path) {
    startsWith(":api:")
}

fun Project.isLayerRepository() = with(path) {
    startsWith(":repository:")
}

fun Project.isLayerUsecase() = with(path) {
    startsWith(":usecase:")
}

fun Project.getFeatureName() = with(path) {
    split(':')[2]
}

fun Project.getProjectByPath(path: String) =
    project.subprojects.find { it.path == path }
        ?: noProject("There is no project with path: $path")

fun noProject(message: String = ""): Nothing = throw ProjectNotYetGeneratedException(message)
class ProjectNotYetGeneratedException(message: String) : IllegalStateException(message)

fun OrderlyPluginAbstraction.getProjectByPath(path: String) = project.getProjectByPath(path)

fun OrderlyPluginAbstraction.getDomain() = getProjectByPath(":domain")
fun OrderlyPluginAbstraction.getDto() = getProjectByPath(":dto")
fun OrderlyPluginAbstraction.getApiCommon() = getProjectByPath(":api:common")
fun OrderlyPluginAbstraction.getApiCommonLive() = getProjectByPath(":api:common-live")
fun OrderlyPluginAbstraction.getCommonKotlin() = getProjectByPath(":common:kotlin")
