package main;

public class Cell {
	
	public int occupied, color;

	public Cell(int occupied, int color) {
		this.occupied = occupied;
		this.color = color;
	}

	public int getOccupied() {
		return occupied;
	}

	public void setOccupied(int occupied) {
		this.occupied = occupied;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}	

}
