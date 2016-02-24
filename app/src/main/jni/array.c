#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, "jni_log", __VA_ARGS__))

JNIEXPORT jint JNICALL
Java_com_seniorzhai_learnndk_MainActivity_sum(JNIEnv *env, jobject instance, jintArray j_array) {
    int i = 0;
    jint sum = 0;
    jint *array = (*env)->GetIntArrayElements(env, j_array, NULL);
    jint arr_len = (*env)->GetArrayLength(env, j_array);
    for (i = 0; i < arr_len; i++) {
        sum += array[i];
    }

    LOGV("sum=%d", sum);
    (*env)->ReleaseIntArrayElements(env, j_array, array, 0);

    return sum;
}

JNIEXPORT jint JNICALL
Java_com_seniorzhai_learnndk_MainActivity_max(JNIEnv *env, jobject instance, jintArray array_) {
    jint i = 0, max;

    jint arr_len = (*env)->GetArrayLength(env, array_);

    jint *c_array = (jint *) malloc(sizeof(jint) * arr_len);
    // 初始化缓冲区
    memset(c_array, 0, sizeof(jint) * arr_len);
    // 拷贝Java数组中的所有元素到缓冲区
    (*env)->GetIntArrayRegion(env, array_, 0, arr_len, c_array);
    for (i = 0; i < arr_len; ++i) {
        if (c_array[i] > max) {
            max = c_array[i];
        }
    }
    free(c_array);
    return max;
}

JNIEXPORT jint JNICALL
Java_com_seniorzhai_learnndk_MainActivity_min(JNIEnv *env, jobject instance, jintArray array_) {
    jint i, min = 0;
    jint arr_len;
    // 第三个参数表示返回的数组指针是原始数组还是拷贝原始数据到临时缓冲区的指针
    // JNI_TRUE表示临时缓冲区指针 JNI_FALSE表示原始数组指针
    jint *c_array = (*env)->GetIntArrayElements(env, array_, JNI_FALSE);
    if (c_array == NULL) {
        return 0;
    }
    arr_len = (*env)->GetArrayLength(env, array_);
    for (i = 0; i < arr_len; ++i) {
        if (min > c_array[i]) {
            min = c_array[i];
        }
    }
    (*env)->ReleaseIntArrayElements(env, array_, c_array, 0);
    return min;
}

JNIEXPORT jint JNICALL
Java_com_seniorzhai_learnndk_MainActivity_multiply(JNIEnv *env, jobject instance,

                                                   jintArray array_) {
    jboolean isCopy;
    jint i;
    jint result = 1;
    jint arr_len = 0;
    // GetPrimitiveArrayCritical/ReleasePrimitiveArrayCritical会阻塞GC，保证自己不被阻塞
    jint *array = (*env)->GetPrimitiveArrayCritical(env, array_, &isCopy);
    LOGV("isCopy:%s", isCopy ? "true" : "false");
    if (array == NULL) {
        return 0;
    }
    arr_len = (*env)->GetArrayLength(env, array_);
    for (i = 0; i < arr_len; ++i) {
        result *= array[i];
    }
    (*env)->ReleasePrimitiveArrayCritical(env, array_, array, 0);
    return result;
}