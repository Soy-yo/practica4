package es.ucm.fdi.model;

import java.util.Map;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

  public static final String TYPE = "mc";

  public MostCrowdedJunction(String id) {
    super(id, 1);
  }

  @Override
  protected void switchLights() {
    if (timeUnits == timeLapse - 1) {
      if (currentRoadOn != null) {
        currentRoadOn.switchLight();
      }
      findNextRoad();
      currentRoadOn.switchLight();
      timeLapse = Math.max(currentRoadOn.vehicleCount() / 2, 1);
      timeUnits = 0;
    } else {
      timeUnits++;
    }
  }

  private void findNextRoad() {
    IncomingRoad road = (IncomingRoad) getNextRoad();
    IncomingRoad mostCrowded = road;
    if (currentRoadOn == null) {
      currentRoadOn = road;
    }
    while (road != currentRoadOn) {
      if (road.vehicleCount() > mostCrowded.vehicleCount()) {
        mostCrowded = road;
      }
      road = (IncomingRoad) getNextRoad();
    }
    currentRoadOn = mostCrowded;
    // apuntar a la siguiente carretera
    while (road != currentRoadOn) {
      road = (IncomingRoad) getNextRoad();
    }
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    super.fillReportDetails(kvps);
    kvps.put("type", TYPE);
  }

}
