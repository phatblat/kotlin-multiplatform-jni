kotlin {
    jvm {
        val processResources = compilations["main"].processResourcesTaskName
        (tasks[processResources] as ProcessResources).apply {
            // A task that build the JNI shared library for all targets and
            // copy each outputs into $buildDir/resources-jni
            dependsOn("buildJniNative")
            from("$buildDir/resources-jni")
        }
    }
}

tasks.withType<KotlinJvmTest> {
    dependsOn("buildJniNative")
    systemProperty(
        "java.library.path",
        rootDir.resolve(
            "cpp_jni/build/cmake/out/jni-${currentOs.familyName}/lib"
        ).absolutePath
    )
}
