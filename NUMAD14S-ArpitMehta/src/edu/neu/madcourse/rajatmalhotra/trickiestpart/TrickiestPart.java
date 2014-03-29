package edu.neu.madcourse.rajatmalhotra.trickiestpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.arpitmehta.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class TrickiestPart extends Activity implements RecognitionListener {
	
	private static final String TAG = "TrickiestPart";

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
	private static final int TRIGGER_RECOGNITION_REQUEST_CODE = 1002;
	private static final int REPS_RECOGNITION_REQUEST_CODE = 1001;
	private static final int WEIGHTS_RECOGNITION_REQUEST_CODE = 1001;

	private int test;

	private EditText metTextHint;
	private ListView mlvTextMatches;
	private Spinner msTextMatches;
	private Button mbtSpeak;
	private TextToSpeech mttsPrompt;

	private enum InteractionState {
		LISTENING_FOR_TRIGGER, PROMPT_EXERCISE, LISTENING_FOR_EXERCISE, PROMPT_REPS, LISTENING_FOR_REPS, PROMPT_WEIGHTS, LISTENING_FOR_WEIGHTS, PROMPT_RESULT
	}

	InteractionState state = InteractionState.LISTENING_FOR_TRIGGER;

	private static enum TriggerResult {
		TRIGGER_NOT_FOUND, TRIGGER_FOUND
	}

	TriggerResult trigResult = TriggerResult.TRIGGER_NOT_FOUND;

	private static enum RepsResult {
		REPS_NOT_FOUND, REPS_FOUND
	}

	RepsResult repsResult = RepsResult.REPS_NOT_FOUND;

	private static enum WeightsResult {
		WEIGHTS_NOT_FOUND, WEIGHTS_FOUND
	}

	WeightsResult wtResult = WeightsResult.WEIGHTS_NOT_FOUND;

	static Intent speechRecognizerIntent = new Intent();
	static PackageManager pm;
	static String exerciseName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trickiest_part);

		// Initialize package manger
		pm = getPackageManager();

		metTextHint = (EditText) findViewById(R.id.etTextHint);
		mlvTextMatches = (ListView) findViewById(R.id.lvTextMatches);
		msTextMatches = (Spinner) findViewById(R.id.sNoOfMatches);
		mbtSpeak = (Button) findViewById(R.id.btSpeak);
		mttsPrompt = new TextToSpeech(getApplicationContext(),
				new TextToSpeech.OnInitListener() {

					@Override
					public void onInit(int status) {
						if (status != TextToSpeech.ERROR) {
							mttsPrompt.setLanguage(Locale.US);
						}
					}
				});

		mttsPrompt.setSpeechRate(0.9f);

		checkVoiceRecognition();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (mttsPrompt != null) {
			mttsPrompt.stop();
			mttsPrompt.shutdown();
		}
	}

	@Override
	protected void onDestroy() {
		super.onPause();

		if (mttsPrompt != null) {
			mttsPrompt.stop();
			mttsPrompt.shutdown();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			mbtSpeak.setEnabled(false);
			mbtSpeak.setText("Voice recognizer not present");
			Toast.makeText(this, "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
		}
	}

	public static Intent getSimpleRecognizerIntent(String prompt) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, prompt);
		return intent;
	}

	public void speak(View view) {
		speakText(SpeechPrompts.WELCOME_PROMPT);
		// TODO
		// speakText(SpeechPrompts.getExampleInputPrompt());

		// TODO Temp variable to run the state machine loop only once. For
		// testing
		int loop = 1;

		// Begin the loop
		while (state != InteractionState.PROMPT_RESULT) {
			switch (state) {
			case LISTENING_FOR_TRIGGER:
				speechRecognizerIntent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				// Specify the calling package to identify your application
				speechRecognizerIntent.putExtra(
						RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
								.getPackage().getName());

				// Given an hint to the recognizer about what the user is going
				// to say
				// There are two form of language model available
				// 1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
				// 2.LANGUAGE_MODEL_FREE_FORM : If not sure about the words or
				// phrases
				// and its domain.
				speechRecognizerIntent.putExtra(
						RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

				int noOfMatches = 10;

				// Specify how many results you want to receive. The results
				// will be
				// sorted where the first result is the one with higher
				// confidence.
				speechRecognizerIntent.putExtra(
						RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);

				// Start the Voice recognizer activity for the result.
				startActivityForResult(speechRecognizerIntent,
						TRIGGER_RECOGNITION_REQUEST_CODE);
				break;

			case PROMPT_EXERCISE:
				speakText(SpeechPrompts.EXERCISE_NAME_PROMPT);
				break;

			default:
				break;
			}
		}

		// TODO
		// Display an hint to the user about what he should say.
		// speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,
		// metTextHint.getText()
		// .toString());

		// // If number of Matches is not selected then return show toast
		// message
		// if (msTextMatches.getSelectedItemPosition() ==
		// AdapterView.INVALID_POSITION) {
		// Toast.makeText(this, "Please select No. of Matches from spinner",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		//
		// int noOfMatches = Integer.parseInt(msTextMatches.getSelectedItem()
		// .toString());
		//
		// // Specify how many results you want to receive. The results will be
		// // sorted where the first result is the one with higher confidence.
		// speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,
		// noOfMatches);
		//
		// // Start the Voice recognizer activity for the result.
		// startActivityForResult(speechRecognizerIntent,
		// VOICE_RECOGNITION_REQUEST_CODE);
	}

	private void speakText(String toSpeak) {
		mttsPrompt.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);

		while (mttsPrompt.isSpeaking()) {
			// Wait
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TRIGGER_RECOGNITION_REQUEST_CODE:
			if (RESULT_OK == resultCode) {
				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				int trigMatchIndex = -1;

				if (!textMatchList.isEmpty()) {
					if (false != MatcherUtil.findTrigger(textMatchList)) {
						trigResult = TriggerResult.TRIGGER_FOUND;
						state = InteractionState.PROMPT_EXERCISE;
					} else {

					}
				}
			}
			break;

		default:
			break;
		}

		// TODO Handle elseif cases later
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

			// If Voice recognition is successful then it returns RESULT_OK
			if (resultCode == RESULT_OK) {

				// if(searchText.regionMatches(true, 0, "i just did", 0,
				// 10)); {
				// Toast.makeText(getApplicationContext(),
				// "Trigger phrase found", Toast.LENGTH_LONG).show();
				//
				// String[] parts = searchText.split(" ");
				// String repsWord = parts[3];
				// try {
				// int nreps = Integer.parseInt(repsWord);
				// Toast.makeText(getApplicationContext(), repsWord,
				// Toast.LENGTH_LONG).show();
				// } catch (NumberFormatException e) {
				// Toast.makeText(getApplicationContext(),
				// "Number not detected", Toast.LENGTH_LONG).show();
				// }
				// }
				// }

				// if (!textMatchList.isEmpty()) {
				// // If first Match contains the 'search' word
				// // Then start web search.
				// if (textMatchList.get(0).contains("search")) {
				//
				// String searchQuery = textMatchList.get(0);
				// searchQuery = searchQuery.replace("search", "");
				// Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
				// search.putExtra(SearchManager.QUERY, searchQuery);
				// startActivity(search);
				// } else {
				// // populate the Matches
				// mlvTextMatches.setAdapter(new ArrayAdapter<String>(
				// this, android.R.layout.simple_list_item_1,
				// textMatchList));
				// }
				//
				// }
				// Result code for various error.
			} else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
				showToastMessage("Audio Error");
			} else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
				showToastMessage("Client Error");
			} else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
				showToastMessage("Network Error");
			} else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
				showToastMessage("No Match");
			} else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
				showToastMessage("Server Error");
			}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Helper method to show the toast message
	 **/
	void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onReadyForSpeech(Bundle params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResults(Bundle results) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		// TODO Auto-generated method stub
		
	}

}
