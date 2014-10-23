/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class uk_ac_uea_mathsthing_NativeParser */

#ifndef _Included_uk_ac_uea_mathsthing_NativeParser
#define _Included_uk_ac_uea_mathsthing_NativeParser

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     uk_ac_uea_mathsthing_NativeParser
 * Method:    create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_uk_ac_uea_mathsthing_NativeParser_create
  (JNIEnv *, jobject);

/*
 * Class:     uk_ac_uea_mathsthing_NativeParser
 * Method:    setFormulaNative
 * Signature: (I[Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_uk_ac_uea_mathsthing_NativeParser_setFormulaNative
  (JNIEnv *, jobject, jint, jobjectArray);

/*
 * Class:     uk_ac_uea_mathsthing_NativeParser
 * Method:    getResultNative
 * Signature: ([Ljava/lang/String;[D)D
 */
JNIEXPORT jdouble JNICALL Java_uk_ac_uea_mathsthing_NativeParser_getResultNative
  (JNIEnv *, jobject, jobjectArray, jdoubleArray);

/*
 * Class:     uk_ac_uea_mathsthing_NativeParser
 * Method:    getFirstDerivativeNative
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_uk_ac_uea_mathsthing_NativeParser_getFirstDerivativeNative
  (JNIEnv *, jobject);

/*
 * Class:     uk_ac_uea_mathsthing_NativeParser
 * Method:    destroy
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_uk_ac_uea_mathsthing_NativeParser_destroy
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
