import java.util.Comparator;

public class ComparePhoneNumber implements Comparator <Contact>{

	@Override
	public int compare(Contact o1, Contact o2) {
		return o1.PhoneNumber.compareTo(o2.PhoneNumber);
	}

}
