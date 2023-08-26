import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class AddCmd extends LibraryCommand{
	
	private String argumentInput;
	
	public AddCmd(String Input) {
		
		super(CommandType.ADD,Input);
		
		// TODO Auto-generated constructor stub
	}
	
	
	// add new books to LibraryData.
	public void execute(LibraryData data) {
		
		Objects.requireNonNull(data, "Given LibraryData must not be null.");
		
			File a = new File(this.argumentInput);
		  data.loadData(Paths.get(a.getAbsolutePath()));
	
			
			
		
		
	}
	
	
	protected boolean parseArguments(String argumentInput) {
		
		Objects.requireNonNull(argumentInput, "Given input argument must not be null.");
		File a = new File(argumentInput);
		
		File file = new File(a.getAbsolutePath());
		
		//Check whether the format of file is csv and whether the path exists.
		if(argumentInput.endsWith(".csv")&&file.exists()) {
			
			
			this.argumentInput = argumentInput; 
			return true;
				
	}
		return false;

}
}
