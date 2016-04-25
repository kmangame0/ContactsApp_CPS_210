
public class CompareFirstName extends CompareName{

	@Override
	public int compare(Contact o1, Contact o2) {
		return o1.FirstName.compareTo(o2.FirstName) ;
	}

}
