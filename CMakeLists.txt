cmake_minimum_required(VERSION 3.4.1)
project(CPP_Base64_JNI)

if(not defined TARGET)
  if(not defined ANDROID_ABI)
    message(FATAL_ERROR "Please define TARGET.")
  endif()
  set(TARGET, "android-$ANDROID_ABI")
endif()

find_package(JNI REQUIRED)

include_directories(${JNI_INCLUDE_DIRS})
include_directories(../cpp_native/src)

add_library(cpp_base64_jni SHARED src/base64_jni.cpp)

target_link_libraries(cpp_base64_jni ../cpp_native/build/${TARGET}/libcpp_base64.a)
