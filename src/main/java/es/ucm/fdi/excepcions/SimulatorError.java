package es.ucm.fdi.excepcions;

public class SimulatorError extends Exception {

  public SimulatorError() {
  }

  public SimulatorError(String msg) {
    super(msg);
  }

  public SimulatorError(String msg, Throwable cause) {
    super(msg, cause);
  }

}
