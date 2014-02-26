/**
 * 
 */
package edu.neu.madcourse.arpitmehta.wordgame;

/**
 * @author Arpit
 *
 */
public class GridCoordinate {
	/**
	 * The x coordinate
	 */
	private int x;
	
	/**
	 * The y coordinate
	 */
	private int y;
	
	/**
	 * Default constructor
	 */
	public GridCoordinate() {
		super();
	}
	
	public GridCoordinate(final int xval, final int yval) {
		super();
		
		this.x = xval;
		this.y = yval;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
