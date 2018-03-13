package es.ucm.fdi.model;

import java.util.Map;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

  public MostCrowdedJunction(String id) {
    super(id, 0);
  }

  @Override
  protected void switchLights() {
    if (timeUnits == timeLapse) {
      currentRoadOn.switchLight();
      findNextRoad();
      currentRoadOn.switchLight();
      timeLapse = Math.max(currentRoadOn.vehicleCount() / 2, 1);
      timeUnits = 0;
    }
    timeUnits++;
  }

  private void findNextRoad() {
    IncomingRoad road = (IncomingRoad) getNextRoad();
    IncomingRoad mostCrowded = road;
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
    kvps.put("type", "mc");
    super.fillReportDetails(kvps);
  }

}
