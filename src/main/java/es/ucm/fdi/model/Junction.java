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
    if (!enteringRoads.isEmpty()) {
      if (currentRoadOn != null) {
        Queue<Vehicle> vehicleList = enteringRoads.get(currentRoadOn);
        if (!vehicleList.isEmpty()) {
          Vehicle vehicle = vehicleList.poll();
          vehicle.moveToNextRoad();
        }
      }
      switchLights();
    }
  }

  private void switchLights() {
    // pone en verde el primer semáforo la primera vez o cada vuelta
    if (nextRoad == null || !nextRoad.hasNext()) {
      nextRoad = enteringRoads.keySet().iterator();
    }
    currentRoadOn = nextRoad.next();
  }

  public Road getStraightRoad(String previousJunction) {
    for (Road r : enteringRoads.keySet()) {
      if (r.getSource().equals(previousJunction)) {
        return r;
      }
    }
    return null;
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    if (!enteringRoads.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder();
      for (Map.Entry<Road, Queue<Vehicle>> e : enteringRoads.entrySet()) {
        stringBuilder.append("(" + e.getKey() + "," + lightColor(e.getKey()) + ",[");
        for (Vehicle v : e.getValue()) {
          stringBuilder.append(v + ",");
        }
        if (!e.getValue().isEmpty()) {
          stringBuilder.deleteCharAt(stringBuilder.length() - 1); // coma
        }
        stringBuilder.append("]),");
      }
      kvps.put("queues", stringBuilder.substring(0, stringBuilder.length() - 1));
    } else {
      kvps.put("queues", "");
    }
  }

  @Override
  protected String getReportHeader() {
    return SECTION_TAG_NAME;
  }

  private String lightColor(Road road) {
    return road.equals(currentRoadOn) ? "green" : "red";
  }

}
