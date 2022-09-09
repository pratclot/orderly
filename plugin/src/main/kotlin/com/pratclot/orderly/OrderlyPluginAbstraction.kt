package com.pratclot.orderly

import org.gradle.api.Project

interface OrderlyPluginAbstraction {
    var project: Project
    var extension: OrderlyPluginExtension

}
