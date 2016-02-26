#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, "jni_log", __VA_ARGS__))

JNIEXPORT void JNICALL
Java_com_seniorzhai_learnndk_MainActivity_callJavaStaticMethod(JNIEnv *env, jclass type) {
    jclass clazz = NULL;
    jstring jstr = NULL;
    jmethodID mid_static = NULL;
    // 查找类
    clazz = (*env)->FindClass(env, "com/seniorzhai/learnndk/ClassMethod");
    if (clazz == NULL) {
        LOGV("找不到这个类");
        return;
    }

    mid_static = (*env)->GetStaticMethodID(env, clazz, "callStaticMethod", "(Ljava/lang/String;)V");
    if (mid_static == NULL) {
        LOGV("找不到callStaticMethod方法");
        return;
    }
    jstr = (*env)->NewStringUTF(env, "我是静态方法");
    (*env)->CallStaticVoidMethod(env, clazz, mid_static, jstr);

    (*env)->DeleteLocalRef(env, clazz);
    (*env)->DeleteLocalRef(env, jstr);
}

JNIEXPORT void JNICALL
Java_com_seniorzhai_learnndk_MainActivity_callJavaInstanceMethod(JNIEnv *env, jclass type) {
    jclass clazz = NULL;
    jmethodID mid_construct = NULL;
    jmethodID mid_instance = NULL;
    jobject jobject;
    jstring jstr;
    clazz = (*env)->FindClass(env, "com/seniorzhai/learnndk/ClassMethod");
    if (clazz == NULL) {
        LOGV("找不到这个类");
        return;
    }
    mid_construct = (*env)->GetMethodID(env, clazz, "<init>", "()V");
    if (mid_construct == NULL) {
        LOGV("找不到默认的构造方法");
        return;
    }

    /*
     *  方法签名：
     *      格式(形参参数类型列表)返回值
     *      引用类型用L开头后面紧跟类的全路径名
     *  示例：
     *      String f() -> ()Ljava/lang/String;
     *      long f(int i,Class c) -> (ILjava/lang/Class;)J
     *      String(byte[] bytes) -> ([B)V
     */
    /*
     *  参数类型：
     *      boolean -> Z
     *      byte    -> B
     *      char    -> C
     *      short   -> S
     *      int     -> I
     *      long    -> J
     *      float   -> F
     *      double  -> D
     */
    mid_instance = (*env)->GetMethodID(env, clazz, "callInstanceMethod", "(Ljava/lang/String;I)V");
    if (mid_instance == NULL) {
        LOGV("找不到callInstanceMethod方法");
        return;
    }
    // 创建类实例
    jobject = (*env)->NewObject(env, clazz, mid_construct);
    if (jobject == NULL) {
        LOGV("创建不成功");
        return;
    }
    jstr = (*env)->NewStringUTF(env, "我是实例方法");
    (*env)->CallVoidMethod(env, jobject, mid_instance, jstr, 20);

    (*env)->DeleteLocalRef(env, clazz);
    (*env)->DeleteLocalRef(env, jobject);
    (*env)->DeleteLocalRef(env, jstr);

}