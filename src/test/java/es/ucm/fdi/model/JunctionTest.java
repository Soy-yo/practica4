package es.ucm.fdi.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

class JunctionTest {

  @Test
  void simpleAdvance() {
    Junction source = new Junction("jt1");
    Junction mid = new Junction("jt2");
    Junction dest = new Junction("jt3");
    Queue<Junction> queue = new ArrayDeque<>();
    queue.add(source);
    queue.add(mid);
    queue.add(dest);

    Vehicle vehicle = new Vehicle("vt1", 20, queue);
    Road road1 = new Road("rt1", 20, 20, "jt1", "jt2");
    Road road2 = new Road("rt2", 20, 20, "jt2", "jt3");
    mid.addRoad(road1);
    dest.addRoad(road2);
    vehicle.moveToNextRoad();
    road1.advance();

    Map<String, String> correct = new LinkedHashMap<>();
    correct.put("", "junction_report");
    correct.put("id", "jt2");
    correct.put("time", String.valueOf(1));
    correct.put("queues", "(rt1,red,[vt1])");

    Map<String, String> result = mid.generateReport(1);

    assertEquals(correct, result);

    mid.advance(); // red -> green
    mid.advance();

    correct = new LinkedHashMap<>();
    correct.put("", "junction_report");
    correct.put("id", "jt2");
    correct.put("time", String.valueOf(1));
    correct.put("queues", "(rt1,green,[])");

    result = mid.generateReport(1);

    assertEquals(correct, result);
  }

  @Test
  void reportTest() {
    Junction source = new Junction("jt1");
    Junction dest = new Junction("jt2");
    Queue<Junction> queue = new ArrayDeque<>();
    queue.add(source);
    queue.add(dest);

    Road road1 = new Road("rt1", 100, 50, "jt1", "jt2");
    Road road2 = new Road("rt2", 100, 50, "", "");

    dest.addRoad(road1);
    dest.addRoad(road2);

    Vehicle vehicle1 = new Vehicle("vt1", 20, queue);
    Vehicle vehicle2 = new Vehicle("vt2", 20, new ArrayDeque<>(queue));
    vehicle1.moveToNextRoad();
    vehicle2.moveToNextRoad();

    dest.vehicleIn(vehicle1);
    dest.vehicleIn(vehicle2);

    Map<String, String> correct = new LinkedHashMap<>();
    correct.put("", "junction_report");
    correct.put("id", "jt2");
    correct.put("time", String.valueOf(1));
    correct.put("queues", "(rt1,red,[vt1,vt2]),(rt2,red,[])");

    Map<String, String> result = dest.generateReport(1);

    assertEquals(correct, result);
  }

}