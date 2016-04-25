import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class ContactBook {
	private TreeSet<Contact> ContactIndex;
	private final static String ls = System.getProperty("line.separator");
	
	public ContactBook(){
		ContactIndex = new TreeSet<Contact>(new ChainContactComparator
				(new CompareFirstName(), new CompareLastName(), 
				 new CompareEmail(), new ComparePhoneNumber()));
	}
	
	public void addContactToBook(Contact c) {
		ContactIndex.add(c);
	}
	
	public void removeContactFromBook(Contact c) {
		ContactIndex.remove(c);
	}
	
	public void WriteCBToFile() throws IOException{
		File CB = new File("Contacts.txt");
		PrintWriter pw = new PrintWriter(CB);
		
		pw.write(this.toString());
		
		pw.close();
	}
	
	public String toString(){
		StringBuilder retVal = new StringBuilder();
		
		for(Contact c : ContactIndex){
			retVal.append(c.toString().trim() + ls);
		}
		retVal.setLength(retVal.length()-1);
		return retVal.toString();
	}
	
	public Contact[] GetContacts() {
		return ContactIndex.toArray(new Contact[0]);
	}
}
