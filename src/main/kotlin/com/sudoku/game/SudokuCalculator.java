package com.sudoku.game;

public class SudokuCalculator {
	private SudokuBoard sudokuBoard;
	private int showIndex;
	
	public SudokuCalculator() {
		String[] justSomething = new String[81];
		setSquares(justSomething);
	}
	
	public void setSquares(String[] strSquares) {
		int[] squares = new int[strSquares.length];
		
		for (int index=0; index<strSquares.length; ++index) { 
			if (index >= 0 && index < squares.length) {		
				try {
					squares[index] = Integer.parseInt(strSquares[index]);
				} catch (NumberFormatException e) {
					squares[index] = 0;
				}
			}
		}
		
		sudokuBoard = new SudokuBoard(squares);
	}

//=================================================================
	
	/**
	 * 
	 */
	public void startIteratingSquares() {
		showIndex=-1;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNextSquare() {
		++showIndex;
		int val = sudokuBoard.getSquareValue(showIndex / 9, showIndex % 9); 
		if (val > 0)
			return Integer.toString(val);
		return "";
	}

	public String getNextSquareSet() {
		int index=showIndex;
		return sudokuBoard.getSquarePossibleValueString(index / 9, index % 9);
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasNextSquare() {
		return ((showIndex+1) < sudokuBoard.maxSquares());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean nextSquareIsNew() {
		int index=showIndex+1;
		return sudokuBoard.isLastSquarePlayed((index) / 9, (index) % 9);
	}

	//=================================================================
	
	public void calculateNextField() {
		sudokuBoard.calculatePossibleField();
	}
	
	public boolean hasNextfields() {
		return sudokuBoard.hasSolutionStep();
	}

	public boolean canUndo() {
		return sudokuBoard.canUndo();
	}

	public void takeBackLastField() {
		sudokuBoard.takeBackLastField();
	}
	
	public int getSolutionCount() {
		return sudokuBoard.countAllPossibilities(false);
	}
	
	public boolean isLegal() {
		return sudokuBoard.isLegal();
	}
}
