package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.TrafficSimulator;

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

  @Override
  public String toString() {
    return getClass() + ": " + id;
  }

  public interface Builder {

    Event parse(IniSection section);

    default boolean matchesType(IniSection section) {
      return section.getValue("type") == null;
    }

    default boolean isValid(String id) {
      return id.matches("[a-zA-Z1-9_]++");
    }

    default int parsePositiveInt(IniSection section, String key, int defaultValue) {
      try {
        int result = Integer.parseInt(parseString(section, key));
        if (result >= 0) {
          return result;
        }
      } catch (Exception e) {
      }
      return defaultValue;
    }

    default long parsePositiveLong(IniSection section, String key, long defaultValue) {
      try {
        long result = Long.parseLong(parseString(section, key));
        if (result > 0) {
          return result;
        }
      } catch (Exception e) {
      }
      return defaultValue;
    }

    default String[] parseIdList(IniSection section, String key, int minElements) {
      String values = parseString(section, key);
      String[] result = values.split("[, ]+");
      if (result.length < minElements) {
        throw new IllegalArgumentException("The id list for " + key + " must contain at list "
            + minElements + " elements");
      }
      return result;
    }

    default String parseString(IniSection section, String key) {
      String result = section.getValue(key);
      if (result == null) {
        throw new IllegalArgumentException("missing " + key);
      }
      return result;
    }

    default int parsePositiveInt(IniSection section, String key) {
      int result;
      try {
        result = Integer.parseInt(parseString(section, key));
        if (result <= 0) {
          throw new IllegalArgumentException(key + " must be positive");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(key + " must be a number", e);
      }
      return result;
    }

    default double parsePositiveDouble(IniSection section, String key, double maxValue) {
      double result;
      try {
        result = Double.parseDouble(parseString(section, key));
        if (result < 0 || result > maxValue) {
          throw new IllegalArgumentException(key + " has to be between 0 and " + maxValue);
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(key + " must be a number");
      }
      return result;
    }

    default String getId(IniSection section) {
      String id = parseString(section, "id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id + " is not a valid id");
      }
      return id;
    }

  }

}
