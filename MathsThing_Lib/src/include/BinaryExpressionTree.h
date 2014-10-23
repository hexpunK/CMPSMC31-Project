/*
 * BinaryExpressionTree.h
 *
 *  Created on: 22 Oct 2014
 *      Author: Jordan
 */

#ifndef BINARYEXPRESSIONTREE_H_
#define BINARYEXPRESSIONTREE_H_

#include "AbstractBinaryTree.h"

#include <string>

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

class BinaryExpressionTree: public AbstractBinaryTree<tstring*> {
public:
	BinaryExpressionTree();

	BinaryExpressionTree(tstring* item);

	void addLeftTree(AbstractBinaryTree<tstring*> *tree);

	void addRightTree(AbstractBinaryTree<tstring*> *tree);

	bool insert(tstring* item);

	bool insert(AbstractBinaryTree<tstring*> *tree);

	bool contains(tstring* item);

	void remove(tstring* item);

	~BinaryExpressionTree() {}
};

} /* namespace mathsthing */

#endif /* BINARYEXPRESSIONTREE_H_ */
