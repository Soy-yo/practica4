package es.ucm.fdi.model;

import java.util.*;

public class Junction extends SimulatedObject {

  private static String SECTION_TAG_NAME = "junction_report";

  private Map<Road, Queue<Vehicle>> enteringRoads;
  private Road currentRoadOn;
  private Iterator<Road> nextRoad;

  public Junction(String id) {
    super(id);
    enteringRoads = new LinkedHashMap<>();
  }

  public void addRoad(Road road) {
    enteringRoads.put(road, new ArrayDeque<>());
  }

  public void vehicleIn(Vehicle vehicle) {
    enteringRoads.get(vehicle.getRoad()).add(vehicle);
  }

  @Override
  public void advance() {
    // pone en verde el primer semáforo la primera vez o cada vuelta
    if (nextRoad == null || !nextRoad.hasNext()) {
      nextRoad = enteringRoads.keySet().iterator();
    }
    currentRoadOn = nextRoad.next();
    Queue<Vehicle> vehicleList = enteringRoads.get(currentRoadOn);
    if (!vehicleList.isEmpty()) {
      Vehicle vehicle = vehicleList.peek();
      vehicle.moveToNextRoad();
      vehicleList.poll();
    }
  }
  
  public Road getStraightRoad(String previousJunction) {
	  for(Road r : enteringRoads.keySet()) {
		 if(r.getSource().equals(previousJunction)) {
			 return r;
		 }
	  }
	  return null;
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<Road, Queue<Vehicle>> e : enteringRoads.entrySet()) {
      stringBuilder.append("(" + e.getKey().id + "," + lightColor(e.getKey()) + ",[");
      for (Vehicle v : e.getValue()) {
        stringBuilder.append(v.id + ",");
      }
      if (!e.getValue().isEmpty()) {
        stringBuilder.deleteCharAt(stringBuilder.length() - 1); // coma
      }
      stringBuilder.append("]),");
    }
    kvps.put("queues", stringBuilder.substring(0, stringBuilder.length() - 1));
  }
  
  @Override
  protected String getReportHeader() {
    return SECTION_TAG_NAME;
  }

  private String lightColor(Road road) {
    return road.equals(currentRoadOn) ? "green" : "red";
  }

}
