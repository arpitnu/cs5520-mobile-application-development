package edu.neu.madcourse.arpitmehta.wordgame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.RelativeLayout;

public class GameView extends RelativeLayout {
	
	/**
	 * The GameView Tag
	 */
	private static final String TAG = "GameView";
	
	/**
	 * Game View Constructor
	 * 
	 * @param context
	 */
	public GameView(Context context) {
		super(context);
		
		setFocusable(true);
		this.addView(new LetrisGameView(context));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		
		super.onDraw(canvas);
	}

}
