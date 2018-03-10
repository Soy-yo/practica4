package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.TrafficSimulator;

public class NewJunctionEvent extends Event {

  private static final String SECTION_TAG_NAME = "new_junction";

  NewJunctionEvent(int time, String id) {
    super(time, id);
  }

  @Override
  public void execute(TrafficSimulator simulator) {
    Junction junction = new Junction(id);
    simulator.addSimulatedObject(junction);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)) {
        return null;
      }

      int time = parseInt(section, "time", 0, x -> x >= 0);

      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id + " is not a valid id");
      }

      return new NewJunctionEvent(time, id);
    }

  }

}
