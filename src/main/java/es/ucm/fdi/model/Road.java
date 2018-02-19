package es.ucm.fdi.model;

import es.ucm.fdi.util.MultiTreeMap;

public class Road {

	private int length;
	private int maxSpeed;
	private MultiTreeMap<Integer, Vehicle> vehicleList;
	
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
	
	public void advance() {
		vehicleList.innerValues().forEach(v -> {
			int baseSpeed = (int)Math.min(
					maxSpeed,
					maxSpeed / Math.max(vehicleList.sizeOfValues(), 1) + 1);
			// TODO: calcular factor de reducción (obstáculos ? 1 : 2)
			int reductionFactor = 1;
			v.setCurrentSpeed(baseSpeed / reductionFactor);
			v.advance();
		});
	}
	
	public String generateReport() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
