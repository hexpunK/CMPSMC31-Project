/*
 * JNIUtils.h
 *
 *  Created on: 23 Oct 2014
 *      Author: Jordan
 */

#ifndef JNIUTILS_H_
#define JNIUTILS_H_

#include <jni.h>
#include <iostream>
#include <typeinfo>

#if defined(_WIN32) || defined(WIN32) || defined(__CYGWIN__) || defined(__MINGW32__) || defined(__BORLANDC__)
#include <win32\jni_md.h>
#elif __APPLE__
#include <apple/jni_md.h>
#elif __linux__
#include <linux/jni_md.h>
#endif

#include "CoreDefines.h"
#include "NativeParser.h"

namespace mathsthing {

/*
 * Gets the current JVM Environment for when it is needed outside of JNI header
 *  methods.
 * Returns:
 * 	JNIEnv* - A pointer to the Java Environment.
 */
JNIEnv* GetEnv();

/*
 * Gets the pointer for an object pointed to by the calling Java class. This
 * requires that the Java class has a long field named "ptr" to read from.
 * Returns:
 * 	NativeParser* - A pointer to a new or found NativeParser instance.
 */
NativeParser* GetPointer(JNIEnv *env, jobject thisObj);

jint ThrowNoClassDefException(JNIEnv *env, tchar* msg);

jint ThrowException(JNIEnv *env, tchar* msg);

jint ThrowNullPointerException(JNIEnv *env, tchar* msg);

jint ThrowFormulaException(JNIEnv *env, tchar* msg);

}

#endif /* JNIUTILS_H_ */
