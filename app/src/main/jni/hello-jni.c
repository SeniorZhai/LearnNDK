#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, "jni_log", __VA_ARGS__))

JNIEXPORT void JNICALL
Java_com_seniorzhai_learnndk_MainActivity_dataType(JNIEnv *env, jobject instance, jshort s, jint i,
                                                   jlong l, jfloat f, jdouble d, jchar c,
                                                   jstring str_, jboolean bool, jbyte b,
                                                   jobject obj, jintArray arr_) {
    const char *str = (*env)->GetStringUTFChars(env, str_, 0);
    jint *arr = (*env)->GetIntArrayElements(env, arr_, NULL);

    LOGV("------------");
    LOGV("short=%hd", s);
    LOGV("int=%d", i);
    LOGV("long=%ld", l);
    LOGV("float=%f", f);
    LOGV("double=%lf", d);
    LOGV("boolean=%s", bool ? "true" : "false");
    LOGV("char=%c", c);
    LOGV("byte=%d", b);
    LOGV("String=%s", str);
    LOGV("------------");

    (*env)->ReleaseStringUTFChars(env, str_, str);
    (*env)->ReleaseIntArrayElements(env, arr_, arr, 0);
}