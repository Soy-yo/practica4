package es.ucm.fdi.events;

public class NewVehicleEvent extends Event {
	
	public static final String SECTION_TAG_NAME = "new_vehicle";
	
	private int max_speed;
	private String[] itinerary;
	
	public NewVehicleEvent(int max_speed, String[] itinerary){
		
	}
}
