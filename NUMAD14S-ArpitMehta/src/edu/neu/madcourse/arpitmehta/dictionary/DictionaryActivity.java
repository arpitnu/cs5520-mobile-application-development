package edu.neu.madcourse.arpitmehta.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.neu.madcourse.arpitmehta.R;

public class DictionaryActivity extends Activity {
	
	/**
	 * ArrayList of words to search from
	 */
	ArrayList<String> searchWordLst = new ArrayList<String>();
	
	/**
	 * ArrayList of words that are currently displayed
	 */
	ArrayList<String> displayedWordLst = new ArrayList<String>();
	
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
		
		// Clear resources to be used
		searchWordLst.clear();
		displayedWordLst.clear();
		
		// Clear EditText Field
		EditText et = (EditText) findViewById(R.id.etDictionary);
		et.setText("");
				
		// Clear Word suggestions Display Field
		LinearLayout ll = (LinearLayout) findViewById(R.id.llWordView);
		ll.removeAllViews();
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
		
		final EditText etWordInput = (EditText) findViewById(R.id.etDictionary);
		
		// Setup Text Change Listener
		etWordInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				enteredWord = etWordInput.getText();
				
				if(enteredWord.length() == 2) {
					// Load words for search
					loadFileWords(enteredWord.toString());
				}
				else if(enteredWord.length() > 2) {
					// Search word
					searchWord(enteredWord.toString());
				}
				else {
					// To be handled
				}				
			}
		});
	}
	
	/**
	 * searchWord
	 * 		Function searches the word in the word list and 
	 * 		displays it
	 * @param word 
	 * 
	 * @param none
	 * 
	 * @return void
	 */
	protected void searchWord(final String word) {
		if(searchWordLst.contains(word.toString())) {
			// Word found. Add to display list only if the word is not displayed yet
			LinearLayout llWordDisplay = (LinearLayout) findViewById(R.id.llWordView);
			
			if(!displayedWordLst.contains(word)) {
				displayedWordLst.add(word);
				TextView tvWord = new TextView(this);
				tvWord.setText(word);
				tvWord.setTextSize(getResources().getDimension(R.dimen.textsize));
				tvWord.setGravity(Gravity.CENTER_HORIZONTAL);
				llWordDisplay.addView(tvWord);			
			}
			
			try {
				Uri beep = Uri.parse("android.resource://edu.neu.madcourse.arpitmehta/raw/" + R.raw.beep);
				Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), beep);
				r.play();
			} catch(Exception e) {}
		}
	}	
	
	/**
	 * loadFileContents
	 * 		Function searches the word in the word list and 
	 * 		displays it
	 * @param word 
	 * 
	 * @param none
	 * 
	 * @return void
	 */
	protected void loadFileWords(final String word) {
		String fileName = new String("dictionary/" + word.substring(0, 2) + ".txt");
		String currWord = new String();
		
		// Clear word list
		searchWordLst.clear();
		
		try {
			inStreamFileToSearch = am.open(fileName);
			brFileToSearch = new BufferedReader(new InputStreamReader(inStreamFileToSearch));
			
			while(null != (currWord = brFileToSearch.readLine())) {
				searchWordLst.add(currWord);
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
		// Clear EditText Field
		EditText et = (EditText) findViewById(R.id.etDictionary);
		et.setText("");
				
		// Clear Word suggestions Display Field
		LinearLayout ll = (LinearLayout) findViewById(R.id.llWordView);
		ll.removeAllViews();
	}
	
	public void openAckDialog(View view) {
		Intent ackIntent = new Intent(this, DictionaryAck.class);
		startActivity(ackIntent);
	}

}
