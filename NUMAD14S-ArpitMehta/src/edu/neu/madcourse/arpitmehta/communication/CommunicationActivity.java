package edu.neu.madcourse.arpitmehta.communication;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.mhealth.api.KeyValueAPI;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class CommunicationActivity extends Activity {

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	/**
	 * The Application context
	 */
	private Context context;

	/**
	 * The Activity Tag
	 */
	private static final String TAG = "GCM_Communication";

	/**
	 * The GCM
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Show the Up button in the action bar.
		// setupActionBar();

		context = getApplicationContext();
		setContentView(R.layout.activity_communication);
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

	private void unregister() {
		// Log
		Log.d(CommunicationConstants.TAG, "UNREGISTER USERID: " + regId);

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					msg = "Device Unregistration Sent";
					KeyValueAPI.put("pbj1203", "1312789", "alertText",
							"Notification");
					KeyValueAPI.put("pbj1203", "1312789", "titleText",
							"Unregister");
					KeyValueAPI.put("pbj1203", "1312789", "contentText",
							"Unregistering Successful!");
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

					KeyValueAPI.put("pbj1203", "1312789", "alertText",
							"Register Notification");
					KeyValueAPI.put("pbj1203", "1312789", "titleText",
							"Register");
					KeyValueAPI.put("pbj1203", "1312789", "contentText",
							"Registering Successful!");

					regId = gcm.register(CommunicationConstants.GCM_SENDER_ID);

					int cnt = 0;

					if (KeyValueAPI.isServerAvailable()) {

						if (!KeyValueAPI.get("pbj1203", "1312789", "cnt")
								.contains("Error")) {
							Log.d("????", KeyValueAPI.get("pbj1203", "1312789",
									"cnt"));
							cnt = Integer.parseInt(KeyValueAPI.get("pbj1203",
									"1312789", "cnt"));
						}
						String getString;
						boolean flag = false;
						for (int i = 1; i <= cnt; i++) {
							getString = KeyValueAPI.get("pbj1203", "1312789",
									"regid" + String.valueOf(i));
							Log.d(String.valueOf(i), getString);
							if (getString.equals(regId))
								flag = true;
						}

						if (!flag) {
							KeyValueAPI.put("pbj1203", "1312789", "cnt",
									String.valueOf(cnt + 1));
							KeyValueAPI.put("pbj1203", "1312789", "regid"
									+ String.valueOf(cnt + 1), regId);
						}
						msg = "Device Registration Sent";

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
		getMenuInflater().inflate(R.menu.communication, menu);
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

	/**
	 * registerDevice Register the device
	 * 
	 * @param View
	 * 
	 * @return void
	 */
	public void registerDevice(View view) {
		if (checkPlayServices()) {
			regId = getRegistrationId(context);
			if (regId.isEmpty()) {
				registerInBackground();
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}
	}

	public void unregisterDevice(View view) {
		// Unregister Device
		unregister();
	}

	public void startSyncCommunication(View view) {
		Intent loginActivityIntent = new Intent(this, LoginActivity.class);
		startActivity(loginActivityIntent);
	}

	public void startAsyncCommunication(View view) {
		// Intent sendMessageActivityIntent = new Intent(this,
		// SendMessageActivity.class);
		// startActivity(sendMessageActivityIntent);
	}

	/**
	 * showCommunicationAck Display the acknowledgements for the Communication
	 * Activity
	 * 
	 * @param View
	 */
	public void showCommunicationAck(View view) {
		Intent commAckIntent = new Intent(this, CommunicationAckActivity.class);
		startActivity(commAckIntent);
	}

	/**
	 * quitActivity Quit activity and return to main menu
	 * 
	 * @param View
	 */
	public void quitActivity(View v) {
		// Unregister Device only if device is not previously unregistered
		if (regId != null) {
			unregister();
		}

		finish();
	}

}
