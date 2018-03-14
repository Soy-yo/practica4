package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.TrafficSimulator;

public class NewRoadEvent extends Event {

  protected static final String SECTION_TAG_NAME = "new_road";

  protected String sourceId;
  protected String destinationId;
  protected int maxSpeed;
  protected int length;

  NewRoadEvent(int time, String id, String sourceId, String destinationId, int maxSpeed,
               int length) {
    super(time, id);
    this.sourceId = sourceId;
    this.destinationId = destinationId;
    this.maxSpeed = maxSpeed;
    this.length = length;
  }

  @Override
  public void execute(TrafficSimulator simulator) {
    Road road = new Road(id, length, maxSpeed, sourceId, destinationId);
    simulator.addSimulatedObject(road);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)) {
        return null;
      }

      int time = parsePositiveInt(section, "time", 0);

      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id + " is not a valid id");
      }

      //TODO: trycatch(?) (mirar tambien en las subclases) Edu
      String src = section.getValue("src");
      String dest = section.getValue("dest");

      int maxSpeed;
      try {
        maxSpeed = getIntValue("max_speed", section);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Road max speed must be a positive number");
      }

      int length;
      try {
        length = Integer.parseInt(section.getValue("length"));
        if (length <= 0) {
          throw new IllegalArgumentException("Road length must be positive");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Road length must be a number", e);
      }

      return new NewRoadEvent(time, id, src, dest, maxSpeed, length);
    }

  }

}
