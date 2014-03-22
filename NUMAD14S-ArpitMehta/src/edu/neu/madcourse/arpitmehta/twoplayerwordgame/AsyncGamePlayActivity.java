package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.madcourse.arpitmehta.R.layout;
import edu.neu.madcourse.arpitmehta.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_game_play);
		
		context = getApplicationContext();
	}
	
	/**
	 * Exit Login Screen
	 * 
	 * @param view
	 *            {@link View}
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
		
	}
}
