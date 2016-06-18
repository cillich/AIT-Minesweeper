package hu.ait.android.minesweeper.model;

import java.util.ArrayList;
import java.util.Random;

public class MinesweeperModel {


    //*************** Initial Values **********************

    /**
     * Instance of the MinesweeperModel
     */
    private static MinesweeperModel instance = null;

    /**
     * Number of fields wide the game board is
     */
    public int gameBoardWidth = 6;

    /**
     * Number of fields tall the game board is
     */
    public int gameBoardHeight = 6;

    /**
     * Number of mines there are in the game.
     * Must be less than gameBoardWidth * gameBoardHeight
     */
    public int numberOfMines = 4;

    // All possible field states
    public static final short UNREVEALED = 0;
    public static final short EMPTY = 1;
    public static final short NUMBER = 2;
    public static final short FLAG = 3;
    public static final short MINE = 4;

    // Different board sizes and mine number based on difficult level
    public static final int EASY_BOARD_SIZE = 5;
    public static final int EASY_MINE_NUMBER = 3;
    public static final int MEDIUM_BOARD_SIZE = 7;
    public static final int MEDIUM_MINE_NUMBER = 5;
    public static final int DIFFICULT_BOARD_SIZE = 9;
    public static final int DIFFICULT_MINE_NUMBER = 7;


    //***************** Instantiating a new instance of MinesweeperModel ********************

