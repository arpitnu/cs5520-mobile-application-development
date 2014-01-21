package edu.neu.madcourse.arpitmehta.dictionary;

import edu.neu.madcourse.arpitmehta.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class DictionaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dictionary);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dictionary, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// Clear EditText Field
		EditText et = (EditText) findViewById(R.id.etDictionary);
		et.setText("");
		
		// Clear Word suggestions Display Field
		LinearLayout ll = (LinearLayout) findViewById(R.id.llWordView);
		ll.removeAllViews();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * exitDictionary
	 * 		Exit the Dictionary activity and return to the main activity.
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void exitDictionary(View view) {
		finish();
	}

}
