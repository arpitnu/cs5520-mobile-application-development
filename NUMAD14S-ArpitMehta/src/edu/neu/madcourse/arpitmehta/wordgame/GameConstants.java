package edu.neu.madcourse.arpitmehta.wordgame;

public class GameConstants {
	/**
	 * Number of rows in grid
	 */
	private static final int NUM_GRID_ROWS = 7;
	
	/**
	 * Number of columns in grid
	 */
	private static final int NUM_GRID_COLUMNS = 7;
	
	/**
	 * The alphabet set
	 */
	private static final String ALPHABET_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * The Vertical top & bottom padding on canvas
	 */
	private static final int GRID_PADDING_TOP = 150;
	
	/**
	 * The Bottom Padding Of Game control bitmaps
	 */
	private static final int BITMAP_PADDING_BOTTOM = 40;
	
	/**
	 * The Side padding of game control bitmaps
	 */
	private static final int BITMAP_PADDING_SIDE = 20;
	
	/**
	 * The number of bottom game control bitmaps
	 */
	private static final int NUM_BITMAPS = 4;
	
	/**
	 * The Top Padding for timer and score
	 */
	private static final int PADDING_TOP = 20;
	
	/** 
	 * The side padding for timer display text
	 */
	private static final int TIMER_PADDING_LEFT = 40;
	
	/**
	 * The Timer & Score text size
	 */
	private static final int TEXT_SIZE = 40;

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

	/**
	 * @return the gridPaddingTop
	 */
	public static int getGridPaddingTop() {
		return GRID_PADDING_TOP;
	}

	/**
	 * @return the bitmapPaddingBottom
	 */
	public static int getBitmapPaddingBottom() {
		return BITMAP_PADDING_BOTTOM;
	}

	/**
	 * @return the bitmapPaddingSide
	 */
	public static int getBitmapPaddingSide() {
		return BITMAP_PADDING_SIDE;
	}

	/**
	 * @return the numBitmaps
	 */
	public static int getNumBitmaps() {
		return NUM_BITMAPS;
	}

	/**
	 * @return the paddingTop
	 */
	public static int getPaddingTop() {
		return PADDING_TOP;
	}

	/**
	 * @return the timerPaddingLeft
	 */
	public static int getTimerPaddingLeft() {
		return TIMER_PADDING_LEFT;
	}

	/**
	 * @return the textSize
	 */
	public static int getTextSize() {
		return TEXT_SIZE;
	}
}
