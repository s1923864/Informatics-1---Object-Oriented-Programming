import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.InvalidPathException;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Objects;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all user interface related
 * functionality such as printing menus and displaying the game board.
 */
public class FoxHoundUI {

    /** Indicates a field on the game board not occupied by a figure. */
    private static final char EMPTY_FIELD = '.';
    /** For numbers below this, use a leading zero to display them. */
    private static final int LEADING_ZERO_THRESHOLD = 9;

    /** Number of main menu entries. */
    private static final int MENU_ENTRIES = 4;
    /** Main menu display string. */
    private static final String MAIN_MENU =
        "\n1. Move\n2. Save Game\n3. Load Game" 
      + "\n4. Exit\n\nEnter 1 - 4:";

    /** 
     * Message with instructions for the position menu. 
     * 
     * Provide identifier for first column and last column
     * as well as board dimension as formatting arguments.
     */
    private static final String POSITION_MENU = 
        "\nProvide origin and destination coordinates.\n"
      + "Enter two positions between %s1-%s%d:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;
    /** Menu entry to save the game. */
    public static final int MENU_SAVE = 2;
    /** Menu entry to load a game. */
    public static final int MENU_LOAD = 3;
    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 4;

    /**
     * Display the game board using ASCII graphics.
     * 
     * @param players position of all figures on the board in board coordinates
     * @param dim dimension of the game board
     * @throws IllegalArgumentException if players array contains illegal entries or dimension is invalid
     * @throws NullPointerException if players array is null or contains null values
     */
    public static void displayBoard(String[] players, int dim) {
        FoxHoundUtils.boardSetupCheck(players, dim);

        StringBuilder bld = new StringBuilder();
        bld.append(getColumnHeader(dim)).append("\n");

        for (int row = 0; row < dim; row++) {
            String rowId = getRowId(dim, row);
            bld.append(rowId).append(" ");

            for (int col = 0; col < dim; col++) {
                bld.append(getFieldSymbol(row, col, players));                
            }
            bld.append(" ").append(rowId).append("\n");
        }

        bld.append("\n").append(getColumnHeader(dim)).append("\n");

        System.out.println(bld);
    }

    /**
     * Calculate the graphical display for the row indicator.
     * 
     * @param dim dimension of the game board
     * @param row row of the game board (starting with zero from top to bottom)
     * @return a formatted string indicating the row of the game board
     */
    private static String getRowId(int dim, int row) {
        String prefix = "";
        if (dim > LEADING_ZERO_THRESHOLD && row < LEADING_ZERO_THRESHOLD) {
            prefix = "0";
        }
        return prefix + (row + 1);
   }

   /**
    * Pick the correct field symbol for a given position on the game 
    * board. 
    * 
    * This depends on whether the field is occupied or not and if so
    * by which figure.
    *
    * @param row row of the game board (starting with zero from top to bottom)
    * @param col row of the game board (starting with zero from left to right)
    * @param players position of all figures on the board in board coordinates
    * @return a character as graphical representation of the specified game board field
    */
    private static char getFieldSymbol(int row, int col, String[] players) {
        String bCoords = FoxHoundUtils.getBoardCoords(row, col);

        if (FoxHoundUtils.isFoxCoord(bCoords, players)) {
            return FoxHoundUtils.FOX_FIELD;
        } else if (FoxHoundUtils.isHoundCoord(bCoords, players)) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return EMPTY_FIELD;
        }
    }

    /**
     * Calculate the column header for the given board dimension.
     * 
     * Columns are alphabetical starting with {@value FoxHoundUtils#COLUMN_START_COORD}.
     * 
     * @param dim dimension of the game board
     * @return a formatted string with a column header
     */
    private static String getColumnHeader(int dim) {
        final String padding = dim <= LEADING_ZERO_THRESHOLD ? "  " : "   ";
        
        StringBuilder bld = new StringBuilder();
        bld.append(padding);
        for(int i = 0; i < dim; i++) {
            bld.append("" + (char)(FoxHoundUtils.COLUMN_START_COORD + i));
        }
        bld.append(padding).append("\n");
    
        return bld.toString();
    }

    /**
     * Display current board coordinates of all figures.
     * 
     * @param players position of all figures on the board in board coordinates
     */
    public static void displayPlayerPositions(String[] players) {
        System.out.println("Players: " + Arrays.toString(players));
    }

    /**
     * Print the main menu and query the user for an entry selection.
     * 
     * @param figureToMove the figure type that has the next move
     * @param stdin a Scanner object to read user input from
     * @return a number representing the menu entry selected by the user
     * @throws IllegalArgumentException if the given figure type is invalid
     * @throws NullPointerException if the given Scanner is null
     */
    public static int mainMenuQuery(char figureToMove, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        if (figureToMove != FoxHoundUtils.FOX_FIELD 
         && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }

        String nextFigure = 
            figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";

        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }

            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }

            stdin.nextLine(); // throw away the rest of the line
        }

        return input;
    }

    /**
     * Query the user for two position coordinates to update
     * a figure on the game board.
     * 
     * Positions given by the user must be in board coordinates
     * 
     * @param dim dimension of the game board
     * @param stdin a Scanner object to read user input from
     * @return the coordinates provided by the user with the origin as
     * first entry and the destination as second
     * @throws IllegalArgumentException if the given dimension is invalid
     * @throws NullPointerException if the given Scanner is null
     */
    public static String[] positionQuery(int dim, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        FoxHoundUtils.dimensionCheck(dim);

        String start = "" + FoxHoundUtils.COLUMN_START_COORD; 
        String end = "" + (char)(FoxHoundUtils.COLUMN_START_COORD + dim - 1);
        String menuMsg = String.format(POSITION_MENU, start, end, dim);

        String[] input = null;
        while (input == null) {
            System.out.println(menuMsg);

            String line = stdin.nextLine();
            input = line.split(" ");

            boolean validInput = 
                input.length == 2
             && FoxHoundUtils.isBoardCoordinate(input[0], dim) 
             && FoxHoundUtils.isBoardCoordinate(input[1], dim);

            if (!validInput) {
                System.err.println("ERROR: Please enter valid " 
                    + "coordinate pair separated by space.");
                input = null; // reset input variable
            }
        }

        return input;
    }

    /**
     * Query the user for a file path.
     * 
     * @param stdin a Scanner object to read user input from
     * @return path to a file provided by the user
     */
    public static Path fileQuery(Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null.");

        Path result = null;
        while (result == null) {
            System.out.println("Enter file path:");
            String input = stdin.nextLine();
            try {
                result = Paths.get(input);
            } catch(InvalidPathException e) {
                System.err.println("ERROR: Please provide valid path.");
            }
        }
        return result;
    }
}







