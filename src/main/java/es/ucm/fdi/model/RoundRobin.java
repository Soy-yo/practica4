package es.ucm.fdi.model;

public class RoundRobin extends Junction {

  private int minTimeSlice;
  private int maxTimeSlice;
  private int timeLapse;
  private int timeUnits;

  public RoundRobin(String id, int minTimeSlice, int maxTimeSlice) {
    super(id);
    this.minTimeSlice = minTimeSlice;
    this.maxTimeSlice = maxTimeSlice;
    timeLapse = maxTimeSlice;
    timeUnits = 0;
  }

  @Override
  protected void switchLights() {
    if (timeUnits == timeLapse) {
      super.switchLights();
      // TODO: se ha usado el intervalo entero
      if (true) {
        timeLapse = Math.min(timeLapse + 1, maxTimeSlice);
        // TODO: el intervalo no se ha usado nada
      } else if (true) {
        timeLapse = Math.max(timeLapse - 1, minTimeSlice);
      }
      timeUnits = 0;
    }
  }

}
