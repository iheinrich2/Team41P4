
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

    // TODO declare any needed data members
	private T start;
	private T end;
	private String label;

    public Interval(T start, T end, String label) {
        // TODO Auto-generated constructor stub
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    @Override
    public T getStart() {
        // TODO Auto-generated method stub
    	return start;
    }

    @Override
    public T getEnd() {
        // TODO Auto-generated method stub
    	return end;
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
    	return label;
    }

    @Override
    public boolean overlaps(IntervalADT<T> other) throws IllegalArgumentException{
        // TODO Auto-generated method stub
    	if (other == null) throw new IllegalArgumentException();
    	if ((int) other.getEnd() < (int) end) return false;
    	if ((int) other.getStart() > (int) start) return false;
    	else return true;
    }

    @Override
    public boolean contains(T point) {
        // TODO Auto-generated method stub
    	if ( (int) point > (int) start && (int) point < (int) end) return true;
    	else return false;
    }

    @Override
    public int compareTo(IntervalADT<T> other) {
        // TODO Auto-generated method stub
    	if ( (int) start < (int) other.getStart() ) return -1;
    	else if ( (int) start == (int) other.getStart() ) {
    		if ( (int) end < (int) other.getEnd() ) return -1;
    		if ( (int) end == (int) other.getEnd() ) return 0;
    	}
    	return 1;
    }

}
