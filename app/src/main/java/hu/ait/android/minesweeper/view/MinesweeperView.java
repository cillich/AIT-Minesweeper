package hu.ait.android.minesweeper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.android.minesweeper.MainActivity;
import hu.ait.android.minesweeper.R;
import hu.ait.android.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {

    /**
     * Original size for numbers before being scaled by size of board
     */
    public static final int NUMBER_SIZE = 300;
    /**
     * Board game background style
     */
    private Paint paintBackground;

    /**
     * Field edge lines' style
     */
    private Paint paintLine;

    /**
     * Number style
     */
    private Paint paintNumber;
    /**
     * How many fields wide the game board is
     */
    private int gameBoardWidth;

    /**
     * How many fields tall the game board is
     */
    private int gameBoardHeight;

    // Image bitmaps
    private Bitmap emptyField;
    private Bitmap flagField;
    private Bitmap mineField;


    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Storing the game board width and height
        gameBoardWidth = MinesweeperModel.getInstance().gameBoardWidth;
        gameBoardHeight = MinesweeperModel.getInstance().gameBoardHeight;

        // If there is a new game board, redraw the screen and set newGameBoard to false
        if (MinesweeperModel.getInstance().newGameBoard) {
            clearScreen();
            MinesweeperModel.getInstance().newGameBoard = false;
        }

        // Setting the background color for the game board view
        paintBackground = new Paint();
        paintBackground.setColor(Color.GRAY);
        paintBackground.setStyle(Paint.Style.FILL);

        // Setting the line style for Minesweeper field edges
        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        // Setting the text style for Minesweeper field numbers
        paintNumber = new Paint();
        paintNumber.setColor(Color.RED);

        // Dynamically size number text
        paintNumber.setTextSize(NUMBER_SIZE/MinesweeperModel.getInstance().gameBoardWidth);



        // Image for empty field
        emptyField = BitmapFactory.decodeResource(getResources(), R.drawable.square);

        // Image for flag field
        flagField = BitmapFactory.decodeResource(getResources(), R.drawable.flag);

        // Image for mine field
        mineField = BitmapFactory.decodeResource(getResources(), R.drawable.mine);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBackground);
        drawGameBoard(canvas);
        drawFields(canvas);
        if (MinesweeperModel.getInstance().isLost()) {
            ((MainActivity)getContext()).showFinishedGameMessage(getContext().getString(R.string.lostMessage));
        } else if (MinesweeperModel.getInstance().isWon()) {
            ((MainActivity)getContext()).showFinishedGameMessage(getContext().getString(R.string.wonMessage));
        }

        emptyField = Bitmap.createScaledBitmap(emptyField,
                getWidth() / gameBoardWidth,
                getHeight() / gameBoardHeight, true);

        flagField = Bitmap.createScaledBitmap(flagField,
                getWidth() / gameBoardWidth,
                getHeight() / gameBoardHeight, true);

        mineField = Bitmap.createScaledBitmap(mineField,
                getWidth() / gameBoardWidth,
                getHeight() / gameBoardHeight, true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN &&
                !(MinesweeperModel.getInstance().isLost() || MinesweeperModel.getInstance().isWon())) {

            int tX = ((int) event.getX()) / (getWidth() / gameBoardWidth);
            int tY = ((int) event.getY()) / (getHeight() / gameBoardHeight);

            if (tX < gameBoardWidth && tY < gameBoardHeight) {
                MinesweeperModel.getInstance().onTouchHandler(tX, tY);
                // Check if the game has been lost or won and send a snackbar if it has

                invalidate();
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * Draws the Minesweeper game board onto the given canvas
     *
     * @param canvas Where the game board will be drawn
     */
    private void drawGameBoard(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // draws the horizontal lines
        for (int i = 1; i < gameBoardHeight; i++) {
            canvas.drawLine(0, i * (getHeight() / gameBoardHeight),
                    getWidth(), i * (getHeight() / gameBoardHeight),
                    paintLine);
        }

        // draws the vertical lines
        for (int i = 1; i < gameBoardWidth; i++) {
            canvas.drawLine(i * (getWidth() / gameBoardWidth), 0,
                    i * (getWidth() / gameBoardWidth), getHeight(),
                    paintLine);
        }



    }

    /**
     * Draws the state of each field on the game board.
     *
     * @param canvas Where the fields will be drawn
     */
    private void drawFields(Canvas canvas) {
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                short content = MinesweeperModel.getInstance().getFieldContent(j, i);

                switch (content) {
                    case MinesweeperModel.EMPTY:

                        canvas.drawBitmap(emptyField, getXCoordinate(j), getYCoordinate(i), null);
                        break;

                    case MinesweeperModel.NUMBER:
                        int nearbyMines = MinesweeperModel.getInstance().numberOfMinesNearby(j, i);
                        canvas.drawText(String.valueOf(nearbyMines),
                                getXCoordinate(j), getUpperYCoordinate(i),
                                paintNumber);
                        break;

                    case MinesweeperModel.FLAG:

                        canvas.drawBitmap(flagField, getXCoordinate(j), getYCoordinate(i), null);
                        break;

                    case MinesweeperModel.MINE:

                        canvas.drawBitmap(mineField, getXCoordinate(j), getYCoordinate(i), null);
                        break;

                }

            }
        }

        if (MinesweeperModel.getInstance().isLost()) {
            int[][] mines = MinesweeperModel.getInstance().getMines();

            for (int i = 0; i < mines.length; i++) {
                mineField = Bitmap.createScaledBitmap(mineField,
                        getWidth() / gameBoardWidth,
                        getHeight() / gameBoardHeight, true);
                canvas.drawBitmap(mineField, getXCoordinate(mines[i][1]), getYCoordinate(mines[i][0]), null);
            }
        }
    }

    /**
     * Finds the X coordinate of the given field on the canvas
     *
     * @param col Column of the field
     * @return X coordinate of the field
     */
    private float getXCoordinate(int col) {
        return col * (getWidth() / gameBoardWidth);
    }

    /**
     * Finds the Y coordinate of the given field on the canvas
     *
     * @param row Row of the field
     * @return Y coordinate of the field
     */
    private float getYCoordinate(int row) {
        return row * (getHeight() / gameBoardHeight);
    }

    /**
     * Finds the uppermost Y coordinate of the given field on the canvas
     *
     * @param row Row of the field
     * @return Uppermost Y coordinate of the field
     */
    private float getUpperYCoordinate(int row) {
        return getYCoordinate(row) + (getHeight() / gameBoardHeight);
    }

    /**
     * Restarts the game and clears the screen
     */
    public void clearScreen() {
        MinesweeperModel.getInstance().restartGame();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);

    }
}
