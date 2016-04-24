import java.util.Comparator;

public abstract class CompareName implements Comparator <Contact>{

	@Override
	public abstract int compare(Contact o1, Contact o2);

}
