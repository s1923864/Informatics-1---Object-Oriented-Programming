import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {
	
    private String title;
    
    private String[] authors;
    
    private float rating;
    
    private String ISBN;
    
    private int pages;
    
    
    // Constructor.
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages ) {
    	
    	// Check parameter title.
    	Objects.requireNonNull(title,"Title can't be null.");
    	
    	// Check parameter authors.
    	for(String author : authors)
    	Objects.requireNonNull(author, "Every author can't be null.");
    	Objects.requireNonNull(authors, "Authors can't be null.");
    	
    	// Check parameter rating.
    	Objects.requireNonNull(rating, "Rating can't be null.");
    	if(rating < 0.0 || rating > 5.0)
    	throw new IllegalArgumentException("Invalid rating!");
    	
    	// Check parameter ISBN.
    	Objects.requireNonNull(ISBN, "ISBN can't be null.");
    	
    	// Check parameter pages.
    	Objects.requireNonNull(pages, "Pages can't be null.");
    	if(pages < 0)
    	throw new IllegalArgumentException("Invalid pages!");
    	
    	
    	// Initialise each private instance field.
    	this.title = title;
    	this.authors = authors;
    	this.rating = rating;
    	this.ISBN = ISBN;
    	this.pages = pages;
      	
    }
    
    
    
    
    // Get private instance field title.
    public String getTitle() {
    	return this.title;
    }
    
    // Get private instance field authors.
    public String[] getAuthors() {
    	return this.authors;
    }
    
    // Get private instance field rating.
    public float getRating() {
    	return this.rating;
    }
    
    // Get private instance field ISBN.
    public String getISBN() {
    	return this.ISBN;
    }
    
    // Get private instance field pages.
    public int getPages() {
    	return this.pages;
    }
    
    
    
    
    // Overwrite toString method.
    /**
     * The format of BookEntry object is :
     * 
     * <title>
     * by <author 1>, <author 2>,...
     * Rating: <rating>
     * ISBN: <ISBN>
     * <pages> pages
     */
	public String toString() {
		
		
		
	return	  this.title + "\n"
		
		      +    "by " + String.join(",",this.authors) + "\n"
		
		      +    "Rating: " + String.format("%.2f",this.rating) + "\n"
		
		      +    "ISBN: " + this.ISBN + "\n"
		
		      +    this.pages + " pages";
		
		
	}
	
	
	
	// Overwrite equals method.
	public boolean equals(Object o) {
		
		if (this==o)
			return true;
		
		// Compare class.
		if(o instanceof BookEntry) {
			
			BookEntry a = (BookEntry) o;
			
			// Compare 5 private instance fields.
			if(this.getTitle().equals(a.getTitle()))
				
			if(this.getRating() == a.getRating())
					
			if(this.getPages() == a.getPages())
						
			if(this.getISBN().equals(a.getISBN()))
							
			if(this.getAuthors().equals(a.getAuthors()))
			return true;
		}
		
		return false;
		
		
	}
	
	
	// Overwrite hashcode method.
	public int hashCode() {
		
		return  ( ( ( ( this.getTitle().hashCode()*4 + 
				(int) (this.getRating() )*4 
				+ this.getPages() ) )*4
				+ this.getISBN().hashCode() )*4
				+ this.getAuthors().hashCode() )*4;
	}
	
}
