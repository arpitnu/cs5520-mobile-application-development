package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

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
	
	@Override
	public int hashCode() {
		final int prime = 59;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridCoordinate other = (GridCoordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
