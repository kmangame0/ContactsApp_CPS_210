
public class CompareLastName extends CompareName{

	@Override
	public int compare(Contact o1, Contact o2) {
		return o1.LastName.compareTo(o2.LastName);
	}

}
