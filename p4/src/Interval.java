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

public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

    	//Start value of the Interval
	private T start;
	//End value of the Interval
	private T end;
	//Label for the Interval
	private String label;

    /**
    * Constructor for the Interval Class, constructs a new instance of Interval
    *@param start the start value for the interval
    *@param end the end value for the interval
    *@param label the label for the interval
    */
    public Interval(T start, T end, String label) {
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    @Override
    /**
     * Method to return the start value of the interval
     */
    public T getStart() {
    	return start;
    }

    @Override
    /**
     * Getter method to get the end value of the interval
     */
    public T getEnd() {
    	return end;
    }

    @Override
    /**
     * Getter method to get the label of the interval
     */
    public String getLabel() {
    	return label;
    }

    @Override
	/**
	 * Method to determine if this interval overlaps with another interval.
	 * Returns true if they do.
	 * @param other target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
    public boolean overlaps(IntervalADT<T> other) throws IllegalArgumentException{
    	if (other == null) throw new IllegalArgumentException();
    	if ((int) other.getEnd() < (int) end) return false;
    	if ((int) other.getStart() > (int) start) return false;
    	else return true;
    }

    @Override
	/**
	 * Returns true if given point is inside the interval.
	 * 
	 * @param point is given point to check to see if its in interval
	 * @return true if it contains the point
	 */
    public boolean contains(T point) {
    	if ( (int) point > (int) start && (int) point < (int) end) return true;
    	else return false;
    }

    @Override
	/**
	 * Compares this interval with the other and return a negative value 
	 * if this interval comes before the "other" interval.  Intervals 
	 * are compared first on their start time.  The end time is only considered
	 * if the start time is the same.
	 *
	 * @param other is the interval to compare to
	 *            
	 * @return negative if this interval's comes before the other interval, 
	 * positive if this interval comes after the other interval,
	 * and 0 if the intervals are the same.
	 */
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
