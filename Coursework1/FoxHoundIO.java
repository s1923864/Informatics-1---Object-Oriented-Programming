import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
public class FoxHoundIO {

    /** Indicator for a loading error. */
    private static final char LOAD_ERROR = '#';

    /**
     * Load player positions and next turn from the given file.
     * 
     * The expected file format is a symbol for the next move (F or H)
     * followed by all hound positions in board coordinates, followed
     * by the fox position in board coordinates. All of it should be 
     * separated with single spaces and have no line breaks.
     * 
     * Loading files only works for classic 8 x 8 board dimensions.
     * 
     * @param players position of all figures on the board in board coordinates.
     * Contents loaded from file are saved in the given array.
     * @param fileName the file name where saved positions can be found
     * @return indicator for figure occupying this field or error value in case of loading error
     */
    public static char loadGame(String[] players, Path fileName) {
        Objects.requireNonNull(players, "Given players array must not be null.");
        Objects.requireNonNull(players, "Given fileName must not be null.");
        FoxHoundUtils.boardSetupCheck(players, FoxHoundUtils.DEFAULT_DIM);

        if (!Files.isRegularFile(fileName) || !Files.isReadable(fileName)) {
            System.err.println("ERROR: Invalid file name: " + fileName);
            return LOAD_ERROR;
        }

        String content = null;
        try {
            content = Files.readString(fileName);
        } catch (Exception e) {
            System.err.println("ERROR: Reading file content failed: " + e);
            return LOAD_ERROR;
        }

        return parseContent(content, players);
    }

    /**
     * Parse content loaded from a game file and 
     * create a corresponding players array.
     * 
     * @param content figure positions loaded from file
     * @param players position of all figures on the board in board coordinates.
     * Contents loaded from file are saved in the given array.
     * @return indicator for figure occupying this field or error value in case of formatting error
     */
    private static char parseContent(String content, String[] players) {
        // check lines
        String[] lines = content.split("\\n");
        if (lines.length != 1) {
            System.err.println("ERROR: Invalid number of lines: " + lines.length);
            return LOAD_ERROR;
        }

        // check line elements
        String[] elements = lines[0].split(" ");
        if (elements.length != (FoxHoundUtils.DEFAULT_DIM / 2) + 2) {
            System.err.println("ERROR: Invalid number of elements: " + elements.length);
            return LOAD_ERROR;
        }

        // check coordinates
        for (int i = 1; i < elements.length; i++) {
            String pos = elements[i];
            if (!FoxHoundUtils.isBoardCoordinate(pos, FoxHoundUtils.DEFAULT_DIM)) {
                System.err.println("ERROR: Invalid coordinate: " + pos);
                return LOAD_ERROR;
            }
        }

        // check specifier for next move
        if (elements[0].length() > 1 || !FoxHoundUtils.isValidTurn(elements[0].charAt(0))) {
            System.err.println("ERROR: Invalid figure symbol: " + elements[0]);
            return LOAD_ERROR;       
        }

        // save board coordinates
        for (int i = 1; i < elements.length; i++) {
            players[i - 1] = elements[i];
        }

        // return next move
        return elements[0].charAt(0);
    }

    /**
     * Save the given board positions and next turn at the given file location.
     * 
     * The given file must not yet exist and the specified directory must be 
     * available.
     * 
     * Saving files only works for classic 8 x 8 board dimensions.
     * 
     * @param players position of all figures on the board to be saved
     * @param toMove the next figure to move
     * @param fileName the file name and path to save the file at
     * @return true if saving was successful, false otherwise
     */
    public static boolean saveGame(String[] players, char toMove, Path fileName) {
        Objects.requireNonNull(players, "Given players array must not be null.");
        Objects.requireNonNull(players, "Given fileName must not be null.");
        FoxHoundUtils.boardSetupCheck(players, FoxHoundUtils.DEFAULT_DIM);
        if (!FoxHoundUtils.isValidTurn(toMove)) {
            throw new IllegalArgumentException("Given figure id invalid: " + toMove);
        }

        if (Files.exists(fileName)) {
            System.err.println("ERROR: File already exists: " + fileName);
            return false;
        }

        // if there is a parent folder, make sure it exists
        Path parent = fileName.getParent();
        if (parent != null && !Files.exists(parent)) {
            System.err.println("ERROR: Subdirectory does not exist: " + parent);
            return false;
        }

        StringJoiner output = new StringJoiner(" ");
        output.add("" + toMove);
        for (String pos : players) {
            output.add(pos);
        }

        try {
            Files.writeString(fileName, output.toString());
        } catch (Exception e) {
            System.err.println("ERROR: Writing to file failed: " + e);
            return false;
        }

        System.out.println("Board saved to " + fileName);
        return true;
    }
}
