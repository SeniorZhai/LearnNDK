#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, "jni_log", __VA_ARGS__))

JNIEXPORT void JNICALL
Java_com_seniorzhai_learnndk_MainActivity_call(JNIEnv *env, jclass type) {

    jclass subclazz;
    jclass supclazz;
    jmethodID mid_sub_init;
    jmethodID mid_run;

    jobject obj_sub;

    subclazz = (*env)->FindClass(env, "com/seniorzhai/learnndk/SubClass");
    if (subclazz == NULL) {
        LOGV("未找到SubClass");
        return;
    }
    mid_sub_init = (*env)->GetMethodID(env, subclazz, "<init>", "()V");
    if (mid_sub_init == NULL) {
        LOGV("未找到SubClass的构造方法");
        return;
    }

    obj_sub = (*env)->NewObject(env, subclazz, mid_sub_init);
    if (obj_sub == NULL) {
        return;
    }

    supclazz = (*env)->FindClass(env, "com/seniorzhai/learnndk/SupClass");
    if (supclazz == NULL) {
        LOGV("未找到SupClass");
        return;
    }
    mid_run = (*env)->GetMethodID(env, supclazz, "run", "()V");
    if (mid_run == NULL) {
        LOGV("未找到run方法");
        return;
    }

    (*env)->CallNonvirtualVoidMethod(env, obj_sub, supclazz, mid_run);

    (*env)->DeleteLocalRef(env, subclazz);
    (*env)->DeleteLocalRef(env, supclazz);
    (*env)->DeleteLocalRef(env, obj_sub);
    
}