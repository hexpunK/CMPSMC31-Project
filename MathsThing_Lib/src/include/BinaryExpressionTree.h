/*
 * BinaryExpressionTree.h
 *
 *  Created on: 22 Oct 2014
 *      Author: Jordan
 */

#ifndef BINARYEXPRESSIONTREE_H_
#define BINARYEXPRESSIONTREE_H_

#include "AbstractBinaryTree.h"
#include "CoreDefines.h"

#include <string>

namespace mathsthing {

class BinaryExpressionTree: public AbstractBinaryTree<tchar*> {
public:
	BinaryExpressionTree();

	BinaryExpressionTree(tchar *item);

	void addLeftTree(AbstractBinaryTree<tchar*> *tree);

	void addRightTree(AbstractBinaryTree<tchar*> *tree);

	bool insert(tchar* item);

	bool insert(AbstractBinaryTree<tchar*> *tree);

	bool contains(tchar *item);

	void remove(tchar *item);

	~BinaryExpressionTree() {}
};

} /* namespace mathsthing */

#endif /* BINARYEXPRESSIONTREE_H_ */
