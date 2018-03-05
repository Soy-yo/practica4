package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;

public abstract class Event {

  protected final int time;
  protected final String id;

  public Event(int time, String id) {
    this.time = time;
    this.id = id;
  }

  public int getTime() {
    return time;
  }

  public String getId() {
    return id;
  }

  public abstract void execute();

  public interface Builder {

    Event parse(IniSection section);

    default boolean isValid(String id) {
      return id.matches("[a-zA-Z1-9_]++");
    }

    default int parseInt(IniSection section, String key, int defaultValue) {
      try {
        return Integer.parseInt(section.getValue(key));
      } catch (Exception e) {
        return defaultValue;
      }
    }

    default String[] parseIdList(IniSection section, String key) {
      String values = section.getValue(key);
      return values.split("[, ]+");
    }

  }

}
