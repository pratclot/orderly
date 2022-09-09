package com.pratclot.orderly.tasks.experimental

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.buildinit.tasks.InitBuild
import kotlin.reflect.full.superclasses


/**
 * A hacky task to imitate `gradle init` programmatically. Specifically, this task allows to
 * configure the directory where the new project would be initiated.
 */
abstract class InitBuildInDirectory : InitBuild() {
    @get:Input
    abstract val projectDirConfigurable: Property<String>

    private fun fixPath() {
        try {
            val common = project.layout.projectDirectory.dir(projectDirConfigurable.get())

            val field = this::class.superclasses[0].java.superclass.getDeclaredField("projectDir")
            field.isAccessible = true
            field.set(this, common)
            println("projectDir is now: ${field.get(this)}")
        } catch (ex: Throwable) {
            println(ex)
        }
    }

    override fun setupProjectLayout() {
        fixPath()
        super.setupProjectLayout()
    }

    override fun getProjectName(): String {
        fixPath()
        return super.getProjectName()
    }
}
