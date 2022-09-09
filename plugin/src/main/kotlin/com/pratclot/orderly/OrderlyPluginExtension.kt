package com.pratclot.orderly

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.repositories.ArtifactRepository
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

const val EXTENSION_NAME = "orderly"
const val DEFAULT_PACKAGE_NAME = "com.demo"
const val DEFAULT_DIR_NAME_API = "api"
const val DEFAULT_DIR_NAME_API_COMMON = "common"
const val DEFAULT_DIR_NAME_API_COMMON_LIVE = "common-live"
const val DEFAULT_DIR_NAME_DOMAIN = "domain"
const val DEFAULT_DIR_NAME_DTO = "dto"
const val DEFAULT_DIR_NAME_COMMON = "common"
const val DEFAULT_DIR_NAME_COMMON_ANDROID = "android"
const val DEFAULT_DIR_NAME_COMMON_ANDROID_TEST = "androidtest"
const val DEFAULT_DIR_NAME_COMMON_KOTLIN = "kotlin"
const val DEFAULT_DIR_NAME_COMMON_TEST = "test"
const val DEFAULT_FEATURE_NAME = "feature1"
const val DEFAULT_PLUGINS = "kotlin-kapt"

abstract class OrderlyPluginExtension {

    abstract val packageName: Property<String>
    abstract val dirNameApi: Property<String>
    abstract val dirNameApiCommon: Property<String>
    abstract val dirNameApiCommonLive: Property<String>
    abstract val dirNameDomain: Property<String>
    abstract val dirNameDto: Property<String>
    abstract val dirNameCommon: Property<String>
    abstract val dirNameCommonKotlin: Property<String>
    abstract val dirNameCommonAndroid: Property<String>

    abstract val features: ListProperty<String>

    /**
     *
     */
    abstract val repositories: ListProperty<ArtifactRepository>

    /**
     * Allows to configure all subprojects with certain plugins.
     */
    abstract val commonPlugins: ListProperty<String>

    /**
     *
     */
//    abstract val dependencies: MapProperty<Dependencies, DependencyHandler.() -> Unit>

    /**
     * Does not work due to `DependencyHandlerExtensions` being unavailable for the root buildscript.
     */
    abstract val dependenciesApiCommonLive: Property<DependencyHandler.() -> Unit>

    /**
     * A list of strings is used to help with the above issue. This is a workaround, hopefully there is a way to make the `DependencyHandler` work.
     */
    abstract val dependenciesApiCommonLiveStrings: ListProperty<String>

    /**
     *
     */
    abstract val testInstrumentationRunner: Property<String>

    init {
        packageName.convention(DEFAULT_PACKAGE_NAME)
        dirNameApi.convention(DEFAULT_DIR_NAME_API)
        dirNameApiCommon.convention(DEFAULT_DIR_NAME_API_COMMON)
        dirNameApiCommonLive.convention(DEFAULT_DIR_NAME_API_COMMON_LIVE)
        dirNameDomain.convention(DEFAULT_DIR_NAME_DOMAIN)
        dirNameDto.convention(DEFAULT_DIR_NAME_DTO)
        dirNameCommon.convention(DEFAULT_DIR_NAME_COMMON)
        dirNameCommonKotlin.convention(DEFAULT_DIR_NAME_COMMON_KOTLIN)
        dirNameCommonAndroid.convention(DEFAULT_DIR_NAME_COMMON_ANDROID)

        features.convention(listOf(DEFAULT_FEATURE_NAME))

        commonPlugins.convention(listOf(DEFAULT_PLUGINS))

        dependenciesApiCommonLiveStrings.convention(
            listOf(
                "com.squareup.retrofit2:retrofit:2.9.0",
                "com.squareup.okhttp3:logging-interceptor:4.10.0",
                "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0",
            )
        )

        testInstrumentationRunner.convention("androidx.test.runner.AndroidJUnitRunner")
    }
}


fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)
