/*
 * NativeParser.cpp
 *
 *  Created on: 15 Oct 2014
 *      Author: Jordan
 */

#include "include/NativeParser.h"

namespace mathsthing {

void NativeParser::setFormula(unsigned int tokenCount, tchar **forumla)
{

}

double NativeParser::getResult(std::map<tchar*, double> params)
{
	return 0.0;
}

tchar* NativeParser::getDerivative()
{
	return _T("ffff");
}

NativeParser::~NativeParser()
{

}

} /* namespace mathsthing */
