package com.arun;

class MazeCell {

	public int value;
	// 0 -> Left Wall, 1 -> Right Wall, 2 -> Top Wall, 3 -> Bottom Wall
	public Boolean walls[];

	public MazeCell(Boolean left, Boolean right, Boolean top, Boolean bottom) {
		super();
		this.value = -1;
		walls = new Boolean[4];
		walls[0] = left;
		walls[1] = right;
		walls[2] = top;
		walls[3] = bottom;
	}
}
