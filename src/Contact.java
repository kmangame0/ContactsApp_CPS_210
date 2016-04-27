
public class Contact{

	protected String FirstName;
	protected String LastName;
	protected String PhoneNumber;
	protected String Email;
	public String getFirstName() {
		return FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public String getFormattedPhoneNumber() {
		String formattedPhoneNumber = String.format("(%s) %s-%s", PhoneNumber.substring(0,3), 
						PhoneNumber.substring(3,6), PhoneNumber.substring(6,10));
		return formattedPhoneNumber;
	}

	public String getEmail() {
		return Email;
	}

	public Contact(String FirstName, String LastName, String Email, String PhoneNumber) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.PhoneNumber = PhoneNumber;
		this.Email = Email;
	}
	
	public String toString(){
		return FirstName.trim() +", " +LastName.trim() +", " +Email.trim() +", " +PhoneNumber.trim();
		
	}
}
