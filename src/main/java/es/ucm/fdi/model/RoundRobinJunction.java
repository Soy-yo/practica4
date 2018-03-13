package es.ucm.fdi.model;

import java.util.Map;

public class RoundRobinJunction extends JunctionWithTimeSlice {

  private int minTimeSlice;
  private int maxTimeSlice;
  private int timeLapse;
  private int timeUnits;

  public RoundRobinJunction(String id, int minTimeSlice, int maxTimeSlice) {
    super(id, maxTimeSlice);
    this.minTimeSlice = minTimeSlice;
    this.maxTimeSlice = maxTimeSlice;
  }

  @Override
  protected void switchLights() {
    if (timeUnits == timeLapse) {
      super.switchLights();
      if (timesUsed == timeUnits) {
        timeLapse = Math.min(timeLapse + 1, maxTimeSlice);
      } else if (timesUsed == 0) {
        timeLapse = Math.max(timeLapse - 1, minTimeSlice);
      }
      timeUnits = 0;
    }
    timeUnits++;
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    kvps.put("type", "rr");
    super.fillReportDetails(kvps);
  }


}
