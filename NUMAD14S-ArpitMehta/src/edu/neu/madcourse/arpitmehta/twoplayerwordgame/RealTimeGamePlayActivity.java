package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.neu.madcourse.arpitmehta.R;
import edu.neu.mhealth.api.KeyValueAPI;

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
	private static class LogoutAsyncTask extends
			AsyncTask<String, Integer, Boolean> {

		@Override
		protected void onPostExecute(Boolean logoutStatus) {
			if (false == logoutStatus) {
				Toast.makeText(appContext, "Server Unavailable.",
						Toast.LENGTH_LONG).show();
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

	private static class InviteAsyncTask extends
			AsyncTask<String, Integer, Integer> {

		@Override
		protected void onPostExecute(Integer inviteResult) {
			switch (inviteResult) {
			case 3:
				// Server Unavailable
				Toast.makeText(appContext, "Server Unavailable.",
						Toast.LENGTH_SHORT).show();
				break;

			case 2:
				// Opponent not online
				Toast.makeText(appContext, "Opponent user not online.",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:
				// Opponent user is online
				Toast.makeText(appContext,
						"Invitation sent. Waiting for opponent to accept...",
						Toast.LENGTH_LONG).show();
				break;

			case 0:
			default:
				// User inviting himself/herself to play
				Toast.makeText(appContext,
						"You're inviting yourself to play. Not allowed.",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			String opponentUname = params[0].toString();
			Integer returnVal = 0;

			// Check if the user is not inviting himself
			if (opponentUname.equals(TwoPlayerWordGameProperties
					.getGamePropertiesInstance().getLoginUsername())) {
				returnVal = 0;
			} else {
				int index = 1;
				Boolean isInvitedUserOnline = false;
				String userOnServer = null;
				String inviterUser = TwoPlayerWordGameProperties
						.getGamePropertiesInstance().getLoginUsername();

				if (KeyValueAPI.isServerAvailable()) {
					userOnServer = KeyValueAPI.get(
							TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(), "User: "
									+ Integer.toString(index));
					while (!userOnServer.equals("Error: No Such User Key")) {
						if (userOnServer.equals(opponentUname)) {
							isInvitedUserOnline = true;
							break;
						}

						index++;
					}

					if (false != isInvitedUserOnline) {
						KeyValueAPI.put(
								TwoPlayerWordGameConstants.getTeamName(),
								TwoPlayerWordGameConstants.getPassword(),
								opponentUname, "is invited");
						KeyValueAPI.put(
								TwoPlayerWordGameConstants.getTeamName(),
								TwoPlayerWordGameConstants.getPassword(),
								opponentUname + "is invited by", inviterUser);
						// KeyValueAPI.put(
						// TwoPlayerWordGameConstants.getTeamName(),
						// TwoPlayerWordGameConstants.getPassword(),
						// "invitee: ", opponentUname);
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.setOpponentUsername(opponentUname);

						// Opponent user is online
						returnVal = 1;
					} else {
						// Opponent user not online
						returnVal = 2;
					}
				} else {
					// Server unavailable
					returnVal = 3;
				}
			}
			return returnVal;
		}
	}

	private static class SentRequestAsyncTask extends
			AsyncTask<String, Integer, Integer> {

		@Override
		protected void onPostExecute(Integer sentRequestResult) {
			switch (sentRequestResult) {
			case -1:
				// Do Nothing
				break;

			case 2:
				// Server Unavailable
				Toast.makeText(appContext, "Server Unavailable.",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:
				// Sent request accepted by opponent
				// TODO
				// Intent realTimeWordGameIntent = new
				// Intent(RealTimeGamePlayActivity.this,
				// RealTimeWordGame.class);
				break;

			default:
				Log.e(TAG, "SentRequestAsyncTask: Unexpected Return Value");
				break;
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			Integer returnVal;

			if (false == isSentGameRequestAccepted) {
				if (KeyValueAPI.isServerAvailable()) {
					// Server Available. Check for status of sent requests
					if ((KeyValueAPI.get(TwoPlayerWordGameConstants
							.getTeamName(), TwoPlayerWordGameConstants
							.getPassword(), "Game Between "
							+ TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getLoginUsername()
							+ " + "
							+ TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getOpponentUsername()).equals("true"))
							|| (KeyValueAPI.get(TwoPlayerWordGameConstants
									.getTeamName(), TwoPlayerWordGameConstants
									.getPassword(), "Game Between "
									+ TwoPlayerWordGameProperties
											.getGamePropertiesInstance()
											.getOpponentUsername()
									+ " + "
									+ TwoPlayerWordGameProperties
											.getGamePropertiesInstance()
											.getLoginUsername()).equals("true"))) {
						// Set sent request accepted flag
						isSentGameRequestAccepted = true;

						KeyValueAPI.put(TwoPlayerWordGameConstants
								.getTeamName(), TwoPlayerWordGameConstants
								.getPassword(), TwoPlayerWordGameProperties
								.getGamePropertiesInstance().getLoginUsername()
								+ " status: ", "playing");

						// Game request accepted by opponent
						returnVal = 1;
					} else {
						// Game request not accepted by opponent
						returnVal = 0;
					}
				} else {
					// Server unavailable
					returnVal = 2;
				}
			} else {
				// Sent Request Already Accepted by another user
				returnVal = -1;
			}
			return returnVal;
		}
	}

	private class ReceivedRequstAsyncTask extends
			AsyncTask<String, Integer, Integer> {

		private String inviterUser;

		@Override
		protected void onPostExecute(Integer rcvdRequestResult) {
			switch (rcvdRequestResult) {
			case -1:
				// Do nothing
				break;

			case 3:
				// Server Unavailable
				Toast.makeText(appContext, "Server Unavailable.",
						Toast.LENGTH_SHORT).show();
				break;

			case 2:
				Toast.makeText(appContext, "Connection Error",
						Toast.LENGTH_SHORT).show();

				// Clear Invitation Requests
				new ClearInvitesTask().execute("Clear Inivitations");
				break;

			case 1:
				if (false != isGameRequestReceived) {
					// TODO for testing
					// Toast.makeText(getApplicationContext(),
					// "Game Request Received", Toast.LENGTH_SHORT).show();

					AlertDialog.Builder acceptInviteDialogAlertBuilder = new AlertDialog.Builder(
							RealTimeGamePlayActivity.this);

					// The alert dialog click listener
					DialogInterface.OnClickListener alertDialogListener = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case DialogInterface.BUTTON_POSITIVE:
								// Clear Invitation Requests
								new ClearInvitesTask()
										.execute("Clear Inivitations");

								// Set properties
								TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.setIsInvited(true);
								TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.setInvitedByUsername(inviterUser);

								// Store the Game ID
								saveGameId();

								// Begin game once the invitation is accepted
								new InviteAcceptedAsyncTask().execute("");

								break;

							case DialogInterface.BUTTON_NEGATIVE:
								isGameRequestReceived = false;

								// Clear Invitation Requests
								new ClearInvitesTask()
										.execute("Clear Inivitations");
								break;

							default:
								break;
							}
						}
					};

					acceptInviteDialogAlertBuilder
							.setMessage(inviterUser + " invites you to play.")
							.setPositiveButton("Accept", alertDialogListener)
							.setNegativeButton("Reject", alertDialogListener)
							.show();
				}
				break;

			default:
				Log.e(TAG, "ReceivedRequstAsyncTask: Unexpected Return Value");
				break;
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			Integer returnVal = null;

			if (isGameRequestReceived == false) {
				if (KeyValueAPI.isServerAvailable()) {
					String myInviationStatus = KeyValueAPI.get(
							TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(),
							TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getLoginUsername());

					if (myInviationStatus.equals("is invited")) {
						inviterUser = KeyValueAPI.get(
								TwoPlayerWordGameConstants.getTeamName(),
								TwoPlayerWordGameConstants.getPassword(),
								TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.getLoginUsername()
										+ "is invited by");

						if (!inviterUser.contains("Error")) {
							isGameRequestReceived = true;
							returnVal = 1;
						} else {
							// Error
							returnVal = 2;
						}
					}
					else {
						returnVal = -1;
					}
				} else {
					// Server Unavailable
					returnVal = 3;
				}
			} else {
				// Game Request Already Recieved
				returnVal = -1;
			}

			return returnVal;
		}

	}

	private class InviteAcceptedAsyncTask extends
			AsyncTask<String, Integer, Integer> {

		@Override
		protected void onPostExecute(Integer accpetResult) {
			if (accpetResult == 1) {
				Toast.makeText(appContext,
						"Unable to confirm acceptance. Server Unavailable.",
						Toast.LENGTH_SHORT).show();
			} else if (accpetResult == 0) {
				// DO Nothing
			} else {
				Log.e(TAG, "InviteAcceptedAsyncTask: Unexpected return value");
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			Integer returnVal = null;

			if (KeyValueAPI.isServerAvailable()) {
				KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
						TwoPlayerWordGameConstants.getPassword(),
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.getLoginUsername()
								+ "accepted invitation from"
								+ TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.getInvitedByUsername(), "true");

				returnVal = 0;
			} else {
				// Server Unavailable
				returnVal = 1;
			}

			return returnVal;
		}

	}

	/**
	 * Save the game ID
	 */
	protected void saveGameId() {
		new AsyncTask<String, Integer, Integer>() {

			@Override
			protected void onPostExecute(Integer result) {

			}

			@Override
			protected Integer doInBackground(String... params) {
				Integer returnVal = null;

				if (KeyValueAPI.isServerAvailable()) {
					String key = TwoPlayerWordGameProperties
							.getGamePropertiesInstance().getLoginUsername()
							+ TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getInvitedByUsername() + "gameId";
					String gameid = TwoPlayerWordGameProperties
							.getGamePropertiesInstance().getLoginUsername()
							+ TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getInvitedByUsername() + "gameId";

					Log.d(TAG, "Game ID: " + gameid);

					KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(), key,
							gameid);

					TwoPlayerWordGameProperties.getGamePropertiesInstance()
							.setIsGameIdGenerated(true);
					TwoPlayerWordGameProperties.getGamePropertiesInstance()
							.setGameId(gameid);
				} else {
					// Server Unavailable
					returnVal = 0;
				}

				return returnVal;
			}
		}.execute("");
	}

	private static class ClearInvitesTask extends
			AsyncTask<String, Integer, Integer> {

		@Override
		protected void onPostExecute(Integer clearInvitesResult) {
			if (clearInvitesResult == 1) {
				Toast.makeText(appContext,
						"Server Unavailable. Unable to clear invite.",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			Integer returnVal = null;

			if (KeyValueAPI.isServerAvailable()) {
				KeyValueAPI.clearKey(TwoPlayerWordGameConstants.getTeamName(),
						TwoPlayerWordGameConstants.getPassword(),
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.getLoginUsername());

				KeyValueAPI.clearKey(TwoPlayerWordGameConstants.getTeamName(),
						TwoPlayerWordGameConstants.getPassword(),
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.getLoginUsername() + "is invited by");

				returnVal = 0;
			} else {
				// Server Unavailable
				returnVal = 1;
			}

			return returnVal;
		}

	}

	/**
	 * The Application Context
	 */
	private static Context appContext;

	/**
	 * Flag indicates if a sent game play request is accepted by opponent
	 */
	static Boolean isSentGameRequestAccepted = false;

	/**
	 * Flag to indicate if the logged in user has accepted an opponent's request
	 */
	static Boolean isGameRequestReceived = false;

	/**
	 * Flag to indicate my online status (i.e, if the RealTimeGamePlayActivity
	 * is in front)
	 */
	Boolean myOnlineStatus;

	/**
	 * Sent Game Play Requests Handler
	 */
	Handler sentRequestHandler;

	/**
	 * Received Game Play Requests Handler
	 */
	Handler rcvdRequestHandler;

	/**
	 * The Activity Tag
	 */
	protected final static String TAG = "RealTimeGamePlayActivity";

	/**
	 * sentRequestCheckRunnable
	 */
	private Runnable sentRequestCheckRunnable = new Runnable() {

		@Override
		public void run() {
			Log.d(TAG, "Running sentRequestHandler Runnable");

			SentRequestAsyncTask sentRequestAsyncTask = new SentRequestAsyncTask();

			if ((false != myOnlineStatus)
					&& (false == isSentGameRequestAccepted)) {
				sentRequestAsyncTask.execute("Checking Sent Game Requests");
			}
		}
	};

	/**
	 * rcvdRequestCheckRunnable
	 */
	private Runnable rcvdRequestCheckRunnable = new Runnable() {

		@Override
		public void run() {
			Log.d(TAG, "Running rcvdRequestHandler Runnable");

			ReceivedRequstAsyncTask rcvdRequstAsyncTask = new ReceivedRequstAsyncTask();

			if ((false != myOnlineStatus) && (false == isGameRequestReceived)) {
				try {
					rcvdRequstAsyncTask.execute("Checking Received Game Requests");
				} catch (IllegalStateException e) {
					Log.d(TAG, "IllegalStateException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	};

	/**
	 * RealTimeGamePlayActivity onCreate
	 * 
	 * @param savedInstanceState
	 *            {@link Bundle}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_real_time_game_play);

		appContext = getApplicationContext();

		// Display the login user name
		String loginUname = TwoPlayerWordGameProperties
				.getGamePropertiesInstance().getLoginUsername();
		TextView tvLoginUsername = (TextView) findViewById(R.id.tvLoggedInUsername);
		tvLoginUsername.setText("User Logged In As: " + loginUname);

		// Set properties
		TwoPlayerWordGameProperties.getGamePropertiesInstance().setIsInvited(
				false);
		TwoPlayerWordGameProperties.getGamePropertiesInstance()
				.setIsGameIdGenerated(false);

		// No requests accepted to start with
		isSentGameRequestAccepted = false;

		// Handlers
		handleReceivedGamePlayRequests();
		handleSentGamePlayRequests();
	}

	private void handleSentGamePlayRequests() {
		sentRequestHandler = new Handler();

		// Schedule Sent Requests Runnable every 1000 ms
		sentRequestHandler.postDelayed(sentRequestCheckRunnable, 1000);
	}

	private void handleReceivedGamePlayRequests() {
		rcvdRequestHandler = new Handler();

		rcvdRequestHandler.postDelayed(rcvdRequestCheckRunnable, 2000);
	}

	@Override
	protected void onPause() {
		super.onPause();

		myOnlineStatus = false;

		// Remove any pending posts of Runnabler that are in the message queue.
		sentRequestHandler.removeCallbacks(sentRequestCheckRunnable);
		rcvdRequestHandler.removeCallbacks(rcvdRequestCheckRunnable);
	}

	@Override
	protected void onResume() {
		super.onStart();

		myOnlineStatus = true;
		isSentGameRequestAccepted = false;

		// Set properties
		TwoPlayerWordGameProperties.getGamePropertiesInstance().setIsInvited(
				false);
		TwoPlayerWordGameProperties.getGamePropertiesInstance()
				.setIsGameIdGenerated(false);

		// Handlers
		handleReceivedGamePlayRequests();
		handleSentGamePlayRequests();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		// Log the user out and exit
		new LogoutAsyncTask().execute("Logout");

		// Remove any pending posts of Runnable that are in the message queue.
		sentRequestHandler.removeCallbacks(sentRequestCheckRunnable);
		rcvdRequestHandler.removeCallbacks(rcvdRequestCheckRunnable);

		Toast.makeText(appContext, "User Logged Out.", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Log the user out and exit
		new LogoutAsyncTask().execute("Logout");

		// Remove any pending posts of Runnable that are in the message queue.
		sentRequestHandler.removeCallbacks(sentRequestCheckRunnable);
		rcvdRequestHandler.removeCallbacks(rcvdRequestCheckRunnable);
	}

	// TODO check
	@Override
	protected void onStop() {
		super.onStop();

		// Log the user out and exit
		new LogoutAsyncTask().execute("Logout");

		// Remove any pending posts of Runnable that are in the message queue.
		sentRequestHandler.removeCallbacks(sentRequestCheckRunnable);
		rcvdRequestHandler.removeCallbacks(rcvdRequestCheckRunnable);
	}

	/**
	 * Display the real time two player game play instructions.
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
				.setMessage(R.string.realTimeGamePlayInstructionsText);

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

		Toast.makeText(appContext, "User Logged Out", Toast.LENGTH_SHORT)
				.show();
		finish();
	}

	/**
	 * Invite other user to play
	 * 
	 * @param view
	 *            {@link View}
	 * 
	 * @return void
	 */
	public void inviteClickHandler(View view) {
		AlertDialog.Builder inviteAlertBuilder = new AlertDialog.Builder(this);

		// Set title of the alert
		inviteAlertBuilder.setTitle("Invite Another User");

		// Set the message of the alert
		inviteAlertBuilder.setMessage("Enter the username of the opponent: ");

		// Initialize an EditText view to input the opponent username
		final EditText etOpponentUsername = new EditText(this);
		inviteAlertBuilder.setView(etOpponentUsername);

		// Set the positive button click listener
		inviteAlertBuilder.setPositiveButton("Send Request",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String opponentUsername = etOpponentUsername.getText()
								.toString();

						// Start the invite async task
						new InviteAsyncTask().execute(opponentUsername);
					}

				});

		// Set the negative button click listener
		inviteAlertBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do Nothing
					}
				});

		// Show the alert
		inviteAlertBuilder.show();
	}
}
