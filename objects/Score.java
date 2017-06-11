
package net.ocps.tchs.MDGame.objects;

public class Score implements Comparable<Score> {
	
	String name;
	int score;
	String dateAndTime; //YYYY-MM-DD_HH:mm:SS

	//takes in a string, the name associated with the score, takes in the score
	public Score(String givenName, int givenScore) {
		name = givenName;
		score = givenScore;
	}
	
	//sorts the scores array greatest to least
	public static Score[] sortScores(Score[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[i].getScore() > array[j].getScore()) {
					Score temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
		
		return array;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}

	@Override
	public int compareTo(Score s) {
		if (this.score == s.score) {
			//oldest first, so this should work
			return this.dateAndTime.compareTo(s.dateAndTime);
		}
		return s.score - this.score;
	}
}
