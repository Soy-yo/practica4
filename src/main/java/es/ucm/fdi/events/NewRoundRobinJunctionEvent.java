package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.RoundRobinJunction;
import es.ucm.fdi.model.TrafficSimulator;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {

  private int maxTimeSlice;
  private int minTimeSlice;

  NewRoundRobinJunctionEvent(int time, String id, int minTimeSlice, int maxTimeSlice) {
    super(time, id);
    this.maxTimeSlice = maxTimeSlice;
    this.minTimeSlice = minTimeSlice;
  }

  @Override
  public void execute(TrafficSimulator simulator) {
    RoundRobinJunction rrJunction = new RoundRobinJunction(id,
        minTimeSlice, maxTimeSlice);
    simulator.addSimulatedObject(rrJunction);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)
          || !RoundRobinJunction.TYPE
          .equals(section.getValue("type"))) {
        return null;
      }

      int time = parsePositiveInt(section, "time", 0);

      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id
            + " is not a valid id");
      }

      int maxTimeSlice;
      try {
        maxTimeSlice = getIntValue("max_time_slice", section);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Max time slice must be a positive number");
      }

      int minTimeSlice;
      try {
        minTimeSlice = getIntValue("min_time_slice", section);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Min time slice must be a positive number");
      }

      return new NewRoundRobinJunctionEvent(time, id, minTimeSlice, maxTimeSlice);
    }

  }

}
