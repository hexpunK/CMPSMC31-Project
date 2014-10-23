/*
 * AbstractBinaryTree.h
 *
 *  Created on: 22 Oct 2014
 *      Author: Jordan
 */

#ifndef ABSTRACTBINARYTREE_H_
#define ABSTRACTBINARYTREE_H_

namespace mathsthing {

template <class E>
class AbstractBinaryTree {
protected:
	E item;
	AbstractBinaryTree *leftNode;
	AbstractBinaryTree *rightNode;

public:
	virtual void addLeftTree(AbstractBinaryTree *tree) = 0;

	virtual void addRightTree(AbstractBinaryTree *tree) = 0;

	virtual bool insert(E item) = 0;

	virtual bool insert(AbstractBinaryTree *tree) = 0;

	virtual bool contains(E item) = 0;

	virtual bool empty()
	{
		if (leftNode != nullptr) {
			return leftNode->empty();
		}

		if (rightNode != nullptr) {
			return rightNode->empty();
		}

		return &item != nullptr;
	}

	virtual void remove(E item) = 0;

	virtual unsigned int size()
	{
		unsigned int leftSize = 0;
		unsigned int rightSize = 0;

		if (leftNode == nullptr && rightNode == nullptr)
			return 1;
		else {
			if (leftNode != nullptr) {
				leftSize = leftNode->size() + 1;
			}
			if (rightNode != nullptr) {
				rightSize = rightNode->size() + 1;
			}

			if (leftSize > rightSize) {
				return leftSize;
			} else {
				return rightSize;
			}
		}
	}

	virtual ~AbstractBinaryTree()
	{
		if (leftNode != nullptr)
			delete leftNode;
		if (rightNode != nullptr)
			delete rightNode;
	}
};
}

#endif /* ABSTRACTBINARYTREE_H_ */
