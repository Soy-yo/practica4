package es.ucm.fdi.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

class VehicleTest {

  @Test
  void simpleAdvance() {
    Junction source = new Junction("jt1");
    Junction dest = new Junction("jt2");
    Queue<Junction> queue = new ArrayDeque<>();
    queue.add(source);
    queue.add(dest);

    Road r = new Road("rt1", 100, 100, "jt1", "jt2");
    dest.addRoad(r);

    Vehicle vehicle1 = new Vehicle("vt1", 50, queue);
    Vehicle vehicle2 = new Vehicle("vt2", 5, new ArrayDeque<>(queue));
    vehicle1.moveToNextRoad();
    vehicle2.moveToNextRoad();
    vehicle1.setCurrentSpeed(20);
    vehicle2.setCurrentSpeed(20);
    vehicle1.advance();
    vehicle2.advance();

    assertEquals(20, vehicle1.getLocation());
    assertEquals(5, vehicle2.getLocation());
  }

  @Test
  void endOfRoad() {
    Junction source = new Junction("jt1");
    Junction dest = new Junction("jt2");
    Queue<Junction> queue = new ArrayDeque<>();
    queue.add(source);
    queue.add(dest);

    Road r = new Road("rt1", 100, 100, "jt1", "jt2");
    dest.addRoad(r);

    Vehicle vehicle1 = new Vehicle("vt1", 50, queue);
    Vehicle vehicle2 = new Vehicle("vt2", 100, new ArrayDeque<>(queue));
    vehicle1.moveToNextRoad();
    vehicle2.moveToNextRoad();
    vehicle1.setCurrentSpeed(50);
    vehicle2.setCurrentSpeed(100);
    vehicle1.advance();
    vehicle2.advance();

    assertEquals(50, vehicle1.getLocation());
    assertEquals(100, vehicle2.getLocation());

    Map<String, String> correct = new HashMap<>();
    correct.put("", "junction_report");
    correct.put("id", "jt2");
    correct.put("time", String.valueOf(1));
    correct.put("queues", "(rt1,red,[vt2])");

    Map<String, String> result = dest.generateReport(1);

    assertEquals(correct, result);
  }

  @Test
  void brokenDown() {
    Junction source = new Junction("jt1");
    Junction dest = new Junction("jt2");
    Queue<Junction> queue = new ArrayDeque<>();
    queue.add(source);
    queue.add(dest);

    Road r = new Road("rt1", 50, 50, "jt1", "jt2");
    dest.addRoad(r);

    Vehicle vehicle = new Vehicle("vt1", 20, queue);
    vehicle.moveToNextRoad();
    vehicle.setCurrentSpeed(20);
    vehicle.setFaulty(2);
    vehicle.advance();
    vehicle.advance();
    assertEquals(0, vehicle.getLocation());
    vehicle.advance();
    assertEquals(20, vehicle.getLocation());
  }

}