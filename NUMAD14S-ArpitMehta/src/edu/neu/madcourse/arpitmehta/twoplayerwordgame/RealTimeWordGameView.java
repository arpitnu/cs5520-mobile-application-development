package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

import java.util.ArrayList;

import edu.neu.madcourse.arpitmehta.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class RealTimeWordGameView extends View {

	/**
	 * The ID
	 */
	private static final int ID = 1;

	/**
	 * The Count down Timer
	 */
	CountDownTimer cntDownTmr;

	/**
	 * Vibrator object
	 */
	Vibrator vibr;

	/**
	 * Paint object for background
	 */
	Paint backgroundPaint = new Paint();

	/**
	 * The foreground
	 */
	Paint foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * Paint for rectangle
	 */
	Paint rectPaint = new Paint();

	/**
	 * Button Paint
	 */
	Paint buttonPaint = new Paint();

	/**
	 * The paint for selected buttons
	 */
	Paint buttonPaintOther = new Paint();

	/**
	 * Exit string
	 */
	String exitString = "EXIT";

	/**
	 * The list of coordinated of grid rectangles
	 */
	ArrayList<GridCoordinate> indexesSelected = new ArrayList<GridCoordinate>();

	boolean found = false;

	/**
	 * The selected paint
	 */
	Paint selPaint = new Paint();

	/**
	 * The height of screen
	 */
	float screenHeight = getHeight();

	/**
	 * Screen width
	 */
	float screenWidth = getWidth();

	/**
	 * Grid definitions
	 */
	float gridtop = (float) (screenHeight * 0.2);
	float gridBottom = (float) (screenHeight * 0.7);
	float gridHeight = gridBottom - gridtop;
	float gridWidth = getWidth();

	/**
	 * Tile parameters
	 */
	float tileHeight = gridHeight / TwoPlayerGameConstants.NUM_GRID_ROWS;
	float tileWidth = gridWidth / TwoPlayerGameConstants.NUM_GRID_COLUMNS;

	/**
	 * selected tile x & y
	 */
	private int selX;
	private int selY;

	int selectedX;
	int selectedY;

	/**
	 * The selected word
	 */
	String word = "";

	/**
	 * List of selected tiles
	 */
	ArrayList<Rect> selRectList = new ArrayList<Rect>();

	boolean touched = false;

	/**
	 * The game
	 */
	private final RealTimeWordGameActivity gamePlay;

	/**
	 * The current time elapsed in game play
	 */
	long currTime;

	/**
	 * The hint button position parameters
	 */
	int hintButtonTop = (int) (screenHeight * 0.75);
	int hintButtonBottom = (int) (screenHeight * 0.85);
	int hintButtonLeft = (int) (screenWidth * 0.1);
	int hintButtonRight = (int) (screenWidth * 0.45);

	/**
	 * Exit button position parameters
	 */
	int exitButtonTop = (int) (screenHeight * 0.75);
	int exitButtonBottom = (int) (screenHeight * 0.85);
	int exitButtonLeft = (int) (screenWidth * 0.55);
	int exitButtonRight = (int) (screenWidth * 0.9);

	/**
	 * The score
	 */
	int myScore = 0;

	/**
	 * Score view position parameters
	 */
	int scoreTop = (int) (screenHeight * 0.05);
	int scoreBottom = (int) (screenHeight * 0.15);
	int scoreLeft = (int) (screenWidth * 0.1);
	int scoreRight = (int) (screenWidth * 0.45);

	/**
	 * The timer
	 */
	int timer;

	/**
	 * Timer position parameters
	 */
	int timerTop = (int) (screenHeight * 0.05);
	int timerBottom = (int) (screenHeight * 0.15);
	int timerLeft = (int) (screenWidth * 0.55);
	int timerRight = (int) (screenWidth * 0.9);

	/**
	 * The opponent score position parameters
	 */
	int oppScoreTop = (int) (screenHeight * 0.89);
	int oppScoreBottom = (int) (screenHeight * 0.95);
	int oppScoreLeft = (int) (screenWidth * 0.25);
	int oppScoreRight = (int) (screenWidth * 0.75);

	/**
	 * RealTimeWordGameView constructor
	 * 
	 * @param context
	 */
	public RealTimeWordGameView(Context context) {
		super(context);

		this.gamePlay = (RealTimeWordGameActivity) context;

		setFocusable(true);

		setFocusableInTouchMode(true);

		setBackgroundColor(getResources().getColor(R.color.white));

		vibr = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		setId(ID);

		myScore = 0;

		currTime = TwoPlayerGameConstants.TIMER_DURATION;

		cntDownTmr = new CountDownTimer(currTime,
				TwoPlayerGameConstants.TIMER_TICK_DURATION) {

			public void onTick(long millisUntilFinished) {
				currTime = millisUntilFinished;
				timer = (int) millisUntilFinished / 1000;
				invalidate();
			}

			public void onFinish() {
				gamePlay.finalScore = myScore;
				gamePlay.timeUp();
			}
		}.start();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		screenWidth = w;
		screenHeight = h;

		gridtop = (int) (h * 0.2);
		gridBottom = (int) (h * 0.7);
		gridHeight = gridBottom - gridtop;
		gridWidth = getWidth();

		tileHeight = gridHeight / TwoPlayerGameConstants.NUM_GRID_ROWS;
		tileWidth = gridWidth / TwoPlayerGameConstants.NUM_GRID_COLUMNS;

		hintButtonLeft = (int) (screenWidth * 0.1);
		hintButtonRight = (int) (screenWidth * 0.45);
		hintButtonTop = (int) (screenHeight * 0.75);
		hintButtonBottom = (int) (screenHeight * 0.85);

		exitButtonLeft = (int) (screenWidth * 0.55);
		exitButtonRight = (int) (screenWidth * 0.9);
		exitButtonTop = (int) (screenHeight * 0.75);
		exitButtonBottom = (int) (screenHeight * 0.85);

		scoreLeft = (int) (screenWidth * 0.1);
		scoreRight = (int) (screenWidth * 0.45);
		scoreTop = (int) (screenHeight * 0.05);
		scoreBottom = (int) (screenHeight * 0.15);

		timerLeft = (int) (screenWidth * 0.55);
		timerRight = (int) (screenWidth * 0.9);
		timerTop = (int) (screenHeight * 0.05);
		timerBottom = (int) (screenHeight * 0.15);

		oppScoreLeft = (int) (screenWidth * 0.25);
		oppScoreRight = (int) (screenWidth * 0.75);
		oppScoreTop = (int) (screenHeight * 0.89);
		oppScoreBottom = (int) (screenHeight * 0.95);

		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		backgroundPaint.setColor(getResources().getColor(R.color.black));

		rectPaint.setColor(getResources().getColor(R.color.background_main));

		canvas.drawRect(0, gridtop, getWidth(), gridBottom, rectPaint);

		buttonPaint.setColor(getResources().getColor(R.color.orange));
		buttonPaint.setStyle(Paint.Style.FILL);

		buttonPaintOther.setColor(getResources().getColor(
				R.color.color_button_clickable));
		buttonPaintOther.setStyle(Paint.Style.FILL);

		canvas.drawRect(exitButtonLeft, exitButtonTop, exitButtonRight,
				exitButtonBottom, buttonPaintOther);

		canvas.drawRect(hintButtonLeft, hintButtonTop, hintButtonRight,
				hintButtonBottom, buttonPaintOther);

		canvas.drawRect(scoreLeft, scoreTop, scoreRight, scoreBottom,
				buttonPaint);

		canvas.drawRect(timerLeft, timerTop, timerRight, timerBottom,
				buttonPaint);

		canvas.drawRect(oppScoreLeft, oppScoreTop, oppScoreRight,
				oppScoreBottom, buttonPaint);

		buttonPaint.setStyle(Paint.Style.STROKE);

		buttonPaint.setColor(getResources().getColor(R.color.black));

		buttonPaintOther.setStyle(Paint.Style.STROKE);

		buttonPaintOther.setColor(getResources().getColor(R.color.black));

		canvas.drawRect(exitButtonLeft, exitButtonTop, exitButtonRight,
				exitButtonBottom, buttonPaintOther);

		canvas.drawRect(hintButtonLeft, hintButtonTop, hintButtonRight,
				hintButtonBottom, buttonPaintOther);

		canvas.drawRect(scoreLeft, scoreTop, scoreRight, scoreBottom,
				buttonPaint);

		canvas.drawRect(timerLeft, timerTop, timerRight, timerBottom,
				buttonPaint);

		canvas.drawRect(oppScoreLeft, oppScoreTop, oppScoreRight,
				oppScoreBottom, buttonPaint);

		// Draw the major grid lines
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(0, gridtop + (i * tileHeight), getWidth(), gridtop
					+ (i * tileHeight), backgroundPaint);
		}
		for (int i = 0; i < 7; i++) {
			canvas.drawLine((i * tileWidth), gridtop, (i * tileWidth),
					gridBottom, backgroundPaint);
		}

		// Define color and style for numbers
		foregroundPaint.setColor(getResources().getColor(
				R.color.puzzle_foreground));
		foregroundPaint.setStyle(Style.FILL);
		foregroundPaint.setTextSize(tileHeight * 0.75f);
		foregroundPaint.setTextScaleX(tileWidth / tileHeight);
		foregroundPaint.setTextAlign(Paint.Align.CENTER);

		// Draw the character in the center of the tile
		FontMetrics fm = foregroundPaint.getFontMetrics();

		// Centering in X: use alignment (and X at midpoint)
		float x = tileWidth / 2;

		// Centering in Y: measure ascent/descent first
		float y = tileHeight / 2 - (fm.ascent + fm.descent) / 2;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				canvas.drawText(this.gamePlay.getTileString(i, j), j
						* tileWidth + x, (gridtop + (i * tileHeight) + y),
						foregroundPaint);
			}
		}

		// Draw the exit button text
		foregroundPaint.setTextSize((exitButtonBottom - exitButtonTop) * 0.35f);
		foregroundPaint
				.setTextScaleX(((exitButtonRight - exitButtonLeft) / (exitButtonBottom - exitButtonTop))
						* (float) 0.6);
		fm = foregroundPaint.getFontMetrics();
		x = (exitButtonRight - exitButtonLeft) / 2;
		y = ((exitButtonBottom - exitButtonTop) / 2) - (fm.ascent + fm.descent)
				/ 2;
		canvas.drawText(exitString, (exitButtonLeft + x), (exitButtonTop + y),
				foregroundPaint);

		// Draw the hint button text
		foregroundPaint.setTextSize((hintButtonBottom - hintButtonTop) * 0.35f);
		foregroundPaint
				.setTextScaleX(((hintButtonRight - hintButtonLeft) / (hintButtonBottom - hintButtonTop))
						* (float) 0.5);
		fm = foregroundPaint.getFontMetrics();
		x = (hintButtonRight - hintButtonLeft) / 2;
		y = ((hintButtonBottom - hintButtonTop) / 2) - (fm.ascent + fm.descent)
				/ 2;

		canvas.drawText("HINT", (hintButtonLeft + x), (hintButtonTop + y),
				foregroundPaint);

		// Draw my score text
		foregroundPaint.setTextSize((scoreBottom - scoreTop) * 0.35f);
		foregroundPaint
				.setTextScaleX(((scoreRight - scoreLeft) / (scoreBottom - scoreTop))
						* (float) 0.4);
		fm = foregroundPaint.getFontMetrics();
		x = (scoreRight - scoreLeft) / 2;
		y = ((scoreBottom - scoreTop) / 2) - (fm.ascent + fm.descent) / 2;
		canvas.drawText(("SCORE: " + Integer.toString(myScore)),
				(scoreLeft + x), (scoreTop + y), foregroundPaint);

		// Draw the opponent score text
		foregroundPaint.setTextSize((oppScoreBottom - oppScoreTop) * 0.35f);
		foregroundPaint
				.setTextScaleX(((oppScoreRight - oppScoreLeft) / (oppScoreBottom - oppScoreTop))
						* (float) 0.2);
		fm = foregroundPaint.getFontMetrics();
		x = (oppScoreRight - oppScoreLeft) / 2;
		y = ((oppScoreBottom - oppScoreTop) / 2) - (fm.ascent + fm.descent) / 2;

		canvas.drawText(("OPPONENT SCORE: " + TwoPlayerWordGameProperties
				.getGamePropertiesInstance().getOpponentScore()),
				(oppScoreLeft + x), (oppScoreTop + y), foregroundPaint);
		
		// Draw the timer text
		if (timer <= 5) {
			foregroundPaint.setColor(getResources().getColor(R.color.red));
		}
		foregroundPaint.setTextSize((timerBottom - timerTop) * 0.35f);
		foregroundPaint
				.setTextScaleX(((timerRight - timerLeft) / (timerBottom - timerTop))
						* (float) 0.5);
		fm = foregroundPaint.getFontMetrics();
		x = (timerRight - timerLeft) / 2;
		y = ((timerBottom - timerTop) / 2) - (fm.ascent + fm.descent) / 2;
		canvas.drawText(("TIME: " + Integer.toString(timer)), (timerLeft + x),
				(timerTop + y), foregroundPaint);
		
		
		if (touched == true) {
			if (found == false
					|| TwoPlayerWordGameProperties
					.getGamePropertiesInstance().getIsHintEnabled() == false) {
				selPaint.setColor(getResources().getColor(
						R.color.puzzle_selected));
				for (Rect r : selRectList) {
					canvas.drawRect(r, selPaint);
				}
			} 
			else {
				if (TwoPlayerWordGameProperties
						.getGamePropertiesInstance().getIsHintEnabled() == true) {
					selPaint.setColor(getResources().getColor(R.color.green));
					selPaint.setAlpha(50);
					for (Rect r : selRectList) {
						canvas.drawRect(r, selPaint);
					}
				}
			}
		}
	}

	private Rect getRect(int x, int y) {
		Rect r = new Rect();
		r.set((int) (x * tileWidth), (int) (y * tileHeight), (int) (x
				* tileWidth + tileWidth), (int) (y * tileHeight + tileHeight));

		return r;
	}

	private Rect select(int x, int y) {
		selX = Math.min(Math.max(x, 0), 6);
		selY = Math.min(Math.max(y, 2), 6);
		return getRect(selX, selY);
	}

	private void exitClick() {
		this.gamePlay.finish();
		this.gamePlay.exitPressed();
	}

	private void hintClick() {

		if (TwoPlayerWordGameProperties
				.getGamePropertiesInstance().getIsHintEnabled() == false) {
			Toast.makeText(gamePlay, "Hint Mode enabled", Toast.LENGTH_SHORT)
					.show();
			TwoPlayerWordGameProperties
			.getGamePropertiesInstance().setIsHintEnabled(true);
		} else {
			Toast.makeText(gamePlay, "Hint Mode disabled", Toast.LENGTH_SHORT)
					.show();
			TwoPlayerWordGameProperties
			.getGamePropertiesInstance().setIsHintEnabled(false);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			float x = event.getX();
			float y = event.getY();
			if (x >= exitButtonLeft && x <= exitButtonRight
					&& y >= exitButtonTop && y <= exitButtonBottom) {
				vibr.vibrate(85);
				exitClick();
			}

			if (x >= hintButtonLeft && x <= hintButtonRight
					&& y >= hintButtonTop && y <= hintButtonBottom) {
				hintClick();
				invalidate();
				vibr.vibrate(85);
			}
			break;
		}

		case MotionEvent.ACTION_UP: {
			selectedX = 0;
			selectedY = 0;

			if (found == true) {
				TwoPlayerWordGameMusic.play(gamePlay, R.raw.beep);
				
				myScore = myScore + (word.length() * 10);
				
				TwoPlayerWordGameProperties
				.getGamePropertiesInstance().setMyScore(myScore);
				gamePlay.indexesSelected = this.indexesSelected;

				Toast.makeText(gamePlay, "Valid Word Detected!",
						Toast.LENGTH_SHORT).show();

				gamePlay.changeLetters();
				
				invalidate();
			}
			
			word = "";
			
			selRectList.clear();
			
			indexesSelected.clear();
			
			found = false;
			
			invalidate();
			
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			if (event.getY() >= gridtop && event.getY() <= gridBottom) {
				touched = true;
				int x = (int) (event.getX() / tileWidth);
				int y = (int) (event.getY() / tileHeight);

				if (!(selectedX == x && selectedY == y)) // diagonal
																	// check
				{
					if ((x == (selectedX + 1) && (y == selectedY + 1))
							|| (x == (selectedX - 1) && (y == selectedY - 1))
							|| (x == (selectedX + 1) && (y == selectedY - 1))
							|| (x == (selectedX - 1) && (y == selectedY + 1))) {
						Toast.makeText(gamePlay,
								"Diagonal selections not allowed.",
								Toast.LENGTH_SHORT).show();
						
						selRectList.clear();
						
						indexesSelected.clear();
					}

					else if (!selRectList.contains(select(x, y))) {
						indexesSelected.add(new GridCoordinate(y - 2,
								x));
						
						selRectList.add(select(x, y));
						
						vibr.vibrate(70);
					}
					else {
						// Do nothing
					}
					
					CharSequence txtSel = gamePlay.getTileString(selY - 2, selX);
					word = word.concat(txtSel.toString());
					if (word.length() >= 2 && (gamePlay.isWordFound(word))) {
						found = true;
					} 
					else {
						found = false;
					}
				}
				
				selectedX = x;
				
				selectedY = y;
				
				invalidate();
				
				break;
			}
		}
		}
		return true;
	}
}
