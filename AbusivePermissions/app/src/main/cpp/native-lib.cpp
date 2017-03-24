#include <jni.h>
#include <string>
#include <array>
#include <cstdio>
#include <iostream>
#include <memory>
#include <stdexcept>
#include <stdlib.h>

extern "C"
jstring
Java_lafferty_com_abusivepermissions_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
jstring
Java_lafferty_com_abusivepermissions_MainActivity_lsCall(
        JNIEnv* env,
jobject obj) {
    std::string hello = "Hello from C++";
    char cmd [] = "ls";

    std::array<char, 128> buffer;
    std::string result = "";
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    if (!pipe) throw std::runtime_error("popen() failed!");
    while (!feof(pipe.get())) {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
            result += buffer.data();
    }

    return env->NewStringUTF(result.c_str());
}


extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_lsCall(
        JNIEnv * env,
        jobject obj,
        jstring jinput) {
    std::string hello = "Hello from C++";
    const char * input = env->GetStringUTFChars(jinput, (jboolean *) JNI_FALSE);
    char cmd [] = "ls";

    std::array<char, 128> buffer;
    std::string result = "";
    std::shared_ptr<FILE> pipe(popen(input, "r"), pclose);

    if (!pipe) throw std::runtime_error("popen() failed!");
    while (!feof(pipe.get())) {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
            result += buffer.data();
    }

    if(input != NULL)
    {
        env->ReleaseStringUTFChars(jinput, input);
    }

    return env->NewStringUTF(result.c_str());
}
