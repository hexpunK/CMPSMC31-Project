#include "include/uk_ac_uea_mathsthing_NativeParser.h"
#include "include/NativeParser.h"
#include "include/JNIUtils.h"
#include <iostream>

JNIEXPORT jlong JNICALL Java_uk_ac_uea_mathsthing_NativeParser_create
	(JNIEnv *env, jobject thisObj)
{
#ifdef _DEBUG
		std::cout << "Creating NativeParser" << std::endl;
#endif
	// Check for an existing parser.
	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	// Create a new NativeParser if there isn't one stored in the Java class.
	if (parser == nullptr) {
#ifdef _DEBUG
		std::cout << "New NativeParser needed" << std::endl;
#endif
		parser = new mathsthing::NativeParser;
	}
	jlong address = (jlong)&parser;
#ifdef _DEBUG
	std::cout << "Parser located at address:" << std::hex << address << std::endl;
#endif

	return address; // Return the pointer address.
}

JNIEXPORT void JNICALL Java_uk_ac_uea_mathsthing_NativeParser_setFormulaNative
	(JNIEnv *env, jobject thisObj, jint size, jobjectArray tokens)
{
	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
}

JNIEXPORT jdouble JNICALL Java_uk_ac_uea_mathsthing_NativeParser_getResultNative
	(JNIEnv* env, jobject thisObj, jobjectArray keys, jdoubleArray values)
{
	jint arraySz = env->GetArrayLength(keys);
	jdouble *valArr = env->GetDoubleArrayElements(values, false);
	std::map<tchar*, double> params;

	for (int i = 0; i < arraySz; i++) {
		jstring jKey = (jstring)env->GetObjectArrayElement(keys, i);

		const tchar* key = (tchar*)env->GetStringUTFChars(jKey, false);
		//tstring key = mathsthing::JStringToString(env, jKey);
		double value = valArr[i];

#ifdef _DEBUG
		std::cout << "Param pair: " << key << "-" << value << std::endl;
#endif
		params.insert(std::pair<tchar*, double>(const_cast<tchar*>(key), value));
	}

	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	if (parser == nullptr) return -1;

	return parser->getResult(params);
}

JNIEXPORT jstring JNICALL Java_uk_ac_uea_mathsthing_NativeParser_getFirstDerivativeNative
	(JNIEnv *env, jobject thisObj)
{
	return env->NewStringUTF("Test");
}

JNIEXPORT void JNICALL Java_uk_ac_uea_mathsthing_NativeParser_destroy
	(JNIEnv *env, jobject thisObj)
{
#ifdef _DEBUG
		std::cout << "Cleaning up NativeParser" << std::endl;
#endif
	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	//delete parser;
}
