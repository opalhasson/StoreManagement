package Model;

import java.util.Comparator;

public class SortByBarcodeDESC implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		int compare = o1.compareTo(o2);
		if (compare < 0)
			return 1;
		
		else if (compare > 0)
			return -1;
		
		else
			return 0;
	}
}
