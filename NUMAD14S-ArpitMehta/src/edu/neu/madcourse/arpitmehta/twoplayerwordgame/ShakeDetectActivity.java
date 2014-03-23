package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import java.util.ArrayList;
import java.util.Random;

import edu.neu.madcourse.arpitmehta.R;
import edu.neu.madcourse.arpitmehta.twoplayerwordgame.ShakeDetector.OnShakeListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class ShakeDetectActivity extends Activity {
	
	/**
	 * Shake Detector
	 */
	private ShakeDetector shakeDetector;
	
	/**
	 * The SensorManager
	 */
    private SensorManager sensorManager;
    
    /**
     * The Accelerometer Sensor
     */
    private Sensor accelerometer;

	private final String TAG = "ShakeDetectActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shake_detect);
		// Show the Up button in the action bar.
//		setupActionBar();
		
		// Initialize the sensor manager
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		// Initialize the accelerometer object
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		// Initialize the shake detector
		shakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				startRandomGamePlayMode();
			}
		});
	}
	
	protected void startRandomGamePlayMode() {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		
		// Add the Sync & Async game play activities to the list
    	classes.add(SyncGamePlayActivity.class);
    	classes.add(AsyncGamePlayActivity.class);
    	
    	Random random = new Random();
		int randomNumber = random.nextInt(2);
		
		Log.d(TAG , Integer.toString(randomNumber));
		
		// Create an intent based on random activity.
    	Intent i = new Intent(this, classes.get(randomNumber));
    	
    	// Start the activity
    	startActivity(i);
	}

	@Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }   
}
