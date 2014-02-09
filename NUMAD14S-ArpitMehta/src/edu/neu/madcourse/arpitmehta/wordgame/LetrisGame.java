package edu.neu.madcourse.arpitmehta.wordgame;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
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
	 * The list of valid words selected
	 */
	ArrayList<String> validSelectedWords = new ArrayList<String>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		// TODO Game difficulty

		letrisPuzzle = getPuzzle();
		gameView = new LetrisGameView(this);

		setContentView(gameView);
		
		setupActionBar();
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

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
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

		// Character c = null;
		// try {
		// c = Character.valueOf(letrisPuzzle[y *
		// GameConstants.getNumGridColumns() + x]);
		// } catch (IndexOutOfBoundsException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// return c;
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

	public void processTile(int selX, int selY) {
		// TODO Auto-generated method stub
		
	}

	

}
