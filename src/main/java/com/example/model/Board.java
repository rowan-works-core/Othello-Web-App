package com.example.model;

public class Board {

	
	private static final int SIZE = 8;
	private final Stone[][] grid;
	
	
	public Board() {
		this.grid = new Stone[SIZE][SIZE];
		initialize();
	}
	
	private void initialize() {
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				grid[row][col] = Stone.EMPTY;
			}
		}
		
		//初期配置
		grid[3][3] = Stone.WHITE;
		grid[3][4] = Stone.BLACK;
		grid[4][3] = Stone.BLACK;
		grid[4][4] = Stone.WHITE;
	}
	
	
	public Stone[][] getGrid(){
		return grid;
	}
	
	
	public Stone getStone(int row, int col) {
		return grid[row][col];
	}
	
	
	public void setStone(int row, int col, Stone stone) {
        grid[row][col] = stone;
    }
	
	
    public int getSize() {
        return SIZE;
    }
	

}
