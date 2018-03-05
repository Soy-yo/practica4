package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Junction;

public class NewJunctionEvent extends Event {

  private static final String SECTION_TAG_NAME = "new_junction";

  public NewJunctionEvent(int time, String id) {
    super(time, id);
  }

  @Override
  public void execute() {
    Junction junction = new Junction(id);
  }

  class NewJunctionEventBuilder implements Event.Builder {

    @Override
    public Event parse(IniSection section) {
      if (!section.getTag().equals(SECTION_TAG_NAME)) {
        return null;
      }
      int time = parseInt(section, "time", 0);
      String id = section.getValue("id");
      return new NewJunctionEvent(time, id);
    }

  }

}
