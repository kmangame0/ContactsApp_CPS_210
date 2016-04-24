import java.util.Comparator;

public class CompareEmail implements Comparator <Contact>{

	@Override
	public int compare(Contact o1, Contact o2) {
		return o1.Email.compareTo(o2.Email);
	}

}
