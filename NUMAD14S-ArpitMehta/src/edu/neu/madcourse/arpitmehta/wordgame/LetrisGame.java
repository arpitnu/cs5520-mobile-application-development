package edu.neu.madcourse.arpitmehta.wordgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.neu.madcourse.arpitmehta.R;

public class LetrisGame extends Activity {
	
	/**
	 * Flag to hold the game state
	 */
	private boolean isGameRunning;

	/**
	 * The tag for LetrisGame
	 */
	private static final String TAG = "LetrisGame";

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
	 * Vibrator Object
	 */
	private Vibrator v;

	/**
	 * The list of words from a file selected on the basis of first 2 characters
	 */
	private ArrayList<String> searchWordLst = new ArrayList<String>();

	/**
	 * The AssetsManager
	 */
	private AssetManager am;

	/**
	 * File Input Stream
	 */
	private InputStream inStreamFileToSearch;

	/**
	 * The Buffered Reader
	 */
	private BufferedReader brFileToSearch;
	
	private static final int NEW_GAME = 1;
	
	private static final int CONTINUE_GAME = -1;

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
		gameTimer = new GameTimer(gameView, GameConstants.getTimerDuration(),
				GameConstants.getTimerTickDuration());

		// Initialize Vibrator
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		// Initialize AssetManager
		am = getAssets();
	}

	/**
	 * getPuzzle 
	 * 		Function generates and returns a new puzzle.
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
		selCharList.clear();
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

		selCharList.clear();

		finish();
	}

	/**
	 * processTile Process the tile touched by the user
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return void
	 */
	public void processTile(int x, int y) {
		// Get alphabet of tile
		Character c = Character.valueOf(letrisPuzzle[y
				* GameConstants.getNumGridRows() + x]);

		Log.d(TAG, "Character in tile: " + Character.toString(c));

		selCharList.add(c);

		v.vibrate(GameConstants.getVibrationDuration());

		StringBuilder sb = new StringBuilder();
		Iterator<Character> it = selCharList.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
		}

		selectedWord = sb.toString();

		// Check if the new word is valid
		if (selectedWord.length() == 2) {
			// Load words for search
			loadFileWords(selectedWord);
		} else if (selectedWord.length() > 2) {
			// Search word
			searchWord(selectedWord);
		} else {
			// Do Nothing
		}
	}

	/**
	 * searchWord Function searches the word in the word list and displays it
	 * 
	 * @param word
	 * 
	 * @param none
	 * 
	 * @return void
	 */
	protected void searchWord(final String word) {
		if (searchWordLst.contains(word.toLowerCase().toString())) {
			Log.d(TAG, "valid word found!");
			// Indicate to gameView that valid word is found
			gameView.setValidWordFound(true);

			if (!validSelectedWords.contains(word)) {
				validSelectedWords.add(word);
			} else {
				Log.d(TAG, "valid word already selected before");
			}
		}
	}

	/**
	 * loadFileContents Function searches the word in the word list and displays
	 * it
	 * 
	 * @param word
	 * 
	 * @param none
	 * 
	 * @return void
	 */
	private void loadFileWords(final String word) {
		String fileName = new String("dictionary/"
				+ word.substring(0, 2).toLowerCase().toString() + ".txt");
		String currWord = new String();
		
		Log.d(TAG, "Filename: " + fileName);

		// Clear word list
		searchWordLst.clear();

		try {
			inStreamFileToSearch = am.open(fileName);
			brFileToSearch = new BufferedReader(new InputStreamReader(
					inStreamFileToSearch));

			while (null != (currWord = brFileToSearch.readLine())) {
				searchWordLst.add(currWord);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
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
	 * @param gameScore
	 *            the gameScore to set
	 */
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	/**
	 * @return the selCharList
	 */
	public ArrayList<Character> getSelCharList() {
		return selCharList;
	}

	/**
	 * @param selCharList
	 *            the selCharList to set
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
	 * @param selectedWord
	 *            the selectedWord to set
	 */
	public void setSelectedWord(String selectedWord) {
		this.selectedWord = selectedWord;
	}

	public void startMusic() {
		// Play word game music
		WordGameMusic.play(getApplicationContext(), R.raw.word_game);
	}

	public void pauseMusic() {
		WordGameMusic.stop(this);
	}

	public void modifyScore(int length) {
		gameScore = gameScore + length * GameConstants.getCharacterValue();
	}

	public void resetWordSearchParams() {
		selCharList.clear();
		setSelectedWord(new String());
	}

	/**
	 * @return the isGameRunning
	 */
	public boolean isGameRunning() {
		return isGameRunning;
	}

	/**
	 * @param isGameRunning the isGameRunning to set
	 */
	public void setGameRunning(boolean isGameRunning) {
		this.isGameRunning = isGameRunning;
	}
}
