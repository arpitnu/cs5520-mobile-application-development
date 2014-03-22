package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.madcourse.arpitmehta.communication.CommunicationActivity;
import edu.neu.mhealth.api.KeyValueAPI;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class CommunicationIntentService extends IntentService {
	

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	static final String TAG = "CommunicationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Reciving Data ...");
		String alertText = "";
		String titleText = "";
		String contentText = "";
//		if (Communication_Globals.mode == 1)
//			contentText = +"is online";
		Bundle extras = intent.getExtras();
//		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
//		String messageType = gcm.getMessageType(intent);
		Log.d(String.valueOf(extras.size()), extras.toString());
		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			alertText = KeyValueAPI.get("arpitm","qwerty","alertText");
			titleText = KeyValueAPI.get("arpitm","qwerty","titleText");
			contentText = KeyValueAPI.get("arpitm","qwerty","contentText");
			sendNotification(alertText, titleText, contentText);
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		CommunicationBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	public void sendNotification(String alertText, String titleText,
			String contentText) {
		Log.d(TAG, "Send Notification");
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent notificationIntent;
		notificationIntent = new Intent(this, CommunicationActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		notificationIntent.putExtra("show_response", "show_response");
		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(
				this, CommunicationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

		
		NotificationCompat.Builder mBuilder = 
				new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_stat_cloud)
				.setContentTitle(titleText)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
				.setContentText(contentText)
				.setTicker(alertText)
				.setAutoCancel(true)
				.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
			mBuilder.setContentIntent(intent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

	public CommunicationIntentService() {
		super("CommunicationIntentService");
	}	
}
