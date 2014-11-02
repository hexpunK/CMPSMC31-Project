package uk.ac.uea.mathsthing.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * A generic Binary Tree implementation. Inherits from the Collections API to 
 * provide a uniform method of interaction with Java.
 * 
 * @author Jordan Woerner
 * @version 0.1
 * @param <E> The generic type to store in this BinaryTree and all its child 
 * nodes.
 */
public class BinaryTree<E> implements Collection<E> {

	/** The Object to store in this node. */
	protected E item;
	/** The left child for this node. */
	protected BinaryTree<E> leftNode;
	/** The right child for this node. */
	protected BinaryTree<E> rightNode;
	
	public BinaryTree() {
		
		this.item = null;
		this.leftNode = null;
		this.rightNode = null;
	}
	
	public BinaryTree(E item) {
		
		this.item = item;
		this.leftNode = null;
		this.rightNode = null;
	}
	
	public BinaryTree(E item, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
		
		this.item = item;
		this.leftNode = leftTree;
		this.rightNode = rightTree;
	}
	
	public void addLeftNode(E item) {
		
		leftNode = new BinaryTree<E>(item);
	}
	
	public void addLeftTree(BinaryTree<E> tree) {
		
		leftNode = tree;
	}
	
	public void addRightNode(E item) {
		
		rightNode = new BinaryTree<E>(item);
	}
	
	public void addRightTree(BinaryTree<E> tree) {
		
		rightNode = tree;
	}

	@Override
	public boolean add(E arg) {
	
		if (item == null) {
			item = arg;
			return true;
		} else if (leftNode == null) {
			leftNode = new BinaryTree<>(arg);
			return true;
		} else if (rightNode == null) {
			rightNode = new BinaryTree<>(arg);
			return true;
		} else {
			if (!leftNode.add(arg)) {
				return rightNode.add(arg);
			} else {
				return true;
			}
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> arg) {
		
		for (E item : arg) {
			if (this.add(item))
				return true;
		}
		
		return false;
	}

	@Override
	public void clear() {
		
		item = null;
		leftNode = null;
		rightNode = null;
	}

	@Override
	public boolean contains(Object arg) {
		
		if (item != null && item == arg) {
			return true;
		} else if (leftNode == null) {
			if (!leftNode.contains(arg)) {
				if (rightNode == null) {
					return rightNode.contains(arg);
				}
			} else {
				return true;
			}
		}
			
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg) {
		
		for (Object item : arg) {
			if (this.contains(item))
				return true;
		}
		
		return false;
	}

	@Override
	public boolean isEmpty() {
		
		return (leftNode == null && rightNode == null && item == null);
	}

	@Override
	public Iterator<E> iterator()
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Iterators are not supported "
				+ "for this version of BinaryTree.");
	}

	@Override
	public boolean remove(Object arg0)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Removal is not supported "
				+ "for this version of BinaryTree.");
	}

	@Override
	public boolean removeAll(Collection<?> arg0)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Removal is not supported "
				+ "for this version of BinaryTree.");
	}

	@Override
	public boolean retainAll(Collection<?> arg0)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Retaining is not supported "
				+ "for this version of BinaryTree.");
	}

	@Override
	public int size() {
		
		int leftSize = 0;
		int rightSize = 0;
		
		if (leftNode == null && rightNode == null)
			return 1;
		else {
			if (leftNode != null) {
				leftSize = leftNode.size() + 1;
			}
			if (rightNode != null) {
				rightSize = rightNode.size() + 1;
			}
			
			if (leftSize > rightSize) {
				return leftSize;
			} else {
				return rightSize;
			}
		}
	}

	@Override
	public Object[] toArray()
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Array conversion is not "
				+ "supported for this version of BinaryTree.");
	}

	@Override
	public <T> T[] toArray(T[] arg0)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Array conversion is not "
				+ "supported for this version of BinaryTree.");
	}
	
	public String toString() {		
		return item.toString();
	}
}
