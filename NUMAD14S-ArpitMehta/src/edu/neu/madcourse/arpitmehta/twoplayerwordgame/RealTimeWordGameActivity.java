package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;

import android.app.Activity;
import android.os.Bundle;

public class RealTimeWordGameActivity extends Activity {
	/**
	 * The Tag
	 */
	private static final String TAG = "RealTimeWordGameActivity";
	
	/**
	 * The Word Game View
	 */
	private RealTimeWordGameView gameView;
	
	/**
	 * The Puzzle
	 */
	private char puzzle[][];
	
	/**
	 * Puzzle String
	 */
	String puzzleString;
	
	/**
	 * BufferedReader for file input
	 */
	BufferedReader br;
	
	/**
	 * List of indexes selected
	 */
	ArrayList<GridCoordinate> indexesSelected = new ArrayList<GridCoordinate>();
	
	/**
	 * List of words
	 */
	HashSet<String> hashwordList = new HashSet<String>();
	
	/**
	 * List of words for hint
	 */
	HashSet<String> hashHintWordList = new HashSet<String>();
	
	/**
	 * The final Score
	 */
	int finalScore;
	
	private final String epuzzleString = "HAPPYNGREATERHKITCHENMONEYRBURGERS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		new 
	}
}
