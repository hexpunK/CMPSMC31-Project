/*
 * BinaryExpressionTree.cpp
 *
 *  Created on: 22 Oct 2014
 *      Author: Jordan
 */

#include "include/BinaryExpressionTree.h"
#include "include/AbstractBinaryTree.h"

namespace mathsthing {

BinaryExpressionTree::BinaryExpressionTree()
{
	this->item = nullptr;
	this->leftNode = nullptr;
	this->rightNode = nullptr;
}

BinaryExpressionTree::BinaryExpressionTree(tstring* item)
{
	this->item = item;
	this->leftNode = nullptr;
	this->rightNode = nullptr;
}

void BinaryExpressionTree::addLeftTree(AbstractBinaryTree<tstring*> *tree)
{
	if (leftNode == nullptr) {
		leftNode = tree;
		return;
	}

	leftNode->addLeftTree(tree);
}

void BinaryExpressionTree::addRightTree(AbstractBinaryTree<tstring*> *tree)
{
	if (rightNode == nullptr) {
			rightNode = tree;
			return;
		}

	rightNode->addLeftTree(tree);
}

bool BinaryExpressionTree::insert(tstring* item)
{
	if (&this->item == nullptr) {
		this->item = item;
		return true;
	} else if (leftNode == nullptr) {
		leftNode = new BinaryExpressionTree(item);
		return true;
	} else if (rightNode == nullptr) {
		rightNode = new BinaryExpressionTree(item);
		return true;
	} else {
		if (!leftNode->insert(item)) {
			return rightNode->insert(item);
		} else {
			return true;
		}
	}
}

bool BinaryExpressionTree::insert(AbstractBinaryTree<tstring*> *tree)
{
	if (leftNode == nullptr) {
		leftNode = tree;
		return true;
	} else if (rightNode == nullptr) {
		rightNode = tree;
		return true;
	} else {
		if (!leftNode->insert(tree)) {
			return rightNode->insert(tree);
		} else {
			return true;
		}
	}
}

bool BinaryExpressionTree::contains(tstring* item)
{
	if (this->item == item)
		return true;

	if (leftNode != nullptr && leftNode->contains(item)) {
		return true;
	} else if (rightNode != nullptr) {
		return rightNode->contains(item);
	}

	return false;
}

void BinaryExpressionTree::remove(tstring* item)
{
	if (this->item == item) {
		this->~BinaryExpressionTree();
		return;
	}

	if (leftNode != nullptr) {
		leftNode->remove(item);
	} else if (rightNode != nullptr) {
		rightNode->remove(item);
	}
}

} /* namespace mathsthing */
