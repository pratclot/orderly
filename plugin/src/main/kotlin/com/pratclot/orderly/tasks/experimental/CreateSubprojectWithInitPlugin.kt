package com.pratclot.orderly.tasks.experimental

import com.pratclot.orderly.tools.convertToGradlePathNotation
import com.pratclot.orderly.tools.linkSubprojectInSettings
import org.gradle.api.tasks.TaskAction
import org.gradle.buildinit.InsecureProtocolOption

abstract class CreateSubprojectWithInitPlugin : InitBuildInDirectory() {

    init {
        packageName = (project.properties["packageName"] ?: "com.some.name").toString()
        insecureProtocol.set(InsecureProtocolOption.WARN)
    }

    @TaskAction
    fun exec() {
        project.linkSubprojectInSettings("${projectDirConfigurable.get().convertToGradlePathNotation()}:$projectName")
    }
}
