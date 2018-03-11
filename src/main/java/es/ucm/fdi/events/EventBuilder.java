package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;

public class EventBuilder {

  private static final Event.Builder[] SUPPORTED_EVENTS = {
      new NewVehicleEvent.Builder(),
      new NewRoadEvent.Builder(),
      new NewJunctionEvent.Builder(),
      new MakeVehicleFaultyEvent.Builder()
  };

  public static Event parse(IniSection section) throws IllegalStateException {
    int i = 0;
    Event result = null;
    try {
      while (i < SUPPORTED_EVENTS.length && result == null) {
        result = SUPPORTED_EVENTS[i].parse(section);
        i++;
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Something went wrong while parsing " +
          section.getTag(), e);
    }
    return result;
  }

}
