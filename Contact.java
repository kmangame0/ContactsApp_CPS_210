import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Contact{

	protected String FirstName;
	protected String LastName;
	protected String PhoneNumber;
	protected String Email;
	
	public Contact(String FirstName, String LastName, String PhoneNumber, String Email) throws IOException{
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.PhoneNumber = PhoneNumber;
		this.Email = Email;
		
	}

	public String toString(){
		return FirstName +", " +LastName +", " +PhoneNumber +", " +Email ;
		
	}

	
	
}
