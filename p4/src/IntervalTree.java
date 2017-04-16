
/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          team41_p3
// FILE:             Interval
//
// TEAM:    Team 41, IDGAF
// Authors: 
// Author1: (Jarrett Benson, jbenson6@wisc.edu, jbenson6, Lec 002)
// Author2: (Cameron Carlson, ccarlson24@wisc.edu, ccarlson, Lec 002) 
// Author3: (Isaac Heinrich, iheinrich@wisc.edu, iheinrich, Lec 002)  
///////////////////////////////////////////////////////////////////////////////
import java.util.List;
import java.util.ArrayList;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {

	// Root node for the interval tree
	private IntervalNode<T> root;
	// Size of the interval tree
	private int size;
	// Height of the interval tree
	private int height;

	/**
	 * Parameter-less constructor to just create a new interval tree with null
	 * root
	 */
	public IntervalTree() {
		root = null;
	}

	/**
	 * Constructor that constructs an IntervalTree with the passed in root
	 * 
	 * @param root
	 *            is root of the IntervalTree
	 */
	public IntervalTree(IntervalNode<T> root) {
		this.root = root;
	}

	@Override
	/**
	 * Getter method to get the root of the interval tree
	 */
	public IntervalNode<T> getRoot() {
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		root = insertHelper(this.root, interval);
		size += 1;
	}

	private IntervalNode<T> insertHelper(IntervalNode<T> node, IntervalADT<T> interval)
			throws IllegalArgumentException {
		if (interval == null) {
			throw new IllegalArgumentException();
		} else if (node == null) {
			node = new IntervalNode<T>(interval);
			node.setMaxEnd(node.getInterval().getEnd());
			return node;
		} else {
			int k = 0;
			k = node.getInterval().compareTo(interval);
			switch (k) {
			case 1:
				IntervalNode<T> newNode = insertHelper(node.getLeftNode(), interval);
				node.setLeftNode(newNode);
				if (interval.getEnd().compareTo(node.getMaxEnd()) > 0) {
					node.setMaxEnd(interval.getEnd());
				}
				return node;
			case -1:
				IntervalNode<T> diffNode = insertHelper(node.getRightNode(), interval);
				node.setRightNode(diffNode);
				if (interval.getEnd().compareTo(node.getMaxEnd()) > 0) {
					node.setMaxEnd(interval.getEnd());
				}
				return node;
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	@Override
	/**
	 * Method to delete the node that contains the passed in interval, uses
	 * recursive helper method
	 * 
	 * @throws IllegalArgumentException
	 *             if interval is null
	 * @throws IntervalNotFoundException
	 *             if the interval does not exist
	 */
	public void delete(IntervalADT<T> interval) throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		// Call to recursive helper method
		deleteHelper(root, interval);

	}

	@Override
	/**
	 * Recursive helper method for delete method, updates maxEnds of nodes if
	 * deleting nodes changes the maxEnds.
	 * 
	 * @param node
	 *            is the interval node that is being checked
	 * @param interval
	 *            is the interval that will be deleted
	 * @throws IllegalArgumentException
	 *             if interval is null
	 * @throws IntervalNotFoundException
	 *             if interval is not null, but isn't in the tree
	 *
	 * @return root of the tree after deleting the interval
	 */
	public IntervalNode<T> deleteHelper(IntervalNode<T> node, IntervalADT<T> interval)
			throws IntervalNotFoundException, IllegalArgumentException {
		if (node == null) {
			throw new IntervalNotFoundException(interval.toString());
		}

		// If the interval is in the node, delete and replace with the in-order
		// successor
		if (node.getInterval().compareTo(interval) == 0) {
			// if right child exists
			if (!(node.getRightNode() == null)) {
				// set interval to the in-order successors interval
				node.setInterval(node.getSuccessor().getInterval());
				deleteHelper(node.getSuccessor(), interval);

				// Update the maxEnd

				node.setMaxEnd(node.getSuccessor().getMaxEnd());

				return node;
			}
			// if right child doesn't exist, return left child
			if (node.getRightNode() == null) {
				return node.getLeftNode();

			}
		}
		// If interval is in the right subtree
		if (node.getInterval().compareTo(interval) < 0) {
			node.setRightNode(deleteHelper(node.getRightNode(), interval));
			updateMaxEnd(node);

		}
		// If interval is in the left subtree
		if (node.getInterval().compareTo(interval) > 0) {
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
			updateMaxEnd(node);
		}

		return null;
	}

	/**
	 * Method to update the maxEnd value for a nodes sub-nodes.
	 * 
	 * @param node
	 *            is node to be updated
	 * @return the updated maxEnd for the node
	 */
	private T updateMaxEnd(IntervalNode<T> node) {
		// Setting max end to the intervals end value,
		// will change if it needs to be updated
		node.setMaxEnd(node.getInterval().getEnd());

		/*
		 * Check both subtrees to see if maxEnd needs to be updated
		 */
		// Right
		if (!(node.getRightNode() == null)
				// if the nodes current end is less than the end node,
				// update it to the larger value
				&& node.getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) < 0) {
			// Update to right nodes maxEnd
			node.setMaxEnd(node.getRightNode().getMaxEnd());
		}
		// Left
		if (!(node.getLeftNode() == null)
				// if the nodes current end is less than the end node,
				// update it to the larger value
				&& node.getMaxEnd().compareTo(node.getLeftNode().getMaxEnd()) < 0) {
			// Update to left nodes maxEnd
			node.setMaxEnd(node.getLeftNode().getMaxEnd());
		}

		// return the updated maxEnd
		return node.getMaxEnd();

	}

	@Override
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		ArrayList<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, result);
		return result;
	}

	private void findOverlappingHelper(IntervalNode<T> node, IntervalADT<T> interval,
			ArrayList<IntervalADT<T>> result) {
		if (node == null)
			return;
		// if (start1 < end2 and > start2) or (end1 > start2 and < end1) it
		// overlaps
		if ((node.getInterval().getStart().compareTo(interval.getEnd()) <= 1
				&& node.getInterval().getStart().compareTo(interval.getStart()) >= 1)
				|| (node.getInterval().getEnd().compareTo(interval.getStart()) >= 1
						&& node.getInterval().getEnd().compareTo(interval.getEnd()) <= 1)) {
			result.add(node.getInterval());
		}
		if (node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 1) {
			findOverlappingHelper(node.getLeftNode(), interval, result);
		} else if (node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 1) {
			findOverlappingHelper(node.getLeftNode(), interval, result);
		}
	}

	@Override
	/**
	 * Uses recursive helper method to search and return a list of intervals
	 * containing the point passed in.
	 * 
	 * @param point
	 *            is the point used to find out which intervals contain it
	 * @return the ouputed list of intervals containing the passed in point
	 */
	public List<IntervalADT<T>> searchPoint(T point) {
		if (point == null) {
			throw new IllegalArgumentException();
		}
		// New list to store the values that contain the point
		List<IntervalADT<T>> output = new ArrayList<IntervalADT<T>>();
		// Recursive call to the helper method
		searchPointHelper(point, root, output);
		return output;
	}

	/**
	 * Recursive helper method used to search through both right and left
	 * subtrees and then add to the output list if there is
	 * 
	 * @param point
	 *            is the given point we are looking for in intervals
	 * @param node
	 *            is the node we look at their children
	 * @param output
	 *            is the list of intervals that contain the point
	 */
	private void searchPointHelper(T point, IntervalNode<T> node, List<IntervalADT<T>> output) {
		if (node == null) {
			return;
		}
		// Check to see if the interval has the point, if does add it to the
		// list
		if (node.getInterval().contains(point)) {
			output.add(node.getInterval());
		}
		// Recursive call to the right child
		searchPointHelper(point, node.getRightNode(), output);
		// Recursive call to left child
		searchPointHelper(point, node.getLeftNode(), output);
	}

	@Override
	/**
	 * Getter method for the size of the tree
	 */
	public int getSize() {
		return size;
	}

	@Override
	/**
	 * Getter method for the height of the tree
	 */
	public int getHeight() {
		return getHeightHelper(root);
	}

	private int getHeightHelper(IntervalNode<T> t) {
		if (t == null)
			return 0;
		int leftHeight = getHeightHelper(t.getLeftNode());
		int rightHeight = getHeightHelper(t.getRightNode());
		if (leftHeight > rightHeight)
			return 1 + leftHeight;
		else
			return 1 + rightHeight;
	}

	@Override
	/**
	 * Method to determine if a tree has an exact match for start and end of the
	 * passed in interval Uses recursive helper function to call with root node
	 * 
	 * @param interval
	 *            is the target interval we're searching for
	 * @return call to the recursive helper method, true if the tree contains
	 *         the interval, false if not
	 * @throws IllegalArgumentException
	 *             if the interval is null
	 */
	public boolean contains(IntervalADT<T> interval) {
		// Call to recursive helper, passing in the root and passed in interval
		return containsHelper(root, interval);
		// TODO Auto-generated method stub
	}

	/**
	 * Recursive helper method for the contains method. Returns true iff the
	 * passed in node's interval is equal to the passed in interval.
	 *
	 * @param node
	 *            is the root passed in
	 * @param interval
	 *            is the interval that was passed into the original contains
	 *            method
	 * @return true if the tree contains that interval, false if not
	 */
	private boolean containsHelper(IntervalNode<T> node, IntervalADT<T> interval) {
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		if (node == null) {
			return false;
		}
		// if they are equal, return true
		if (node.getInterval().compareTo(interval) == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	/**
	 * Method to print the stats of the tree
	 */
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + height);
		System.out.println("Size: " + size);
		System.out.println("-----------------------------------------");
	}

}
