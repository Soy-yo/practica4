package es.ucm.fdi.model;

import java.util.Map;
import java.util.Queue;

public class Vehicle extends SimulatedObject {

  private static final String SECTION_TAG_NAME = "vehicle_report";

  private int maxSpeed;
  private int currentSpeed;
  private Road road;
  private int location;
  private Queue<Junction> itinerary;
  private int faulty;
  private boolean hasArrived;
  private int kilometrage;

  public Vehicle(String id, int maxSpeed, Queue<Junction> itinerary) {
    super(id);
    this.maxSpeed = maxSpeed;
    this.itinerary = itinerary;
  }

  public int getFaulty() {
    return faulty;
  }

  public void setFaulty(int faulty) {
    this.faulty += faulty;
  }

  @Override
  public void advance() {
    if (faulty > 0) {
      faulty--;
    } else {
      int newLocation = location + currentSpeed;
      if (newLocation >= road.getLength()) {
        newLocation = road.getLength();
        Junction nextJ = itinerary.poll();
        nextJ.vehicleIn(this);
      }
      kilometrage += newLocation - location;
      location = newLocation;
    }
  }

  public void moveToNextRoad() {
    if (road != null) {
      road.vehicleOut(this);
    }
    String actual = itinerary.poll().getId();
    Junction next = itinerary.peek();
    if (next == null) {
      hasArrived = true;
    } else {
      road = next.getStraightRoad(actual);
      road.vehicleIn(this);
    }
  }

  public int getLocation() {
    return location;
  }
  
  public Road getRoad() {
		return road;
	}
  
  public void setCurrentSpeed(int currentSpeed) {
    this.currentSpeed = Math.min(currentSpeed, maxSpeed);
  }

  @Override
  public void fillReportDetails(Map<String, String> kvps) {
    kvps.put("speed", String.valueOf(currentSpeed));
    kvps.put("kilometrage", String.valueOf(kilometrage));
    kvps.put("faulty", String.valueOf(faulty));
    kvps.put("location", "(" + road + "," + location + ")");
  }

  @Override
  protected String getReportHeader() {
    return SECTION_TAG_NAME;
  }

}
