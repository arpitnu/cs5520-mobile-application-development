package edu.neu.madcourse.arpitmehta.wordgame;

import edu.neu.madcourse.arpitmehta.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class WordGameActivity extends Activity implements OnClickListener {
	
	/**
	 * The TAG for WordGameActivity
	 */
	private static final String TAG = "Word Game";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_game);
		
		// Show the Up button in the action bar.
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.word_game, menu);
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
		case R.id.wordGameSettings:
			Intent wordGamePrefsIntent = new Intent(this, WordGamePrefs.class);
			startActivity(wordGamePrefsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void launchnewWordGame(View v) {
		Log.d(TAG, "Launching New Word Game");
		Intent letrisGameIntent = new Intent(this, LetrisGame.class);
		// TODO implement difficulty
		startActivity(letrisGameIntent);
	}
	
	/**
	 * launchWordGameAckActivity
	 * 		Display Word Game Acknowledgments
	 * 
	 * @param View v
	 * 
	 * @return void
	 */
	public void launchWordGameAckActivity(View v) {
		Intent wordGameAckIntent = new Intent(this, WordGameAckActivity.class);
		startActivity(wordGameAckIntent);
	}
	
	/**
	 * exitWordGame
	 * 		Exit Word Game and return to main menu of the App
	 * 
	 * @param View v
	 * 
	 * @return void
	 */
	public void exitWordGame(View v) {
		finish();
	}

}
