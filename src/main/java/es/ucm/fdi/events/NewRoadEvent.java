package es.ucm.fdi.events;

import es.ucm.fdi.ini.IniSection;

public class NewRoadEvent extends Event {

  private static final String SECTION_TAG_NAME = "new_road";

  private String sourceId;
  private String destinationId;
  private int maxSpeed;
  private int length;

  public NewRoadEvent(
      int time,
      String id,
      String sourceId,
      String destinationId,
      int maxSpeed,
      int length) {
    super(time, id);
    this.sourceId = sourceId;
    this.destinationId = destinationId;
    this.maxSpeed = maxSpeed;
    this.length = length;
  }

  @Override
  public void execute() {

  }

  class NewRoadEventBuilder implements EventBuilder {

    @Override
    public Event parse(IniSection section) {
      if (!section.getTag().equals(SECTION_TAG_NAME)) {
        return null;
      }
      // TODO: try catch...
      int time = parseInt(section, "time", 0);
      String id = section.getValue("id");
      String src = section.getValue("sec");
      String dest = section.getValue("dest");
      int maxSpeed = Integer.parseInt(section.getValue("max_speed"));
      int length = Integer.parseInt(section.getValue("length"));
      return new NewRoadEvent(time, id, src, dest, maxSpeed, length);
    }

  }

}
