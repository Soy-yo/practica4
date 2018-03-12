package es.ucm.fdi.model;

import java.util.Queue;
import java.util.Random;

public class Car extends Vehicle {

	private int resistance;
	private int traveled;
	private double faultyProbability;
	private int maxFaultDuration;
	private Random randomNumber;

	public Car(String id, int maxSpeed, Queue<Junction> itinerary,
			int resistance, double faultProbability, int maxFaultDuration,
			long seed) {
		super(id, maxSpeed, itinerary);
		this.resistance = resistance;
		this.faultyProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		randomNumber = new Random(seed);
		traveled = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void advance() {
		double random = randomNumber.nextDouble();
		if (faulty == 0 || traveled >= resistance
				|| random < faultyProbability) {
			setFaulty(randomNumber.nextInt(maxFaultDuration) + 1);
		} else {
			int advanced = kilometrage;
			super.advance();
			traveled += kilometrage - advanced;
		}
	}
}
