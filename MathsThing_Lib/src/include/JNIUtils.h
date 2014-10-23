/*
 * JNIUtils.h
 *
 *  Created on: 23 Oct 2014
 *      Author: Jordan
 */

#ifndef JNIUTILS_H_
#define JNIUTILS_H_

#include <jni.h>
#include <string>
#include <iostream>
#include <typeinfo>
#if defined(_WIN32) || defined(WIN32) || defined(__CYGWIN__) || defined(__MINGW32__) || defined(__BORLANDC__)
#include <win32/jni_md.h>
#elif __APPLE__
#include <apple/jni_md.h>
#elif __linux__
#include <linux/jni_md.h>
#endif

#include "CoreDefines.h"

namespace mathsthing {

/*
 * Gets the current JVM Environment for when it is needed outside of JNI header
 *  methods.
 * Returns:
 * 	JNIEnv* - A pointer to the Java Environment.
 */
JNIEnv* GetEnv()
{
	JNIEnv* env;
	JavaVM** vms;
	jsize vmCount;

	JNI_GetCreatedJavaVMs(vms, 0, &vmCount);
	vms = new JavaVM*[vmCount];
	JNI_GetCreatedJavaVMs(vms, vmCount, &vmCount);

	for (int i = 0; i < vmCount; i++) {
		int envStat = vms[i]->GetEnv((void**)env, JNI_VERSION_1_6);
		if (envStat == JNI_EDETACHED) {
#ifdef _DEBUG
			tout << "No Java VM attached." << std::endl;
#endif
			return nullptr;
		} else if (envStat == JNI_EVERSION) {
#ifdef _DEBUG
			tout << "Unsupported JNI version." << std::endl;
#endif
			return nullptr;
		}

		return env;
	}

	return nullptr;
}

/*
 * Gets the pointer for an object pointed to by the calling Java class. This
 * requires that the Java class has a long field named "ptr" to read from.
 * Type:
 * 	T - The class of the object to find a pointer for.
 * Returns:
 * 	T* - A pointer to the templated type.
 */
template<class T>
T* GetPointer(JNIEnv *env, jobject thisObj)
{
#ifdef _DEBUG
	tout << "Type: " << typeid(T).name() << std::endl;
#endif
	jclass thisCls = env->GetObjectClass(thisObj);
	jfieldID fieldID = env->GetFieldID(thisCls, "ptr", "J");
#ifdef _DEBUG
	tout << "FieldID: " << fieldID << std::endl;
#endif
	if (fieldID == nullptr) return 0;

	jlong ptrVal = env->GetLongField(thisObj, fieldID);
	if (ptrVal <= 0) {
#ifdef _DEBUG
		tout << "Null pointer" << std::endl;
#endif
		return 0;
	}
#ifdef _DEBUG
	else {
		tout << "Pointer to: " << std::hex << ptrVal << std::endl;
	}
#endif

	void* ptr = (void*)ptrVal;
	T* inst = reinterpret_cast<T*>(ptr);
	return inst;
}

jint ThrowNoClassDefException(JNIEnv *env, tchar* msg)
{
	jclass exClass;
	tchar* className = "java/lang/NoClassDefFoundError";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}

jint ThrowException(JNIEnv *env, tchar* msg)
{
	jclass exClass;
	tchar* className = "java/lang/Exception";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}

jint ThrowNullPointerException(JNIEnv *env, tchar* msg)
{
	jclass exClass;
	tchar* className = "java/lang/NullPointerException";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}

jint ThrowFormulaException(JNIEnv *env, tchar* msg)
{
	jclass exClass;
	tchar* className = "uk/ac/uea/mathsthing/util/FormulaException";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}

}

#endif /* JNIUTILS_H_ */
