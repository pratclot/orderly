package com.pratclot.orderly.tools

import java.io.File

/**
 * A separate plugin-level class helps to easily access plugin-level resources via a class loader.
 */
object ResourceLoader {

    fun copyFileFromResources(
        pathToResource: String,
        file: File,
        modifier: String.() -> String = { this },
    ) {
        javaClass.classLoader.getResourceAsStream(pathToResource).bufferedReader().use { reader ->
            file.writer()
                .use { writer ->
                    var line = reader.readLine()?.modifier()
                    while (line != null) {
                        writer.write(line)
                        writer.write("\n")
                        line = reader.readLine()?.modifier()
                    }
                }
        }
    }
}
