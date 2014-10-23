#ifndef CORE_DEFINES_H
#define CORE_DEFINES_H

/*
 * Unicode wide character replacement macros.
 */
#ifdef _UNICODE
#define tstring std::wstring
#define tchar wchar_t
#define _T(x) #x
#define tout std::wcout
#else
#define tstring std::string
#define tchar char
#define _T(x) #x
#define tout std::cout
#endif

#endif
