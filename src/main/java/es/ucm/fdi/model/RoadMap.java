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

	public void addVehicle(Vehicle v) throws IllegalArgumentException {
    if (!contains(v.getId())) {
			vehicles.put(v.getId(), v);
		} else {
      throw new IllegalArgumentException("This vehicle is already registered");
		}
	}

	public void addRoad(Road r) throws IllegalArgumentException {
    if (!contains(r.getId())) {
			roads.put(r.getId(), r);
		} else {
      throw new IllegalArgumentException("This road is already registered");
		}
	}

	public void addJunction(Junction j) throws IllegalArgumentException {
    if (!contains(j.getId())) {
			junctions.put(j.getId(), j);
		} else {
      throw new IllegalArgumentException("This junction is already registered");
		}
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

	public Road RoadSearch(String id) {
		return roads.get(id);
	}

	public Junction JunctionSearch(String id) {
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

}
