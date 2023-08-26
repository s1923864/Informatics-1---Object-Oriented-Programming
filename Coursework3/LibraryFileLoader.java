import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     * @throws UnsupportedOperationException Not implemented yet!
     */
    public List<BookEntry> parseFileContent() {
    	
        // TODO Remove exception and implement me
    	
    	//this list contains the information from a csv file.
    	List<String> content = new ArrayList<>();
    	content = this.fileContent; 
    	
    	//returned list.
    	List<BookEntry> all = new ArrayList<>();
    	
    	for(int i = 1;i<content.size();i++) {
    		
    		String[] details = content.get(i).split(",");
    		
    		
    				
    					
    				//parse the parameter the constructor of BookEntry.
    					all.add(new BookEntry(details[0],
    							              details[1].split("-"),
    							              Float.parseFloat(details[2]),
    							              details[3],
    							              Integer.parseInt(details[4])));
    					
    					
    			
    				
    		
    		
    				
    					
    	}
    	
    	return all;
    	
        
    }
    public static void main(String[] args) {
    	LibraryFileLoader a = new LibraryFileLoader();
    	a.loadFileContent(Paths.get("C:/Users/Lyw20/Desktop/inf1b-cw3_v0.2/data/books01.csv"));
    	System.out.println(a.fileContent.toString());
    }
   
}
