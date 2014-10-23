/*
 * Token.h
 *
 *  Created on: 23 Oct 2014
 *      Author: Jordan
 */

#ifndef TOKEN_H_
#define TOKEN_H_

#include "CoreDefines.h"

namespace mathsthing {

enum TokenType {
	EMPTY,
	OPERATOR,
	OPERAND,
	CONSTANT,
	FUNCTION
};

struct Token {
	tchar* value;
	TokenType type;
};

inline bool operator==(const Token &left, const Token &right)
{
	return (left.type == right.type) && (left.value == right.value);
}

inline bool operator!=(const Token &left, const Token &right) { return !(left==right); }

} /* namespace mathsthing */

#endif /* TOKEN_H_ */
