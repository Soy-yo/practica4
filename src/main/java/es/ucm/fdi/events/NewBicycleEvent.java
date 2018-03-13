package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Bicycle;
import es.ucm.fdi.model.TrafficSimulator;

public class NewBicycleEvent extends NewVehicleEvent {

	NewBicycleEvent(int time, String id, int maxSpeed, String[] itinerary) {
		super(time, id, maxSpeed, itinerary);
	}

	@Override
	public void execute(TrafficSimulator simulator) {
		Bicycle bicycle = new Bicycle(id, maxSpeed, simulator.getPath(itinerary));
		simulator.addSimulatedObject(bicycle);
	}

	static class Builder implements Event.Builder {

		@Override
		public Event parse(IniSection section) {

			if (!section.getTag().equals(SECTION_TAG_NAME)
					|| !Bicycle.TYPE.equals(section.getValue("type"))) {
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
			
			return new NewBicycleEvent(time, id, maxSpeed, itinerary);
		}

	}

}
