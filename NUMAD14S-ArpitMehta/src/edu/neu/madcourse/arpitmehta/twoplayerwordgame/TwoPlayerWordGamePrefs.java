package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import edu.neu.madcourse.arpitmehta.R;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class TwoPlayerWordGamePrefs extends PreferenceActivity {
	// Option names and default values
	private static final String OPT_MUSIC = "music_two_player_word_game";
	private static final boolean OPT_MUSIC_DEF = true;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.word_game_settings);
	}

	/**
	 * Get the current value of the music option
	 * 
	 * @param context {@link Context}
	 * 
	 * @return boolean returns the music preference value
	 */
	public static boolean getMusic(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
	}
}
