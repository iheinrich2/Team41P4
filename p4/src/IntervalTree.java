import java.util.List;
import java.util.ArrayList;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	private IntervalNode<T> root;
	private int size;
	private int height;

	public IntervalTree(){
		root = null;
	}
	public IntervalTree(IntervalNode<T> root){
		this.root = root;
	}
	@Override
	public IntervalNode<T> getRoot() {
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		// TODO Auto-generated method stub
			root = insertHelper(this.root, interval);
			size += 1;
	}
	private IntervalNode<T> insertHelper(IntervalNode<T> node,
					IntervalADT<T> interval) throws IllegalArgumentException {
		if (interval == null){
			throw new IllegalArgumentException();
		}
		else if (node == null){
			node = new IntervalNode<T>(interval);
			node.setMaxEnd(node.getInterval().getEnd());
			return node;
		}
		else {
			int k = 0;
			k = node.getInterval().compareTo(interval);
			switch (k){
			case 1:
				IntervalNode<T> newNode = insertHelper(node.getLeftNode(), interval);
				node.setLeftNode(newNode);
				return node;
			case -1:
				IntervalNode<T> diffNode = insertHelper(node.getRightNode(), interval);
				node.setRightNode(diffNode);
				return node;
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null){
			throw new IllegalArgumentException();
		}
		deleteHelper(root, interval);
		

	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
			if(node == null){
				throw new IntervalNotFoundException(node.toString());
			}
			
			if(node.getInterval().compareTo(interval) == 0){
				if(!(node.getRightNode() == null)){
					node.setInterval(node.getSuccessor().getInterval());
					deleteHelper(node.getSuccessor(), interval);
					
					node.setMaxEnd(node.getSuccessor().getMaxEnd());
					
					return node;
				}
				if(node.getRightNode() == null){
					return node.getLeftNode();
					
				}
			}
			if(node.getInterval().compareTo(interval) < 0){
				node.setRightNode(deleteHelper(node.getRightNode(), interval));
				//TODO write this method
				updateMaxEnd();
				
			}
			if(node.getInterval().compareTo(interval) > 0){
				node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
				//TODO write this method
				updateMaxEnd();
			}
			
			return null;
	}

	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		if(point == null){
			throw new IllegalArgumentException();
		}
		List<IntervalADT<T>> output = new ArrayList<IntervalADT<T>>();
		searchPointHelper(point, root, output);
		return output;
	}
	
	private void searchPointHelper(T point,
			IntervalNode<T> node, List<IntervalADT<T>> output){
		if(node == null){
			return;
		}
		if(node.getInterval().contains(point)){
			output.add(node.getInterval());
		}
		searchPointHelper(point, node.getRightNode(), output);
		searchPointHelper(point, node.getLeftNode(), output);
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean contains(IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + height);
		System.out.println("Size: " + size);
		System.out.println("-----------------------------------------");
	}

	/*
	 * Method to check if necessary to update the max end, and then updating
	 * if it does.
	 */

	private void updateMaxEnd(){
		
	}
}
