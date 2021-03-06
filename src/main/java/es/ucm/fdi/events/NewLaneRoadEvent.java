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
    simulator.addSimulatedObject(new LaneRoad(id, length, maxSpeed, sourceId, destinationId,
        lanes));
  }

  static class Builder extends NewRoadEvent.Builder {

    @Override
    public boolean matchesType(IniSection section) {
      return LaneRoad.TYPE.equals(section.getValue("type"));
    }

    @Override
    public NewRoadEvent parseType(IniSection section, int time, String id, String src,
                                  String dest, int maxSpeed, int length) {
      int lanes = parsePositiveInt(section, "lanes");
      return new NewLaneRoadEvent(time, id, src, dest, maxSpeed, length, lanes);
    }

  }

}
