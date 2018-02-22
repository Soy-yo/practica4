package es.ucm.fdi.model;

public abstract class SimulatedObject {

  protected final String id; // id único

  public SimulatedObject(String id) {
    this.id = id;
  }

  public abstract void advance();

  public abstract String generateReport(int time);

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SimulatedObject other = (SimulatedObject) o;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

}
