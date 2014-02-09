package edu.neu.madcourse.arpitmehta.wordgame;

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
		pauseBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_pause);
		resumeBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_play);
		hintBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_hint);
		stopBitmap = BitmapFactory.decodeResource(res,
				R.drawable.button_black_stop);

		setId(ID);
	}

	// TODO
	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	//
	//
	// }

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
	 * getRect Returns the rectangle selection
	 * 
	 * @param x
	 * @param y
	 * @param rect
	 * 
	 * @return void
	 */
	private void getRect(int x, int y, Rect rect) {
		rect.set(((int) (x * tileWidth)), ((int) (y * tileHeight)), ((int) (x
				* tileWidth + tileWidth)),
				((int) (y * tileHeight + tileHeight)));
	}

	/**
	 * onDraw
	 * 
	 * @param canvas
	 * 
	 * @return void
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// Draw Background
		Log.d(TAG, "onDraw Background");
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		// Draw the board...
		// Define colors for the grid lines
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

		// Draw the horizontal grid lines
		Log.d(TAG, "onDraw Horizontal Lines");
		for (int i = 0; i <= GameConstants.getNumGridColumns(); i++) {
			canvas.drawLine(0, GameConstants.getGridPaddingTop() + i
					* tileHeight, getWidth(), GameConstants.getGridPaddingTop()
					+ i * tileHeight, dark);

			canvas.drawLine(0, GameConstants.getGridPaddingTop() + i
					* tileHeight + 1, getWidth(),
					GameConstants.getGridPaddingTop() + i * tileHeight + 1,
					hilite);
		}

		// Draw the Vertical grid lines
		Log.d(TAG, "onDraw Vertical Lines");
		for (int i = 0; i < GameConstants.getNumGridRows(); i++) {
			canvas.drawLine(
					i * tileWidth,
					GameConstants.getGridPaddingTop(),
					i * tileWidth,
					getHeight()
							- (getHeight() - GameConstants.getGridPaddingTop() - GameConstants
									.getNumGridRows() * tileHeight), dark);

			canvas.drawLine(
					i * tileWidth + 1,
					GameConstants.getGridPaddingTop(),
					i * tileWidth + 1,
					getHeight()
							- (getHeight() - GameConstants.getGridPaddingTop() - GameConstants
									.getNumGridRows() * tileHeight), hilite);
		}

		// Draw the characters
		Log.d(TAG, "onDraw Characters");
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
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

		// Compute the padding between bitmaps
		Log.d(TAG, "onDraw Game Controls");
		int interBitmapPadding = (getWidth()
				- (2 * GameConstants.getBitmapPaddingSide())
				- pauseBitmap.getWidth() - resumeBitmap.getWidth()
				- hintBitmap.getWidth() - stopBitmap.getWidth())
				/ (GameConstants.getNumBitmaps() - 1);

		// Draw pause bitmap on canvas
		int pauseBitmapLeft = getLeft() + GameConstants.getBitmapPaddingSide();
		int pauseBitmapTop = getHeight() - pauseBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(pauseBitmap, pauseBitmapLeft, pauseBitmapTop,
				bitmapPaint);

		// Draw Resume bitmap on canvas
		int resumeBitmapLeft = pauseBitmapLeft + pauseBitmap.getWidth()
				+ interBitmapPadding;
		int resumeBitmapTop = getHeight() - resumeBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(resumeBitmap, resumeBitmapLeft, resumeBitmapTop,
				bitmapPaint);

		// Draw hint bitmap on canvas
		int hintBitmapLeft = resumeBitmapLeft + resumeBitmap.getWidth()
				+ interBitmapPadding;
		int hintBitmapTop = getHeight() - hintBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(hintBitmap, hintBitmapLeft, hintBitmapTop,
				bitmapPaint);

		// Draw hint bitmap on canvas
		int stopBitmapLeft = hintBitmapLeft + hintBitmap.getWidth()
				+ interBitmapPadding;
		int stopBitmapTop = getHeight() - stopBitmap.getHeight()
				- GameConstants.getBitmapPaddingBottom();
		canvas.drawBitmap(stopBitmap, stopBitmapLeft, stopBitmapTop,
				bitmapPaint);
		
		// Draw the timer text
		Log.d(TAG, "onDraw Timer Text");
		gameTimerPaint.setTextSize(GameConstants.getTextSize());
		int gameTimerX = getLeft() + GameConstants.getTimerPaddingLeft();
		int gameTimerY = getTop() + GameConstants.getPaddingTop() + GameConstants.getTextSize();
		canvas.drawText("Seconds Left: ", gameTimerX, gameTimerY, gameTimerPaint);
		
		// Draw Score text
		Log.d(TAG, "onDraw Score Text");
		scorePaint.setTextSize(GameConstants.getTextSize());
		int scoreTextX = getLeft() + GameConstants.getTimerPaddingLeft();
		int scoreTextY = gameTimerY + 10 + GameConstants.getTextSize();
		canvas.drawText("Score: ", scoreTextX, scoreTextY, scorePaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(event);
		} else {
			select((int) (event.getX() / tileWidth),
					(int) (event.getY() / tileHeight));
			game.showKeypadOrError(selX, selY);
			Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);
			return true;
		}
	}

	private void select(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
