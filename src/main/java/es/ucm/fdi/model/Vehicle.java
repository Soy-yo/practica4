package es.ucm.fdi.model;

import java.util.List;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;

public class Vehicle extends SimulatedObject {

    private static final String SECTION_TAG_NAME = "vehicle_report";

	private int maxSpeed;
	private int currentSpeed;
	private Road road;
	private int location;
	private List<Junction> itinerary;
	private int breakdownTime;
	private boolean hasArrived;
	private int kilometrage;

	public Vehicle(String id, int maxSpeed, Road road) {
		super(id);
		this.maxSpeed = maxSpeed;
		this.road = road;
	}

	public int getBreakdownTime() {
		return breakdownTime;
	}

	@Override
	public void advance() {
		if (breakdownTime > 0) {
			breakdownTime--;
		} else {
			int newLocation = location + currentSpeed;
			location = (newLocation >= road.getLength()) ? road.getLength()
					: newLocation;
			kilometrage += currentSpeed;
		}
	}

	public void moveToNextRoad() {
		if (road != null) {
			road.vehicleOut(this);
		}
        // TODO: probablemente no sea new Road(); esto está para que compile
		Road newRoad = hasArrived ? null : new Road("id", 0, 0);
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

	@Override
	public String generateReport(int time) {

		Ini ini = new Ini();
		IniSection sec = new IniSection(SECTION_TAG_NAME);

		sec.setValue("id", id);
		sec.setValue("time", time);
		sec.setValue("speed", currentSpeed);
		sec.setValue("kilometrage", kilometrage);
		sec.setValue("faulty", breakdownTime);
		String loc = (location == road.getLength()) ? "arrived" : "(" + road.id
				+ "," + location + ")";
		sec.setValue("location", loc);

		ini.addSection(sec);

		return ini.toString();
	}
	
}
