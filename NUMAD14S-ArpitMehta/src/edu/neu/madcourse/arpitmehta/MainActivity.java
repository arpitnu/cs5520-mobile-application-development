package edu.neu.madcourse.arpitmehta;

import edu.neu.madcourse.arpitmehta.sudoku.Sudoku;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * showAboutActivity
	 * 		"About" activity handler function. 
	 * 		Called when the user clicks "About" Button 
	 * 
	 * @param view
	 * 			Android View class
	 * 
	 * @return void
	 */
	public void showAboutActivity(View view) {
		// Intent to bind 'About' activity
		Intent aboutIntent = new Intent(this, AboutActivity.class);
		
		// start about activity.
		startActivity(aboutIntent);
	}
	
	/**
	 * generateError
	 * 		Generates error that causes the app to crash
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void generateError(View view) {
		// Division by 0 error.
		@SuppressWarnings("unused")
		int errVal = 1/0;		
	}
	
	/**
	 * launchSudoku
	 * 		launch sudoku application
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void launchSudoku(View view) {
		// Intent to bind sudoku activity
		Intent sudokuIntent = new Intent(this, Sudoku.class);
		
		// Start Sudoku Activity
		startActivity(sudokuIntent);
	}
	
	/** 	
	 * quitApplication
	 * 		Quit
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void quitApplication(View view) {
		// Finish and exit.
		finish();
		System.exit(0);
	}

}
