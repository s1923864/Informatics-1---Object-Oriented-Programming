import java.util.Objects;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
public class FoxHoundUtils {

    // ATTENTION: Changing the following given constants can 
    // negatively affect the outcome of the auto grading!

    /** Default dimension of the game board in case none is specified. */
    public static final int DEFAULT_DIM = 8;
    /** Minimum possible dimension of the game board. */
    public static final int MIN_DIM = 4;
    /** Maximum possible dimension of the game board. */
    public static final int MAX_DIM = 26;

    /** Start letter for columns in board coordinates. */
    public static final char COLUMN_START_COORD = 'A';
    /** Symbol to represent a hound figure. */
    public static final char HOUND_FIELD = 'H';
    /** Symbol to represent the fox figure. */
    public static final char FOX_FIELD = 'F';
    /** The row in which the fox figure needs to be in order to win the game. */
    public static final int FOX_WIN_ROW = 0;


    // -------------- ERROR CHECKING ------------------------------------------

    /**
     * Check if the given dimension is within valid bounds.
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param dim the board dimension to be checked
     * @throws IllegalArgumentException if given dimensions are out of bounds
     */
    public static void dimensionCheck(int dim) {
        if (dim < MIN_DIM || dim > MAX_DIM) {
            throw new IllegalArgumentException("Given dimension must be between " 
            + MIN_DIM + " and " + MAX_DIM + " but is: " + dim);
        }
    }

    /**
     * Check the setup of the board by validating the given players
     * array against the given dimension.
     * 
     * @param players position of all figures on the board in board coordinates
     * @param dim dimension of the game board
     * @throws IllegalArgumentException if the given players array has invalid entries 
     * or does not match with the given board dimensions
     * @throws IllegalArgumentException if given dimensions are out of bounds
     */
    public static void boardSetupCheck(String[] players, int dim) {
        dimensionCheck(dim);

        if(!isValidPlayersArray(players, dim)) {
            throw new IllegalArgumentException("Given players array is invalid.");
        }
    }

    /**
     * Check if the given position has correct board coordinates considering
     * a specific dimension.
     * 
     * @param pos position to be validated
     * @param dim dimension of the game board
     * @throws NullPointerException if the given position is null
     * @throws IllegalArgumentException if the given coordinate does not match
     * for the given dimension or is otherwise invalid
     */
    private static void boardCoordCheck(String pos, int dim) {
        if (!isBoardCoordinate(pos, dim)) {
            throw new IllegalArgumentException("Given position must" 
            + " be a valid board coordinate for dimension " + dim 
            + " but is: " + pos);
        }
    }

    /**
     * Check if the given position has correct board coordinates.
     * 
     * @param pos position to be validated
     * @throws NullPointerException if the given position is null
     * @throws IllegalArgumentException if the given coordinate is not 
     * a valid board coordinate
     */
    private static void boardCoordCheck(String pos) {
        if (!isBoardCoordinate(pos)) {
            throw new IllegalArgumentException("Given position must" 
            + " be a valid board coordinate but is: " + pos);
        }
    }

