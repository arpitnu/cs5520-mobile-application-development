package edu.neu.madcourse.arpitmehta.communication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class CommunicationBroadcastReceiver extends WakefulBroadcastReceiver {
	
	/**
	 * The Broadcast Receiver Tag
	 */
	private static final String TAG = "GCM_Boradcast_Receiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Initialized...");
		
		// Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                CommunicationIntentService.class.getName());
        
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
	}

}
