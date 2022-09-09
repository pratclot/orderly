package com.pratclot.orderly.tools

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val TEST_LINE = "test text"

class ToolsTest {

    @field:TempDir
    lateinit var tempDir: File

    @Test
    fun `linkSubproject does not add duplicate lines`() {
        tempDir.resolve("orderly.gradle").let { file ->
            file.createNewFile()
            repeat(2) { linkSubproject(":subproject1", file) }

            assertEquals(1, file.readLines().size)
        }
    }

    @Test
    fun `hasNoLine correctly detects lines in files`() {
        tempDir.resolve("file.tmp").run {
            createNewFile()
            assertTrue { hasNoLine(TEST_LINE) }
            appendText(TEST_LINE)
            assertFalse { hasNoLine(TEST_LINE) }
        }
    }
}
