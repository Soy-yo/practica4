package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.MostCrowdedJunction;
import es.ucm.fdi.model.TrafficSimulator;

public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {

  NewMostCrowdedJunctionEvent(int time, String id) {
    super(time, id);
  }

  @Override
  public void execute(TrafficSimulator simulator) {
    MostCrowdedJunction mcJunction = new MostCrowdedJunction(id);
    simulator.addSimulatedObject(mcJunction);
  }

  static class Builder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {

      if (!section.getTag().equals(SECTION_TAG_NAME)
          || !MostCrowdedJunction.TYPE
          .equals(section.getValue("type"))) {
        return null;
      }

      int time = parsePositiveInt(section, "time", 0);

      String id = section.getValue("id");
      if (!isValid(id)) {
        throw new IllegalArgumentException("id " + id
            + " is not a valid id");
      }

      return new NewMostCrowdedJunctionEvent(time, id);
    }

  }
}
