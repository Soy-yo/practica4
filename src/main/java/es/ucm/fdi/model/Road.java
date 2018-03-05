package es.ucm.fdi.model;

import es.ucm.fdi.util.MultiTreeMap;

import java.util.Comparator;
import java.util.Map;

public class Road extends SimulatedObject {

  private static String SECTION_TAG_NAME = "road_report";

  private int length;
  private int maxSpeed;
  private MultiTreeMap<Integer, Vehicle> vehicleList;
  // variable usada para advance
  private boolean brokenDownVehicles;

  public Road(String id, int length, int maxSpeed) {
    super(id);
    this.length = length;
    this.maxSpeed = maxSpeed;
    vehicleList = new MultiTreeMap<>(Comparator.comparing(Integer::intValue).reversed());
  }

  public int getLength() {
    return length;
  }

  public void vehicleIn(Vehicle vehicle) {
    vehicleList.putValue(0, vehicle);
  }

  public void vehicleOut(Vehicle vehicle) {
    // TODO: mirar si es necesario pedir la localización o sólo se sale desde length-1
    int location = vehicle.getLocation();
    vehicleList.removeValue(location, vehicle);
  }

  @Override
  public void advance() {
    final int baseSpeed = (int) Math.min(maxSpeed, maxSpeed / Math.max(vehicleList.sizeOfValues(), 1) + 1);
    brokenDownVehicles = false;
    vehicleList.innerValues().forEach(v -> {
      int reductionFactor = brokenDownVehicles ? 2 : 1;
      brokenDownVehicles = brokenDownVehicles || v.getFaulty() > 0;
      v.setCurrentSpeed(baseSpeed / reductionFactor);
      v.advance();
    });
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    StringBuilder stringBuilder = new StringBuilder();
    vehicleList.innerValues().forEach(v -> stringBuilder.append("(" + v.id + "," + v.getLocation() + "),"));
    kvps.put("state", stringBuilder.substring(0, stringBuilder.length() - 1));
  }

  @Override
  protected String getReportHeader() {
    return SECTION_TAG_NAME;
  }

}
