
import java.util.Arrays;

public class RemoveCmd extends LibraryCommand{
	
	private String argumentInput;
	
	
	//constructer.
	public RemoveCmd(String argumentInput) {
		super(CommandType.REMOVE,argumentInput);
	}

	
	
	//check whether the argumentInput is valid. 
	protected boolean parseArguments(String argumentInput) {
		

		if((argumentInput.startsWith("AUTHOR") && argumentInput.trim().length()>7)
		|| (argumentInput.startsWith("TITLE") && argumentInput.trim().length()>6)) {
			this.argumentInput = argumentInput; 
			return true;
		}
		else return false;
			
	}

	
	
	
	public void execute(LibraryData data) {
		// TODO Auto-generated method stub
		
		//when REMOVE TITLE ...
		if(this.argumentInput.trim().startsWith("TITLE")) {
			
			//get title name.
			String information = this.argumentInput.trim().replace("TITLE", "").trim();
			
			//get all books' number.
			int size = data.getBookData().size(); 
			
			
			//search the book need to remove and remove it.
			for(BookEntry s : data.getBookData())
				if(s.getTitle().equals(information))
					data.getBookData().remove(s);
			
			
			//print the result.
			System.out.println(size!=data.getBookData().size() ? information + ": removed successfully." 
					                                           : information + ": not found.");
			
		}
		
		//when REMOVE AUTHOR ...
		else {
			
			//get author name.
			String information = this.argumentInput.trim().replace("AUTHOR", "").trim();
			
			int num = 0;
			int i = 0;
			
			//get all books.
			
			
			//find the book need to remove and remove it.
			do {
				
				if(Arrays.toString(data.getBookData().get(i).getAuthors()).contains(information)) {
					
					num ++;
					data.getBookData().remove(i);
					continue;
					
				}
				
				i++;
					
			}while(i<data.getBookData().size());
			
			//print the result.
			System.out.println(num + " books removed for author: " + information);
		}
			
			
		
	}
}
