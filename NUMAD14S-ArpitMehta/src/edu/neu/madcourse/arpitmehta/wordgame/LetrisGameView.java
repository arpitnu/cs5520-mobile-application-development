package edu.neu.madcourse.arpitmehta.wordgame;

import java.util.ArrayList;

import edu.neu.madcourse.arpitmehta.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class LetrisGameView extends View {
	/**
	 * The Letris Game View Tag
	 */
	private static final String TAG = "Letris Game View";

	/**
	 * The ID
	 */
	private static final int ID = 43;

	/**
	 * The Game
	 */
	private final LetrisGame game;

	/**
	 * The Canvas
	 */
	Canvas canvas;

	/**
	 * Width of one tile
	 */
	private float tileWidth;

	/**
	 * Height of one tile
	 */
	private float tileHeight;

	/**
	 * X index of selection
	 */
	private int selX;

	/**
	 * Y index of selection
	 */
	private int selY;

	/**
	 * The rectangle used to keep track of selection
	 */
	private final Rect selRect = new Rect();

	/**
	 * The list of selected rectangles
	 */
	private final ArrayList<Rect> selRectList = new ArrayList<Rect>();

	/**
	 * Pain object for the canvas background
	 */
	private Paint background = new Paint();

	/**
	 * Paint object for the dark grid lines
	 */
	private Paint dark = new Paint();

	/**
	 * Paint object for hilite
	 */
	private Paint hilite = new Paint();

	/**
	 * Paint object for foreground alphabet display
	 */
	private Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * The Resources
	 */
	private Resources res;

	/**
	 * The Begin new game bitmap
	 */
	private Bitmap beginBitmap;

	/**
	 * The Pause Bitmap
	 */
	private Bitmap pauseBitmap;

	/**
	 * The Resume Bitmap
	 */
	private Bitmap resumeBitmap;

	/**
	 * The Stop Bitmap
	 */
	private Bitmap stopBitmap;

	/**
	 * The hint Bitmap
	 */
	private Bitmap hintBitmap;

	/**
	 * The Bitmap Paint
	 */
	private Paint bitmapPaint = new Paint();

	/**
	 * The Timer Paint
	 */
	private Paint gameTimerPaint = new Paint();

	/**
	 * The Score Paint
	 */
	private Paint scorePaint = new Paint();

	/**
	 * The word paint
	 */
	private Paint wordPaint = new Paint();

	/**
	 * The X pos of start of grid
	 */
	private int gridStartX;

	/**
	 * The X pos of end of grid
	 */
	private int gridEndX;

	/**
	 * The Y pos of start of grid
	 */
	private int gridStartY;

	/**
	 * The Y pos of end of grid
	 */
	private float gridEndY;

	/**
	 * begin game bitmap left x
	 */
	private int beginBitmapLeft;

	/**
	 * begin game bitmap top y
	 */
	private int beginBitmapTop;

	/**
	 * The padding between bitmap images
	 */
	private int interBitmapPadding;

	/**
	 * Pause Bitmap left x
	 */
	private int pauseBitmapLeft;

	/**
	 * Pause bitmap top y
	 */
	private int pauseBitmapTop;

	/**
	 * Resume bitmap left x
	 */
	private int resumeBitmapLeft;

	/**
	 * Resume bitmap top y
	 */
	private int resumeBitmapTop;

	/**
	 * Hint bitmap left x
	 */
	private int hintBitmapLeft;

	/**
	 * Hint bitmap top y
	 */
	private int hintBitmapTop;

	/**
	 * Stop bitmap left x
	 */
	private int stopBitmapLeft;

	/**
	 * Stop bitmap top y
	 */
	private int stopBitmapTop;

	/**
	 * Count down timer x
	 */
	private int gameTimerX;

	/**
	 * Count down timer y
	 */
	private int gameTimerY;

	/**
	 * Boolean flag to indicate if begin game button is clicked
	 */
	private boolean isBeginClicked = false;

	/**
	 * The text displayed for timer
	 */
	private String timerText = new String();

	/**
	 * The text displayed for score
	 */
	private String scoreText = new String();
	
	/**
	 * The Word Display Text
	 */
	private String displayText = new String();

	/**
	 * The initial timer value
	 */
	private final int initTimerVal = (int) (GameConstants.getTimerDuration() / GameConstants
			.getTimerTickDuration());

	/**
	 * Initial Timer Tick Count
	 */
	private int tickCount = 0;

	/**
	 * The initial score
	 */
	private int initScore = 0;

	private boolean isTimerUpdated = false;

	private boolean isTimerTimedout = false;

	private boolean isInitLoad = true;

	private boolean isPauseButtonClicked = false;

	private boolean isHintsButtonClicked = false;

	private boolean isClickInGridTile = false;

	private boolean isValidWordFound = false;

	private int wordLeft;

	private int wordTop;

	/**
	 * Array List of coordinates of selected rectangles
	 */
	private ArrayList<GridCoordinate> selRectCoordinateList = new ArrayList<GridCoordinate>();

	private Paint selectedPaint = new Paint();

	private boolean isWordClicked = false;

	private int scoreTextX;

	private int scoreTextY;;

	/**
	 * LetrisGameView Constructor
	 * 
	 * @param context
	 */
	public LetrisGameView(Context context) {
		super(context);

		this.game = (LetrisGame) context;
		setFocusable(true);
		setFocusableInTouchMode(true);

		// Initialize resources
		res = getResources();

		// Initialize Bitmaps
		beginBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_begin_game);
		pauseBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_pause);
		resumeBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_play);
		hintBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_hint);
		stopBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_stop);

		// Initialize Timer text
		timerText = Integer.toString(initTimerVal);

		// Initialize score text
		scoreText = Integer.toString(initScore);

		setId(ID);
	}

	/**
	 * onSizeChanged Calculates the size of each tile on the screen.
	 * 
	 * @param w
	 * @param h
	 * @param oldw
	 * @param oldw
	 * 
	 * @return void
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		tileWidth = w / ((float) GameConstants.getNumGridColumns());
		tileHeight = w / ((float) GameConstants.getNumGridRows());
		getRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + tileWidth + ", height "
				+ tileHeight);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * onDraw
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	@Override
	protected void onDraw(Canvas c) {
		canvas = c;

		if (false != isInitLoad) {
			// Draw Background
			Log.d(TAG, "onDraw Background");
			onDrawBackground(canvas);

			// Draw the game grid lines
			Log.d(TAG, "onDraw Grid Lines");
			onDrawGridLines(canvas);
			
			// Draw the characters
			Log.d(TAG, "onDraw Characters");
			onDrawGridCharacters(canvas);

			// Draw game control bitmaps
			Log.d(TAG, "onDraw Game Controls");
			onDrawGameControls(canvas);
			
			// Draw the timer text
			Log.d(TAG, "onDraw Timer Text");
			onDrawTimerView(canvas);
			
			// Draw Score text
			Log.d(TAG, "onDraw Score Text");
			onDrawScoreView(canvas);

			// Reset flag
			isInitLoad = false;
		}
		
		
		//TODO remove?
		if (isBeginClicked != false) {
			// Draw Background
			Log.d(TAG, "onDraw Background");
			onDrawBackground(canvas);

			// Draw the game grid lines
			Log.d(TAG, "onDraw Grid Lines");
			onDrawGridLines(canvas);

			// Draw the characters
			Log.d(TAG, "onDraw Characters");
			onDrawGridCharacters(canvas);

			// Draw game control bitmaps
			Log.d(TAG, "onDraw Game Controls");
			onDrawGameControls(canvas);
			
			// Draw Score text
			Log.d(TAG, "onDraw Score Text");
			onDrawScoreView(canvas);

			// Reset flag
			isBeginClicked = false;
		}

		if (false != isTimerUpdated) {
			// Draw Background
			Log.d(TAG, "onDraw Background");
			onDrawBackground(canvas);

			// Draw the game grid lines
			Log.d(TAG, "onDraw Grid Lines");
			onDrawGridLines(canvas);

			// Draw the characters
			Log.d(TAG, "onDraw Characters");
			onDrawGridCharacters(canvas);

			// Draw game control bitmaps
			Log.d(TAG, "onDraw Game Controls");
			onDrawGameControls(canvas);

			// Draw the timer text
			Log.d(TAG, "onDraw Timer Text");
			onDrawTimerView(canvas);
			
			// Draw Score text
			Log.d(TAG, "onDraw Score Text");
			onDrawScoreView(canvas);

			// Draw the character pressed
			Log.d(TAG, "onDraw Word");
			onDrawWord(canvas);

			// Reset flag
			isTimerUpdated = false;
		} 
//		else {
//			// Draw the timer text
//			Log.d(TAG, "onDraw Timer Text");
//			onDrawTimerView(canvas);
//		}

		if (false != isTimerTimedout) {
			// Draw Background
			Log.d(TAG, "onDraw Background");
			onDrawBackground(canvas);

			// Draw the game grid lines
			Log.d(TAG, "onDraw Grid Lines");
			onDrawGridLines(canvas);

			// Draw the characters
			Log.d(TAG, "onDraw Characters");
			onDrawGridCharacters(canvas);

			// Draw game control bitmaps
			Log.d(TAG, "onDraw Game Controls");
			onDrawGameControls(canvas);

			// Draw the timer text
			Log.d(TAG, "onDraw Timer Text");
			onDrawTimerView(canvas);

			// TODO Display all words selected by user

			// Reset flag
			isTimerTimedout = false;
		} 
//		else {
//			// Draw the timer text
//			Log.d(TAG, "onDraw Timer Text");
//			onDrawTimerView(canvas);
//		}

		if (false != isClickInGridTile) {
			// Draw Background
			Log.d(TAG, "onDraw Background");
			onDrawBackground(canvas);

			// Draw the game grid lines
			Log.d(TAG, "onDraw Grid Lines");
			onDrawGridLines(canvas);

			// Draw the characters
			Log.d(TAG, "onDraw Characters");
			onDrawGridCharacters(canvas);

			// Draw game control bitmaps
			Log.d(TAG, "onDraw Game Controls");
			onDrawGameControls(canvas);

			// Draw the timer text
			Log.d(TAG, "onDraw Timer Text");
			onDrawTimerView(canvas);

			// Draw the character pressed
			Log.d(TAG, "onDraw Word");
			onDrawWord(canvas);
			
			// Draw Selections
			Log.d(TAG, "onDraw Selections");
			onDrawSelections(canvas);
			
			// Draw Score text
			Log.d(TAG, "onDraw Score Text");
			onDrawScoreView(canvas);

			// Reset flag
			isClickInGridTile = false;
		}

		if (false != isPauseButtonClicked) {
			// Draw Background
			Log.d(TAG, "onDraw Background");
			onDrawBackground(canvas);

			// Draw the game grid lines
			Log.d(TAG, "onDraw Grid Lines");
			onDrawGridLines(canvas);

			// Draw the characters
			Log.d(TAG, "onDraw Characters");
			onDrawGridCharacters(canvas);

			// Draw game control bitmaps
			Log.d(TAG, "onDraw Game Controls");
			onDrawGameControls(canvas);

			// Draw the timer text
			Log.d(TAG, "onDraw Timer Text");
			onDrawTimerView(canvas);
			
			// Draw Score text
			Log.d(TAG, "onDraw Score Text");
			onDrawScoreView(canvas);

			// Draw the character pressed
			Log.d(TAG, "onDraw Word");
			onDrawWord(canvas);
			
			// Draw Selections
			Log.d(TAG, "onDraw Selections");
			onDrawSelections(canvas);
		}
		
		if (false != isWordClicked) {
			// Draw Background
			Log.d(TAG, "onDraw Background");
			onDrawBackground(canvas);

			// Draw the game grid lines
			Log.d(TAG, "onDraw Grid Lines");
			onDrawGridLines(canvas);

			// Draw the characters
			Log.d(TAG, "onDraw Characters");
			onDrawGridCharacters(canvas);

			// Draw game control bitmaps
			Log.d(TAG, "onDraw Game Controls");
			onDrawGameControls(canvas);

			// Draw the timer text
			Log.d(TAG, "onDraw Timer Text");
			onDrawTimerView(canvas);

			// Draw Score text
			Log.d(TAG, "onDraw Score Text");
			onDrawScoreView(canvas);

			// Draw the character pressed
			Log.d(TAG, "onDraw Word");
			onDrawWord(canvas);

			// Draw Selections
			Log.d(TAG, "onDraw Selections");
			onDrawSelections(canvas);
			
			isWordClicked = false;
		}

		if (false != isHintsButtonClicked) {

			// Reset flag
			isHintsButtonClicked = false;
		} 
	}

	private void onDrawSelections(Canvas canvas2) {
		if(false == isValidWordFound) {
			selectedPaint.setColor(getResources().getColor(
					R.color.invalid_selection));
		}
		else {
			selectedPaint.setColor(getResources().getColor(
					R.color.valid_selection));
		}
		
		//TODO
		Log.d(TAG, "Num sel rect: " + selRectList.size());
		
		for (Rect rect : selRectList) {
			canvas.drawRect(rect, selectedPaint);
		}
	}

	/**
	 * onDrawWord Draws the character currently typed by the user
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawWord(Canvas canvas) {
		setDisplayWord(game.getSelectedWord());
		wordPaint.setTextAlign(Paint.Align.CENTER);
		wordPaint.setTextSize(GameConstants.getWordSize());
		if (false != isValidWordFound) {
			wordPaint.setColor(getResources().getColor(
					R.color.valid_word_selection));
		} else {
			wordPaint.setColor(getResources().getColor(
					R.color.invalid_word_selection));
		}
		wordLeft = getLeft() + (getWidth() / 2)
				- displayText.length();
		wordTop = getHeight() - GameConstants.getBitmapPaddingBottom() - beginBitmap.getHeight() - GameConstants.getWordPaddingBottom()
				- GameConstants.getWordSize();
		canvas.drawText(displayText, wordLeft, wordTop, wordPaint);
	}
	
	private void setDisplayWord(String word) {
		displayText = word;		
	}

	/**
	 * onDrawBeginGameBitmap Draw begin game bitmap on canvas
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawBeginGameBitmap(Canvas canvas) {
		beginBitmapLeft = (getLeft() + (getWidth() / 2))
				- (beginBitmap.getWidth() / 2);
		beginBitmapTop = getHeight() - beginBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(beginBitmap, beginBitmapLeft, beginBitmapTop,
				bitmapPaint);
	}

	/**
	 * onDrawScoreView Draws the current score view on canvas
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawScoreView(Canvas canvas) {
		if (false != isPauseButtonClicked) {
			scorePaint.setColor(getResources().getColor(
					R.color.paused_forground));
		} else {
			scorePaint.reset();
		}
		scorePaint.setTextSize(GameConstants.getTextSize());
		wordPaint.setTextAlign(Paint.Align.RIGHT);
		scoreTextX = getLeft() + getWidth() - GameConstants.getTimerPaddingLeft() - scoreText.length();
		scoreTextY = getTop() + GameConstants.getPaddingTop() + GameConstants.getTextSize();;
		canvas.drawText(scoreText, scoreTextX, scoreTextY, scorePaint);
	}

	/**
	 * onDrawTimerView Draws the count down timer text
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawTimerView(Canvas canvas) {
		if (false != isPauseButtonClicked) {
			gameTimerPaint.setColor(getResources().getColor(
					R.color.paused_forground));
		} else {
			gameTimerPaint.reset();
		}
		gameTimerPaint.setTextSize(GameConstants.getTextSize());
		gameTimerPaint.setTextAlign(Paint.Align.LEFT);
		gameTimerX = getLeft() + GameConstants.getTimerPaddingLeft();
		gameTimerY = getTop() + GameConstants.getPaddingTop()
				+ GameConstants.getTextSize();
		canvas.drawText(timerText, gameTimerX, gameTimerY, gameTimerPaint);
	}

	/**
	 * onDrawGameControls Draws the game control bitmaps on the canvas
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawGameControls(Canvas canvas) {
		interBitmapPadding = (getWidth()
				- (2 * GameConstants.getBitmapPaddingSide())
				- pauseBitmap.getWidth()
				- hintBitmap.getWidth() 
				- stopBitmap.getWidth()) / (GameConstants.getNumBitmaps() - 1);

		// Draw pause bitmap on canvas
		pauseBitmapLeft = getLeft() + GameConstants.getBitmapPaddingSide();
		pauseBitmapTop = getHeight() - pauseBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(pauseBitmap, pauseBitmapLeft, pauseBitmapTop,
				bitmapPaint);

//		// Draw Resume bitmap on canvas
//		resumeBitmapLeft = pauseBitmapLeft + pauseBitmap.getWidth()
//				+ interBitmapPadding;
//		resumeBitmapTop = getHeight() - resumeBitmap.getHeight()
//				- GameConstants.getBitmapPaddingBottom();
//		canvas.drawBitmap(resumeBitmap, resumeBitmapLeft, resumeBitmapTop,
//				bitmapPaint);

//		// Draw hint bitmap on canvas
//		hintBitmapLeft = resumeBitmapLeft + resumeBitmap.getWidth()
//				+ interBitmapPadding;
//		hintBitmapTop = getHeight() - hintBitmap.getHeight()
//				- GameConstants.getBitmapPaddingBottom();
//		canvas.drawBitmap(hintBitmap, hintBitmapLeft, hintBitmapTop,
//				bitmapPaint);
		
		// Draw hint bitmap on canvas
		hintBitmapLeft = pauseBitmapLeft + pauseBitmap.getWidth()
				+ interBitmapPadding;
		hintBitmapTop = getHeight() - hintBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(hintBitmap, hintBitmapLeft, hintBitmapTop,
				bitmapPaint);

		// Draw hint bitmap on canvas
		stopBitmapLeft = hintBitmapLeft + hintBitmap.getWidth()
				+ interBitmapPadding;
		stopBitmapTop = getHeight() - stopBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(stopBitmap, stopBitmapLeft, stopBitmapTop,
				bitmapPaint);
	}

	/**
	 * onDrawGridCharacters Draws the characters in the grid
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawGridCharacters(Canvas canvas) {
		if (false != isPauseButtonClicked) {
			foreground.setColor(getResources().getColor(
					R.color.paused_forground));
		} else {
			foreground.setColor(getResources().getColor(
					R.color.wordgame_forground));
		}
		foreground.setStyle(Style.FILL);
		foreground.setTextAlign(Paint.Align.CENTER);
		foreground.setTextSize(tileHeight * 0.75f);
		foreground.setTextScaleX(tileWidth / tileHeight);

		// Draw the alphabet in the center of the tile
		FontMetrics fm = foreground.getFontMetrics();
		// Centering in X: use alignment (and X at midpoint)
		float x = tileWidth / 2;
		// Centering in Y: measure ascent/descent first
		float y = tileHeight / 2 - (fm.ascent + fm.descent) / 2;

		for (int i = 0; i < GameConstants.getNumGridRows(); i++) {
			for (int j = 0; j < GameConstants.getNumGridColumns(); j++) {
				canvas.drawText(this.game.getTileString(i, j), i * tileWidth
						+ x,
						j * tileHeight + y + GameConstants.getGridPaddingTop(),
						foreground);
			}
		}
	}

	/**
	 * onDrawGridLines Draws the game grid lines
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawGridLines(Canvas canvas) {
		// Define colors for the grid lines
		dark.setColor(getResources().getColor(R.color.grid_line_dark));
		hilite.setColor(getResources().getColor(R.color.grid_line_hilite));

		// Calculate the grid x & y parameters
		// Initialize Grid x & Y positions
		gridStartX = getLeft();
		gridEndX = gridStartX + getWidth();
		gridStartY = GameConstants.getGridPaddingTop();
		gridEndY = getHeight()
				- (getHeight() - GameConstants.getGridPaddingTop() - GameConstants
						.getNumGridRows() * tileHeight);

		// Draw the horizontal grid lines
		for (int i = 0; i <= GameConstants.getNumGridColumns(); i++) {
			canvas.drawLine(gridStartX, gridStartY + i * tileHeight, gridEndX,
					gridStartY + i * tileHeight, dark);

			canvas.drawLine(gridStartX, gridStartY + i * tileHeight + 1,
					gridEndX, gridStartY + i * tileHeight + 1, hilite);
		}

		// Draw the Vertical grid lines
		for (int i = 0; i < GameConstants.getNumGridRows(); i++) {
			canvas.drawLine(i * tileWidth, gridStartY, i * tileWidth, gridEndY,
					dark);

			canvas.drawLine(i * tileWidth + 1, gridStartY, i * tileWidth + 1,
					gridEndY, hilite);
		}
	}

	/**
	 * onDrawBackground Draws the canvas background
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	private void onDrawBackground(Canvas canvas) {
		background.setColor(getResources()
				.getColor(R.color.wordgame_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	}

	/**
	 * onTouchEvent Handle touch events on the canvas
	 * 
	 * @param event
	 * 
	 * @return boolean
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean retVal = false;

		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			retVal = super.onTouchEvent(event);
		} else {
			if (false == isPauseButtonClicked) {

				// Check for touch event in the grid
				if ((event.getX() >= gridStartX) && (event.getX() <= gridEndY)
						&& (event.getY() >= gridStartY)
						&& (event.getY() <= gridEndY)) {
					select((int) (event.getX() / tileWidth),
							(int) ((event.getY() - gridStartY) / tileHeight));
					Log.d(TAG, "onTouchEvent in grid : x " + selX + ", y "
							+ selY);

					isClickInGridTile = true;

					selRectList.add(selRect);

					// Process the tile
					game.processTile(selX, selY);

					invalidate();

				}
				// Check for click on begin game button
				else if ((event.getX() >= beginBitmapLeft)
						&& (event.getX() <= beginBitmapLeft
								+ beginBitmap.getWidth())
						&& (event.getY() >= beginBitmapTop)
						&& (event.getX() <= beginBitmapTop
								+ beginBitmap.getHeight())) {
					Log.d(TAG, "Begin Button Click");

					// Set flag
					isBeginClicked = true;

					invalidate();

					// Begin timer
					game.startTimer();
				}
				// Check for stop button click
				else if ((event.getX() >= stopBitmapLeft)
						&& (event.getX() <= stopBitmapLeft
								+ stopBitmap.getWidth())
						&& (event.getY() >= stopBitmapTop)
						&& (event.getX() <= stopBitmapTop
								+ stopBitmap.getHeight())) {
					Log.d(TAG, "Stop Button Click");
					game.quitGame();
				}
				// Check for click on pause button
				else if ((event.getX() >= pauseBitmapLeft)
						&& (event.getX() <= pauseBitmapLeft
								+ pauseBitmap.getWidth())
						&& (event.getY() >= pauseBitmapTop)
						&& (event.getX() <= pauseBitmapTop
								+ pauseBitmap.getHeight())) {
					Log.d(TAG, "Pause Button Click");

					isPauseButtonClicked = true;

					// Stop game timer
					game.pauseTimer();
					
					// Pause music
					game.pauseMusic();

					// Disable grid selection
					invalidate();
				}
				// Check for click on hint button
				else if ((event.getX() >= hintBitmapLeft)
						&& (event.getX() <= hintBitmapLeft
								+ hintBitmap.getWidth())
						&& (event.getY() >= hintBitmapTop)
						&& (event.getX() <= hintBitmapTop
								+ hintBitmap.getHeight())) {
					Log.d(TAG, "Hints Button Click");
					//TODO Hints
				}
				// Check for click on the displayed word
				else if((event.getX() >= wordLeft)
						&& (event.getX() <= wordLeft
								+ game.getSelectedWord().length())
						&& (event.getY() >= wordTop)
						&& (event.getX() <= wordTop + GameConstants.getWordSize())) {
					Log.d(TAG, "Word Click");
					isWordClicked = true;
					
					if(false != isValidWordFound) {
						game.modifyScore(displayText.length());						
					}
					
					selRectList.clear();
					setDisplayWord("");
					setScoreValue(game.getGameScore());
					setValidWordFound(false);
					game.resetWordSearchParams();
					invalidate();
				}

				retVal = true;
			} 
			else {
				// Check for click on resume button
				if ((event.getX() >= resumeBitmapLeft)
						&& (event.getX() <= resumeBitmapLeft
								+ resumeBitmap.getWidth())
						&& (event.getY() >= resumeBitmapTop)
						&& (event.getX() <= resumeBitmapTop
								+ resumeBitmap.getHeight())) {
					Log.d(TAG, "Resume Button Click");

					isPauseButtonClicked = false;

					// Resart the timer
					game.resumeTimer();
					
					// Start music
					game.startMusic();
				
				}

				// Check for stop button click
				if ((event.getX() >= stopBitmapLeft)
						&& (event.getX() <= stopBitmapLeft
								+ stopBitmap.getWidth())
						&& (event.getY() >= stopBitmapTop)
						&& (event.getX() <= stopBitmapTop
								+ stopBitmap.getHeight())) {
					Log.d(TAG, "Stop Button Click");
					game.quitGame();

					retVal = true;
				}

				// Check for click on hint button
				if ((event.getX() >= hintBitmapLeft)
						&& (event.getX() <= hintBitmapLeft
								+ hintBitmap.getWidth())
						&& (event.getY() >= hintBitmapTop)
						&& (event.getX() <= hintBitmapTop
								+ hintBitmap.getHeight())) {
					Log.d(TAG, "Hints Button Click");
				}
			}
		}

		return retVal;
	}

	private void setScoreValue(int score) {
		scoreText = Integer.toString(score);
	}

	/**
	 * select Calculate the new x & y position of the selection and then use
	 * getRect() to calculate the new selection rectangle
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return void
	 */
	private void select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), GameConstants.getNumGridColumns() - 1);
		selY = Math.min(Math.max(y, 0), GameConstants.getNumGridRows() - 1);
		getRect(selX, selY, selRect);
	}

	/**
	 * getRect Returns the rectangle selection
	 * 
	 * @param x
	 * @param y
	 * @param rect
	 * 
	 * @return void
	 */
	private void getRect(int x, int y, Rect rect) {
		rect.set(((int) (x * tileWidth)), 
				((int) (gridStartY + y * tileHeight)), 
				((int) (x * tileWidth + tileWidth)),
				((int) (gridStartY + y * tileHeight + tileHeight)));
	}

	/**
	 * timerTimeout Function that is called to indicate that the game has timed
	 * out
	 * 
	 * @param boolean b
	 * 
	 * @return void
	 */
	public void timerTimeout() {
		isTimerTimedout = true;
		String timeoutText = new String("Seconds Left: Game Timeout");
		setTimerText(timeoutText);
		game.pauseMusic();
		invalidate();
	}

	/**
	 * setTimerText Update the timer value
	 * 
	 * @param txt
	 * 
	 * @return void
	 */
	private void setTimerText(String txt) {
		timerText = txt;
	}

	/**
	 * updateSecondsLeft Update the seconds
	 * 
	 * @param none
	 * 
	 * @return void
	 */
	public void updateSecondsLeft() {
		// Set flag
		isTimerUpdated = true;

		// increment tick count
		tickCount++;

		setTimerText(new String("Seconds Left: " + (initTimerVal - tickCount)));

		invalidate();
	}

	/**
	 * @return the isValidWordFound
	 */
	public boolean isValidWordFound() {
		return isValidWordFound;
	}

	/**
	 * @param isValidWordFound
	 *            the isValidWordFound to set
	 */
	public void setValidWordFound(boolean isValidWordFound) {
		this.isValidWordFound = isValidWordFound;
	}

}
