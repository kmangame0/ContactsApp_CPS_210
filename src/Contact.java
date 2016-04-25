
public class Contact{

	protected String FirstName;
	protected String LastName;
	protected String PhoneNumber;
	protected String Email;
	
	public Contact(String FirstName, String LastName, String PhoneNumber, String Email) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.PhoneNumber = PhoneNumber;
		this.Email = Email;
	}
	
	public String getFirstName() {
		return FirstName;
	}

	public String getLastName() {
		return LastName;
	}
	

	public String toString(){
		return FirstName.trim() + "," + LastName.trim() + "," + PhoneNumber.trim() + "," + Email.trim();
	}
}
