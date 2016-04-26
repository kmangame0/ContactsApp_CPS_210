import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class ContactBook{
	private TreeSet<Contact> ContactIndex;
	File CB = new File("Contacts.txt");
	private final static String ls = System.getProperty("line.separator");
	
	public ContactBook(){
		ContactIndex = new TreeSet<Contact>(new ChainContactComparator(new CompareLastName(), new CompareFirstName(), new CompareEmail(), new ComparePhoneNumber()));
		CB = new File("Contacts.txt");
	}
	
	public void addContactToBook(Contact c) throws IOException{
		ContactIndex.add(c);
		
	}
	public void removefromBookandFile(Contact c) throws IOException{
		ContactIndex.remove(c);
		WriteCBToFile();
	}
	
	public void WriteCBToFile() throws IOException{
		PrintWriter pw = new PrintWriter(CB);
		for(Contact c: ContactIndex){
			pw.write(c.toString()+"\r\n");
		}
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
	
	public TreeSet<Contact> getIndex(){
		return ContactIndex;
	}
	
	public Contact[] getContactArray(){
		return ContactIndex.toArray(new Contact[0]);
	}
}
