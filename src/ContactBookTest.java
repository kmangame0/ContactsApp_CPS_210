import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactBookTest {

	public static void main(String[] args) throws IOException {
		ContactBook cb = new ContactBook();
		
		List<Contact> contactAl = new ArrayList<Contact>();
		
		Contact Jessie = new Contact("Jessie", "Brown", "8452368874", "JessieBrown234@gmail.com");
		Contact Bill = new Contact("Bill", "Smith", "9893452232", "SBill22@yahoo.com");
		Contact James  = new Contact("James", "Gilbert", "9893452232", "SBill22@yahoo.com");
		Contact b  = new Contact("Bill3", "Smith", "9893452232", "SBill22@yahoo.com");
		Contact Copy = new Contact("Bill3", "Smith", "9893452232", "SBill22@yahoo.com");
				
		cb.addContactToBook(Jessie);
		cb.addContactToBook(Bill);
		cb.addContactToBook(James);
		cb.addContactToBook(b);
		
		cb.WriteCBToFile();
		
		
		Collections.sort(contactAl, new ChainContactComparator(
				new CompareFirstName(), new CompareLastName(), new CompareEmail(), new ComparePhoneNumber()));
		System.out.println();
		for(Contact c: contactAl){
			System.out.println(c);
		}
	}
}
