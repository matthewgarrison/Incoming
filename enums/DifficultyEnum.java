package com.matthewgarrison.enums;

public enum DifficultyEnum {
	EASY, MEDIUM, HARD;

	public static DifficultyEnum fromInteger(int x) {
		return values()[x];
	}
}
