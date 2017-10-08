package com.matthewgarrison.enums;

public enum Difficulty {
	EASY, MEDIUM, HARD;

	public static Difficulty fromInteger(int x) {
		if (x == 1) return Difficulty.EASY;
		else if (x == 2) return Difficulty.MEDIUM;
		else return Difficulty.HARD;
	}

	public static int toInteger(Difficulty d) {
		if (d == Difficulty.EASY) return 1;
		else if (d == Difficulty.MEDIUM) return 2;
		else return 3;
	}

	public static String getDifficultyName(Difficulty difficulty) {
		if (difficulty == Difficulty.EASY) return "Easy";
		else if (difficulty == Difficulty.MEDIUM) return "Medium";
		else return "Hard";
	}
}
