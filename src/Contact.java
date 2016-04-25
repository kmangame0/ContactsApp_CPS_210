import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Contact{

	protected String FirstName;
	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	protected String LastName;
	protected String PhoneNumber;
	protected String Email;
	
	public Contact(String FirstName, String LastName, String Email, String PhoneNumber) throws IOException{
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.PhoneNumber = PhoneNumber;
		this.Email = Email;
		
	}

	public String toString(){
		return FirstName +", " +LastName +", " +PhoneNumber +", " +Email ;
		
	}

	
	
}
