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

/**
 * This class follows the IntervalADT and contains all methods and fields
 * necessary, including the start and end of it, and comparison methods for
 * needed uses
 * 
 * @authors see above
 *
 * @param <T>
 *            the type of interval start and end
 */
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

	// Start value of the Interval
	private T start;
	// End value of the Interval
	private T end;
	// Label for the Interval
	private String label;

	/**
	 * Constructor for the Interval Class, constructs a new instance of Interval
	 * 
	 * @param start
	 *            the start value for the interval
	 * @param end
	 *            the end value for the interval
	 * @param label
	 *            the label for the interval
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
	 * 
	 * @param other
	 *            target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
	public boolean overlaps(IntervalADT<T> other) {
		return start.compareTo(other.getEnd()) <= 0 && end.compareTo(other.getStart()) >= 0;
	}

	@Override
	/**
	 * Returns true if given point is inside the interval.
	 * 
	 * @param point
	 *            is given point to check to see if its in interval
	 * @return true if it contains the point
	 */
	public boolean contains(T point) {
		return start.compareTo(point) <= 0 && point.compareTo(end) <= 0;
	}

	@Override
	/**
	 * Compares this interval with the other and return a negative value if this
	 * interval comes before the "other" interval. Intervals are compared first
	 * on their start time. The end time is only considered if the start time is
	 * the same.
	 *
	 * @param other
	 *            is the interval to compare to
	 * 
	 * @return negative if this interval's comes before the other interval,
	 *         positive if this interval comes after the other interval, and 0
	 *         if the intervals are the same.
	 */
	public int compareTo(IntervalADT<T> other) {
		int ds = start.compareTo(other.getStart());
		return ds == 0 ? end.compareTo(other.getEnd()) : ds;
	}

	@Override
	public String toString() {
		return label + " [" + start + ", " + end + "]";
	}

}
