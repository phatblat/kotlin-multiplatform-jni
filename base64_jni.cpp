#define EXCEPTION_CLASS "org/example/nativeb64/NativeBase64$Error"

extern "C"
JNIEXPORT jstring JNICALL Java_org_example_nativeb64_cpp_NativeBase64Jvm_encode(
  JNIEnv *env, jobject, jbyteArray jBytes, jboolean url
) {
    // Length of the argument byte array.
    auto length = env->GetArrayLength(jBytes);
    // Maximum number of bytes the encoded result may contain.
    int resultMaxLen = base64_max_encoded_len(length);
    // Allocates result buffer, to be filled (+1 for terminating 0).
    auto resultChars = (char*) malloc(resultMaxLen * sizeof(char) + 1);

    // The bytes contained in the array (as a continuous memory).
    auto bytes = (jbyte*) env->GetPrimitiveArrayCritical(jBytes, nullptr);

    // Fill resultChars with the Base64 encoded value and set resultLen
    // to the actual number of bytes that was set in resultChars.
    int resultLen;
    char* error = base64_encode(
      (const char*)bytes, length, url,
      resultChars, resultMaxLen, &resultLen
    );

    // Release the continuous array of bytes (allowing the JVM to dispose it)
    env->ReleasePrimitiveArrayCritical(jBytes, bytes, JNI_ABORT);

    // If error is not null, it is a string defining the error,
    // so tell the JVM to throw an exception and return.
    if (error != nullptr) {
        env->ThrowNew(env->FindClass(EXCEPTION_CLASS), error);
        free(error);
        return nullptr;
    }

    // Transforms the result into a JVM String object.
    resultChars[resultLen] = 0;
    jstring jResult = env->NewStringUTF(resultChars);

    // Frees the C memory that is no longer needed
    free(resultChars);

    // Returns the JVM String result
    return jResult;
}
