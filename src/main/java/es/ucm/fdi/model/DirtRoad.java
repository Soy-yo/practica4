package es.ucm.fdi.model;

import java.util.Map;

public class DirtRoad extends Road {

  public DirtRoad(String id, int length, int maxSpeed, String sourceId, String destinationId) {
    super(id, length, maxSpeed, sourceId, destinationId);
  }

  @Override
  public void advance() {
    advance(maxSpeed, x -> x + 1);
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    kvps.put("type", "dirt");
    super.fillReportDetails(kvps);
  }

}
