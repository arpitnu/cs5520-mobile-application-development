package edu.neu.madcourse.arpitmehta.wordgame;

import edu.neu.madcourse.arpitmehta.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class WordGamePrefs extends PreferenceActivity {
	// Option names and default values
	   private static final String WORD_GAME_OPT_MUSIC = "letris_music";
	   private static final boolean WORD_GAME_OPT_MUSIC_DEF = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.word_game_settings);
		
		// Show the Up button in the action bar.
		setupActionBar();
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
	
	/**
	 * getMusic
	 * 		Get the current value of music option
	 * 
	 * @param context
	 * 
	 * @return Default Music Option 
	 */
	public static boolean getMusic(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
	            .getBoolean(WORD_GAME_OPT_MUSIC, WORD_GAME_OPT_MUSIC_DEF);
	}
}
