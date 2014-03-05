package edu.neu.madcourse.arpitmehta.communication;

import edu.neu.madcourse.arpitmehta.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);	
	}
	
	public void launchSyncMessagingActivity(View view) {
//		Intent syncCommIntent = new Intent(this, SyncCommunicationActivity.class);
//		startActivity(syncCommIntent);
	}
	
	public void quitLoginActivity(View view) {
		finish();
	}
}
