package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.mhealth.api.KeyValueAPI;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SyncGamePlayActivity extends Activity {

	/**
	 * The Progress Bar
	 */
	ProgressBar pb;

	/**
	 * The EditText UI element to read the user name
	 */
	EditText etUsername;

	public enum SERVER_RESPONSE {
		USERNAME_REGISTERED, USERNAME_REQUIRED, USERNAME_EXISTS, SERVER_UNAVAILABLE
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync_game_play);

		etUsername = (EditText) findViewById(R.id.etTwoPlayerGameUsername);
		pb = (ProgressBar) findViewById(R.id.loginProgressBar);
		pb.setProgress(0);
	}
	
	/**
	 * Exit Login
	 * 
	 * @param view {@link View}
	 */
	public void quitLogin(View view) {
		finish();
	}

	/**
	 * Logs the user in using the username and begins the game play
	 * 
	 * @param view
	 *            {@link View}
	 * 
	 * @return void
	 */
	public void loginButtonClickHandler(View view) {
		// Begin the user login async task
		new AsyncTask<String, Integer, Integer>() {

			@Override
			protected void onPreExecute() {

			}

			@Override
			protected void onPostExecute(Integer result) {
				switch (result) {
				case 3:
					Toast.makeText(getApplicationContext(),
							"Server Unvailable.", Toast.LENGTH_SHORT).show();
					break;

				case 2:
					Toast.makeText(
							getApplicationContext(),
							"Username already exists. Please enter different Username.",
							Toast.LENGTH_SHORT).show();
					break;

				case 1:
					Toast.makeText(getApplicationContext(), "Enter Username.",
							Toast.LENGTH_SHORT).show();
					break;

				case 0:
				default:
					// Begin Real Time Game Play
					Intent realTimeGamePlayIntent = new Intent(
							SyncGamePlayActivity.this,
							RealTimeGamePlayActivity.class);
					startActivity(realTimeGamePlayIntent);
					break;
				}
			}

			@Override
			protected void onProgressUpdate(Integer... progress) {

			}

			@Override
			protected Integer doInBackground(String... params) {
				// Get the username
				String uname = etUsername.getText().toString();
				boolean unameExists = false;
				int loggedInUserCnt = 1;
				Integer retValue = 0;
				String loggedInUser = null;

				if (KeyValueAPI.isServerAvailable()) {
					// Check if the user is already logged in
					loggedInUser = KeyValueAPI.get("arpitm", "qwerty", "User: "
							+ Integer.toString(loggedInUserCnt));
					while (!loggedInUser.equals("Error: No Such Key")) {
						if (uname.equals(loggedInUser)) {
							unameExists = true;
						}

						loggedInUserCnt++;
					}
				} else {
					retValue = 3;
				}

				if (false == unameExists) {
					if (uname != "") {
						// Put the user name in the KeyValueAPI mHealth server
						KeyValueAPI.put("arpitm", "qwerty",
								"User: " + Integer.toString(loggedInUserCnt),
								uname);
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.setLoginUsername(uname);
						TwoPlayerWordGameProperties
								.getGamePropertiesInstance()
								.setLoginUsernameKey(
										"User: "
												+ Integer
														.toString(loggedInUserCnt));
						retValue = 0;
					} else {
						retValue = 1;
					}
				} else {
					retValue = 2;
				}

				return retValue;
			}

		}.execute("log in");
	}
}
