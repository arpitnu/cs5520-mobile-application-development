package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import edu.neu.madcourse.arpitmehta.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class TwoPlayerWordGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_player_word_game);
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
	
	/**
	 * Starts the Synchronous (Real-Time) Two Player Word Game Play Activity
	 * 
	 * @param view {@link View}
	 * 
	 * @return void
	 */
	public void launchSyncGamePlayActivity(View view) {
		Intent syncGamePlayIntent = new Intent(this, SyncGamePlayActivity.class);
		startActivity(syncGamePlayIntent);
	}
	
	/**
	 * Starts the Asynchronous (Turn-Based) Two Player Word Game Play Activity
	 * 
	 * @param view {@link View}
	 * 
	 * @return void
	 */
	public void launchAsyncGamePlayActivity(View view) {
//		Intent asyncGamePlayIntent = new Intent(this, AsyncGamePlayActivity.class);
//		startActivity(asyncGamePlayIntent);
	}
	
	/**
	 * Launches the Two Player Word Game Acknowledgements Activity
	 * 
	 * @param view {@link View}
	 * 
	 * @return void
	 */
	public void launchTwoPlayerWordGameAckActivity(View view) {
		Intent twoPlayerGameAckIntent = new Intent(this, TwoPlayerWordGameAckActivity.class);
		startActivity(twoPlayerGameAckIntent);
	}
	
	/**
	 * Quit the two player word game
	 * 
	 * @param view {@link View}
	 * 
	 * @return void
	 */
	public void quitTwoPlayerWordGame(View view) {
		finish();
	}

}
