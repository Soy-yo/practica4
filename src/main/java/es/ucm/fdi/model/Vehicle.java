package es.ucm.fdi.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;

public class Vehicle {

	private String id;
	private int maxSpeed;
	private int currentSpeed;
	private Road road;
	private int location;
	private List<Junction> itinerary;
	private int breakdownTime;
	private boolean hasArrived;
	private int kilometrage;

	public Vehicle(String id, int maxSpeed, Road road) {
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.road = road;
	}

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

		Ini ini = new Ini();
		IniSection sec;

		sec = new IniSection("vehicle_report");
		sec.setValue("id", id);
		sec.setValue("time", 0);
		sec.setValue("speed", currentSpeed);
		sec.setValue("kilometrage", kilometrage);
		sec.setValue("faulty", breakdownTime);
		String loc = (location == road.getLength()) ? "arrived" : "(" + road.id
				+ "," + location + ")";
		sec.setValue("location", loc);

		ini.addsection(sec);

		return ini.toString();
	}
	
}
