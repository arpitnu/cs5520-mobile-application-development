package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.mhealth.api.KeyValueAPI;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class RealTimeWordGameActivity extends Activity {

	private class ResetInvitationAndInitPuzzleAsyncTask extends
			AsyncTask<String, Integer, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				Toast.makeText(getApplicationContext(),
						"Unable to reset game invitation. Server Unavailable",
						Toast.LENGTH_SHORT).show();
			} else {
				Log.e(TAG,
						"ResetInvitationAndInitPuzzleAsyncTask: Unexpected value returned");
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			Integer retrunVal = 0;

			if (KeyValueAPI.isServerAvailable()) {
				KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
						TwoPlayerWordGameConstants.getPassword(),
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.getLoginUsername()
								+ TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.getGameId() + "hasUpdatedGameBoard",
						"false");

				// Check if both players have accepted the invitation
				if ((KeyValueAPI.get(TwoPlayerWordGameConstants.getTeamName(),
						TwoPlayerWordGameConstants.getPassword(),
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.getLoginUsername()
								+ "accepted invitation from"
								+ TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.getInvitedByUsername()).equals("true"))
						|| (KeyValueAPI.get(TwoPlayerWordGameConstants
								.getTeamName(), TwoPlayerWordGameConstants
								.getPassword(), TwoPlayerWordGameProperties
								.getGamePropertiesInstance()
								.getInvitedByUsername()
								+ "accepted invitation from"
								+ TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.getLoginUsername()).equals("true"))) {
					// Check if both players are on the game screen
					if (((KeyValueAPI.get(TwoPlayerWordGameConstants
							.getTeamName(), TwoPlayerWordGameConstants
							.getPassword(), TwoPlayerWordGameProperties
							.getGamePropertiesInstance().getLoginUsername()
							+ " status: ").equals("playing")) && (KeyValueAPI
								.get(TwoPlayerWordGameConstants.getTeamName(),
										TwoPlayerWordGameConstants
												.getPassword(),
										TwoPlayerWordGameProperties
												.getGamePropertiesInstance()
												.getOpponentUsername()
												+ " status: ")
							.equals("playing")))
							|| ((KeyValueAPI.get(TwoPlayerWordGameConstants
									.getTeamName(), TwoPlayerWordGameConstants
									.getPassword(), TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getOpponentUsername()
									+ " status: ").equals("playing")) && (KeyValueAPI
										.get(TwoPlayerWordGameConstants
												.getTeamName(),
												TwoPlayerWordGameConstants
														.getPassword(),
												TwoPlayerWordGameProperties
														.getGamePropertiesInstance()
														.getLoginUsername()
														+ " status: ")
									.equals("playing")))) {
						KeyValueAPI.clearKey(TwoPlayerWordGameConstants
								.getTeamName(), TwoPlayerWordGameConstants
								.getPassword(), TwoPlayerWordGameProperties
								.getGamePropertiesInstance().getLoginUsername()
								+ "accepted invitation from"
								+ TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.getInvitedByUsername());

						KeyValueAPI.clearKey(TwoPlayerWordGameConstants
								.getTeamName(), TwoPlayerWordGameConstants
								.getPassword(), TwoPlayerWordGameProperties
								.getGamePropertiesInstance()
								.getInvitedByUsername()
								+ "accepted invitation from"
								+ TwoPlayerWordGameProperties
										.getGamePropertiesInstance()
										.getLoginUsername());

						retrunVal = 2;
					} else {
						// Do nothing
					}
				} else {
					// One of the players have not accepted
					return 0;
				}
			} else {
				// Server Unavailable
				retrunVal = 1;
			}

			return retrunVal;
		}
	}

	private class CheckPlayerExitedTask extends
			AsyncTask<String, Integer, Integer> {

		@Override
		protected void onPostExecute(Integer result) {

			if (result == 0) {
				Toast.makeText(getApplicationContext(), "Server Unavailable",
						Toast.LENGTH_SHORT).show();
			}
			if (result == 1) {
				Toast.makeText(getApplicationContext(),
						"Game Over. Opponent surrendered", Toast.LENGTH_SHORT)
						.show();
				finish();
			}
			if(result == 2) {
				// Do nothing
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			Integer returnVal = null;

			if (KeyValueAPI.isServerAvailable()) {
				if ((KeyValueAPI.get(TwoPlayerWordGameConstants.getTeamName(),
						TwoPlayerWordGameConstants.getPassword(),
						TwoPlayerWordGameProperties.getGamePropertiesInstance()
								.getOpponentUsername() + "exitedGame")
						.equals("true"))
						|| (KeyValueAPI.get(TwoPlayerWordGameConstants
								.getTeamName(), TwoPlayerWordGameConstants
								.getPassword(), TwoPlayerWordGameProperties
								.getGamePropertiesInstance()
								.getInvitedByUsername()
								+ "exitedGame").equals("true"))) {
					KeyValueAPI.clearKey(TwoPlayerWordGameConstants
							.getTeamName(), TwoPlayerWordGameConstants
							.getPassword(), TwoPlayerWordGameProperties
							.getGamePropertiesInstance().getOpponentUsername()
							+ "exitedGame");
					KeyValueAPI.clearKey(TwoPlayerWordGameConstants
							.getTeamName(), TwoPlayerWordGameConstants
							.getPassword(), TwoPlayerWordGameProperties
							.getGamePropertiesInstance().getInvitedByUsername()
							+ "exitedGame");
					returnVal = 1;
				}
				else {
					returnVal = 2;
				}
			} else {
				// Server Unavailable
				returnVal = 0;
			}

			return returnVal;
		}
	}
	
	private class UpdatePuzzleAndScoreAsyncTask extends AsyncTask<String, Integer, Integer> {
		
		protected void onPostExecute(Integer result) {
			if (result == 0)
			{
				Toast.makeText(getApplicationContext(), "Server Unavailable", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			Integer returnVal = null;
			
			if(KeyValueAPI.isServerAvailable()) {
				// First check if I'm invited
				if(TwoPlayerWordGameProperties.getGamePropertiesInstance().getIsInvited()) {
					
				}
			}
			else {
				returnVal = 0;
			}
			
			return returnVal;
		}
		
	}

	/**
	 * The Tag
	 */
	private static final String TAG = "RealTimeWordGameActivity";

	/**
	 * The Word Game View
	 */
	private RealTimeWordGameView gameView;

	/**
	 * The Puzzle
	 */
	private char puzzle[][];

	/**
	 * Puzzle String
	 */
	String puzzleString;

	/**
	 * BufferedReader for file input
	 */
	BufferedReader br;

	/**
	 * List of indexes selected
	 */
	ArrayList<GridCoordinate> indexesSelected = new ArrayList<GridCoordinate>();

	/**
	 * List of words
	 */
	HashSet<String> hashwordList = new HashSet<String>();

	/**
	 * List of words for hint
	 */
	HashSet<String> hashHintWordList = new HashSet<String>();

	/**
	 * The final Score
	 */
	int finalScore;

	private final String easyPuzzleString = "HAPPYNGREATERHKITCHENMONEYRBURGERS";

	private Handler exitHandler;

	private Runnable exitHandlerRunnable = new Runnable() {

		@Override
		public void run() {
			CheckPlayerExitedTask exitTask = new CheckPlayerExitedTask();
			exitTask.execute("Checking seeks");
		}
	};

	private Handler puzzleSyncHandler;

	private Runnable puzzleSyncHandlerRunnable = new Runnable() {
		
		@Override
		public void run() {
			UpdatePuzzleAndScoreAsyncTask updateTask = new UpdatePuzzleAndScoreAsyncTask();
			updateTask.execute("Updating");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Clear the invitation and initialize the puzzle
		new ResetInvitationAndInitPuzzleAsyncTask().execute("");

		try {
			puzzle = StringToPuzzleArray(easyPuzzleString);
		} catch (Exception e) {
			Log.d("Exception: ", e.toString());
		}

		// Initialize my score
		TwoPlayerWordGameProperties.getGamePropertiesInstance().setMyScore(0);

		// Initialize Opponent score
		TwoPlayerWordGameProperties.getGamePropertiesInstance()
				.setOpponentScore(0);

		TwoPlayerWordGameProperties.getGamePropertiesInstance()
				.setIsBoardUpdated(false);

		// Get the game id if it is not generated
		if (!TwoPlayerWordGameProperties.getGamePropertiesInstance()
				.getIsGameIdGenerated()) {
			// Get the Game Id from KeyValueAPI and stroe in Properties
			getGameId();
		}

		TwoPlayerWordGameMusic.play(getApplicationContext(), R.raw.word_game);

		handlePuzzleSync();
		handleExitEvent();
	}

	private void handleExitEvent() {
		exitHandler = new Handler();
		exitHandler.postDelayed(exitHandlerRunnable, 1000);

	}

	private void handlePuzzleSync() {
		puzzleSyncHandler = new Handler();
		puzzleSyncHandler.postDelayed(puzzleSyncHandlerRunnable, 1000);
	}

	/**
	 * Get the game Id from KeyValueAPI and store in Properties
	 */
	private void getGameId() {
		new AsyncTask<String, Integer, Integer>() {

			@Override
			protected void onPostExecute(Integer result) {
				if (result == 1) {
					Toast.makeText(getApplicationContext(),
							"Server Unavailable.", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			protected Integer doInBackground(String... params) {
				Integer returnVal;

				if (KeyValueAPI.isServerAvailable()) {
					String gameId = KeyValueAPI.get(TwoPlayerWordGameConstants
							.getTeamName(), TwoPlayerWordGameConstants
							.getPassword(), TwoPlayerWordGameProperties
							.getGamePropertiesInstance().getLoginUsername()
							+ TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getInvitedByUsername() + "gameId");

					// Log
					Log.d(TAG, "game ID for"
							+ TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.getLoginUsername() + " is " + gameId);

					TwoPlayerWordGameProperties.getGamePropertiesInstance()
							.setGameId(gameId);

					returnVal = 0;
				} else {
					// Server Unavailable
					returnVal = 1;
				}

				return returnVal;
			}

		}.execute("");
	}

	/**
	 * Function return a 2D character array of the puzzle string
	 * 
	 * @param pStr
	 *            {@link String}
	 * 
	 * @return char[][]
	 */
	private char[][] StringToPuzzleArray(String pStr) {
		char puzzle[][] = new char[5][7];

		char pCharArr[] = pStr.toCharArray();

		for (int i = 0; i <= 28; i = i + 7) {
			for (int j = 0; j <= 6; j++) {
				puzzle[i / 7][j] = pCharArr[i + j];
			}
		}
		return puzzle;
	}
}
