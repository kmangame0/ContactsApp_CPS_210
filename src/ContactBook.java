import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class ContactBook {
	private TreeSet<Contact> ContactIndex;
	private final static String ls = System.getProperty("line.separator");
	
	public ContactBook(){
		ContactIndex = new TreeSet<Contact>(new ChainContactComparator(new CompareFirstName(), new CompareLastName(), new CompareEmail(), new ComparePhoneNumber()));
	}
	
	public void addContactToBook(Contact c) throws IOException{
		ContactIndex.add(c);
		
	}
	
	public void WriteCBToFile() throws IOException{
		
		File CB = new File("Contacts.txt");
		PrintWriter pw = new PrintWriter(CB);
		
		pw.write(this.toString());
		
		pw.close();
	}
	
	public String toString(){
		String retVal = "";
		for(Contact c : ContactIndex){
			retVal += c.toString() + ls ;
		}
		return retVal;
	}
}