android {
    defaultConfig {
        externalNativeBuild {
            cmake {
                arguments.addAll(listOf(
                        "-DJAVA_AWT_LIBRARY=NotNeeded",
                        "-DJAVA_JVM_LIBRARY=NotNeeded",
                        "-DJAVA_INCLUDE_PATH2=NotNeeded",
                        "-DJAVA_AWT_INCLUDE_PATH=NotNeeded"
                ))
            }
        }
    }
    externalNativeBuild {
        cmake {
            setPath("$rootDir/cpp_jni/CMakeLists.txt")
        }
    }
}

afterEvaluate {
    tasks.withType<AndroidUnitTest>().all { enabled = false }
}
