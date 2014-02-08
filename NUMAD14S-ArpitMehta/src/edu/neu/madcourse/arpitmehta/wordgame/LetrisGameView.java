package edu.neu.madcourse.arpitmehta.wordgame;

import edu.neu.madcourse.arpitmehta.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
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
	Paint background = new Paint();
	
	/**
	 * Paint object for the dark grid lines
	 */
	Paint dark = new Paint();
	
	/**
	 * Paint object for hilite
	 */
	Paint hilite = new Paint();
	
	/**
	 * Paint object for foreground alphabet display
	 */
	Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);

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
		Log.d(TAG, "onDraw");

		// Draw Background
		background.setColor(getResources().getColor(R.color.puzzle_background));
		
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		// Draw the board...
		// Define colors for the grid lines
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

		// Draw the horizontal grid lines
		for (int i = 0; i < GameConstants.getNumGridColumns(); i++) {
			canvas.drawLine(0, i * tileHeight, getWidth(), i * tileHeight, dark);

			canvas.drawLine(0, i * tileHeight + 1, getWidth(), i * tileHeight
					+ 1, hilite);
		}

		// Draw the vertical grid lines
		for (int i = 0; i < GameConstants.getNumGridRows(); i++) {
			canvas.drawLine(i * tileWidth, 0, i * tileWidth, getHeight(), dark);

			canvas.drawLine(i * tileWidth + 1, 0, i * tileWidth + 1,
					getHeight(), hilite);
		}

		// Draw the characters
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
				canvas.drawText(this.game.getTileString(i, j), 
						i * tileWidth + x,
						j * tileHeight + y,
						foreground);
			}
		}
	}

}
