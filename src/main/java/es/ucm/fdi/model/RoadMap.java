package es.ucm.fdi.model;

import java.util.*;

public class RoadMap {

	private LinkedHashMap<String, Vehicle> vehicles;
	private LinkedHashMap<String, Road> roads;
	private LinkedHashMap<String, Junction> junctions;

	public void addVehicle(Vehicle v) throws IllegalArgumentException {
		/*
		 * if(vehicles.get(e.getId()).equals(null)) { String[] ids =
		 * e.getItinerary(); Junction[] junctionArray = new
		 * Junction[ids.length]; for(int i = 0; i < junctionArray.length; i++) {
		 * junctionArray[i] = new Junction(ids[i]); } Queue<Junction> queue =
		 * new LinkedList<>(Arrays.asList(junctionArray));
		 * vehicles.put(e.getId(), new Vehicle(e.getId(), e.getMaxSpeed(),
		 * queue)); } else { throw new IllegalArgumentException(); }
		 */

		if (vehicles.get(v.getId()).equals(null)) {
			vehicles.put(v.getId(), v);
		} else {
			throw new IllegalArgumentException(
					"This vehicle is already registered");
		}
	}

	public void addRoad(Road r) throws IllegalArgumentException {
		/*
		 * if(roads.get(e.getId()).equals(null)) { roads.put(e.getId(), new
		 * Road(e.getId(), e.getMaxSpeed(), e.getLength())); } else { throw new
		 * IllegalArgumentException(); }
		 */
		if (roads.get(r.getId()).equals(null)) {
			roads.put(r.getId(), r);
		} else {
			throw new IllegalArgumentException(
					"This road is already registered");
		}
	}

	public void addJunction(Junction j) throws IllegalArgumentException {
		/*
		 * if(junctions.get(e.getId()).equals(null)) { junctions.put(e.getId(),
		 * new Junction(e.getId())); } else { throw new
		 * IllegalArgumentException(); }
		 */
		if (junctions.get(j.getId()).equals(null)) {
			junctions.put(j.getId(), j);
		} else {
			throw new IllegalArgumentException(
					"This junction is already registered");
		}
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
		return new ArrayList<Vehicle>(vehicles.values());
	}

	public List<Road> getRoads() {
		return new ArrayList<Road>(roads.values());
	}

	public List<Junction> getJunctions() {
		return new ArrayList<Junction>(junctions.values());
	}
}
