import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeSet;

public class ContactBook {
	private TreeSet<Contact> ContactIndex;
	
	public ContactBook(){
		ContactIndex = new TreeSet<Contact>(new ChainContactComparator(new CompareFirstName(), new CompareLastName(), new CompareEmail(), new ComparePhoneNumber()));
	}
	
	public void addContactToBook(Contact c) throws IOException{
		ContactIndex.add(c);
		
	}
	
	public void WriteCBToFile() throws IOException{
		
		File CB = new File("Contacts.txt");
		PrintWriter pw = new PrintWriter(CB);
		for(Contact c: ContactIndex){
			pw.write(c.toString()+"\r\n");
		}
		pw.close();
	}
	
	public String toString(){
		String retVal = "";
		for(Contact c : ContactIndex){
			retVal += c.toString() +"\n";
		}
		return retVal;
	}
	
}
