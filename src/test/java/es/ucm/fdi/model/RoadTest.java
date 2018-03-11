package es.ucm.fdi.model;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class RoadTest {

  @Test
  public void simpleMoving() {
    Junction source = new Junction("jt1");
    Junction dest = new Junction("jt2");
    Queue<Junction> queue = new ArrayDeque<>();
    queue.add(source);
    queue.add(dest);

    Road road = new Road("rt1", 100, 20, "jt1", "jt2");
    dest.addRoad(road);
    Vehicle vehicle1 = new Vehicle("vt1", 20, queue);
    Vehicle vehicle2 = new Vehicle("vt2", 20, new ArrayDeque<>(queue));

    vehicle1.moveToNextRoad();
    vehicle2.moveToNextRoad();

    road.advance();

    Map<String, String> correct = new HashMap<>();
    correct.put("", "road_report");
    correct.put("id", "rt1");
    correct.put("time", String.valueOf(1));
    correct.put("state", "(vt1,11),(vt2,11)");

    Map<String, String> result = road.generateReport(1);

    assertEquals(correct, result);
  }

  @Test
  public void FaultyVehicle() {
    Junction source = new Junction("jt1");
    Junction dest = new Junction("jt2");
    Queue<Junction> queue = new ArrayDeque<>();
    queue.add(source);
    queue.add(dest);

    Road road = new Road("rt1", 100, 20, "jt1", "jt2");
    dest.addRoad(road);

    Vehicle broken = new Vehicle("vt1", 20, queue);
    Vehicle v = new Vehicle("vt2", 15, new ArrayDeque<>(queue));
    broken.moveToNextRoad();
    road.advance();

    v.moveToNextRoad();
    broken.setFaulty(2);

    road.advance();
    road.advance();
    road.advance();

    Map<String, String> correct = new HashMap<>();
    correct.put("", "road_report");
    correct.put("id", "rt1");
    correct.put("time", String.valueOf(1));
    correct.put("state", "(vt1,31),(vt2,21)");

    Map<String, String> result = road.generateReport(1);

    assertEquals(correct, result);
  }

}
