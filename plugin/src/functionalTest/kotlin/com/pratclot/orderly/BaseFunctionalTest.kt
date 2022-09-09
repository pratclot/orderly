package com.pratclot.orderly

import com.pratclot.orderly.tools.ResourceLoader
import com.pratclot.orderly.tools.SETTINGS_GRADLE
import com.pratclot.orderly.tools.linkOrderlySettingsFile
import com.pratclot.orderly.tools.linkSubprojectInSettings
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.io.TempDir
import java.io.File

open class BaseFunctionalTest {

    protected val runner by lazy {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withProjectDir(getProjectDir())
    }

    @field:TempDir
    lateinit var tempFolder: File

    protected fun getProjectDir() = tempFolder
    protected fun getBuildFile() = getProjectDir().resolve(BUILD_FILE_NAME)
    protected fun getSettingsFile() = getProjectDir().resolve(SETTINGS_GRADLE)


    protected fun before() {

        // Setup the test build
        getSettingsFile().writeText("")
        ResourceLoader.copyFileFromResources("$BUILD_FILE_NAME.tpl", getBuildFile())
    }

    protected fun createAppProject() {
        ResourceLoader.copyFileFromResources(
            "test/gradle.properties",
            getProjectDir().resolve("gradle.properties")
        )
        ResourceLoader.copyFileFromResources(
            "test/local.properties",
            getProjectDir().resolve("local.properties")
        )
        getProjectDir().resolve("app/src/main").mkdirs()
        ResourceLoader.copyFileFromResources(
            "android-app/$BUILD_FILE_NAME.tpl",
            getProjectDir().resolve("app/$BUILD_FILE_NAME")
        )
        ResourceLoader.copyFileFromResources(
            "android-app/AndroidManifest.xml",
            getProjectDir().resolve("app/src/main/AndroidManifest.xml")
        ) {
            replace("PLACEHOLDER", "$DEFAULT_PACKAGE_NAME.app")
        }
        linkSubprojectInSettings("app", getProjectDir())
        linkOrderlySettingsFile(getSettingsFile())
    }
}
