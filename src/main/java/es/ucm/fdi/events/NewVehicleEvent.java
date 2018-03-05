package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;

public class NewVehicleEvent extends Event {

	public static final String SECTION_TAG_NAME = "new_vehicle";

	private int maxSpeed;
	private String[] itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, String[] itinerary) {
		super(time, id);
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	class NewVehicleEventBuilder implements Event.Builder {

		// TODO: revisar throws
		@Override
		public Event parse(IniSection section) throws IllegalArgumentException {
			if (!section.getTag().equals(SECTION_TAG_NAME)) {
				return null;
			}
			// TODO: try catch...
			int time = parseInt(section, "time", 0);
			String id = section.getValue("id");
			int maxSpeed = Integer.parseInt(section.getValue("max_speed"));
			String[] itinerary = parseIdList(section, "itinerary");
			if (itinerary.length < 2) {
				throw new IllegalArgumentException();
			}
			return new NewVehicleEvent(time, id, maxSpeed, itinerary);
		}

	}
}
