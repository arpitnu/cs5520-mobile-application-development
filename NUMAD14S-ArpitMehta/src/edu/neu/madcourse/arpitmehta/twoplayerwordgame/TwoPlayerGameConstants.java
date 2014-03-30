package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

public class TwoPlayerGameConstants {
	/**
	 * Number of rows in grid
	 */
	static final int NUM_GRID_ROWS = 5;
	
	/**
	 * Number of columns in grid
	 */
	static final int NUM_GRID_COLUMNS = 7;
	
	/**
	 * The alphabet set
	 */
	static final String ALPHABET_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * The Vertical top & bottom padding on canvas
	 */
	static final int GRID_PADDING_TOP = 100;
	
	/**
	 * The Bottom Padding Of Game control bitmaps
	 */
	static final int BITMAP_PADDING_BOTTOM = 20;
	
	/**
	 * The Side padding of game control bitmaps
	 */
	static final int BITMAP_PADDING_SIDE = 40;
	
	/**
	 * The number of bottom game control bitmaps
	 */
	static final int NUM_BITMAPS = 3;
	
	/**
	 * The Top Padding for timer and score
	 */
	static final int PADDING_TOP = 20;
	
	/** 
	 * The side padding for timer display text
	 */
	static final int TIMER_PADDING_LEFT = 40;
	
	/**
	 * The Timer & Score text size
	 */
	static final int TEXT_SIZE = 40;
	
	/**
	 * The Game Timer Duration in ms
	 */
	static final long TIMER_DURATION = 180000; 	// 3 minutes
	
	/**
	 * The timer tick interval in ms
	 */
	static final long TIMER_TICK_DURATION = 1000; 	// 1sec
	
	/**
	 * Vibration duration in ms
	 */
	static final int VIBRATION_DURATION = 30;
	
	/**
	 * The padding below the displayed word
	 */
	static final int WORD_PADDING_BOTTOM = 10;
	
	/**
	 * The displayed word size
	 */
	static final int WORD_SIZE = 40;
	
	/**
	 * The score value of each character
	 */
	static final int CHARACTER_VALUE = 10;

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

	/**
	 * @return the timerDuration
	 */
	public static long getTimerDuration() {
		return TIMER_DURATION;
	}

	/**
	 * @return the timerTickDuration
	 */
	public static long getTimerTickDuration() {
		return TIMER_TICK_DURATION;
	}

	/**
	 * @return the vibrationDuration
	 */
	public static int getVibrationDuration() {
		return VIBRATION_DURATION;
	}

	/**
	 * @return the wordPaddingBottom
	 */
	public static int getWordPaddingBottom() {
		return WORD_PADDING_BOTTOM;
	}

	/**
	 * @return the wordSize
	 */
	public static int getWordSize() {
		return WORD_SIZE;
	}

	/**
	 * @return the characterValue
	 */
	public static int getCharacterValue() {
		return CHARACTER_VALUE;
	}
}
