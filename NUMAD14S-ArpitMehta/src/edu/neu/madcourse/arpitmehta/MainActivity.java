package edu.neu.madcourse.arpitmehta;

import edu.neu.madcourse.arpitmehta.communication.CommunicationActivity;
import edu.neu.madcourse.arpitmehta.dictionary.DictionaryActivity;
import edu.neu.madcourse.arpitmehta.sudoku.Sudoku;
import edu.neu.madcourse.arpitmehta.twoplayerwordgame.TwoPlayerWordGameActivity;
import edu.neu.madcourse.arpitmehta.wordgame.WordGameActivity;
import edu.neu.madcourse.rajatmalhotra.trickiestpart.MultiTurnExerciseDialogActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 setTitle(R.string.myName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * showAboutActivity "About" activity handler function. Called when the user
	 * clicks "About" Button
	 * 
	 * @param view
	 *            Android View class
	 * 
	 * @return void
	 */
	public void showAboutActivity(View view) {
		// Intent to bind 'About' activity
		Intent aboutIntent = new Intent(this, AboutActivity.class);

		// start about activity.
		startActivity(aboutIntent);
	}

	/**
	 * generateError Generates error that causes the app to crash
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void generateError(View view) {
		// Division by 0 error.
		@SuppressWarnings("unused")
		int errVal = 1 / 0;
	}

	/**
	 * launchSudoku launch sudoku application
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void launchSudoku(View view) {
		// Intent to bind sudoku activity to Main Activity
		Intent sudokuIntent = new Intent(this, Sudoku.class);

		// Start Sudoku Activity
		startActivity(sudokuIntent);
	}

	/**
	 * launchDictionary Launch Test Dictionary Activity
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void launchDictionary(View view) {
		// Intent to bind Dictionary Activity to Main Activity
		Intent dictionaryIntent = new Intent(this, DictionaryActivity.class);

		// Start Activity
		startActivity(dictionaryIntent);
	}

	/**
	 * launchWordGame 
	 * 		Start New Word Game Activity
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void launchWordGame(View view) {
		Intent wordGameIntent = new Intent(this, WordGameActivity.class);
		startActivity(wordGameIntent);
	}
	
	public void launchTwoPlayerWordGame(View view) {
		Intent twoPlayerGameIntent = new Intent(this, TwoPlayerWordGameActivity.class);
		startActivity(twoPlayerGameIntent);
	}
	
	/**
	 * communicate 
	 * 		Start New Communication Activity
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void communicate(View view) {
		Intent commIntent = new Intent(this, CommunicationActivity.class);
		startActivity(commIntent);
	}
	
	public void handleTrickiestPartBtnClick(View view) {
		Intent trickiestPartIntent = new Intent(this, MultiTurnExerciseDialogActivity.class);
		startActivity(trickiestPartIntent);
	}

	/**
	 * quitApplication Quit
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void quitApplication(View view) {
		// Finish and exit.
		finish();
		System.exit(0);
	}

}
