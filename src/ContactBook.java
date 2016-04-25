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
	
	public ContactBook(){
		ContactIndex = new TreeSet<Contact>(new ChainContactComparator(new CompareFirstName(), new CompareLastName(), new CompareEmail(), new ComparePhoneNumber()));
		CB = new File("Contacts.txt");
	}
	
	public void addContactToBook(Contact c) throws IOException{
		ContactIndex.add(c);
		
	}
	public void removefromBookandFile(Contact c) throws IOException{
		ContactIndex.remove(c);
		
		File temp = new File("TempTestFile.txt");
		BufferedReader reader = new BufferedReader(new FileReader(CB));
		BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
		
		String cl;
		
		while((cl = reader.readLine()) != null){
			if(cl.contains(c.FirstName)&& cl.contains(c.LastName) && cl.contains(c.Email)&& cl.contains(c.PhoneNumber)){
				continue;
			}
			writer.write(cl);
		}
		writer.close();
		boolean successful = temp.renameTo(CB);
		System.out.println(successful);
		
	}
	
	public void WriteCBToFile() throws IOException{
		
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
	public TreeSet<Contact> getIndex(){
		return ContactIndex;
	}
}
