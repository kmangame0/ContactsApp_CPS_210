import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ChainContactComparator implements Comparator<Contact>{

	private List<Comparator<Contact>> listComparators;
	
	public ChainContactComparator(Comparator <Contact> CompLName, Comparator <Contact> CompFName, Comparator <Contact> CompEmail, Comparator <Contact> CompPhoneNumber){
		this.listComparators = Arrays.asList(CompLName,CompFName,CompEmail,CompPhoneNumber);
		
	}
	@Override
	public int compare(Contact Contact1, Contact Contact2) {
		for(Comparator<Contact> comparator : listComparators){
			int result = comparator.compare(Contact1, Contact2);
			if(result!=0){
				return result;
			}
		}
		return 0;
	}

}
