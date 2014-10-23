/*
 * BinaryExpressionTree.cpp
 *
 *  Created on: 22 Oct 2014
 *      Author: Jordan
 */

#include "include/BinaryExpressionTree.h"
#include "include/JNIUtils.h"

#include <cmath>

namespace mathsthing {

BinaryExpressionTree::BinaryExpressionTree()
{
	mathsthing::Token empty;
	empty.type = mathsthing::EMPTY;
	this->item = empty;
	this->leftNode = nullptr;
	this->rightNode = nullptr;
}

BinaryExpressionTree::BinaryExpressionTree(Token item)
{
	this->item = item;
	this->leftNode = nullptr;
	this->rightNode = nullptr;
}

void BinaryExpressionTree::addLeftTree(AbstractBinaryTree<Token, double> *tree)
{
	if (leftNode == nullptr) {
		leftNode = tree;
		return;
	}

	leftNode->addLeftTree(tree);
}

void BinaryExpressionTree::addRightTree(AbstractBinaryTree<Token, double> *tree)
{
	if (rightNode == nullptr) {
			rightNode = tree;
			return;
		}

	rightNode->addLeftTree(tree);
}

bool BinaryExpressionTree::insert(Token item)
{
	if (this->item.type == mathsthing::EMPTY) {
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

bool BinaryExpressionTree::insert(AbstractBinaryTree<Token, double> *tree)
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

bool BinaryExpressionTree::contains(Token item)
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

void BinaryExpressionTree::remove(Token item)
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

double BinaryExpressionTree::postorder()
{
	double leftVal = 0.0;
	double rightVal = 0.0;

	if (leftNode != nullptr)
		leftVal = leftNode->postorder();
	if (rightNode != nullptr)
		rightVal = rightNode->postorder();

	switch(item.type)
	{
	case mathsthing::CONSTANT:
		return atof(item.value);
	case mathsthing::OPERAND:
		return 0.0;
	case mathsthing::OPERATOR:
		switch(item.value[0])
		{
		case '*':
			return rightVal * leftVal;
		case '+':
			return rightVal + leftVal;
		case '-':
			return rightVal - leftVal;
		case '/':
			return rightVal / leftVal;
		case '^':
			return std::pow(rightVal, leftVal);
		default:
			JNIEnv* env = mathsthing::GetEnv();
			mathsthing::ThrowFormulaException(env, "Unknown operator found in formula.");
		}
		break;
	case mathsthing::FUNCTION:
		return 0.0;
	default:
		JNIEnv* env = mathsthing::GetEnv();
		mathsthing::ThrowFormulaException(env, "Incorrectly structured formula.");
	}

	return 0.0;
}

} /* namespace mathsthing */
