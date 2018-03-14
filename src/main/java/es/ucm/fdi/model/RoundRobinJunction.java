package es.ucm.fdi.model;

import java.util.Map;

public class RoundRobinJunction extends JunctionWithTimeSlice {

  public static final String TYPE = "rr";

  private int maxTimeSlice;
  private int minTimeSlice;

  public RoundRobinJunction(String id, int maxTimeSlice, int minTimeSlice) {
    super(id, maxTimeSlice);
    this.minTimeSlice = minTimeSlice;
    this.maxTimeSlice = maxTimeSlice;
  }

  @Override
  protected void switchLights() {
    if (timeUnits == timeLapse - 1) {
      super.switchLights();
      if (timesUsed == 0) {
        timeLapse = Math.max(timeLapse - 1, minTimeSlice);
      } else if (timesUsed == timeUnits) {
        timeLapse = Math.min(timeLapse + 1, maxTimeSlice);
      }
      timeUnits = 0;
    } else if (currentRoadOn == null) {
      super.switchLights();
    } else {
      timeUnits++;
    }
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    super.fillReportDetails(kvps);
    kvps.put("type", TYPE);
  }

}
