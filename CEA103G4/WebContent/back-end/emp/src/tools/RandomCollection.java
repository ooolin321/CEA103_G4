package tools;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection {
	// Double(total): <String bookID : Double weight>
	private final NavigableMap<Double, Map.Entry<String, Double>> map = new TreeMap<Double, Map.Entry<String, Double>>();
	private final Random random;
	private double total = 0;

	public RandomCollection() {
		this(new Random());
	}

	public RandomCollection(Random random) {
		this.random = random;
	}

	public RandomCollection add(double weight, String bookID) {
		if (weight <= 0)
			return this;
		total += weight;
		map.put(total, new AbstractMap.SimpleEntry<String, Double>(bookID, weight));
		return this;
	}

	public String next() {
		// 隨機取出特定key之後，將total扣除其weight，並把所有total比他高的元素total扣除其weight，remove才不會失敗，可取出不重複的元素
		double value = random.nextDouble() * total;
		double key = map.higherKey(value);
		double weight = map.get(key).getValue();
//		System.out.println("=====\n前一輪total: " + total);

		total -= weight;
		String bookID = map.get(key).getKey();

//		System.out.println("本次抽中value: " + value);
//		System.out.println("本次抽中key: " + key);
//		System.out.println("本次抽中書: " + bookID + " - " + weight);
//		System.out.println("本輪total: " + total);

		Iterator<Double> iterator = map.navigableKeySet().iterator();
		List<Map.Entry<Double, Map.Entry<String, Double>>> temp = new LinkedList<Map.Entry<Double, Map.Entry<String, Double>>>();

		while (iterator.hasNext()) {
			double otherKey = iterator.next();
			if (otherKey > value) {
				temp.add(new AbstractMap.SimpleEntry<Double, Map.Entry<String, Double>>(otherKey - weight,
						map.get(otherKey)));
				iterator.remove();
			}
		}

		// 移除本該被移除的key
		if (!temp.isEmpty()) {
			temp.remove(0);
		}

		temp.forEach(tempEntry -> {
			map.put(tempEntry.getKey(), tempEntry.getValue());
		});

//		for (Double total : map.navigableKeySet()) {
//			System.out.println(total + " - " + map.get(total).getKey() + " - " + map.get(total).getValue());
//		}

		return bookID;
	}
}