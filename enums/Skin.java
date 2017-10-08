package com.matthewgarrison.enums;

public enum Skin {
	NORMAL, SHEEP;

	public static Skin fromInteger(int x) {
		if (x == 1) return Skin.NORMAL;
		else return Skin.SHEEP;
	}

	public static int toInteger(Skin s) {
		if (s == Skin.NORMAL) return 1;
		else return 2;
	}

	public static String getSkinName(Skin skin) {
		if (skin == Skin.NORMAL) return "Normal";
		else return "Sheep";
	}
}
