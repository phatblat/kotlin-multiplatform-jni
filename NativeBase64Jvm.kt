expect internal fun loadBase64Native()

// Here loadBase64Native simply calls System.loadLibrary("base64_jni") on Android,
// or properly extract and calls System.load(extractedLibraryPath) on desktop JVMs.

class NativeBase64Jvm : NativeBase64 {
    init {
        loadBase64Native()
    }

    external override fun encode(bytes: ByteArray, url: Boolean): String
    external override fun decode(b64: String): ByteArray
}

actual fun getCppNativeBase64(): NativeBase64 = NativeBase64Jvm()
