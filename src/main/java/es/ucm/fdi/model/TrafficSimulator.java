package es.ucm.fdi.model;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.excepcions.SimulatorError;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.util.MultiTreeMap;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Queue;

public class TrafficSimulator {

  private int currentTime;
  private MultiTreeMap<Integer, Event> events;
  private RoadMap roadMap;

  public TrafficSimulator() {
    reset();
  }

  public void reset() {
    currentTime = 0;
    events = new MultiTreeMap<>();
    roadMap = new RoadMap();
  }

  public void addEvent(Event event) {
    if (event.getTime() < currentTime) {
      throw new IllegalStateException(
          "Event " + event.getId() + " is breaking the space-time continuum");
    }
    events.putValue(event.getTime(), event);
  }

  public void addSimulatedObject(SimulatedObject o) {
    roadMap.addSimulatedObject(o);
  }

  public Queue<Junction> getPath(String[] junctions) {
    return roadMap.getPath(junctions);
  }

  public void makeVehicleFaulty(String id, int time) throws IllegalArgumentException {
    Vehicle v = roadMap.vehicleSearch(id);
    if (v != null) {
      v.setFaulty(time);
    } else {
      throw new IllegalArgumentException("Vehicle not found");
    }
  }

  private void writeReport(Map<String, String> report, OutputStream out) throws SimulatorError {
    Ini ini = new Ini();
    IniSection sec = new IniSection(report.get(""));
    report.remove("");
    for (Map.Entry<String, String> entry : report.entrySet()) {
      sec.setValue(entry.getKey(), entry.getValue());
    }
    ini.addSection(sec);
    try {
      ini.store(out);
    } catch (IOException e) {
      throw new SimulatorError("Failed while storing data on ini file", e);
    }
  }

  public void execute(int simulationSteps, OutputStream out) throws SimulatorError {
    int timeLimit = currentTime + simulationSteps - 1;
    while (currentTime <= timeLimit) {
      if (events.containsKey(currentTime)) {
        for (Event e : events.get(currentTime)) {
          e.execute(this);
        }
      }
      for (Road r : roadMap.getRoads()) {
        r.advance();
      }
      for (Junction j : roadMap.getJunctions()) {
        j.advance();
      }
      currentTime++;
      if (out != null) {
        for (Junction j : roadMap.getJunctions()) {
          try {
            writeReport(j.generateReport(currentTime), out);
          } catch (SimulatorError e) {
            throw new SimulatorError("Something went wrong while writing " + j + "'s report", e);
          }
        }
        for (Road r : roadMap.getRoads()) {
          try {
            writeReport(r.generateReport(currentTime), out);
          } catch (SimulatorError e) {
            throw new SimulatorError("Something went wrong while writing " + r + "'s report", e);
          }
        }
        for (Vehicle v : roadMap.getVehicles()) {
          try {
            writeReport(v.generateReport(currentTime), out);
          } catch (SimulatorError e) {
            throw new SimulatorError("Something went wrong while writing " + v + "'s report", e);
          }
        }
      }
    }
  }

}
