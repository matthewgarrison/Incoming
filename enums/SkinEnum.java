package com.matthewgarrison.enums;

public enum SkinEnum {
	NORMAL, SHEEP;

	public static SkinEnum fromInteger(int x) {
		return values()[x];
	}
}
