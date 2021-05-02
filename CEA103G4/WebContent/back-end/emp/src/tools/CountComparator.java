package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CountComparator implements Comparator<Map.Entry<String,Integer>>{

	@Override
	public int compare(Entry<String, Integer> l, Entry<String, Integer> r) {
		//對次數Value降序
		return r.getValue() - l.getValue();
	}

	public static List<Map.Entry<String, Integer>> entriesSortedByValues(Map<String, Integer> map) {
		// https://stackoverflow.com/questions/2864840/treemap-sort-by-value
		// http://www.51gjie.com/java/677.html
		List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(sortedEntries, new CountComparator());
		return sortedEntries;
	}
}