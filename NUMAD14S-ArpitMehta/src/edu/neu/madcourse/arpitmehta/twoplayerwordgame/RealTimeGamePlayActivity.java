package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.mhealth.api.KeyValueAPI;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

/**
 * RealTimeGamePlayActivity class
 * 
 * @author Arpit
 * 
 */
public class RealTimeGamePlayActivity extends Activity {

	/**
	 * LogoutAsyncTask - Logs the user out
	 * 
	 * @author Arpit
	 * 
	 */
	private class LogoutAsyncTask extends AsyncTask<String, Integer, Boolean> {
		
		@Override
		protected void onPostExecute(Boolean logoutStatus) {
			if(false == logoutStatus) {
				Toast.makeText(getApplicationContext(), "Server Unavailable.", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String loginUsernameKey = TwoPlayerWordGameProperties
					.getGamePropertiesInstance().getLoginUsernameKey();
			Boolean logoutDone = false;

			if (KeyValueAPI.isServerAvailable()) {
				KeyValueAPI.clearKey(TwoPlayerWordGameConstants.getTeamName(),
						TwoPlayerWordGameConstants.getPassword(),
						loginUsernameKey);
				logoutDone = true;
			}
			return logoutDone;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_real_time_game_play);

	}
	
	/**
	 * Display the real time two player game play instructions.
	 * 
	 * @param view {@link View}
	 * 
	 * @return void
	 */
	public void instructionsClickHandler(View view) {
		AlertDialog.Builder instructionsAlertBuilder = new AlertDialog.Builder(this);
		instructionsAlertBuilder.setTitle("Instructions");
		instructionsAlertBuilder.setMessage(R.string.realTimeGamePlayInstructionsText);
		
		// Create the AlertDialog Object
		AlertDialog instructionsAlertDialog = instructionsAlertBuilder.create();
		
		// Display the Alert Dialog
		instructionsAlertDialog.show();
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
		new LogoutAsyncTask().execute("Logout");

		Toast.makeText(getApplicationContext(), "User Logged Out",
				Toast.LENGTH_SHORT).show();
		finish();
	}
	
	/**
	 * Invite other user to play
	 * 
	 * @param view {@link View}
	 * 
	 * @return void
	 */
	public void inviteClickHandler(View view) {
		
	}
}
