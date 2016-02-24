#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, "jni_log", __VA_ARGS__))

JNIEXPORT jstring JNICALL
Java_com_seniorzhai_learnndk_MainActivity_handleString(JNIEnv *env, jobject instance,
                                                       jstring j_str) {
    const char *str = NULL;

    // TRUE表示返回原字符串的拷贝，FALSE表示返回元字符串的指针
    jboolean isCopy;

    /*  GetStringUTFChars(env,j_str,&isCopy)
     *  jsrting是指向JVM内部的字符串和C风格的字符串char*不同
     *  Java中默认使用Unicode编码，C则使用UTF编码，所以需要转换
     */
    str = (*env)->GetStringUTFChars(env, j_str, &isCopy);

    LOGV("isCopy:%s", isCopy ? "true" : "false");

    if (str == NULL) {
        return NULL;
    }
    // Unicode长度
    LOGV("String length:%d", (int) (*env)->GetStringLength(env, j_str));
    // UTF-8长度
    LOGV("String UTF length:%d", (int) (*env)->GetStringUTFLength(env, j_str));
    /*
     *  GetStringRegion/GetStringUTFRegion获取Unicode字符串指定范围的内容
     */
    jsize len = (*env)->GetStringLength(env, j_str);  // 获取unicode字符串的长度
    char buff[128] = "hello ";
    char *pBuff = buff + 6;
    // 将JVM中的字符串以utf-8编码拷入C缓冲区,该函数内部不会分配内存空间
    (*env)->GetStringUTFRegion(env, j_str, 0, len, pBuff);

    // 释放内存
    (*env)->ReleaseStringUTFChars(env, j_str, str);
    return (*env)->NewStringUTF(env, buff);
}

JNIEXPORT void JNICALL
Java_com_seniorzhai_learnndk_MainActivity_logStringCritical(JNIEnv *env, jobject instance,
                                                            jstring j_str) {
    /*
     *  GetStringCritical/ReleaseStringCritical之间不能调用任何会让线程阻塞或等待的函数
     *  GetStringCritical会获取一个指向JVM内部字符串的指针，获取这个指针会导致暂停GC线程
     *  适用于大文本
     */
    char buff[256];
    char *pBuff = buff;
    const jchar *c_str = NULL;
    c_str = (*env)->GetStringCritical(env, j_str, NULL);
    if (c_str == NULL) {
        return;
    }
    while (*c_str) {
        *pBuff++ = *c_str++;
    }
    LOGV("String Critical:%s", buff);
    (*env)->ReleaseStringCritical(env, j_str, c_str);
}