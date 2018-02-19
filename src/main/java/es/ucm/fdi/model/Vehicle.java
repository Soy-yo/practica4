package es.ucm.fdi.model;

import java.util.List;

public class Vehicle {

	private int maxSpeed;
	private int currentSpeed;
	private Road road;
	private int location;
	private List<Junction> itinerary;
	private int breakdownTime;
	private boolean hasArrived;

	public Vehicle() {

	}

	public void advance() {
		if (breakdownTime > 0) {
			breakdownTime--;
		} else {
			int newLocation = location + currentSpeed;
			location = (newLocation >= road.getLength()) ? road.getLength()
					: newLocation;
		}
	}

	public void moveToNextRoad() {
		if (road != null) {
			road.vehicleOut(this);
		}
		Road newRoad = hasArrived ? null : new Road();
		if (newRoad == null) {
			hasArrived = true;
		} else {
			road = newRoad;
			road.vehicleIn(this);
		}
	}

	public void setBreakdownTime(int breakdownTime) {
		this.breakdownTime += breakdownTime;
	}

	public int getLocation() {
		return location;
	}

	public void setCurrentSpeed(int currentSpeed) {
		this.currentSpeed = Math.min(currentSpeed, maxSpeed);
	}

	public String generateReport() {
		throw new UnsupportedOperationException();
	}
}
