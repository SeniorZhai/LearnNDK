#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, "jni_log", __VA_ARGS__))

JNIEXPORT void JNICALL
Java_com_seniorzhai_learnndk_MainActivity_instanceField(JNIEnv *env, jclass type, jobject job) {

    jclass clzz;
    jfieldID fid;
    jstring j_str;
    jstring j_newStr;
    const char *c_str = NULL;

    clzz = (*env)->GetObjectClass(env, job);
    if (clzz == NULL) {
        return;
    }

    fid = (*env)->GetFieldID(env, clzz, "name", "Ljava/lang/String;");
    if (fid == NULL) {
        return;
    }

    j_str = (jstring) (*env)->GetObjectField(env, job, fid);
    c_str = (*env)->GetStringUTFChars(env, j_str, NULL);
    if (c_str == NULL) {
        return;
    }
    LOGV("%s", c_str);

    (*env)->ReleaseStringUTFChars(env, j_str, c_str);

    j_newStr = (*env)->NewStringUTF(env, "This is C string");
    if (j_newStr == NULL) {
        return;
    }

    (*env)->SetObjectField(env, job, fid, j_newStr);

    (*env)->DeleteLocalRef(env, clzz);
    (*env)->DeleteLocalRef(env, j_str);
    (*env)->DeleteLocalRef(env, j_newStr);

}

JNIEXPORT void JNICALL
Java_com_seniorzhai_learnndk_MainActivity_staticField(JNIEnv *env, jclass type) {

    jclass clazz;
    jfieldID fid;
    jint num;

    clazz = (*env)->FindClass(env, "com/seniorzhai/learnndk/Student");

    if (clazz == NULL) {
        return;
    }
    fid = (*env)->GetStaticFieldID(env, clazz, "num", "I");
    if (fid == NULL) {
        return;
    }
    num = (*env)->GetStaticIntField(env, clazz, fid);
    LOGV("%d", num);

    (*env)->SetStaticIntField(env, clazz, fid, 100);

    (*env)->DeleteLocalRef(env, clazz);
}