    /**
     * Check if the given players array has valid entries and matches
     * the given board dimension.
     * 
     * @param players position of all figures on the board in board coordinates
     * @param dim dimension of the game board
     * @return true if the players array is valid, false otherwise
     * @throws NullPointerException if the given players array or an entry within 
     * the players array is null
     */
    private static boolean isValidPlayersArray(String[] players, int dim) {
        Objects.requireNonNull(players, "Given players array must not be null.");

        if (players.length != (dim / 2) + 1) {
            return false;
        }

        for (String pos : players) {
            if (!isBoardCoordinate(pos, dim)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Check if the given figure indicator is valid.
     * 
     * @param turn the figure indicator to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isValidTurn(char turn) {
        return turn == FOX_FIELD || turn == HOUND_FIELD;
    }

    // -------------- COORDINATE HELPER ------------------------------------------

    /**
     * Translate numeric row and column coordinates in 
     * a String pair of board coordinates.
     *
     * Board coordinates consist in a letter indicating the column
     * followed by a number indicating the row.
     *
     * Column coordinates start with 0,0 in the top left corner
     * while board coordinates start with A1 in the top left corner.
     *
     * @param row the row coordinate to be translated
     * @param column the column coordinate to be translated
     * @return a translated board coordinate
     */
    public static String getBoardCoords(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Given coordinates must be larger than zero.");
        }
        
        return "" + (char)(COLUMN_START_COORD + column) + (row + 1);
    }

    /**
     * Extract the row of the game board from the given 
     * board coordinates. 
     * 
     * Rows start with zero and go from top to bottom.
     * 
     * @param boardCoords position to extract row from in board coordinates
     * @return the extracted row 
     * @throws NullPointerException if the given board coordinate is null
     * @throws IllegalArgumentException if the given board coordinate has an invalid format
     */
    public static int getRowCoord(String boardCoords) {
        boardCoordCheck(boardCoords);
        return Integer.parseInt(boardCoords.substring(1)) - 1;
    }

    /**
     * Extract the column of the game board from the given 
     * board coordinates. 
     * 
     * Columns start with zero and go from left to right.
     * 
     * @param boardCoords position to extract column from in board coordinates
     * @return the extracted column 
     * @throws NullPointerException if the given board coordinate is null
     * @throws IllegalArgumentException if the given board coordinate has an invalid format
     */
    public static int getColCoord(String boardCoords) {
        boardCoordCheck(boardCoords);
        return boardCoords.charAt(0) - COLUMN_START_COORD;
    }

    /**
     * Validate if the given position is in board coordinates.
     * 
     * @param pos the position to be validated
     * @return true if position is valid, false otherwise
     * @throws NullPointerException if the given position is null
     */
    public static boolean isBoardCoordinate(String pos) {
        return isBoardCoordinate(pos, MAX_DIM);
    }

    /**
     * Validate if the given position is in board coordinates 
     * considering a specific board dimension.
     * 
     * @param pos the position to be validated
     * @param dim the dimension of the game board
     * @return true if position is valid, false otherwise
     * @throws NullPointerException if the given position is null
     */
    public static boolean isBoardCoordinate(String pos, int dim) {
        Objects.requireNonNull(pos, "Given position must not be null.");

        if (pos.length() < 2) {
            return false;
        }
        char column = pos.charAt(0);
        String row = pos.substring(1);

        if (column < COLUMN_START_COORD 
            || column > COLUMN_START_COORD + dim - 1) {
            return false;
        }

        // if the row is not numeric using a regular expression
        // \d+ means look for one or more digits
        if(!row.matches("\\d+")) {
            return false;
        }

        // check if row number is within bounds
        int rowNum = Integer.parseInt(row);
        if (rowNum > dim || rowNum < 1) {
            return false;
        }

        return true;
    }

    // ----------------- Players Array Helper ----------------------------------

    /**
     * Update the array with figure positions for the given values.
     *
     * If no figure with the given old position was found, false is returned. If one
     * can be found, it is updated with the given newPos and true is returned.
     * 
     * @param players position of all figures on the board in board coordinates
     * @param oldPos the old position of the figure to be updated
     * @param newPos the new position of the figure to be updated
     * @return true if successful, false otherwise
     * @throws NullPointerException if any of the three parameters are null
     */
    public static boolean updatePlayerPos(String[] players, String oldPos, String newPos) {
        Objects.requireNonNull(players, "Given player positions must not be null.");
        Objects.requireNonNull(oldPos, "Given old position must not be null.");
        Objects.requireNonNull(newPos, "Given new position must not be null.");

        int pIdx = getPlayerIdx(oldPos, players);
        if (pIdx != -1) {
            players[pIdx] = newPos;
            return true;
        }

        return false;
    }

    /**
     * Check if the given board coordinate is the coordinate of a hound
     * in the given players array.
     * 
     * @param bCoord the coordinate to be checked in board coordinates
     * @param players position of all figures on the board in board coordinates
     * @return true if it is a hound position, false otherwise
     * @throws NullPointerException if any of the two parameters are null
     */
    public static boolean isHoundCoord(String bCoord, String[] players) {
        Objects.requireNonNull(players, "Given player positions must not be null.");
        Objects.requireNonNull(bCoord, "Given coordinate must not be null.");

        int pIdx = getPlayerIdx(bCoord, players);
        return pIdx >= 0 && pIdx < players.length - 1;
    }

    /**
     * Check if the given board coordinate is the coordinate of the fox
     * in the given players array.
     * 
     * @param bCoord the coordinate to be checked in board coordinates
     * @param players position of all figures on the board in board coordinates
     * @return true if it is the fox position, false otherwise
     * @throws NullPointerException if any of the two parameters are null
     */
    public static boolean isFoxCoord(String bCoord, String[] players) {
        Objects.requireNonNull(players, "Given player positions must not be null.");
        Objects.requireNonNull(bCoord, "Given coordinate must not be null.");

        int pIdx = getPlayerIdx(bCoord, players);
        return pIdx == players.length - 1;
    }

    /**
     * Find the given coordinate in the given players array
     * and return the corresponding array index or -1 if it
     * cannot be found.
     * 
     * @param bCoord the coordinate to be searched
     * @param players position of all figures on the board in board coordinates
     * @return the index of the given position in the given players array or -1 if
     * it cannot be found
     */
    private static int getPlayerIdx(String bCoord, String[] players) {
        int result = -1;
        for (int i = 0; i < players.length; i++) {
            if (bCoord.equals(players[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    // -------------------- Position Init -------------------------------

    /**
     * Initialise the positions on the board of all figures for the
     * given board dimensions. 
     *
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param dim dimension of the game board
     * @return position of all figures on the board in board coordinates 
     * in their initial position
     * @throws IllegalArgumentException if given dimensions are out of bounds
     */
    public static String[] initialisePositions(int dim) {
        dimensionCheck(dim);

        final int numHounds = dim / 2;
        final int numFox = 1;

        final int idxHounds = 0;
        final int idxFox = idxHounds + numHounds;

        final int houndStartRow = 0;
        final int foxStartRow = dim - 1;
        /* The relevant parameters are whether the dimension
         * is even or uneven and whether half the dimension is
         * even or uneven, i.e. does the last row start with
         * a white or black field and how many fields until the middle.
         */
        final int foxStartCol = (dim / 2) + (dim % 2) - ((dim / 2) % 2);

        String[] pos = new String[numHounds + numFox];
 
        int houndIdx = 0;
        // place the hounds in their start row spaced out by one field
        for (int colIdx = 1; colIdx < dim; colIdx += 2) {
            pos[houndIdx] = getBoardCoords(houndStartRow, colIdx);
            houndIdx++;
        }

        // place the fox
        pos[idxFox] = getBoardCoords(foxStartRow, foxStartCol);

        return pos;
    }

    // ----------------- Win Conditions ----------------------------------

    /**
     * Check if the given position fulfills the winning
     * positions for the fox.
     * 
     * @param foxPos position to be checked
     * @return true if the given position means a win for the fox, false otherwise
     * @throws NullPointerException if the given position is null
     * @throws IllegalArgumentException if the given coordinate is not 
     * a valid board coordinate
     */
    public static boolean isFoxWin(String foxPos) {
        boardCoordCheck(foxPos);

        int foxRow = getRowCoord(foxPos);
        return foxRow == FOX_WIN_ROW;
    }

    /**
     * Return true if the fox can no longer move.
     *
     * There are five possible scenarios for this to happen:
     * .H.H. ...H. .H... ..... ..... 
     * ..F.. ....F F.... .H.H. .H... 
     * .H.H. ...H. .H... ..F.. F.... 
     * 
     * There would be four more but players are only allowed on 
     * one colour of chess fields and in the top row the fox wins.
     * ..... F.... ..F.. ....F
     * ...H. .H... .H.H. ...H.
     * ....F ..... ..... .....
     *
     * In short, if all up left, up right, down left and down right
     * are either blocked by a hound or the edge of the board. The
     * top row technically counts as a fox win but is included for
     * completeness.
     *
     * @param players positions of all game pieces in board coordinates
     * @param dimension dimension of the board
     * @return true if the fox can no longer move, false otherwise
     * @throws IllegalArgumentException if the given players array contains invalid values or if
     * the given dimensions are invalid
     * @throws NullPointerException if the given players array is null or contains null values.
     */
    public static boolean isHoundWin(String[] players, int dimension) {
        boardSetupCheck(players, dimension);

        String foxPosB =  players[players.length - 1];
        String[] cornerFields = getCornerFields(foxPosB, dimension);
      
        // if any of the corner fields are not taken
        // by a hound, the fox can still move
        for (String corner : cornerFields) {
            if(corner != null && !isHoundCoord(corner, players)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return all valid corner fields to the given board 
     * coordinate.
     *
     * If the coordinate is at the border of the field, then
     * fields which would be outside will be null in the 
     * returned array.
     *
     * @param posB position to be checked in board coordinates
     * @param dim dimension of the board
     * @return array with coordinates of the corners in relation
     * to the given board coordinate
     * @throws IllegalArgumentException if the given dimension or board coordinates are invalid
     * @throws NullPointerException if the given position is null
     */
    public static String[] getCornerFields(String posB, int dim) {
        dimensionCheck(dim);
        boardCoordCheck(posB, dim);

        final int numCorners = 4;
        final int topLeft = 0;
        final int bottomLeft = 1;
        final int bottomRight = 2;
        final int topRight = 3;
        
        int row = getRowCoord(posB);
        int col = getColCoord(posB);

        String[] result = new String[numCorners];

        if (row - 1 >= 0 && col - 1 >= 0) {// top left
            result[topLeft] = getBoardCoords(row - 1, col - 1);
        }
        if (row + 1 < dim && col - 1 >= 0) {// bottom left
            result[bottomLeft] = getBoardCoords(row + 1, col - 1);
        }
        if (row + 1 < dim && col + 1 < dim) {// bottom right
            result[bottomRight] = getBoardCoords(row + 1, col + 1);   
        }    
        if (row - 1 >= 0 && col + 1 < dim) {// top right
            result[topRight] = getBoardCoords(row - 1, col + 1);
        }

        return result;
    }

    // ----------------- Move Conditions ----------------------------------

    /**
     * Check if the given destination can be reached from the given origin
     * for the specified figure.
     *
     * The move is valid if
     *  - the origin contains the specified figure type
     *  - the origin is different from the destination
     *  - the destination is one diagonal field away from the origin
     *  - the destination is not occupied by another figure
     *  - hounds are not moving backwards
     * 
     * @param dim the dimension of the game board
     * @param players position of all figures on the board in board coordinates
     * @param figure the type of figure to be moved
     * @param origin the starting position of the move
     * @param dest the end position of the move
     * @return true if the move was successful, false otherwise
     * @throws IllegalArgumentException if the given dimension, entries in players array,
     * given figure type or given origin and destination are invalid.
     * @throws NullPointerException if the given players array is null or has null entries or
     * if the given origin or destination positions are null
     */
    public static boolean isValidMove(int dim, String[] players, char figure, 
            String origin, String dest) {

        boardSetupCheck(players, dim);
        boardCoordCheck(origin, dim);
        boardCoordCheck(dest, dim);
        if (!isValidTurn(figure)) {
                throw new IllegalArgumentException("Invalid figure type: " + figure);
        }

        // figure wants to stay in the same spot
        if (origin.equals(dest)) {
            return false;
        }

        // specified figure is not actually in given origin position  
        if ((figure == HOUND_FIELD && !isHoundCoord(origin, players)) 
            || (figure == FOX_FIELD && !isFoxCoord(origin, players))) {
            return false; 
        }
        
        return isMoveToValidCorner(dim, players, figure, origin, dest);
    }

    /**
     * Check if the specified move is a move into a valid corner field
     * of the given origin field.
     * 
     * A corner field is valid if it is within board coordinates,
     * not occupied by a different figure and not backward movement for
     * hounds.
     * 
     * @param dim the dimension of the game board
     * @param players position of all figures on the board in board coordinates
     * @param figure the type of figure to be moved
     * @param origin the starting position of the move
     * @param dest the end position of the move
     * @return true if the move is into a valid corner, false otherwise
     */
    private static boolean isMoveToValidCorner(int dim, String[] players, 
            char figure, String origin, String dest) {
       
        boolean success = false;
        String[] cornerFields = getCornerFields(origin, dim);
        for (String corner : cornerFields) {
            // destination is a valid corner field
            // and not occupied by another player
            if(corner != null && corner.equals(dest) 
               && !isHoundCoord(dest, players) 
               && !isFoxCoord(dest, players)) {

                success = true;
                // if the figure is a Hound, it may not move backwards
                if (figure == HOUND_FIELD) {
                    int originRow = getRowCoord(origin);
                    int destRow = getRowCoord(dest);
                    success = originRow < destRow;
                }

                break;
            }
        }       

        return success;
    }
}
