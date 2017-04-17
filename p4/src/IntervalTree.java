
/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          team41_p3
// FILE:             IntervalTree
//
// TEAM:    Team 41, IDGAF
// Authors: 
// Author1: (Jarrett Benson, jbenson6@wisc.edu, jbenson6, Lec 002)
// Author2: (Cameron Carlson, ccarlson24@wisc.edu, ccarlson, Lec 002) 
// Author3: (Isaac Heinrich, iheinrich@wisc.edu, iheinrich, Lec 002)  
///////////////////////////////////////////////////////////////////////////////
import java.util.List;
import java.util.ArrayList;

/**
 * This class follows the IntervalTreeADT and contains all methods and fields
 * necessary, including methods to find intervals containing a point, all
 * intervals that have a portion in a given interval, and addition and removal
 * of intervals
 * 
 * @authors see above
 *
 * @param <T>
 *            the type of interval start and end
 */
public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {

	// Root node for the interval tree
	private IntervalNode<T> root;

	/**
	 * Parameterless constructor to just create a new interval tree with null
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
	/**
	 * this method uses the given interval and calls the helper method to
	 * properly insert it into the interval tree
	 *
	 * @param interval
	 *            the interval to be added
	 */
	public void insert(IntervalADT<T> interval) throws IllegalArgumentException {
		// call to recursive method
		// recursive always returns root node
		root = insertHelper(this.root, interval);
	}

	/**
	 * this is the helper insert method. it traverses the tree until the
	 * appropriate spot for the interval is located, the interval is added, and
	 * maxEnd is updated if needed
	 *
	 * @param node
	 *            the root node of the tree
	 * @param interval
	 *            the interval to be added
	 * @return the node that is inserted
	 */
	private IntervalNode<T> insertHelper(IntervalNode<T> node, IntervalADT<T> interval)
			throws IllegalArgumentException {
		// check if interval is valid
		if (interval == null) {
			throw new IllegalArgumentException();
			// check if current node is empty
		} else if (node == null) {
			// create new node with interval
			node = new IntervalNode<T>(interval);
			// adjust node's max end
			node.setMaxEnd(node.getInterval().getEnd());
			return node;
		} else { // traverse through list
			int k = 0;
			k = node.getInterval().compareTo(interval);
			switch (k) {
			case 1: // if node is smaller than interval
				// traverse through tree
				IntervalNode<T> newNode = insertHelper(node.getLeftNode(), interval);
				// adjust shape of tree
				node.setLeftNode(newNode);
				// check if maxEnd needs to be updated
				if (interval.getEnd().compareTo(node.getMaxEnd()) > 0) {
					node.setMaxEnd(interval.getEnd());
				}
				return node;
			case -1: // if node is bigger than interval
				// traverse through tree
				IntervalNode<T> diffNode = insertHelper(node.getRightNode(), interval);
				// adjust shape of tree
				node.setRightNode(diffNode);
				// check if maxEnd needs to be updated
				if (interval.getEnd().compareTo(node.getMaxEnd()) > 0) {
					node.setMaxEnd(interval.getEnd());
				}
				return node;
			default: // if node is equal to interval
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
				IntervalADT<T> suc = node.getSuccessor().getInterval();
				node.setInterval(suc);
				node.setRightNode(deleteHelper(node.getRightNode(), suc));
			}

			// if right child doesn't exist, return left child
			else
				return node.getLeftNode();
		}

		// If interval is in the right subtree
		if (node.getInterval().compareTo(interval) < 0) {
			node.setRightNode(deleteHelper(node.getRightNode(), interval));

		}

		// If interval is in the left subtree
		if (node.getInterval().compareTo(interval) > 0) {
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
		}

		// update maxEnd with children
		IntervalNode<T> left = node.getLeftNode();
		IntervalNode<T> right = node.getRightNode();

		// selects the biggest child
		T childMax = null;
		if (left != null && right != null) {
			T lEnd = left.getMaxEnd();
			T rEnd = right.getMaxEnd();
			childMax = lEnd.compareTo(rEnd) > 0 ? lEnd : rEnd;
		}

		else if (left != null && right == null)
			childMax = left.getMaxEnd();

		else if (left == null && right != null)
			childMax = right.getMaxEnd();

		// inherit maxEnd from biggest child or itself
		T nodeEnd = node.getInterval().getEnd();
		node.setMaxEnd(childMax != null && childMax.compareTo(nodeEnd) > 0 ? childMax : nodeEnd);

		return node;
	}

	@Override
	/**
	 * this method calls it's helper method and passes the given interval to be
	 * searched for as well as creates a result list and returns it
	 *
	 * @param interval
	 *            the interval that will be searched for
	 * @return a list of each interval that overlaps the given interval
	 */
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		ArrayList<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, result);
		return result;
	}

	/**
	 * this method takes the given root node, interval, and result list, and
	 * searches every node to see if the given interval overlaps with that
	 * node's interval, and if so, adds it to the list
	 *
	 * @param node
	 *            the root of the tree
	 * @param interval
	 *            the interval we are comparing against
	 * @param result
	 *            the list of results
	 */
	private void findOverlappingHelper(IntervalNode<T> node, IntervalADT<T> interval,
			ArrayList<IntervalADT<T>> result) {
		if (node == null)
			return;
		// if (start1 < end2 and > start2) or (end1 > start2 and < end1) it
		// overlaps
		if (node.getInterval().overlaps(interval))
			result.add(node.getInterval());

		IntervalNode<T> child = node.getLeftNode();
		if (child != null && child.getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getLeftNode(), interval, result);
		child = node.getRightNode();
		if (child != null && child.getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getRightNode(), interval, result);
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

		// Recursive call to left child
		searchPointHelper(point, node.getLeftNode(), output);
		// Recursive call to the right child
		searchPointHelper(point, node.getRightNode(), output);
	}

	@Override
	/**
	 * Getter method for the size of the tree
	 */
	public int getSize() {
		return sizeHelper(root);
	}

	/**
	 * gets the size of the tree using a simple recursion
	 *
	 * @param node
	 *            the root of the tree
	 * @return the number of nodes the tree has (size)
	 */
	private int sizeHelper(IntervalNode<T> node) {
		if (node == null)
			return 0;
		else
			return sizeHelper(node.getLeftNode()) + sizeHelper(node.getRightNode()) + 1;
	}

	@Override
	/**
	 * Getter method for the height of the tree
	 */
	public int getHeight() {
		return getHeightHelper(root);
	}

	/**
	 * uses recursion and some comparisons to determine the height of the
	 * interval tree
	 *
	 * @param node
	 *            the root of the tree
	 * @return the height of the tree
	 */
	private int getHeightHelper(IntervalNode<T> node) {
		if (node == null)
			return 0;
		int leftHeight = getHeightHelper(node.getLeftNode());
		int rightHeight = getHeightHelper(node.getRightNode());
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
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");
	}

}
