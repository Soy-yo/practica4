package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.TrafficSimulator;
import es.ucm.fdi.model.Vehicle;

public class NewVehicleEvent extends Event {

	public static final String SECTION_TAG_NAME = "new_vehicle";

	private int maxSpeed;
	private String[] itinerary;

  NewVehicleEvent(int time, String id, int maxSpeed, String[] itinerary) {
		super(time, id);
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}

	@Override
  public void execute(TrafficSimulator simulator) {
    // FIXME: itinerario en vez de road
    Vehicle vehicle = new Vehicle(id, maxSpeed, null);
    simulator.addSimulatedObject(vehicle);
  }

  static class Builder implements Event.Builder {

		@Override
    public Event parse(IniSection section) {

			if (!section.getTag().equals(SECTION_TAG_NAME)) {
				return null;
			}

      int time = parseInt(section, "time", 0, x -> x >= 0);

      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id + " is not a valid id");
      }

      int maxSpeed;
      try {
        maxSpeed = Integer.parseInt(section.getValue("max_speed"));
        if (maxSpeed <= 0) {
          throw new IllegalArgumentException("Vehicle max speed must be positive");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Vehicle max speed must be a number", e);
      }

      String[] itinerary = parseIdList(section, "itinerary");
      if (itinerary == null || itinerary.length < 2) {
        throw new IllegalArgumentException("Vehicle itinerary list must contain at least " +
            "2 junctions");
			}

      return new NewVehicleEvent(time, id, maxSpeed, itinerary);
		}

	}

}
