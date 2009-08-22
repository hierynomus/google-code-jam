package nl.javadude.codejam.gcj08.qualify;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.javadude.codejam.AbstractMultiProblemSolver;
import nl.javadude.codejam.gcj08.qualify.TrainTimetableProblem.StationTrip;
import nl.javadude.codejam.gcj08.qualify.TrainTimetableProblem.Train;
import nl.javadude.codejam.gcj08.qualify.TrainTimetableProblem.Trip;

public class TrainTimetable extends AbstractMultiProblemSolver<TrainTimetableProblem> {

	@Override
	protected TrainTimetableProblem readProblem(BufferedReader lines, int problemNumber) throws Exception {
		TrainTimetableProblem problem = new TrainTimetableProblem();
		problem.problemNumber = problemNumber;
		problem.turnaroundTime = readLineAsInt(lines);
		String[] trains = lines.readLine().split(" ");
		readLines(lines, Integer.parseInt(trains[0]), new TrainTimetableProblem.TripClosure(problem.trainsAtoB));
		readLines(lines, Integer.parseInt(trains[1]), new TrainTimetableProblem.TripClosure(problem.trainsBtoA));
		return problem;
	}

	@Override
	protected List<String> solveProblem(TrainTimetableProblem problem) {
		int[] starts = new int[2];
		List<List<Train>> trains = new ArrayList<List<Train>>(2);
		trains.add(new ArrayList<Train>());
		trains.add(new ArrayList<Train>());
		List<StationTrip> trips = buildStationTrips(problem);

		Collections.sort(trips);
		for (StationTrip st : trips) {
			List<Train> stationTrains = trains.get(st.station);
			Train train = null;
			if (!stationTrains.isEmpty()){
				// A train is at the station
				
				// Search a train that is ready
				for (Train t : stationTrains) {
					if (t.isReady(st.trip.departure)) {
						train = t;
						break;
					}
				}

				if (train != null) {
					// Ready train found, remove and move to other station
					stationTrains.remove(train);
					train = new Train();
					train.readyTime = st.trip.arrival.addMinutes(problem.turnaroundTime);
					trains.get((st.station + 1) % 2).add(train);
				}
			}
			
			if (train == null) {
				starts[st.station]++;
				train = new Train();
				train.readyTime = st.trip.arrival.addMinutes(problem.turnaroundTime);
				trains.get((st.station + 1) % 2).add(train);
			}
		}
		
		
		return Collections.singletonList("Case #" + problem.problemNumber + ": " + starts[0] + " " + starts[1]);
	}

	private List<StationTrip> buildStationTrips(TrainTimetableProblem problem) {
		List<StationTrip> trips = new ArrayList<StationTrip>();
		for (Trip trip : problem.trainsAtoB) {
			StationTrip st = new StationTrip();
			st.station = 0;
			st.trip = trip;
			trips.add(st);
		}
		for (Trip trip : problem.trainsBtoA) {
			StationTrip st = new StationTrip();
			st.station = 1;
			st.trip = trip;
			trips.add(st);
		}
		return trips;
	}

}
