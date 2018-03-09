package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.TrafficSimulator;

public class MakeVehicleFaultyEvent extends Event {

	private static final String SECTION_TAG_NAME = "make_vehicle_faulty";

	private String[] vehicles;
	private int duration;

	// TODO: no hay ID
  MakeVehicleFaultyEvent(int time, String id, String[] vehicles, int duration) {
		super(time, id);
		this.vehicles = vehicles;
		this.duration = duration;
	}

	@Override
  public void execute(TrafficSimulator simulator) {
    for (String id : vehicles) {
      simulator.makeVehicleFaulty(id, duration);
    }
  }

  static class Builder implements Event.Builder {

		@Override
		public Event parse(IniSection section) {
			if (!section.getTag().equals(SECTION_TAG_NAME)) {
				return null;
			}
			// TODO: no hay ID
      int time = parseInt(section, "time", 0, x -> x >= 0);
      //String id = section.getValue("id");
			String[] vehicles = parseIdList(section, "vehicles");
      int duration;
      try {
        duration = Integer.parseInt(section.getValue("duration"));
        if (duration <= 0) {
          throw new IllegalArgumentException("Vehicle's faulty duration must be positive");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Vehicle faulty duration must be a number", e);
      }
      return new MakeVehicleFaultyEvent(time, "", vehicles, duration);
		}

	}

}
