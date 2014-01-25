package edu.neu.madcourse.arpitmehta.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.neu.madcourse.arpitmehta.R;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class DictionaryActivity extends Activity {
	
	/**
	 * ArrayList for HashCodes of all dictionary search files
	 */
	ArrayList<Integer> fileHashLst = new ArrayList<Integer>();
	
	/**
	 * ArrayList of words to search from
	 */
	ArrayList<String> wordLst = new ArrayList<String>();
	
	/**
	 * Editable text.
	 */
	protected Editable enteredWord;
	
	/**
	 * File Input Stream
	 */
	InputStream inStreamFileToSearch;
	
	/**
	 * The Buffered Reader
	 */
	BufferedReader brFileToSearch;
	
	//TODO
	/**
	 * The AssetsManager
	 */
	AssetManager am;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dictionary);
		
		setupActionBar();
		
		// Initialize AssetManager
		am = getAssets();
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
		
		// Clear word list
		wordLst.clear();
		
		// 
		final EditText etWordInput = (EditText) findViewById(R.id.etDictionary);
		//TODO
//		etWordInput.setOnEditorActionListener(new OnEditorActionListener() {
//			
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				boolean handled = false;
//				
//				if(actionId == Editor)
//				
//				return handled;
//			}
//		});
		
		
		etWordInput.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				boolean retVal = false;
				
				enteredWord = etWordInput.getText();
				
				if(enteredWord.length() == 2) {
					// Load words for search
					loadFileContents();
				}
				else if(enteredWord.length() > 2) {
					// Search word
					searchWord();
				}
				else {
					// Clear all words displayed
					//TODO
				}
				
				return retVal;
			}
		});
		
	}
	
	protected void searchWord() {
		if(wordLst.contains(enteredWord.toString())) {
			// Word found. Add to display list
			// TODO
			
			// Word found. Beep
			try {
				Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
				r.play();
			} catch(Exception e) {}
		}
	}	
	
	protected void loadFileContents() {
		String fileName = new String("dictionary/" + enteredWord + ".txt");
		String word = new String();
		
		// Clear word list
		wordLst.clear();
		
		try {
			inStreamFileToSearch = am.open(fileName);
			brFileToSearch = new BufferedReader(new InputStreamReader(inStreamFileToSearch));
			
			while(null != (word = brFileToSearch.readLine())) {
				wordLst.add(word);
			}
		} catch (IOException e) {}
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
	
	/**
	 * clearAllWords
	 * 		Clears all words in the display area
	 * 
	 * @param view
	 * 
	 * @return void
	 */
	public void clearAllWords(View view) {
		
	}
	
	public void openAckDialog(View view) {
		Intent ackIntent = new Intent(this, DictionaryAck.class);
		try {
			startActivity(ackIntent);
		} catch (ActivityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
