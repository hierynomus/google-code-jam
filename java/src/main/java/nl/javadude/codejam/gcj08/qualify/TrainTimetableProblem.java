package nl.javadude.codejam.gcj08.qualify;

import java.util.ArrayList;
import java.util.List;

import nl.javadude.codejam.Closure;
import nl.javadude.codejam.Problem;
import nl.javadude.codejam.helpers.Time;

public class TrainTimetableProblem implements Problem {

	int problemNumber;
	int turnaroundTime;
	List<Trip> trainsAtoB = new ArrayList<Trip>();
	List<Trip> trainsBtoA = new ArrayList<Trip>();

	static class Trip implements Comparable<Trip> {
		Time departure;
		Time arrival;
		public int compareTo(Trip o) {
			return departure.compareTo(o.departure);
		}
		
		@Override
		public String toString() {
			return String.format("%s-%s", departure, arrival);
		}
	}

	static class TripClosure implements Closure {

		private final List<Trip> tripList;
		public TripClosure(List<Trip> tripList) {
			this.tripList = tripList;
		}

		public void execute(String string) {
			Trip trip = new Trip();
			String[] split = string.split(" ");
			trip.departure = new Time(split[0]);
			trip.arrival = new Time(split[1]);
			tripList.add(trip);
		}
		
	}
	
	static class StationTrip implements Comparable<StationTrip>{
		int station;
		Trip trip;
		public int compareTo(StationTrip o) {
			return trip.departure.compareTo(o.trip.departure);
		}
	}
	
	static class Train {
		Time readyTime;
		
		boolean isReady(Time time) {
			return readyTime.compareTo(time) <= 0;
		}
	}
}
