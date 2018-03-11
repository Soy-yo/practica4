package es.ucm.fdi.model;

import java.util.*;

public class RoadMap {

  private Map<String, Vehicle> vehicles;
  private Map<String, Road> roads;
  private Map<String, Junction> junctions;

  public RoadMap() {
    reset();
  }

  public void reset() {
    vehicles = new LinkedHashMap<>();
    roads = new LinkedHashMap<>();
    junctions = new LinkedHashMap<>();
  }

  public boolean contains(String id) {
    return vehicles.containsKey(id) || roads.containsKey(id) || junctions.containsKey(id);
  }

  public void addSimulatedObject(SimulatedObject o) {
    if (o instanceof Vehicle) {
      addVehicle((Vehicle) o);
    } else if (o instanceof Road) {
      addRoad((Road) o);
    } else if (o instanceof Junction) {
      addJunction((Junction) o);
    }
  }

  public void addVehicle(Vehicle v) throws IllegalArgumentException {
    if (contains(v.getId())) {
      throw new IllegalArgumentException("This vehicle is already registered");
    }
    List<Junction> itinerary = v.getItinerary();
    for (Junction j : itinerary) {
      if (!junctions.containsKey(j.getId())) {
        throw new IllegalArgumentException("Junction " + j.getId() + " in vehicle's " + v.getId()
            + " itinerary does not exists in the road map");
      }
    }
    v.moveToNextRoad();
    vehicles.put(v.getId(), v);
  }

  public void addRoad(Road r) throws IllegalArgumentException {
    if (contains(r.getId())) {
      throw new IllegalArgumentException("This road is already registered");
    }
    Junction destination = junctionSearch(r.getDestiny());
    if (!junctions.containsKey(r.getSource())) {
      throw new IllegalArgumentException("Couldn't find source for road" + r.getId());
    }
    if (destination == null) {
      throw new IllegalArgumentException("Couldn't find destination for road" + r.getId());
    }
    destination.addRoad(r);
    roads.put(r.getId(), r);
  }

  public void addJunction(Junction j) throws IllegalArgumentException {
    if (contains(j.getId())) {
      throw new IllegalArgumentException("This junction is already registered");
    }
    junctions.put(j.getId(), j);
  }

  public SimulatedObject searchById(String id) {
    if (vehicles.containsKey(id)) {
      return vehicles.get(id);
    }
    if (roads.containsKey(id)) {
      return roads.get(id);
    }
    if (junctions.containsKey(id)) {
      return junctions.get(id);
    }
    return null;
  }

  public Vehicle vehicleSearch(String id) {
    return vehicles.get(id);
  }

  public Road roadSearch(String id) {
    return roads.get(id);
  }

  public Junction junctionSearch(String id) {
    return junctions.get(id);
  }

  public List<Vehicle> getVehicles() {
    return Collections.unmodifiableList(new ArrayList<>(vehicles.values()));
  }

  public List<Road> getRoads() {
    return Collections.unmodifiableList(new ArrayList<>(roads.values()));
  }

  public List<Junction> getJunctions() {
    return Collections.unmodifiableList(new ArrayList<>(junctions.values()));
  }

  protected Queue<Junction> getPath(String[] path) {
    Queue<Junction> result = new ArrayDeque<>();
    String previousJunctionId = null;
    for (String id : path) {
      Junction j = junctionSearch(id);
      if (j == null) {
        throw new IllegalArgumentException("Junction " + id + " does not exit in road map");
      }
      if (previousJunctionId != null && j.getStraightRoad(previousJunctionId) == null) {
        throw new IllegalArgumentException("No road connects " + previousJunctionId + " and " + id);
      }
      result.add(j);
      previousJunctionId = id;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Vehicles:\n");
    for (Vehicle v : vehicles.values()) {
      sb.append("\t" + v + "\n");
    }
    sb.append("\nRoads:\n");
    for (Road r : roads.values()) {
      sb.append("\t" + r + "\n");
    }
    sb.append("\nJunctions:\n");
    for (Junction j : junctions.values()) {
      sb.append("\t" + j + "\n");
    }
    return sb.toString();
  }

}
