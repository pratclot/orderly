//package com.pratclot.orderly
//
//import org.junit.Before
//import org.junit.Test
//import java.io.File
//import kotlin.test.assertContains
//
//class PreExistingEnvironmentTest : BaseTest() {
//
//
//    @Before
//    fun setup() {
//        before(false)
//    }
//
//    @Test
//    fun `plugin does not fail if api dir already exists`() {
//
//
//        File(project.rootDir, "api").mkdir()
//
//        project.plugins.apply("com.pratclot.orderly")
//
//        with(project) {
//            assertContains(rootDir.list(), "api")
//        }
//    }
//
//
//    @Test
//    fun `plugin does not fail if api dir contains common dir`() {
//
//        File(project.rootDir, "api").mkdir()
//        File(File(project.rootDir, "api").path, "common").mkdir()
//
//        project.plugins.apply("com.pratclot.orderly")
//
//        with(project) {
//            assertContains(File(project.rootDir, "api").list(), "common")
//        }
//    }
//}
