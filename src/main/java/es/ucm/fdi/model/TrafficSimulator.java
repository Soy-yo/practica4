package es.ucm.fdi.model;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.util.MultiTreeMap;

import java.io.OutputStream;

public class TrafficSimulator {

  private int currentTime;
  private MultiTreeMap<Integer, Event> events;
  private MultiTreeMap<String, SimulatedObject> simulatedObjects;

  public TrafficSimulator() {
    currentTime = 0;
    events = new MultiTreeMap<>();
  }

  public void addEvent(Event event) {
    // TODO: comprobar id único
    if (event.getTime() < currentTime) {
      throw new IllegalStateException("Event " + event.getId() + " is breaking the space-time " +
          "continuum");
    }
    events.putValue(event.getTime(), event);
  }

  public void addSimulatedObject(SimulatedObject simulatedObject) {
    // TODO: añadir a roadMap cuando esté
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void makeVehicleFaulty(String vehicleId, int duration) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void execute(int simulationSteps, OutputStream out) {
    int timelimit = currentTime + simulationSteps - 1;
    while (currentTime <= timelimit) {
      for (Event e : events.get(currentTime)) {
        e.execute(this);
      }
      for (SimulatedObject road : simulatedObjects.get("road")) {
        road.advance();
      }
      for (SimulatedObject junction : simulatedObjects.get("junction")) {
        junction.advance();
      }
      currentTime++;
      if (out != null) {
        for (SimulatedObject junction : simulatedObjects.get("junction")) {
          junction.generateReport(currentTime);
        }
        for (SimulatedObject road : simulatedObjects.get("road")) {
          road.generateReport(currentTime);
        }
        for (SimulatedObject vehicle : simulatedObjects.get("vehicle")) {
          vehicle.generateReport(currentTime);
        }
      }
    }
  }

}
