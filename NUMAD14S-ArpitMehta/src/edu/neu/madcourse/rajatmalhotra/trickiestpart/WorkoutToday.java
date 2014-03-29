package edu.neu.madcourse.rajatmalhotra.trickiestpart;

import edu.neu.madcourse.arpitmehta.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WorkoutToday extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_today);
	       
	}
	
	public void recordWorkout(View view) {

			Intent i = new Intent(this, TrickiestPart.class);
			startActivity(i);
		
		//	Toast.makeText(getApplicationContext(), "Speech Recognition not available!", Toast.LENGTH_SHORT);
		

	}
	
	public void recordWorkoutText(View view) {
		
			Intent i = new Intent(this, ManualInput.class);
			startActivity(i);
		}
			
}
