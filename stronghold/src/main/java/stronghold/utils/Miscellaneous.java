package stronghold.utils;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.people.Person;

public class Miscellaneous {
	public static HashMap<String, Integer> countPeopleFromArray(ArrayList<Person> people) {
		HashMap<String, Integer> result = new HashMap<>();
		for (Person person : people) {
			int current = (result.get(person.getName()) == null ? 0 : result.get(person.getName()));
			result.put(person.getName(), current + 1);
		}
		return result;
	}
}
