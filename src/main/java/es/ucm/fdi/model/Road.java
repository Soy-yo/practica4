package es.ucm.fdi.model;

import es.ucm.fdi.util.MultiTreeMap;

import java.util.Comparator;
import java.util.Map;

public class Road extends SimulatedObject {

  private static String SECTION_TAG_NAME = "road_report";

  private int length;
  private int maxSpeed;
  private MultiTreeMap<Integer, Vehicle> vehicleList;
  private String sourceId;
  private String destinationId;


  public Road(String id, int length, int maxSpeed, String sourceId, String destinationId) {
    super(id);
    this.length = length;
    this.maxSpeed = maxSpeed;
    vehicleList = new MultiTreeMap<>(Comparator.comparing(Integer::intValue).reversed());
    this.sourceId = sourceId;
    this.destinationId = destinationId;
  }

  public int getLength() {
    return length;
  }
  
  public String getSource() {
		return sourceId;
	}
  
  public String getDestiny() {
	return destinationId;
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
    boolean brokenDownVehicles = false;
    MultiTreeMap<Integer, Vehicle> temp = new MultiTreeMap<>(Comparator.comparing(Integer::intValue).reversed());
    for (Vehicle v : vehicleList.innerValues()) {
      int reductionFactor = brokenDownVehicles ? 2 : 1;
      brokenDownVehicles = brokenDownVehicles || v.getFaulty() > 0;
      v.setCurrentSpeed(baseSpeed / reductionFactor);
      v.advance();
      temp.putValue(v.getLocation(), v);
    }
    vehicleList = temp;
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    if (vehicleList.sizeOfValues() > 0) {
      StringBuilder stringBuilder = new StringBuilder();
      for (Vehicle v : vehicleList.innerValues()) {
        stringBuilder.append("(" + v.id + "," + v.getLocation() + "),");
      }
      kvps.put("state", stringBuilder.substring(0, stringBuilder.length() - 1));
    }
  }

  @Override
  protected String getReportHeader() {
    return SECTION_TAG_NAME;
  }

}
