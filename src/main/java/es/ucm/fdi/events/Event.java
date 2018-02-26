package es.ucm.fdi.events;

public abstract class Event {

  protected int time;
  protected String id;

  public Event(int time, String id) {
    this.time = time;
    this.id = id;
  }

  public abstract void execute();

}
