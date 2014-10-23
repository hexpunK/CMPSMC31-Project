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

#include "BinaryExpressionTree.h"
#include "CoreDefines.h"

namespace mathsthing {

class NativeParser {
private:

	tchar *formula;
	std::stack<tchar*> postFix, inFix;
	std::vector<tchar*> tokens;
	BinaryExpressionTree evalTree;

public:
	/*
	 * Sets the formula this parser will use to the specified tokens. Creates
	 * the BinaryExpressionTree required for processing the forumla.
	 * Params:
	 * 	std::vector<tchar*> - A vector of C-style strings. If _UNICODE is
	 * 		defined these will be wchar_t, otherwise these are just char.
	 */
	void setFormula(std::vector<tchar*> tokens);

	/*
	 * Gets the result of processing the stored formula with the parameter
	 * values subbed into the operands.
	 * Params:
	 * 	std::map<tchar*, double> - A mapping of a C-style string parameter
	 * 		name to a double value to replace it with during evaluation.
	 * Returns:
	 *  double - The result of evaulating this forumla.
	 */
	double getResult(std::map<tchar*, double> params);

	/*
	 * Calculates the derivative for the stored forumla.
	 * Returns:
	 * 	tchar* - A C-style string containing the derivative of this formula. If
	 * 		_UNICODE is defined these will be wchar_t, otherwise these are
	 * 		just char.
	 */
	tchar* getDerivative();

	/*
	 * Cleans up this NativeParser, releasing as many resources as possible.
	 */
	~NativeParser();
};

} /* namespace mathsthing */

#endif /* NATIVEPARSER_H_ */
