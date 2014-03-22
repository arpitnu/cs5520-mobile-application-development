package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import android.content.Context;
import android.media.MediaPlayer;
import edu.neu.madcourse.arpitmehta.wordgame.WordGamePrefs;

public class TwoPlayerWordGameMusic {
	   private static MediaPlayer mp = null;

	   /**
	    *  Stop old song and start new one
	    *   	   
	    * @param context {@link Context}
	    * @param resource int
	    * 
	    * @return void
	    */
	   public static void play(Context context, int resource) {
	      stop(context);

	      // Start music only if not disabled in preferences
	      if (WordGamePrefs.getMusic(context)) {
	         mp = MediaPlayer.create(context, resource);
	         mp.setLooping(true);
	         mp.start();
	      }
	   }
	   

	   /**
	    * Stop the music
	    * 
	    * @param context {@link Context}
	    */
	   public static void stop(Context context) { 
	      if (mp != null) {
	         mp.stop();
	         mp.release();
	         mp = null;
	      }
	   }
}
