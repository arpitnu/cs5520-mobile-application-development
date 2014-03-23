package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.madcourse.arpitmehta.communication.CommunicationActivity;
import edu.neu.madcourse.arpitmehta.communication.CommunicationConstants;
import edu.neu.mhealth.api.KeyValueAPI;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AsyncGamePlayActivity extends Activity {
	/**
	 * The Application Context
	 */
	private Context context;

	/**
	 * The Activity Tag
	 */
	private static final String TAG = "AsyncGamePlayActivity";

	/**
	 * The GCM Object
	 */
	GoogleCloudMessaging gcm;

	/**
	 * The Message ID
	 */
	AtomicInteger messageID = new AtomicInteger();

	/**
	 * The Registration ID
	 */
	private String regId;

	/**
	 * The Shared Preferences
	 */
	SharedPreferences prefs;

	/**
	 * The Toast to display messages
	 */
	Toast toast;

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_game_play);

		context = getApplicationContext();

		// Set opponent username as null to begin with
		TwoPlayerWordGameProperties.getGamePropertiesInstance()
				.setTbOpponentUsername(null);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Check device for play services APK
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * checkPlayServices Check the device to make sure it has the Google Play
	 * Services APK. If it doesn't, display a dialog that allows users to
	 * download the APK from the Google Play Store or enable it in the device's
	 * system settings.
	 * 
	 * @param none
	 * 
	 * @return boolean
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}

		return true;
	}

	/**
	 * Exit Login Screen
	 * 
	 * @param view
	 *            {@link View}
	 */
	public void quitLogin(View view) {
//		unregister();
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
		if (checkPlayServices()) {
			regId = getRegistrationId(context);
			if (regId.isEmpty()) {
				registerInBackground();
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
			Toast.makeText(context, "No valid Google Play Services APK found.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void unregister() {
		// Log
		Log.d(CommunicationConstants.TAG, "UNREGISTER USERID: " + regId);

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					msg = "Username Logout Sent";
					KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(),
							"alertText", "Logout Notification");

					KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(),
							"titleText", "Logout");

					KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(),
							"contentText", "Logout Successful!");

					KeyValueAPI.clearKey(TwoPlayerWordGameConstants
							.getTeamName(), TwoPlayerWordGameConstants
							.getPassword(), TwoPlayerWordGameProperties
							.getGamePropertiesInstance().getTbLoginUsername()
							+ "regId");

					gcm.unregister();
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				removeRegistrationId(getApplicationContext());
				toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
				toast.show();
			}
		}.execute();
	}

	/**
	 * Removes the registration id from shared preferences.
	 * 
	 * @param context
	 *            {@link Context}
	 * 
	 * @return void
	 */
	private void removeRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(CommunicationConstants.TAG, "Removig regId on app version "
				+ appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(PROPERTY_REG_ID);
		editor.commit();
		regId = null;
	}

	/**
	 * registerInBackground Registers the application with GCM servers
	 * asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				String msg = "";

				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}

					KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(),
							"alertText", "Login Notification");
					KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(),
							"titleText", "Login");
					KeyValueAPI.put(TwoPlayerWordGameConstants.getTeamName(),
							TwoPlayerWordGameConstants.getPassword(),
							"contentText", "Login Successful!");

					regId = gcm.register(CommunicationConstants.GCM_SENDER_ID);

					// int cnt = 0;

					if (KeyValueAPI.isServerAvailable()) {
						String loginUname = ((EditText) findViewById(R.id.etTbUsername))
								.getText().toString();

						if (!loginUname.equals("")) {
							// Put the reg id in KeyValueAPI
							KeyValueAPI.put(
									TwoPlayerWordGameConstants.getTeamName(),
									TwoPlayerWordGameConstants.getPassword(),
									loginUname + "regId", regId);

							TwoPlayerWordGameProperties
									.getGamePropertiesInstance()
									.setTbLoginUsername(loginUname);

							msg = "User Logged In";
						} else {
							msg = "Enter Username";
						}

					} else {
						msg = "Error :" + "Backup Server is not available";
						return msg;
					}
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}

				// You should send the registration ID to your server over HTTP,
				// so it can use GCM/HTTP or CCS to send messages to your app.
				// The request to your server should be authenticated if your
				// app
				// is using accounts.
				sendRegistrationIdToBackend();

				// Persist the regID - no need to register again.
				storeRegistrationId(context, regId);

				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
				toast.show();
				
				if(msg.equals("User Logged In")) {
					Intent tunBasedGamePlayIntent = new Intent(AsyncGamePlayActivity.this, TurnBasedGamePlayActivity.class);
					startActivity(tunBasedGamePlayIntent);
				}
			}
		}.execute(null, null, null);
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	protected void storeRegistrationId(Context context, String regid) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);

		Log.i(TAG, "Saving regId on app version " + appVersion);

		SharedPreferences.Editor editor = prefs.edit();

		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	protected void sendRegistrationIdToBackend() {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context2) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}

		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * getGCMPreferences Returns the GCM Shared preferences
	 * 
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context2) {
		return getSharedPreferences(
				CommunicationActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * getAppVersion If the registration ID doesn't exist or the app was
	 * updated, getRegistrationId() returns an empty string to indicate that the
	 * app needs to get a new regID. getRegistrationId() calls the following
	 * method to check the app version
	 * 
	 * @param context
	 * 
	 * @param none
	 * 
	 * @return int
	 */
	private int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
}
