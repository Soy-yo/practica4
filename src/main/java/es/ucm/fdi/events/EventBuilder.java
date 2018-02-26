package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;

public interface EventBuilder {

  // TODO: revisar throws
  Event parse(IniSection section) throws IllegalArgumentException;

  // TODO: comprobar corrección de id
  default boolean isValid(String id) {
    throw new UnsupportedOperationException("Not implemented yet");
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
