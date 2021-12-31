package com.sudoku.game;

public class SudokuSolutionStep {
	private SudokuSquare square;
	private int			 value;
	
	SudokuSolutionStep(SudokuSquare sq, int val) {
		square = sq;
		value = val;
	}

	public SudokuSquare getSquare() {
		return square;
	}

	public int getValue() {
		return value;
	}
}
