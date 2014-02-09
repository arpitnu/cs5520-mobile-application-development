package edu.neu.madcourse.arpitmehta.wordgame;

import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.view.View;

public class GameTimer extends CountDownTimer {
	/**
	 * The LetrisGameView on which the timer value is to be displayed
	 */
	LetrisGameView view;
	
	/**
	 * GameTimer
	 * 		Default Constructor
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public GameTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	public GameTimer(LetrisGameView gameView, long millisInFuture,
			long countDownInterval) {
		super(millisInFuture, countDownInterval);
		view = gameView;		
	}

	@Override
	public void onFinish() {
		view.timerTimeout();
	}

	@Override
	public void onTick(long millisUntilFinished) {
		view.updateSecondsLeft();
	}

}
