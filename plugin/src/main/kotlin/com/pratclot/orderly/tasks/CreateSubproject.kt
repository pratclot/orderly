package com.pratclot.orderly.tasks

import com.pratclot.orderly.data.ProjectType
import com.pratclot.orderly.tools.ORDERLY_SETTINGS_GRADLE
import com.pratclot.orderly.tools.SETTINGS_GRADLE
import com.pratclot.orderly.tools.createFileBuildGradle
import com.pratclot.orderly.tools.createManifestFile
import com.pratclot.orderly.tools.hasNoLine
import com.pratclot.orderly.tools.linkOrderlySettingsFile
import com.pratclot.orderly.tools.linkSubprojectInSettings
import com.pratclot.orderly.tools.settingsFileHasNoLinkToOrderlySettings
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.work.Incremental
import org.gradle.work.InputChanges
import java.io.File
import javax.inject.Inject

abstract class CreateSubproject @Inject constructor(private val objectFactory: ObjectFactory) :
    DefaultTask() {

    @get:Input
    abstract val dir: Property<String>

    @get:Input
    abstract val projectType: Property<ProjectType>

    @get:Input
    abstract val packageName: Property<String>

    @get:OutputDirectory
    abstract val projectDir: Property<File>

    @get:OutputFile
    abstract val buildFile: Property<File>

    @get:OutputDirectory
    abstract val srcDirKotlin: Property<File>

    @get:OutputDirectory
    abstract val srcDirTest: Property<File>

    @get:Optional
    @get:OutputFile
    abstract val manifestFile: Property<File>

    @TaskAction
    fun exec2(inputChanges: InputChanges) {
//        logger.lifecycle("incremental mode is on: ${inputChanges.isIncremental}")
        createSubprojectInDir(dir.get(), projectType.get())
    }

    private fun createSubprojectInDir(
        dir: String,
        projectType: ProjectType,
    ) {
        bootstrapSubproject(dir, projectType)

        if (settingsFileHasNoLinkToOrderlySettings(project.rootDir.resolve(SETTINGS_GRADLE))) {
            createOrderlySettingsFile()
            linkOrderlySettingsFile(project.rootDir.resolve(SETTINGS_GRADLE))
        }

        linkSubprojectToOrderlySettingsFile(dir)
    }

    private fun bootstrapSubproject(
        dir: String,
        projectType: ProjectType,
    ) {
        project.mkdir(dir)
        createFileBuildGradle(buildFile.get(), projectType)
        srcDirKotlin.get().mkdirs()
        srcDirTest.get().mkdirs()
        if (projectType in listOf(ProjectType.ANDROID_LIB, ProjectType.ANDROID_APP)) {
            createManifestFile(manifestFile.get(), projectType) {
                replace(
                    "PLACEHOLDER",
                    packageName.get(),
                )
            }
        }
    }

    private fun linkSubprojectToOrderlySettingsFile(dir: String) {
        project.linkSubprojectInSettings(dir.replace('/', ':'))
    }

    private fun createOrderlySettingsFile() {
        project.rootDir.resolve(ORDERLY_SETTINGS_GRADLE).createNewFile()
    }

}
