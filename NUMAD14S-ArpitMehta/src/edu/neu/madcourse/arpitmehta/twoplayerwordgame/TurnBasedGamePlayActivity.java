package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import edu.neu.madcourse.arpitmehta.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;

/**
 * TurnBasedGamePlayActivity Class
 * 
 * @author Arpit
 *
 */

public class TurnBasedGamePlayActivity extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_turn_based_game_play);
	}
	
	/**
	 * Log the user out and exit
	 * 
	 * @param view
	 *            {@link View}
	 * 
	 * @return void
	 */
	public void logoutClickHandler(View view) {
		// Log the user out and exit
//		new LogoutAsyncTask().execute("Logout");

		Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_SHORT)
				.show();
		finish();
	}
	
	/**
	 * Display the turn based two player game play instructions.
	 * 
	 * @param view
	 *            {@link View}
	 * 
	 * @return void
	 */
	public void instructionsClickHandler(View view) {
		AlertDialog.Builder instructionsAlertBuilder = new AlertDialog.Builder(
				this);

		// Set title of the alert
		instructionsAlertBuilder.setTitle("Instructions");

		// Set message of the alert.
		instructionsAlertBuilder
				.setMessage(R.string.turnBasedGamePlayInstructionsText);

		// Create the AlertDialog Object
		AlertDialog instructionsAlertDialog = instructionsAlertBuilder.create();

		// Display the Alert Dialog
		instructionsAlertDialog.show();
	}
}
