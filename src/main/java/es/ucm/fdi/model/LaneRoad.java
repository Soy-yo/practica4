package es.ucm.fdi.model;

import java.util.Map;

public class LaneRoad extends Road {

  private int lanes;

  public LaneRoad(String id, int length, int maxSpeed, String sourceId, String destinationId,
                  int lanes) {
    super(id, length, maxSpeed, sourceId, destinationId);
    this.lanes = lanes;
  }

  @Override
  public void advance() {
    int baseSpeed = (int) Math.min(maxSpeed,
        maxSpeed * lanes / Math.max(vehicleList.sizeOfValues(), 1) + 1);
    advance(baseSpeed, x -> x >= lanes ? 2 : 1);
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    kvps.put("type", "lanes");
    super.fillReportDetails(kvps);
  }

}
