
public class SearchCmd extends LibraryCommand{
	
	
	//private instance field.
	private String argumentInput;
	
	
	//constructer.
	public SearchCmd(String argumentInput) {
		super(CommandType.SEARCH,argumentInput);
	}
	
	
	
	//give parameter to instance field argumentInput.
	protected boolean parseArguments(String argumentInput) {
		
		if(argumentInput.length()>=1 && !(argumentInput.contains(" "))) {
			this.argumentInput = argumentInput;
			return true;
		}
		else return false;
			
	}


	
	//Search
	public void execute(LibraryData data) {
		// TODO Auto-generated method stub
		
		
		//whether find the keyword.
		boolean found = false;
		
		//input keyword
		String keyword = this.argumentInput.toLowerCase();
		
		//search keyword
		for(BookEntry book : data.getBookData())
			if(book.getTitle().toLowerCase().contains(keyword)) {
				System.out.println(book.getTitle());
				found = true;
			}
		
		//when nothing found.
		if(!found)
		System.out.println("No hits found for search term: " + this.argumentInput);
		
	}

}
