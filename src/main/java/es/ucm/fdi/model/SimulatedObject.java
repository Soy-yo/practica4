package es.ucm.fdi.model;

public abstract class SimulatedObject {

    protected final String id;

    public SimulatedObject(String id) {
        this.id = id;
    }

    public abstract void advance();

    public abstract String getSectionTagName();

    public String generateReport(int time) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[" + getSectionTagName() + "]\n");
        stringBuffer.append("id = " + id + "\n");
        stringBuffer.append("time = " + time);
        return stringBuffer.toString();
    }

}
