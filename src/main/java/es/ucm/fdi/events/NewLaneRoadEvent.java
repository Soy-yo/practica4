package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.LaneRoad;
import es.ucm.fdi.model.TrafficSimulator;

public class NewLaneRoadEvent extends NewRoadEvent {

  private int lanes;

  NewLaneRoadEvent(int time, String id, String sourceId,
                   String destinationId, int maxSpeed, int length, int lanes) {
    super(time, id, sourceId, destinationId, maxSpeed, length);
    this.lanes = lanes;
  }

  @Override
  public void execute(TrafficSimulator simulator) {
    LaneRoad laneRoad = new LaneRoad(id, length, maxSpeed, sourceId,
        destinationId, lanes);
    simulator.addSimulatedObject(laneRoad);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)
          || !LaneRoad.TYPE.equals(section.getValue("type"))) {
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

      int lanes;
      try {
        lanes = getIntValue("lanes", section);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Road lanes must be a positive number");
      }

      return new NewLaneRoadEvent(time, id, src, dest, maxSpeed, length, lanes);
    }

  }

}
