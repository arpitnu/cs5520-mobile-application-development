package edu.neu.madcourse.arpitmehta.wordgame;

public class GameConstants {
	/**
	 * Number of rows in grid
	 */
	private static final int NUM_GRID_ROWS = 5;
	
	/**
	 * Number of columns in grid
	 */
	private static final int NUM_GRID_COLUMNS = 7;
	
	/**
	 * The alphabet set
	 */
	private static final String ALPHABET_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * @return the nUM_GRID_ROWS
	 */
	public static int getNumGridRows() {
		return NUM_GRID_ROWS;
	}

	/**
	 * @return the nUM_GRID_COLUMNS
	 */
	public static int getNumGridColumns() {
		return NUM_GRID_COLUMNS;
	}

	/**
	 * @return the alphabetSet
	 */
	public static String getAlphabetSet() {
		return ALPHABET_SET;
	}
}
