import java.util.Objects;

public class ListCmd extends LibraryCommand{
	
	
  private String argumentInput;
	
	
  //constructer.
  public ListCmd(String argumentInput) {
	  super( CommandType.LIST , argumentInput);
  }


  //list the information about all books in library.
  public void execute(LibraryData data) {
	
    Objects.requireNonNull(data, "Given LibraryData must not be null.");
    
    
    //when no book in library.
    if(data.getBookData().isEmpty()) {
    	System.out.println("message: The library has no book entries.");
    	return;
    }
    
    
    //when argument is "short".
    if(this.argumentInput.equals("short") || this.argumentInput.equals("")) {
    	
    	
    	System.out.println(data.getBookData().size() + " books in library:");
    	
    	
    	for(BookEntry a : data.getBookData())
    		System.out.println(a.getTitle());
    	
    }
    
    
    //when argument is "long".
    else if(this.argumentInput.equals("long")) {
    	
    	System.out.println(data.getBookData().size() + " books in library:");
    	
    	for(BookEntry b : data.getBookData())
    		System.out.println(b + "\n");
    	
    }
    
    else 
    	throw new UnsupportedOperationException("invalid input argument!");
	
  
  }



//parse parameter to the instance field argumentInput.
  protected boolean parseArguments(String argumentInput) {
	
	  //ensure correct input of argument.
	  if(argumentInput == "short" || argumentInput == "long" || argumentInput == "") {
		  this.argumentInput = argumentInput;
	    return true;
	  }
	  else
		  return false;
	
  }


  }
