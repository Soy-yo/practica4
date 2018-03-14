package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.DirtRoad;
import es.ucm.fdi.model.TrafficSimulator;

public class NewDirtRoadEvent extends NewRoadEvent {

  NewDirtRoadEvent(int time, String id, String sourceId,
                   String destinationId, int maxSpeed, int length) {
    super(time, id, sourceId, destinationId, maxSpeed, length);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(TrafficSimulator simulator) {
    DirtRoad dirtRoad = new DirtRoad(id, length, maxSpeed, sourceId,
        destinationId);
    simulator.addSimulatedObject(dirtRoad);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)
          || !DirtRoad.TYPE.equals(section.getValue("type"))) {
        return null;
      }

      int time = parsePositiveInt(section, "time", 0);

      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id
            + " is not a valid id");
      }

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
        length = getIntValue("length", section);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Road length must be a positive number");
      }

      return new NewDirtRoadEvent(time, id, src, dest, maxSpeed, length);
    }

  }

}
