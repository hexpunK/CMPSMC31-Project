#include "include/uk_ac_uea_mathsthing_NativeParser.h"
#include "include/JNIUtils.h"
#include "include/NativeParser.h"
#include <iostream>

JNIEXPORT jlong JNICALL Java_uk_ac_uea_mathsthing_NativeParser_create
	(JNIEnv *env, jobject thisObj)
{
#ifdef _DEBUG
	tout << "Creating NativeParser" << std::endl;
#endif
	// Check for an existing parser.
	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	// Create a new NativeParser if there isn't one stored in the Java class.
	if (parser == nullptr) {
#ifdef _DEBUG
	tout << "New NativeParser needed" << std::endl;
#endif
		parser = new mathsthing::NativeParser;
	}
	if (parser == nullptr)
		return mathsthing::ThrowNullPointerException(env, "Could not locate NativeParser");

	jlong address = (jlong)&parser;
#ifdef _DEBUG
	tout << "Parser located at address:" << std::hex << address << std::endl;
#endif

	return address; // Return the pointer address.
}

JNIEXPORT void JNICALL Java_uk_ac_uea_mathsthing_NativeParser_setFormulaNative
	(JNIEnv *env, jobject thisObj, jint size, jobjectArray tokens)
{
	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	if (parser == nullptr) {
		mathsthing::ThrowNullPointerException(env, "Could not locate NativeParser");
		return;
	}

	std::vector<tchar*> strings;
	unsigned int sz = size;
	for (unsigned int i = 0; i < sz; i++) {
		jstring token = (jstring)env->GetObjectArrayElement(tokens, i);
		const tchar *tokenChars = (tchar*)env->GetStringUTFChars(token, JNI_FALSE);
		strings.push_back(const_cast<tchar*>(tokenChars));
		env->ReleaseStringUTFChars(token, tokenChars);
	}

	parser->setFormula(strings);
}

JNIEXPORT jdouble JNICALL Java_uk_ac_uea_mathsthing_NativeParser_getResultNative
	(JNIEnv* env, jobject thisObj, jobjectArray keys, jdoubleArray values)
{
	jint arraySz = env->GetArrayLength(keys);
	jdouble *valArr = env->GetDoubleArrayElements(values, JNI_FALSE);
	std::map<tchar *, double> params;

	for (int i = 0; i < arraySz; i++) {
		jstring jKey = (jstring)env->GetObjectArrayElement(keys, i);

		const tchar *key = (tchar*)env->GetStringUTFChars(jKey, JNI_FALSE);
		double value = valArr[i];
		env->ReleaseStringUTFChars(jKey, key);

#ifdef _DEBUG
		tout << "Param pair: " << key << "-" << value << std::endl;
#endif
		params.insert(std::pair<tchar*, double>(const_cast<tchar*>(key), value));
	}

	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	if (parser == nullptr) {
		return mathsthing::ThrowNullPointerException(env, "Could not locate NativeParser");
	}

	return parser->getResult(params);
}

JNIEXPORT jstring JNICALL Java_uk_ac_uea_mathsthing_NativeParser_getFirstDerivativeNative
	(JNIEnv *env, jobject thisObj)
{
#ifdef _DEBUG
		tout << "Calculating derivative." << std::endl;
#endif
	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	if (parser == nullptr)
		mathsthing::ThrowNullPointerException(env, "Could not locate NativeParser");
		return env->NewStringUTF("");

	return env->NewStringUTF(parser->getDerivative());
}

JNIEXPORT void JNICALL Java_uk_ac_uea_mathsthing_NativeParser_destroy
	(JNIEnv *env, jobject thisObj)
{
#ifdef _DEBUG
	tout << "Cleaning up NativeParser" << std::endl;
#endif
		/*
	mathsthing::NativeParser *parser = mathsthing::GetPointer<mathsthing::NativeParser>(env, thisObj);
	/if (parser != nullptr)
		delete parser; // This segfaults at the moment.
		*/
}
