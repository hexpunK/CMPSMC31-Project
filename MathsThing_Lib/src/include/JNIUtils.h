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

#include "CoreDefines.h"

namespace mathsthing {

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
	if (fieldID == 0) return 0;

	jlong ptrVal = env->GetLongField(thisObj, fieldID);
#ifdef _DEBUG
	if (ptrVal == 0) {
		tout << "Null pointer" << std::endl;
		return 0;
	} else {
		tout << "Pointer to: " << ptrVal << std::endl;
	}
#endif
	void* ptr = (void*)ptrVal;
	T* inst = reinterpret_cast<T*>(ptr);
	return inst;
}

tstring JStringToString(JNIEnv *env, jstring str)
{
#ifdef _DEBUG
	tout << "Converting string" << std::endl;
#endif
	tstring out;

	const jchar* jChars = env->GetStringChars(str, JNI_FALSE);
	jsize strLen = env->GetStringLength(str);

	out.assign(jChars, jChars + strLen);

	env->ReleaseStringChars(str, jChars);
	return out;
}

}

#endif /* JNIUTILS_H_ */
