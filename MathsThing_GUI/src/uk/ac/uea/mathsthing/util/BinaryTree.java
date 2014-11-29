package uk.ac.uea.mathsthing.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * A generic Binary Tree implementation. Inherits from the Collections API to 
 * provide a uniform method of interaction with Java.
 * 
 * @param <E> The generic type to store in this BinaryTree and all its child 
 * nodes.
 * @author Jordan Woerner
 * @version 1.0
 */
public class BinaryTree<E> implements Collection<E> {

	/** The Object to store in this node. */
	protected E item;
	/** The left child for this node. */
	protected BinaryTree<E> leftNode;
	/** The right child for this node. */
	protected BinaryTree<E> rightNode;
	
	/**
	 * Creates an empty {@link BinaryTree} node with no stored item or child 
	 * nodes.
	 * 
	 * @since 1.0
	 */
	public BinaryTree() 
	{		
		this.item = null;
		this.leftNode = null;
		this.rightNode = null;
	}
	
	/**
	 * Creates a {@link BinaryTree} node with the specified item stored and no 
	 * child nodes.
	 * 
	 * @param item The item to store.
	 * @since 1.0
	 */
	public BinaryTree(E item)
	{		
		this.item = item;
		this.leftNode = null;
		this.rightNode = null;
	}
	
	/**
	 * Creates a new {@link BinaryTree} node with the specified item stored and
	 *  the specified child {@link BinaryTree} nodes. 
	 * 
	 * @param item The item to store in this {@link BinaryTree}.
	 * @param leftTree The {@link BinaryTree} to set as the left child.
	 * @param rightTree The {@link BinaryTree} to set as the right child.
	 * @since 1.0
	 */
	public BinaryTree(E item, 
			final BinaryTree<E> leftTree, 
			final BinaryTree<E> rightTree)
	{		
		this.item = item;
		this.leftNode = leftTree;
		this.rightNode = rightTree;
	}
	
	/**
	 * Adds a new {@link BinaryTree} as the left child with the specified item 
	 * stored in the node.
	 * 
	 * @param item The item to store in the new {@link BinaryTree} node.
	 * @since 1.0
	 */
	public void addLeftNode(E item)
	{		
		leftNode = new BinaryTree<E>(item);
	}
	
	/**
	 * Adds an existing {@link BinaryTree} to this tree as the left child.
	 * 
	 * @param tree The {@link BinaryTree} to attach to this one as the left 
	 * child.
	 * @since 1.0
	 */
	public void addLeftTree(BinaryTree<E> tree)
	{		
		leftNode = tree;
	}
	
	/**
	 * Adds a new {@link BinaryTree} as the right child with the specified item 
	 * stored in the node.
	 * 
	 * @param item The item to store in the new {@link BinaryTree} node.
	 * @since 1.0
	 */
	public void addRightNode(E item)
	{		
		rightNode = new BinaryTree<E>(item);
	}
	
	/**
	 * Adds an existing {@link BinaryTree} to this tree as the right  child.
	 * 
	 * @param tree The {@link BinaryTree} to attach to this one as the right 
	 * child.
	 * @since 1.0
	 */
	public void addRightTree(BinaryTree<E> tree)
	{		
		rightNode = tree;
	}

	/**
	 * Adds the specified item to this {@link BinaryTree}. If there is nothing 
	 * stored in this node it will be added here. Otherwise child nodes will be
	 *  created until the item can be stored in one.
	 *  
	 * @param arg The item to store in this {@link BinaryTree}.
	 * @return Returns true if the item can be stored, false otherwise.
	 * @since 1.0
	 */
	@Override
	public boolean add(E arg)
	{	
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

	/**
	 * Adds all the items in the provided {@link Collection} to this 
	 * {@link BinaryTree}. New nodes will be created if needed until all the 
	 * items are stored.
	 * 
	 * @param arg The {@link Collection} of items to store in this tree.
	 * @return Returns true if the items are all added. False otherwise.
	 * @since 1.0
	 */
	@Override
	public boolean addAll(Collection<? extends E> arg)
	{		
		for (E item : arg) {
			if (!this.add(item))
				return false;
		}
		
		return true;
	}

	/**
	 * Removes the child nodes and stored item for this {@link BinaryTree}.
	 */
	@Override
	public void clear()
	{		
		item = null;
		leftNode = null;
		rightNode = null;
	}

	/**
	 * Checks to see if this {@link BinaryTree} contains the specified item.
	 * 
	 * @param arg The {@link Object} to look for in this tree.
	 * @return Returns true if this tree node or any of the child nodes contain
	 *  the specified {@link Object}. Returns false otherwise.
	 */
	@Override
	public boolean contains(Object arg) 
	{		
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

	/**
	 * Checks if this tree contains all of the items in the specified 
	 * {@link Collection}.
	 * 
	 * @param arg A {@link Collection} of objects to look for.
	 * @return Returns true if this {@link BinaryTree} contains every item in 
	 * the specified {@link Collection}.
	 */
	@Override
	public boolean containsAll(Collection<?> arg)
	{		
		boolean contains = true;
		for (Object item : arg) {
			if (!this.contains(item)) {
				contains = false;
				break;
			}
		}
		
		return contains;
	}

	/**
	 * Checks if this tree is entirely empty.
	 * 
	 * @return Returns true if there is no item stored in this tree and no 
	 * child trees exist. Returns false otherwise.
	 */
	@Override
	public boolean isEmpty()
	{		
		return (leftNode == null && rightNode == null && item == null);
	}

	/**
	 * Not available in this implementation of {@link BinaryTree}.
	 */
	@Override
	public Iterator<E> iterator()
			throws UnsupportedOperationException 
	{
		throw new UnsupportedOperationException("Iterators are not supported "
				+ "for this version of BinaryTree.");
	}

	/**
	 * Not available in this implementation of {@link BinaryTree}.
	 */
	@Override
	public boolean remove(Object arg0)
			throws UnsupportedOperationException 
	{
		throw new UnsupportedOperationException("Removal is not supported "
				+ "for this version of BinaryTree.");
	}

	/**
	 * Not available in this implementation of {@link BinaryTree}.
	 */
	@Override
	public boolean removeAll(Collection<?> arg0)
			throws UnsupportedOperationException 
	{
		throw new UnsupportedOperationException("Removal is not supported "
				+ "for this version of BinaryTree.");
	}

	/**
	 * Not available in this implementation of {@link BinaryTree}.
	 */
	@Override
	public boolean retainAll(Collection<?> arg0)
			throws UnsupportedOperationException 
	{
		throw new UnsupportedOperationException("Retaining is not supported "
				+ "for this version of BinaryTree.");
	}

	/**
	 * Gets the height of this {@link BinaryTree}.
	 * 
	 * @return Returns an integer representing the height of this tree.
	 * @since 1.0
	 */
	@Override
	public int size() 
	{		
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

	/**
	 * Not available in this implementation of {@link BinaryTree}.
	 */
	@Override
	public Object[] toArray()
			throws UnsupportedOperationException 
	{
		throw new UnsupportedOperationException("Array conversion is not "
				+ "supported for this version of BinaryTree.");
	}

	/**
	 * Not available in this implementation of {@link BinaryTree}.
	 */
	@Override
	public <T> T[] toArray(T[] arg0)
			throws UnsupportedOperationException 
	{
		throw new UnsupportedOperationException("Array conversion is not "
				+ "supported for this version of BinaryTree.");
	}
	
	public String toString() 
	{		
		return item.toString();
	}
}
