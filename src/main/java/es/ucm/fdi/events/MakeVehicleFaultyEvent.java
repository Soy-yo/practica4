package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;

public class MakeVehicleFaultyEvent extends Event {

	private static final String SECTION_TAG_NAME = "make_vehicle_faulty";

	private String[] vehicles;
	private int duration;

	// TODO: no hay ID
	public MakeVehicleFaultyEvent(int time, String id, String[] vehicles,
			int duration) {
		super(time, id);
		this.vehicles = vehicles;
		this.duration = duration;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}

  class MakeVehicleFaultyEventBuilder implements Event.Builder {

		@Override
		public Event parse(IniSection section) {
			if (!section.getTag().equals(SECTION_TAG_NAME)) {
				return null;
			}
			// TODO: try catch...
			// TODO: no hay ID
			int time = parseInt(section, "time", 0);
			String id = section.getValue("id");
			String[] vehicles = parseIdList(section, "vehicles");
			int duration = Integer.parseInt(section.getValue("duration"));
			return new MakeVehicleFaultyEvent(time, id, vehicles, duration);
		}

	}

}
