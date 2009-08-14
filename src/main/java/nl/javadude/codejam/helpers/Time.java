package nl.javadude.codejam.helpers;

public class Time implements Comparable<Time> {
	int hours, minutes;

	public Time(String time) {
		String[] split = time.split(":");
		hours = Integer.parseInt(split[0]);
		minutes = Integer.parseInt(split[1]);
	}

	Time(int hours, int minutes) {
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public int compareTo(Time o) {
		if (hours != o.hours) {
			return hours - o.hours;
		} else {
			return minutes - o.minutes;
		}
	}

	public Time addMinutes(int minutes) {
		Time t = new Time(this.hours, this.minutes);
		t.minutes = (t.minutes + minutes) % 60;
		if (t.minutes < minutes) {
			t.hours++;
		}
		return t;
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d", hours, minutes);
	}
	
}
