
package com.matthewgarrison.objects;

public class Score implements Comparable<Score> {

    String name;
    int value;

    public Score(String givenName, int givenScore) {
        name = givenName;
        value = givenScore;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    // Sorts scores greatest to least, and breaks ties alphabetically.
    @Override
    public int compareTo(Score s) {
        if (this.value == s.value) {
            return this.name.compareTo(s.name);
        }
        return s.value - this.value;
    }

	public String toString() {
		return this.name + " " + this.value;
	}
}
