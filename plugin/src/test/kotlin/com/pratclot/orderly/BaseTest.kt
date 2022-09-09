package com.pratclot.orderly

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

abstract class BaseTest {

    lateinit var project: Project

    protected fun before(apply: Boolean = true) {

        // Create a test project and apply the plugin
        project = ProjectBuilder.builder()
            .withName("testProject")
            .build()
        if (apply) project.plugins.apply("com.pratclot.orderly")
    }

}
