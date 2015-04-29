package json.templates.song;

import java.util.Comparator;

public class SongComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		Song s1 = (Song)o1;
		Song s2 = (Song)o2;
		return s1.id.compareTo(s2.id);
	}
}