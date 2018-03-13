package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Car;
import es.ucm.fdi.model.TrafficSimulator;

public class NewCarEvent extends NewVehicleEvent {

	private int resistance;
	private double faultProbability;
	private int maxFaultDuration;
	private long seed;

	NewCarEvent(int time, String id, int maxSpeed, String[] itinerary,
			int resistance, double faultProbability, int maxFaultDuration,
			long seed) {
		super(time, id, maxSpeed, itinerary);
		this.resistance = resistance;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		this.seed = seed;
	}

	@Override
	public void execute(TrafficSimulator simulator) {
		Car car = new Car(id, maxSpeed, simulator.getPath(itinerary),
				resistance, faultProbability, maxFaultDuration, seed);
		simulator.addSimulatedObject(car);
	}

	static class Builder implements Event.Builder {

		@Override
		public Event parse(IniSection section) {

			if (!section.getTag().equals(SECTION_TAG_NAME)
					|| !Car.TYPE.equals(section.getValue("type"))) {
				return null;
			}

			int time = parseInt(section, "time", 0, x -> x >= 0);

			String id = section.getValue("id");
			if (!isValid(id)) {
				throw new IllegalArgumentException("id " + id
						+ " is not a valid id");
			}

			int maxSpeed;
			try {
				maxSpeed = getIntValue("max_speed", section);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(
						"Vehicle max speed must be a positive number");
			}

			String[] itinerary = parseIdList(section, "itinerary");
			if (itinerary == null || itinerary.length < 2) {
				throw new IllegalArgumentException(
						"Vehicle itinerary list must contain at least "
								+ "2 junctions");
			}

			int resistance;
			try {
				resistance = getIntValue("resistance", section);
			} catch (IllegalArgumentException e) {
				throw e;
			}

			double faultProbability;
			try {
				faultProbability = Double.parseDouble(section
						.getValue("fault_probability"));
				if (faultProbability < 0) {
					throw new IllegalArgumentException(
							"Fault Probability can't be negative");
				}
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Fault Probability must be a number");
			}

			int maxFaultDuration;
			try {
				maxFaultDuration = getIntValue("max_fault_duration", section);
			} catch (IllegalArgumentException e) {
				throw e;
			}

			long seed;
			try {
				seed = Integer.parseInt(section.getValue("seed"));
				if (seed <= 0) {
					throw new IllegalArgumentException("Seed must be positive");
				}
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Seed must be a number", e);
			}
			return new NewCarEvent(time, id, maxSpeed, itinerary, resistance,
					faultProbability, maxFaultDuration, seed);
		}

	}
}
