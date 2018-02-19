package es.ucm.fdi.model;

public abstract class SimulatedObject {

    public abstract void advance();

    public String generateReport() {
        throw new UnsupportedOperationException();
    }

}
