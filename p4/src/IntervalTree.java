import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	// TODO declare any data members needed for this class
	private IntervalNode <T> root;
	private int height;
	private int size;
	@Override
	public IntervalNode<T> getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
						return node;
		// TODO Auto-generated method stub
	}

	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
						return null;
		// TODO Auto-generated method stub
	}

	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public int getSize() {
		return size;
		// TODO Auto-generated method stub
	}

	@Override
	public int getHeight() {
		return height;
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(IntervalADT<T> interval) {
		return false;
		// TODO Auto-generated method stub
	}

	@Override
	public void printStats() {
		// TODO Auto-generated method stub
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + height);
		System.out.println("Size: " + size);
		System.out.println("-----------------------------------------");
	}

}

