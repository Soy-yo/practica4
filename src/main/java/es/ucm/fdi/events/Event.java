package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.TrafficSimulator;

import java.util.function.Predicate;

public abstract class Event {

	protected final int time;
	protected final String id;

	Event(int time, String id) {
		this.time = time;
		this.id = id;
	}

	public int getTime() {
		return time;
	}

	public String getId() {
		return id;
	}

	public abstract void execute(TrafficSimulator simulator);

	public interface Builder {

		Event parse(IniSection section);

		default boolean isValid(String id) {
			return id.matches("[a-zA-Z1-9_]++");
		}

		default int parseInt(IniSection section, String key, int defaultValue,
				Predicate<Integer> predicate) {
			try {
				int result = Integer.parseInt(section.getValue(key));
				if (predicate.test(result)) {
					return result;
				}
			} catch (Exception e) {
			}
			return defaultValue;
		}

		default String[] parseIdList(IniSection section, String key) {
			String values = section.getValue(key);
			return values.split("[, ]+");
		}

		default int getIntValue(String sectionName, IniSection section)
				throws IllegalArgumentException {
			int result;
			try {
				result = Integer.parseInt(section.getValue(sectionName));
				if (result <= 0) {
					throw new IllegalArgumentException(sectionName.substring(0,
							1).toUpperCase()
							+ sectionName.substring(1) + " must be positive");
				}
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(sectionName.substring(0, 1)
						.toUpperCase()
						+ sectionName.substring(1)
						+ " must be a number", e);
			}
			return result;
		}

	}

	@Override
	public String toString() {
		return getClass() + ": " + id;
	}

}
