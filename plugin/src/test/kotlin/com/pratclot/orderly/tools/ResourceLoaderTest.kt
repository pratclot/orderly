package com.pratclot.orderly.tools

import com.pratclot.orderly.BUILD_FILE_NAME
import org.gradle.internal.impldep.org.junit.After
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertTrue

internal class ResourceLoaderTest {

    private val resourceLoader = ResourceLoader

    @field:TempDir
    lateinit var temporaryFolder: File

    @BeforeEach
    fun setup() {
//        temporaryFolder = TemporaryFolder()
//        temporaryFolder.create()
    }

    @After
    fun cleanup() {
        temporaryFolder.delete()
    }

    @Test
    fun `can load single resource`() {
        val file = temporaryFolder.resolve(BUILD_FILE_NAME)
        resourceLoader.copyFileFromResources(
            "$BUILD_FILE_NAME.tpl",
            file
        )
        val lines = file.readLines()
        assertTrue(lines.any { it.contains("id('com.pratclot.orderly')") })
    }
}
