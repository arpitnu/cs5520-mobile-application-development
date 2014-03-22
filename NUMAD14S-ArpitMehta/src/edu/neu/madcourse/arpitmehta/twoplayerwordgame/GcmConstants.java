package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

public class GcmConstants {
	/**
	 * The TAG
	 */
	public static final String TAG = "GCM_Constants";
	
	/**
	 * The Sender ID
	 */
    public static final String GCM_SENDER_ID = "302656947666";
    
    /**
     * The GCM Reference URL
     */
    public static final String BASE_URL = "https://android.googleapis.com/gcm/send";
    
    /**
     * The preferences name
     */
    public static final String PREFS_NAME = "GCM_Communication";
    
    /**
     * The GCM API Key
     */
    public static final String GCM_API_KEY = "AIzaSyCeDO0KcXfvXDkGdBZ7wVu0KtmxonPd9Oo";
    
    /**
     * The Notification Type
     */
    public static final int SIMPLE_NOTIFICATION = 22;
    
    /**
     * GCM message time to live
     */
    public static final long GCM_TIME_TO_LIVE = 60L * 60L * 24L * 7L * 4L; // 4 Weeks
    
    /**
     * The mode
     */
    public static int mode = 0;
}
