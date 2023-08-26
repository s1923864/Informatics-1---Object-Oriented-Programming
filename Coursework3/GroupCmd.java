
import java.util.*;


public class GroupCmd extends LibraryCommand{

	
	//private instance field.
	private String argumentInput;
	
	
	//constructer.
	public GroupCmd(String argumentInput) {
		super(CommandType.GROUP,argumentInput);
		
	}




	//Group.
	public void execute(LibraryData data) {
		// TODO Auto-generated method stub
		
		
		
		if(data.getBookData().isEmpty()) {
			System.out.println("The library has no book entries.");
			return;
		}
		
		
		
		if(argumentInput.trim().equals("TITLE")) {
			
			ArrayList<ArrayList<String>> titles = new ArrayList<>();
			
			
			System.out.println("Grouped data by TITLE");
			
			
			
			for(int j = 0;j<=24;j++)
				titles.add(new ArrayList<String>());
			
			
			for(BookEntry a : data.getBookData()) {
				
				
			int i = a.getTitle().toUpperCase().charAt(0)-'A';
			
			
			if(Character.isDigit(a.getTitle().charAt(0)))
				 titles.get(24).add(a.getTitle());
			
			else titles.get(i).add(a.getTitle());
			
			
			
			}
			
			
			
			int num = -1;
			do {
				
				
				num++;
				  
				
				
				if(titles.get(num).isEmpty())
					continue;
				
			Collections.sort(titles.get(num));
				
			if(num==titles.size()-1)
			System.out.println("## [0-9]");
			else
			System.out.println("## " + (char)('A' + num));
			
			
			System.out.println(titles.get(num).toString().substring(1,titles.get(num).toString().length()-1).replaceAll(", ","\n"));			
				
				
				
			}while(num<=23);
			
			
			
		}
		
		else {
			
			
			Set<String> allauthors = new TreeSet<>();
			ArrayList<String> authors = new ArrayList<>();
			
			
			System.out.println("Grouped data by AUTHOR");
			
			for(BookEntry a : data.getBookData())
				allauthors.addAll(Arrays.asList(a.getAuthors()));
			
			
			authors.addAll(allauthors);
			Collections.sort(authors);
			
			int num = 0;
			
			do {
				
				System.out.println("## " + authors.get(num));
				for(BookEntry b : data.getBookData()) {
				   if(b.getAuthors().toString().contains(authors.get(num)))
					   System.out.println(b.getTitle());
				}
				
				
			}while(num<allauthors.size());
		}
		
			
		
	}
	
	
	
	//give parameter to instance field argumentInput.
	protected boolean parseArguments(String argumentInput) {
		
		
		if(argumentInput.equals("TITLE") || argumentInput.equals("AUTHOR")) {
			this.argumentInput = argumentInput;
			return true;
		}
		else return false;
		
		
			
	}
	
	
}
