/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          team41_p3
// FILE:             IntervalNode
//
// TEAM:    Team 41, IDGAF
// Authors: 
// Author1: (Jarrett Benson, jbenson6@wisc.edu, jbenson6, Lec 002)
// Author2: (Cameron Carlson, ccarlson24@wisc.edu, ccarlson, Lec 002) 
// Author3: (Isaac Heinrich, iheinrich@wisc.edu, iheinrich, Lec 002)  
///////////////////////////////////////////////////////////////////////////////

/**
 * This class manages the interval node and all its methods/fields, including
 * the interval it contains, the maxEnd, its children, and methods to set
 * and get these fields
 * 
 * @authors see above
 *
 * @param <T>
 *            the type of interval start and end
 */

public class IntervalNode<T extends Comparable<T>> {
	// Interval stored in the node.
	private IntervalADT<T> interval;

	// Each node stores the maxEnd of the interval in its subtree.
	private T maxEnd;

	// LeftNode and RightNode.
	private IntervalNode<T> leftNode, rightNode;

	/**
	 * Constructor to create a new IntervalNode. Set the interval data structure
	 * present as member variable above and maxEnd associated with the interval.
	 * Hint: Use interval.getEnd() to get the end of the interval. Note: In your
	 * intervalTree, this will get updated subsequently.
	 * 
	 * @param interval
	 *            the interval data member.
	 */
	public IntervalNode(IntervalADT<T> interval) {
		maxEnd = interval.getEnd();
		leftNode = null;
		rightNode = null;
		this.interval = interval;
	}

	/**
	 * Returns the next in-order successor of the BST. Hint: Return left-most
	 * node in the right subtree. Return null if there is no rightNode.
	 *
	 * @return in-order successor node
	 */
	public IntervalNode<T> getSuccessor() {
		if (rightNode != null) {
			IntervalNode<T> current = rightNode;
			// go to the left most node
			while (current.getLeftNode() != null)
				current = current.getLeftNode();
			return current;
		} else
			return null;
	}

	/**
	 * Returns the interval associated with the node.
	 * 
	 * @return the interval data field.
	 */
	public IntervalADT<T> getInterval() {
		return interval;
	}

	/**
	 * Setter for the interval.
	 * 
	 * @param interval
	 *            the interval to be set at this node.
	 */
	public void setInterval(IntervalADT<T> interval) {
		this.interval = interval;
	}

	/**
	 * Setter for the maxEnd. This represents the maximum end point associated
	 * in any interval stored at the subtree rooted at this node (inclusive of
	 * this node).
	 * 
	 * @param maxEnd
	 *            the maxEnd associated with this node.
	 *
	 */
	public void setMaxEnd(T maxEnd) {
		this.maxEnd = maxEnd;
	}

	/**
	 * Getter for the maxEnd member variable.
	 * 
	 * @return the maxEnd.
	 */
	public T getMaxEnd() {
		return maxEnd;
	}

	/**
	 * Getter for the leftNode reference.
	 *
	 * @return the reference of leftNode.
	 */
	public IntervalNode<T> getLeftNode() {
		return leftNode;
	}

	/**
	 * Setter for the leftNode of this node.
	 * 
	 * @param leftNode
	 *            the left node.
	 */
	public void setLeftNode(IntervalNode<T> leftNode) {
		this.leftNode = leftNode;
	}

	/**
	 * Getter for the rightNode of this node.
	 * 
	 * @return the rightNode.
	 */
	public IntervalNode<T> getRightNode() {
		return rightNode;
	}

	/**
	 * Setter for the rightNode of this node.
	 * 
	 * @param rightNode
	 *            the rightNode of this node.
	 */
	public void setRightNode(IntervalNode<T> rightNode) {
		this.rightNode = rightNode;
	}

}
