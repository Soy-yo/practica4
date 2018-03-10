package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.TrafficSimulator;

public class NewRoadEvent extends Event {

  private static final String SECTION_TAG_NAME = "new_road";

  private String sourceId;
  private String destinationId;
  private int maxSpeed;
  private int length;

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
    Road road = new Road(id, length, maxSpeed);
    simulator.addSimulatedObject(road);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)) {
        return null;
      }

      int time = parseInt(section, "time", 0, x -> x >= 0);

      // TODO: NPE check
      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id + " is not a valid id");
      }

      String src = section.getValue("sec");
      String dest = section.getValue("dest");

      int maxSpeed;
      try {
        maxSpeed = Integer.parseInt(section.getValue("max_speed"));
        if (maxSpeed <= 0) {
          throw new IllegalArgumentException("Road max speed must be positive");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Road max speed must be a number", e);
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
