/*
 * NativeParser.h
 *
 *  Created on: 15 Oct 2014
 *      Author: Jordan
 */

#ifndef NATIVEPARSER_H_
#define NATIVEPARSER_H_

#include <vector>
#include <map>
#include <stack>
#include <iostream>

#if defined(_WIN32) || defined(WIN32) || defined(__CYGWIN__) || defined(__MINGW32__) || defined(__BORLANDC__)
#include <win32\jni_md.h>
#elif __APPLE__
#include <apple\jni_md.h>
#elif __linux__
#include <linux/jni_md.h>
#endif

#include "BinaryExpressionTree.h"
#include "uk_ac_uea_mathsthing_NativeParser.h"
#include "CoreDefines.h"

namespace mathsthing {

class NativeParser {
private:
	tchar *formula;
	std::stack<tchar*> postFix, inFix;
	std::vector<tchar*> tokens;
	BinaryExpressionTree evalTree;

public:
	void setFormula(unsigned int tokenCount, tchar *formula);

	double getResult(std::map<tchar*, double> params);

	tchar* getDerivative();

	~NativeParser();
};

} /* namespace mathsthing */

#endif /* NATIVEPARSER_H_ */
