package es.ucm.fdi.model;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.util.MultiTreeMap;

import java.io.OutputStream;

public class TrafficSimulator {

	private int currentTime;
	private MultiTreeMap<Integer, Event> events;
	private RoadMap roadMap;

	public TrafficSimulator() {
		currentTime = 0;
		events = new MultiTreeMap<>();
	}

	public void addEvent(Event event) {
		// TODO: comprobar id único (se comprueba al añadirlos) Edu 09/03
		if (event.getTime() < currentTime) {
			throw new IllegalStateException(
					"Event " + event.getId() + " is breaking the space-time continuum");
		}
		events.putValue(event.getTime(), event);
	}

	public void addSimulatedObject(SimulatedObject o) {
		switch (o.getClass().getName()) {
		case "Vehicle":
			Vehicle v = (Vehicle) o;
			roadMap.addVehicle(v);
			break;
		case "Road":
			Road r = (Road) o;
			roadMap.addRoad(r);
			break;
		case "Junction":
			Junction j = (Junction) o;
			roadMap.addJunction(j);
			break;
		}
	}

	public void makeVehicleFaulty(String id, int time)
			throws IllegalArgumentException {
		Vehicle v = roadMap.vehicleSearch(id);
		if (v != null) {
			v.setFaulty(time);
		} else {
			throw new IllegalArgumentException("Vehicle not found");
		}
	}

	public void execute(int simulationSteps, OutputStream out) {
		int timelimit = currentTime + simulationSteps - 1;
		while (currentTime <= timelimit) {
			for (Event e : events.get(currentTime)) {
				e.execute();
			}
			for (SimulatedObject road : roadMap.getRoads()) {
				road.advance();
			}
			for (SimulatedObject junction : roadMap.getJunctions()) {
				junction.advance();
			}
			currentTime++;
			if (out != null) {
				for (SimulatedObject junction : roadMap.getJunctions()) {
					junction.generateReport(currentTime);
				}
				for (SimulatedObject road : roadMap.getRoads()) {
					road.generateReport(currentTime);
				}
				for (SimulatedObject vehicle : roadMap.getVehicles()) {
					vehicle.generateReport(currentTime);
				}
			}
		}
	}

}