    /**
     * Instantiates the new MinesweeperModel with the generated game board and mines
     */
    private MinesweeperModel () {
        resetModel();
        resetMines();
    }

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }

        return instance;
    }



    //******************* Game Model ****************************

    /**
     * Matrix holding the field states visible to the player
     */
    private short[][] model = new short[gameBoardHeight][gameBoardWidth];

    /**
     * Matrix holding the coordinates of the mines on the game board
     */
    private int[][] mines = new int[numberOfMines][2];

    /**
     * The current time the user has been playing the game
     */
    private int timeCounter = 0;

    /**
     * If the user is in Flag Mode, this boolean will be set to true.
     * Flag Mode means a flag will be placed in any field the user touches.
     */
    private boolean flagMode = false;

    /**
     * If this boolean is set to true, the player has lost the game
     */
    private boolean isLost = false;

    /**
     * If the game board had been resized from the MenuActivity, this value will be set to true so
     * the view class knows to clear the screen. This prevents game board drawing issues when the user
     * goes back to the MenuActivity and selects a new difficulty level.
     */
    public boolean newGameBoard = true;


    //************************** Model Getters and Setters *************************

    /**
     * Sets the difficulty level of the game based on the int.
     * 1 = easy
     * 2 = medium
     * 3 = difficult
     */
    public void setGameDifficulty(int i) {
        switch (i) {
            case 1:
                gameBoardWidth = EASY_BOARD_SIZE;
                gameBoardHeight = EASY_BOARD_SIZE;
                numberOfMines = EASY_MINE_NUMBER;
                break;
            case 2:
                gameBoardHeight = MEDIUM_BOARD_SIZE;
                gameBoardWidth = MEDIUM_BOARD_SIZE;
                numberOfMines = MEDIUM_MINE_NUMBER;
                break;
            case 3:
                gameBoardWidth = DIFFICULT_BOARD_SIZE;
                gameBoardHeight = DIFFICULT_BOARD_SIZE;
                numberOfMines = DIFFICULT_MINE_NUMBER;
                break;
        }
        newGameBoard = true;
    }
    /**
     * Gets the content in the desired field from the game model
     *
     * @param x X value of the field
     * @param y Y value of the field
     * @return content of desired field
     */
    public short getFieldContent(int x, int y) {
        return model[y][x];
    }

    /**
     * Sets the content of the given field in the game model to the given game state
     *
     * @param x X value of the field
     * @param y Y value of the field
     * @param content updated game state of the field
     */
    public void setFieldContent(int x, int y, short content) {
        model[y][x] = content;
    }

    /**
     *
     * @return true if the game is currently in flag mode, false otherwise
     */
    public boolean isFlagModeOn() {
        return flagMode;
    }

    /**
     * Switches the value of the game's flag mode
     */
    public void toggleFlagMode() {
        flagMode = !flagMode;
    }

    /**
     * Updates the time counter to the new time
     */
    public void incrementTimeCounter() {
        timeCounter++;
    }

    /**
     * Resets the time counter to 0
     */
    public void resetTimeCounter() {
        timeCounter = 0;
    }

    /**
     * Gets the time the player has been playing
     *
     * @return the current time since the game started
     */
    public int getTimeCounter() {
        return timeCounter;
    }

    /**
     * Getter for the array of mines on the game board
     *
     * @return A matrix of all mines' coordinates
     */
    public int[][] getMines() {
        return mines;
    }

    /**
     * Gets the field content of the given mine
     *
     * @param mine array of row and column of the desired mine
     * @return content of desired mine field
     */
    public short getMineFieldContent(int[] mine) {
        int xPosition = mine[1];
        int yPosition = mine[0];
        return getFieldContent(xPosition, yPosition);
    }

    public void setMineFieldContent(int[] mine, short content) {
        int xPosition = mine[1];
        int yPosition = mine[0];
        setFieldContent(xPosition, yPosition, content);
    }

    /**
     * Determines if the player lost the game
     *
     * @return true if the player lost the game, false otherwise
     */
    public boolean isLost() {
        return isLost;
    }

    public void restartGame() {
        resetModel();
        resetMines();
        resetTimeCounter();
        isLost = false;
    }




    /**
     * Determines if the given field contains a mine
     *
     * @param x x coordinate of the field
     * @param y y coordinate of the field
     * @return true if the given field is a mine, false othewise
     */
    public boolean isMine(int x, int y) {
        for (int i = 0; i < numberOfMines; i++) {
            if (mines[i][0] == y) {
                if (mines[i][1] == x) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Counts the number of fields in the game that are in a given field state
     *
     * @param fieldState desired field state
     * @return int representing the number of fields with the given state
     */
    public int gameBoardCounter(short fieldState) {
        int total = 0;
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                if (getFieldContent(j, i) == fieldState) {
                    total++;
                }

            }
        }
        return total;
    }

    //********************** Game State Getters and Setters ********************


    /**
     * Determines if the player won the game
     *
     * @return true if the player won the game, false otherwise
     */
    public boolean isWon() {
        for (int i = 0; i < numberOfMines; i++) {
            short field = getMineFieldContent(mines[i]);
            if (field != MinesweeperModel.FLAG) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the game model when the user touches at the given coordinates
     *
     * @param x column of the field touched
     * @param y row of the field touched
     */
    public void onTouchHandler(int x, int y) {
        short field = getFieldContent(x, y);
        if (flagMode) {
            if (field == MinesweeperModel.FLAG) {
                setFieldContent(x, y, MinesweeperModel.UNREVEALED);
            } else if (isMine(x, y)) {
                setFieldContent(x, y, MinesweeperModel.FLAG);
            } else {
                isLost = true;
            }
        } else {
            if (isMine(x, y) && field != MinesweeperModel.FLAG) {
                isLost = true;
            } else if (!isMine(x, y)){
                if (numberOfMinesNearby(x, y) > 0) {
                    setFieldContent(x, y, MinesweeperModel.NUMBER);
                } else {
                    setFieldContent(x, y, MinesweeperModel.EMPTY);
                    revealAllAdjacentFields(x, y);
                }
            }
        }
    }

    /**
     * Reveals empty and number fields adjacent to the given field. Only called on empty fields.
     *
     * @param x x coordinate of the given field
     * @param y y coordinate of the given field
     */
    private void revealAllAdjacentFields(int x, int y) {
        // We can assume no mine will be found here because this function is only called
        // on empty fields so only empty or number fields can be adjacent
        if ((y - 1) >= 0 && getFieldContent(x, y - 1) == MinesweeperModel.UNREVEALED) {
            if (numberOfMinesNearby(x, y - 1) > 0) {
                setFieldContent(x, y - 1, MinesweeperModel.NUMBER);
                revealHorizontalAdjacentFields(x, y - 1);
            } else {
                setFieldContent(x, y - 1, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x, y - 1);
            }
        }
        if ((y + 1) < gameBoardHeight && getFieldContent(x, y + 1) == MinesweeperModel.UNREVEALED) {
            if (numberOfMinesNearby(x, y + 1) > 0) {
                setFieldContent(x, y + 1, MinesweeperModel.NUMBER);
                revealHorizontalAdjacentFields(x, y + 1);
            } else {
                setFieldContent(x, y + 1, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x, y + 1);
            }
        }
        if ((x - 1) >= 0 && getFieldContent(x - 1, y) == MinesweeperModel.UNREVEALED) {
            if (numberOfMinesNearby(x - 1, y) > 0) {
                setFieldContent(x - 1, y, MinesweeperModel.NUMBER);
                revealVerticalAdjacentFields(x - 1, y);
            } else {
                setFieldContent(x - 1, y, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x - 1, y);
            }
        }
        if((x + 1) < gameBoardWidth && getFieldContent(x + 1, y) == MinesweeperModel.UNREVEALED) {
            if (numberOfMinesNearby(x + 1, y) > 0) {
                setFieldContent(x + 1, y, MinesweeperModel.NUMBER);
                revealVerticalAdjacentFields(x + 1, y);
            } else {
                setFieldContent(x + 1, y, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x + 1, y);
            }
        }
    }


    private void revealHorizontalAdjacentFields(int x, int y) {
        if ((x - 1) >= 0 && getFieldContent(x - 1, y) == MinesweeperModel.UNREVEALED && !isMine(x - 1, y)) {
            if (numberOfMinesNearby(x - 1, y) > 0) {
                setFieldContent(x - 1, y, MinesweeperModel.NUMBER);
            } else {
                setFieldContent(x - 1, y, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x - 1, y);
            }
        }
        if((x + 1) < gameBoardWidth && getFieldContent(x + 1, y) == MinesweeperModel.UNREVEALED && !isMine(x + 1, y)) {
            if (numberOfMinesNearby(x + 1, y) > 0) {
                setFieldContent(x + 1, y, MinesweeperModel.NUMBER);
            } else {
                setFieldContent(x + 1, y, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x + 1, y);
            }
        }
    }

    private void revealVerticalAdjacentFields(int x, int y) {
        if ((y - 1) >= 0 && getFieldContent(x, y - 1) == MinesweeperModel.UNREVEALED && !isMine(x, y - 1)) {
            if (numberOfMinesNearby(x, y - 1) > 0) {
                setFieldContent(x, y - 1, MinesweeperModel.NUMBER);
            } else {
                setFieldContent(x, y - 1, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x, y - 1);
            }
        }
        if ((y + 1) < gameBoardHeight && getFieldContent(x, y + 1) == MinesweeperModel.UNREVEALED && !isMine(x, y + 1)) {
            if (numberOfMinesNearby(x, y + 1) > 0) {
                setFieldContent(x, y + 1, MinesweeperModel.NUMBER);
            } else {
                setFieldContent(x, y + 1, MinesweeperModel.EMPTY);
                revealAllAdjacentFields(x, y + 1);
            }
        }
    }


    /**
     * Resets all field states to the beginning state
     */
    private void resetModel() {
        model = new short[gameBoardHeight][gameBoardWidth];
        for (int x = 0; x < gameBoardWidth; x++) {
            for (int y = 0; y < gameBoardHeight; y++) {
                setFieldContent(x, y, MinesweeperModel.UNREVEALED);
            }
        }
    }

    /**
     * Generates new random mines
     */
    private void resetMines() {
        mines = new int[numberOfMines][2];
        Random randomCoordinate = new Random();
        ArrayList<Integer> columnValues = new ArrayList<Integer>();
        ArrayList<Integer> rowValues = new ArrayList<Integer>();

        for (int i = 0; i < numberOfMines; i++) {
            boolean mineSet = false;

            while (!mineSet) {
                int newCol = randomCoordinate.nextInt(gameBoardWidth);
                int newRow = randomCoordinate.nextInt(gameBoardHeight);

                // Ensures there is not already a mine in the randomly chosen field
                if (!columnValues.contains(newCol) && !rowValues.contains(newRow)) {
                    mines[i][0] = newRow;
                    mines[i][1] = newCol;
                    columnValues.add(newCol);
                    rowValues.add(newRow);
                    mineSet = true;
                }
            }


        }

    }



    /**
     * Returns the number of mines in the fields adjacent to the given field
     *
     * @param x X coordinate of the field
     * @param y Y coordinate of the field
     * @return The number of mines nearby
     */
    public int numberOfMinesNearby(int x, int y) {
        int numberOfNearbyMines = 0;

        if (isMine(x - 1, y - 1)) {
            numberOfNearbyMines++;
        }
        if (isMine(x - 1, y)) {
            numberOfNearbyMines++;
        }
        if (isMine(x - 1, y + 1)) {
            numberOfNearbyMines++;
        }
        if (isMine(x, y - 1)) {
            numberOfNearbyMines++;
        }
        if (isMine(x, y + 1)) {
            numberOfNearbyMines++;
        }
        if (isMine(x + 1, y - 1)) {
            numberOfNearbyMines++;
        }
        if (isMine(x + 1, y)) {
            numberOfNearbyMines++;
        }
        if (isMine(x + 1, y + 1)) {
            numberOfNearbyMines++;
        }

        return numberOfNearbyMines;
    }


}
