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

#ifdef _UNICODE
#define tstring std::wstring
#define tchar wchar_t
#define _T(x) L#x
#else
#define tstring std::string
#define tchar char
#define _T(x) #x
#endif

namespace mathsthing {

template<class T>
T* GetPointer(JNIEnv *env, jobject thisObj)
{
#ifdef _DEBUG
	std::cout << "Type: " << typeid(T).name() << std::endl;
#endif
	jclass thisCls = env->GetObjectClass(thisObj);
	jfieldID fieldID = env->GetFieldID(thisCls, "ptr", "J");
#ifdef _DEBUG
	std::cout << "FieldID: " << fieldID << std::endl;
#endif
	if (fieldID == 0) return 0;

	jlong ptrVal = env->GetLongField(thisObj, fieldID);
#ifdef _DEBUG
	if (ptrVal == 0) {
		std::cout << "Null pointer" << std::endl;
		return 0;
	} else {
		std::cout << "Pointer to: " << ptrVal << std::endl;
	}
#endif
	void* ptr = (void*)ptrVal;
	T* inst = reinterpret_cast<T*>(ptr);
	return inst;
}
/*
tstring JStringToString(JNIEnv *env, jstring str)
{
	std::cout << "Converting string" << std::endl;
	tstring out;

	const jchar* jChars = env->GetStringChars(str, false);
	jsize strLen = env->GetStringLength(str);
	const jchar* tmp = jChars;

	out.assign(jChars, jChars + strLen);

	env->ReleaseStringChars(str, jChars);
	return out;
}
*/
}

#endif /* JNIUTILS_H_ */
