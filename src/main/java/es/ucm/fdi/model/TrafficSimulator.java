package es.ucm.fdi.model;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.util.MultiTreeMap;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

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
    // TODO: instanceof mejor?
		switch (o.getClass().getName()) {
      case "Vehicle":
        roadMap.addVehicle((Vehicle) o);
        break;
      case "Road":
        roadMap.addRoad((Road) o);
        break;
      case "Junction":
        roadMap.addJunction((Junction) o);
        break;
      default:
		}
	}

  public void makeVehicleFaulty(String id, int time) throws IllegalArgumentException {
		Vehicle v = roadMap.vehicleSearch(id);
		if (v != null) {
			v.setFaulty(time);
		} else {
			throw new IllegalArgumentException("Vehicle not found");
		}
	}
  
  private void writeReport(Map<String, String> report, OutputStream out) {
	  	Ini ini = new Ini();
	    IniSection sec;

	    sec = new IniSection(report.get(""));
	    report.remove("");
	    for (Map.Entry<String, String> entry : report.entrySet())
	    {
	        sec.setValue(entry.getKey(), entry.getValue());
	    }
	    ini.addSection(sec);
	    try {
			ini.store(out);
		} catch (IOException e) {
			// FIXME: no se que hacer aqui
		}
  }

	public void execute(int simulationSteps, OutputStream out) {
    int timeLimit = currentTime + simulationSteps - 1;
    while (currentTime <= timeLimit) {
			for (Event e : events.get(currentTime)) {
        e.execute(this);
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
          writeReport(j.generateReport(currentTime), out);
				}
        for (Road r : roadMap.getRoads()) {
          writeReport(r.generateReport(currentTime), out);
				}
        for (Vehicle v : roadMap.getVehicles()) {
          writeReport(v.generateReport(currentTime), out);
				}
			}
		}
	}
	
}
