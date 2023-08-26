import java.nio.file.Path;
import java.util.Scanner;

/** 
 * The Main class of the fox hound program.
 * 
 * It contains the main game loop where main menu interactions
 * are processed and handler functions are called.
  */
public class FoxHoundGame {

    /** 
     * This scanner can be used by the program to read from
     * the standard input. 
     * 
     * Every scanner should be closed after its use, however, if you do
     * that for StdIn, it will close the underlying input stream as well
     * which makes it difficult to read from StdIn again later during the
     * program.
     * 
     * Therefore, it is advisable to create only one Scanner for StdIn 
     * over the course of a program and only close it when the program
     * exits. Additionally, it reduces complexity. 
     */
    private static final Scanner STDIN_SCAN = new Scanner(System.in);

    /**
     * Parse command line arguments for the Fox-Hound Game
     * 
     * If no argument is passed, a default dimension of 
     * {@value FoxHoundUtils#DEFAULT_DIM} is used. 
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be
     * board dimensions.
     * @return the dimension of the game board
     */
    private static int parseDimensions(String[] args) {
        if (args.length < 1) { // no argument provided, using default dimensions
            return FoxHoundUtils.DEFAULT_DIM;
        }
        
        int dimension = -1;
        try {
            dimension = Integer.parseInt(args[0]);
            if (dimension < FoxHoundUtils.MIN_DIM || dimension > FoxHoundUtils.MAX_DIM) {
                System.err.println("ERROR: Dimensions must be between " 
                    + FoxHoundUtils.MIN_DIM + " and " + FoxHoundUtils.MAX_DIM + ".");
                dimension = -1;
            }
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid dimension argument: " + args[0]);
        }

        if (dimension == -1) {
            System.err.println("ERROR: Parsing dimensions failed. Default will be used.");
            dimension = FoxHoundUtils.DEFAULT_DIM;
        }

        return dimension;
    }

    /**
     * Query the player for the name of a file and save the current
     * game state to this file.
     * 
     * @param players current position of all figures on the board in board coordinates
     * @param turn the figure type that has the next move
     */
    private static void handleSaving(String[] players, char turn) {
        Path filename = FoxHoundUI.fileQuery(STDIN_SCAN);
        boolean success = FoxHoundIO.saveGame(players, turn, filename);
        if (!success) {
            System.err.println("ERROR: Saving file failed.");
        }
    }

    /**
     * Query the player for the name of a file to be loaded,
     * load the corresponding file content and update figure
     * positions accordingly.
     * 
     * @param players current position of all figures on the board in board coordinates
     * @return The figure type that has the next move or an error
     * code if loading failed.
     */
    private static char handleLoading(String[] players) {
        Path filename = FoxHoundUI.fileQuery(STDIN_SCAN);
        return FoxHoundIO.loadGame(players, filename);
    }

    /**
     * Query the user for coordinates regarding the next move and
     * update the figure positions accordingly.
     * 
     * @param dim the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     * @param figure the figure type that has the next move
     */
    private static void handleMove(int dim, String[] players, char figure) {

        boolean moveDone = false;
        while(!moveDone) {
            String[] pos = FoxHoundUI.positionQuery(dim, STDIN_SCAN);

            if (FoxHoundUtils.isValidMove(dim, players, figure, pos[0], pos[1])) {
                FoxHoundUtils.updatePlayerPos(players, pos[0], pos[1]);
                moveDone = true;
            } else {
                System.err.println("ERROR: Invalid move. Try again!");
            }
        }
    }

    /**
     * Check if the current positions of all figures on the 
     * game board constitute a winning condition for one 
     * of the parties.
     * 
     * @param dim the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     * @param figure the figure type that has moved last
     * @return true if one of the parties has one, false otherwise
     */
    private static boolean checkWin(int dim, String[] players, char figure) {
        boolean win = false;
        String winner = null;
        if (figure == FoxHoundUtils.FOX_FIELD) {
            win = FoxHoundUtils.isFoxWin(players[players.length - 1]);
            winner = "The Fox wins!";
        } else {
            win = FoxHoundUtils.isHoundWin(players, dim);
            winner = "The Hounds win!";
        }

        if (win) {
            System.out.println(winner);
            FoxHoundUI.displayBoard(players, dim);
        }
        return win;
    }

    /**
     * Swap between fox and hounds to determine the next
     * figure to move.
     * 
     * @param currentTurn last figure to be moved
     * @return next figure to be moved
     */
    private static char swapPlayers(char currentTurn) {
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    /**
     * The main loop of the game. Interactions with the main
     * menu are interpreted and executed here.
     * 
     * @param dim the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     */
    private static void gameLoop(int dim, String[] players) {

        // start each game with the Fox
        char turn = FoxHoundUtils.FOX_FIELD;
        boolean exit = false;
        while (!exit) {
            System.out.println("\n#################################");
            FoxHoundUI.displayBoard(players, dim);
            FoxHoundUI.displayPlayerPositions(players);

            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);
            
            // handle menu choice
            switch (choice) {
                case FoxHoundUI.MENU_MOVE:
                    handleMove(dim, players, turn);
                    exit = checkWin(dim, players, turn);
                    turn = swapPlayers(turn);
                    break;
                case FoxHoundUI.MENU_SAVE:
                    handleSaving(players, turn);
                    break;
                case FoxHoundUI.MENU_LOAD:
                    char nextTurn = handleLoading(players);
                    if (FoxHoundUtils.isValidTurn(nextTurn)) {
                        turn = nextTurn;
                    } else {
                        System.err.println("ERROR: Loading from file failed.");
                    }
                    break;
                case FoxHoundUI.MENU_EXIT:
                    exit = true;
                    break;
                default:
                    System.err.println("ERROR: invalid menu choice: " + choice);
            }
        }
    }

    /**
     * Entry method for the Fox and Hound game. 
     * 
     * The dimensions of the game board can be passed in as
     * optional command line argument.
     * 
     * If no argument is passed, a default dimension of 
     * {@value FoxHoundUtils#DEFAULT_DIM} is used. 
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be
     * board dimensions.
     */
    public static void main(String[] args) {
        int dimension = parseDimensions(args);

        String[] players = FoxHoundUtils.initialisePositions(dimension);

        gameLoop(dimension, players);

        // Close the scanner reading the standard input stream       
        STDIN_SCAN.close();
    }
}
