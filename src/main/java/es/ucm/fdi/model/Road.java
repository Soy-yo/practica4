package es.ucm.fdi.model;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.util.MultiTreeMap;

import java.util.Comparator;

public class Road extends SimulatedObject {

    private static String SECTION_TAG_NAME = "road_report";

	private int length;
	private int maxSpeed;
	private MultiTreeMap<Integer, Vehicle> vehicleList;
	// variable usada para advance
    private boolean brokenDownVehicles;

	public Road(String id, int length, int maxSpeed) {
	    super(id);
	    this.length = length;
	    this.maxSpeed = maxSpeed;
	    vehicleList = new MultiTreeMap<>(Comparator.comparing(Integer::intValue).reversed());
    }
	
	public int getLength() {
		return length;
	}
	
	public void vehicleIn(Vehicle vehicle) {
		vehicleList.putValue(0, vehicle);
	}
	
	public void vehicleOut(Vehicle vehicle) {
		// TODO: mirar si es necesario pedir la localización o sólo se sale desde length-1
		int location = vehicle.getLocation();
		vehicleList.removeValue(location, vehicle);
	}

	@Override
	public void advance() {
        final int baseSpeed = (int)Math.min(maxSpeed, maxSpeed / Math.max(vehicleList.sizeOfValues(), 1) + 1);
	    brokenDownVehicles = false;
		vehicleList.innerValues().forEach(v -> {
            int reductionFactor = brokenDownVehicles ? 2 : 1;
            brokenDownVehicles = brokenDownVehicles || v.getBreakdownTime() > 0;
            v.setCurrentSpeed(baseSpeed / reductionFactor);
            v.advance();
		});
	}

	@Override
    public String generateReport(int time) {

		Ini ini = new Ini();
		IniSection sec = new IniSection(SECTION_TAG_NAME);

		sec.setValue("id", id);
		sec.setValue("time", time);
        if (!vehicleList.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            vehicleList.innerValues().forEach(v -> stringBuffer.append("(" + v.id + "," + v.getLocation() + "),"));
            sec.setValue("state", stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString());
        } else {
            sec.setValue("state", "");
        }

		ini.addSection(sec);

		return ini.toString();
    }

}
