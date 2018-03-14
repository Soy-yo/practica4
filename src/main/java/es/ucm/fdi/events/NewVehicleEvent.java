package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.TrafficSimulator;
import es.ucm.fdi.model.Vehicle;

public class NewVehicleEvent extends Event {

  protected static final String SECTION_TAG_NAME = "new_vehicle";

  protected int maxSpeed;
  protected String[] itinerary;

  NewVehicleEvent(int time, String id, int maxSpeed, String[] itinerary) {
    super(time, id);
    this.maxSpeed = maxSpeed;
    this.itinerary = itinerary;
  }

  @Override
  public void execute(TrafficSimulator simulator) {
    Vehicle vehicle = new Vehicle(id, maxSpeed, simulator.getPath(itinerary));
    simulator.addSimulatedObject(vehicle);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)) {
        return null;
      }

      int time = parsePositiveInt(section, "time", 0);

      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id + " is not a valid id");
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
        throw new IllegalArgumentException("Vehicle itinerary list must contain at least " +
            "2 junctions");
      }

      return new NewVehicleEvent(time, id, maxSpeed, itinerary);
    }

  }

}
