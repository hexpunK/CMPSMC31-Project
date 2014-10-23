/*
 * BinaryExpressionTree.h
 *
 *  Created on: 22 Oct 2014
 *      Author: Jordan
 */

#ifndef BINARYEXPRESSIONTREE_H_
#define BINARYEXPRESSIONTREE_H_

#include "AbstractBinaryTree.h"
#include "Token.h"

namespace mathsthing {

class BinaryExpressionTree: public AbstractBinaryTree<Token, double> {
public:
	BinaryExpressionTree();

	BinaryExpressionTree(Token item);

	void addLeftTree(AbstractBinaryTree<Token, double> *tree);

	void addRightTree(AbstractBinaryTree<Token, double> *tree);

	bool insert(Token item);

	bool insert(AbstractBinaryTree<Token, double> *tree);

	bool contains(Token item);

	void remove(Token item);

	double preorder() { return 0.0; }

	double inorder() { return 0.0; }

	double postorder();

	~BinaryExpressionTree() {}
};

} /* namespace mathsthing */

#endif /* BINARYEXPRESSIONTREE_H_ */
