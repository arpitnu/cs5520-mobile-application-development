package edu.neu.madcourse.arpitmehta.wordgame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import edu.neu.madcourse.arpitmehta.R;

public class LetrisGame extends Activity {

	/**
	 * The tag for LetrisGame
	 */
	private static final String TAG = "Letris";

	/**
	 * The puzzle reference
	 */
	private static final String PREF_PUZZLE = "letris_puzzle";

	/**
	 * The game view
	 */
	private LetrisGameView gameView;

	/**
	 * The Letris puzzle character array
	 */
	private char letrisPuzzle[];
	
	/**
	 * The list of characters selected
	 */
	private ArrayList<Character> selCharList = new ArrayList<Character>();

	/**
	 * The list of valid words selected
	 */
	private ArrayList<String> validSelectedWords = new ArrayList<String>();
	
	/**
	 * Current word selected
	 */
	private String selectedWord = new String();
	
	/**
	 * The game timer
	 */
	private GameTimer gameTimer;
	
	/**
	 * The game score
	 */
	private int gameScore = 0;
	
	/**
	 * Int value to indicate the number of character (tiles) touched.
	 */
	private int numCharsSelected = 0;
	
	/**
	 * Vibrator Object
	 */
	private Vibrator v;
 	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		// TODO Game difficulty
		
		// Initialize Puzzle
		letrisPuzzle = getPuzzle();
		
		// Initialize Game View
		gameView = new LetrisGameView(this);

		setContentView(gameView);
		
		// Initialize Game Timer
		gameTimer = new GameTimer(gameView, GameConstants.getTimerDuration(), GameConstants.getTimerTickDuration());
		
		// Initialize Vibrator
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}

	/**
	 * getPuzzle Function returns a new puzzle.
	 * 
	 * @param none
	 * 
	 * @return void
	 */
	private char[] getPuzzle() {
		int num_cells = GameConstants.getNumGridRows()
				* GameConstants.getNumGridColumns();
		char[] puz = new char[num_cells];
		Random rand = new Random();
		String alphabet = GameConstants.getAlphabetSet();

		for (int i = 0; i < num_cells; i++) {
			puz[i] = alphabet.charAt(rand.nextInt(alphabet.length()));
		}

		// Log the generated puzzle
		Log.d(TAG, puz.toString());

		return puz;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letris, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.d(TAG, "onResume");

		// Play word game music
		WordGameMusic.play(getApplicationContext(), R.raw.word_game);
	}

	@Override
	protected void onPause() {
		super.onPause();

		Log.d(TAG, "onPause");

		// Stop word game music
		WordGameMusic.stop(this);

		// Save the current puzzle
		getPreferences(MODE_PRIVATE).edit()
				.putString(PREF_PUZZLE, toPuzzleString(letrisPuzzle)).commit();
	}

	@Override
	protected void onStop() {
		super.onStop();

		Log.d(TAG, "onStop");

		// Clear the list of valid words
		validSelectedWords.clear();
	}

	/**
	 * toPuzzleString Convert an array into a puzzle string
	 * 
	 * @param puzzle
	 * 
	 * @return String
	 */
	private String toPuzzleString(char[] puzzle) {
		StringBuilder buf = new StringBuilder();
		for (char c : puzzle) {
			buf.append(c);
		}
		return buf.toString();
	}

	/**
	 * getTileString Return a string for the tile at the given coordinates
	 * 
	 * @param i
	 * @param j
	 * 
	 * @return void
	 */
	public String getTileString(int x, int y) {
		Character c = getTile(x, y);

		return String.valueOf(c);
	}

	/**
	 * getTile Return the tile at the given coordinates
	 * 
	 * @param x
	 *            int
	 * @param y
	 * 
	 * @return Character
	 */
	private Character getTile(int x, int y) {
		return (Character.valueOf(letrisPuzzle[y
				* GameConstants.getNumGridRows() + x]));
	}

	/**
	 * setTile Change the tile at the given coordinates
	 * 
	 * @param x
	 * @param y
	 * @param value
	 * 
	 * @return void
	 */
	private void setTile(int x, int y, char value) {
		letrisPuzzle[y * GameConstants.getNumGridRows() + x] = value;
	}
	
	/**
	 * Quit Game
	 */
	public void quitGame() {
		Log.d(TAG, "User request to quit game");

		// Clear the list of valid words
		validSelectedWords.clear();
		
		finish();	
	}
	
	/**
	 * processTile
	 * 		Process the tile touched by the user		
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return void
	 */
	public void processTile(int x, int y) {
		// Get alphabet of tile
		Character c = Character.valueOf(letrisPuzzle[y * GameConstants.getNumGridRows() + x]);
		
		Log.d(TAG, "Character in tile: " + Character.toString(c));
		
		selCharList.add(c);
		
		v.vibrate(GameConstants.getVibrationDuration());
		
		StringBuilder sb = new StringBuilder();
		Iterator<Character> it = selCharList.iterator();
		while(it.hasNext()) {
			sb.append(it.next());
		}
		
		selectedWord = sb.toString();
		
		numCharsSelected++;
		
	}
	
	/**
	 * Start the game timer
	 */
	public void startTimer() {
		gameTimer.start();
	}
	
	/**
	 * Pause the game timer
	 */
	public void pauseTimer() {
		gameTimer.cancel();
	}
	
	/**
	 * Resume timer count
	 */
	public void resumeTimer() {
		gameTimer.start();
	}

	/**
	 * @return the gameScore
	 */
	public int getGameScore() {
		return gameScore;
	}

	/**
	 * @param gameScore the gameScore to set
	 */
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	/**
	 * @return the numCharsSelected
	 */
	public int getNumCharsSelected() {
		return numCharsSelected;
	}

	/**
	 * @param numCharsSelected the numCharsSelected to set
	 */
	public void setNumCharsSelected(int numCharsSelected) {
		this.numCharsSelected = numCharsSelected;
	}

	/**
	 * @return the selCharList
	 */
	public ArrayList<Character> getSelCharList() {
		return selCharList;
	}

	/**
	 * @param selCharList the selCharList to set
	 */
	public void setSelCharList(ArrayList<Character> selCharList) {
		this.selCharList = selCharList;
	}

	/**
	 * @return the selectedWord
	 */
	public String getSelectedWord() {
		return selectedWord;
	}

	/**
	 * @param selectedWord the selectedWord to set
	 */
	public void setSelectedWord(String selectedWord) {
		this.selectedWord = selectedWord;
	}

	

}
