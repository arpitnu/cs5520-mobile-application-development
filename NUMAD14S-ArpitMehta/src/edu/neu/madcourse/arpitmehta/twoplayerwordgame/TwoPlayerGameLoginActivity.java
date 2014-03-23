package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import edu.neu.madcourse.arpitmehta.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class TwoPlayerGameLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_player_game_login);
		// Show the Up button in the action bar.
//		setupActionBar();
	}
	
	public void handleLoginBtnClick(View view) {
		
	}
	
	public void handleQuitBtnClick(View view) {
		finish();
	}
}
