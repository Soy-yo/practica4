package es.ucm.fdi.model;

public abstract class SimulatedObject {

  protected final String id;

  public SimulatedObject(String id) {
    this.id = id;
  }

  public abstract void advance();

  public abstract String generateReport(int time);

}
