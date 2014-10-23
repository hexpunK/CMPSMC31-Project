/*
 * JNIUtils.cpp
 *
 *  Created on: 23 Oct 2014
 *      Author: Jordan
 */

#include "include/JNIUtils.h"

namespace mathsthing {

JNIEnv* GetEnv() {
	JNIEnv* env = nullptr;
	JavaVM** vms = nullptr;
	jsize vmCount;

	JNI_GetCreatedJavaVMs(vms, 0, &vmCount);
	vms = new JavaVM*[vmCount];
	JNI_GetCreatedJavaVMs(vms, vmCount, &vmCount);

	for (int i = 0; i < vmCount; i++) {
		int envStat = vms[i]->GetEnv((void**) env, JNI_VERSION_1_6);
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

NativeParser* GetPointer(JNIEnv *env, jobject thisObj) {
#ifdef _DEBUG
	tout<< "Type: " << typeid(NativeParser).name() << std::endl;
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
		tout << "Pointer to: 0x" << std::hex << ptrVal << std::endl;
	}
#endif

	void* ptr = (void*)ptrVal;
	NativeParser* inst = reinterpret_cast<NativeParser*>(ptr);
	return inst;
}

jint ThrowNoClassDefException(JNIEnv *env, tchar* msg) {
	jclass exClass;
	tchar* className = "java/lang/NoClassDefFoundError";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}

jint ThrowException(JNIEnv *env, tchar* msg) {
	jclass exClass;
	tchar* className = "java/lang/Exception";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}

jint ThrowNullPointerException(JNIEnv *env, tchar* msg) {
	jclass exClass;
	tchar* className = "java/lang/NullPointerException";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}

jint ThrowFormulaException(JNIEnv *env, tchar* msg) {
	jclass exClass;
	tchar* className = "uk/ac/uea/mathsthing/util/FormulaException";

	exClass = env->FindClass(className);
	if (exClass == nullptr) {
		return ThrowNoClassDefException(env, msg);
	}

	return env->ThrowNew(exClass, msg);
}
}