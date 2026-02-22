package com.example.model;

public enum Stone {
	EMPTY,
	BLACK,
	WHITE;

	public Stone opposite() {
		if(this == BLACK) return WHITE;
		if(this == WHITE) return BLACK;
		return EMPTY;
	}
}
