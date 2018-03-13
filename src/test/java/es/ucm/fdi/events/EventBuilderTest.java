package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventBuilderTest {

  private class TestSimulator extends TrafficSimulator {

    RoadMap roadMapTest = new RoadMap();

    @Override
    public void addSimulatedObject(SimulatedObject o) {
      roadMapTest.addSimulatedObject(o);
    }

    @Override
    public Queue<Junction> getPath(String[] path) {
      return roadMapTest.getPath(path);
    }

    List<Vehicle> getVehicles() {
      return roadMapTest.getVehicles();
    }

    List<Road> getRoads() {
      return roadMapTest.getRoads();
    }

    List<Junction> getJunctions() {
      return roadMapTest.getJunctions();
    }

  }

  @Test
  void simpleVehicleEvent() {
    TestSimulator simulator = new TestSimulator();
    Junction j1 = new Junction("jt1");
    Junction j2 = new Junction("jt2");
    Road r = new Road("rt1", 10, 10, "jt1", "jt2");
    simulator.addSimulatedObject(j1);
    simulator.addSimulatedObject(j2);
    simulator.addSimulatedObject(r);
    List<Junction> junctions = new ArrayList<>();
    junctions.add(j1);
    junctions.add(j2);

    IniSection section = new IniSection("new_vehicle");
    section.setValue("time", 0);
    section.setValue("id", "vt1");
    section.setValue("max_speed", 10);
    section.setValue("itinerary", "jt1,jt2");

    Event event = EventBuilder.parse(section);

    assertNotNull(event);
    event.execute(simulator);

    assertEquals(simulator.getJunctions(), junctions);
    assertEquals(simulator.getVehicles().get(0).getId(), "vt1");
  }

}