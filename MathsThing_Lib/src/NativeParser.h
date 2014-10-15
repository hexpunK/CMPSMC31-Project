/*
 * NativeParser.h
 *
 *  Created on: 15 Oct 2014
 *      Author: Jordan
 */

#ifndef NATIVEPARSER_H_
#define NATIVEPARSER_H_

#include <string>
#include <vector>

#if defined(_WIN32) || defined(WIN32) || defined(__CYGWIN__) || defined(__MINGW32__) || defined(__BORLANDC__)
#include <win32\jni_md.h>
#elif __APPLE__

#elif __linux__

#ifdef _UNICODE
#define tstring std::wstring
#else
#define tstring std::string
#endif

namespace mathsthing {

class NativeParser {
private:
	std::vector<tstring> toekns;
public:
	NativeParser();
};

} /* namespace mathsthing */

#endif /* NATIVEPARSER_H_ */
