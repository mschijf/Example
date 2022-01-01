
package com.sudoku.game;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class SudokuBoard {
	public static final int MAX_ROW = 9;
	public static final int MAX_COL = 9;

	SudokuSquare [][] allSquares;
	Vector<SudokuTuple> allTuples;
	Stack<SudokuSolutionStep> solutions;

	private static final int[] emptySquares = {
			0,0,0, 0,0,0, 0,8,0,
			6,8,0, 4,7,0, 0,2,0,
			0,1,9, 5,0,8, 6,4,7,

			0,6,0, 9,0,0, 0,0,4,
			3,4,2, 6,8,0, 0,0,0,
			1,9,0, 0,5,0, 8,3,0,

			0,0,0, 7,2,0, 4,0,3,
			0,0,6, 0,0,5, 0,1,0,
			0,0,3, 8,9,1, 5,0,0
	};

	public SudokuBoard() {
		this(emptySquares);
	}

	public SudokuBoard(int[] intSquares) {
		allTuples = new Vector<SudokuTuple>();
		allSquares = new SudokuSquare[9][9];
		solutions = new Stack<SudokuSolutionStep>();
		
		//create the squares
		for (int r=0; r<9; r++) 
			for (int c=0; c<9; c++) 
				allSquares[r][c] = new SudokuSquare();
		
		//create the row tuples
		for (int r=0; r<9; ++r) {
			SudokuTuple aTuple = new SudokuTuple(); 
			allTuples.add(aTuple);
			for (int c=0; c<9; c++) {
				aTuple.addSquare(allSquares[r][c]);
			}
		}
		
		//create column tuples
		for (int c=0; c<9; c++) {
			SudokuTuple aTuple = new SudokuTuple();
			allTuples.add(aTuple);
			for (int r=0; r<9; ++r) {
				aTuple.addSquare(allSquares[r][c]);
			}
		}

		//create block tuples
		for (int br=0; br<3; ++br) {
			for (int bc=0; bc<3; ++bc) {
				SudokuTuple aTuple = new SudokuTuple(); 
				allTuples.add(aTuple);
				for (int r=br*3; r<br*3+3; r++) {
					for (int c=bc*3; c<bc*3+3; c++) {
						aTuple.addSquare(allSquares[r][c]);
					}
				}
			}
		}
		
		/*
		//create diagonal tuples 
		SudokuTuple aTupleULLR = new SudokuTuple();
		SudokuTuple aTupleURLL = new SudokuTuple();
		allTuples.add(aTupleULLR);
		allTuples.add(aTupleURLL);
		for (int r=0; r<9; r++) {
			aTupleULLR.addSquare(allSquares[r][r]);
			aTupleURLL.addSquare(allSquares[r][8-r]);
		}
		*/
		
		
		//enter the values
		for (int r=0; r<9; r++) { 
			for (int c=0; c<9; c++) { 
				int sqVal = intSquares[r*9+c];
				if (sqVal >= 1 && sqVal <= 9)
					allSquares[r][c].setValue(sqVal);
			}
		}
	}

	//==============================================================================

	public boolean isLegal() {
		if (allTuples == null)
			return false;

		for (SudokuTuple tuple: allTuples)
			if (tuple.illegal())
				return false;
		if (this.countAllPossibilities(true) != 1)
			return false;
		return true;
	}

	//==============================================================================

	public boolean isLastSquarePlayed(int row, int col) {
		if (solutions.isEmpty())
			return false;
		return (solutions.peek().getSquare() == allSquares[row][col]);
	}

	public String getSquarePossibleValueString(int row, int col) {
		return allSquares[row][col].possibilityString();
	}

	private void recalculateAllPossibilities() {
		for (SudokuSquare[] row: allSquares) 
			for (SudokuSquare square: row) 
				square.initPossibilities();
		
		for (SudokuSquare[] row: allSquares) 
			for (SudokuSquare square: row) 
				square.recalcPossibilities();
	}
	
	public void takeBackLastField() {
		if (solutions.isEmpty())
			return;
		
		SudokuSolutionStep solution = solutions.pop();
		solution.getSquare().setValue(0);
		recalculateAllPossibilities();
	}
	
	public boolean canUndo() {
		return !solutions.isEmpty();
	}
	
	public int getSquareValue(int row, int col) {
		return allSquares[row][col].getAcceptedValue();
	}
	
	//==============================================================================

	private SudokuSolutionStep getSolutionFollowingRule1() {
		for (int r=0; r<9; r++) { 
			for (int c=0; c<9; c++) {
				SudokuSquare sq = allSquares[r][c]; 
				if (sq.hasExactlyOnePossibleValue()) {
					return new SudokuSolutionStep(sq, sq.getPossibleValue() );
				}
			}
		}
		
		return null;
	}

	private SudokuSolutionStep getSolutionFollowingRule2() {
		for (SudokuTuple tuple:allTuples) {
			SudokuSolutionStep step = tuple.findSolvableSquare();
			if (step != null) { 
				return step;				
			}
		}
		return null;
	}
	
	private SudokuSolutionStep getSimpleSolutionStep() {
		SudokuSolutionStep solution;
		
		solution = getSolutionFollowingRule1();
		if (solution == null) 
			solution = getSolutionFollowingRule2();
		
		return solution;
	}
	
	private SudokuSolutionStep getComplexSolutionStep() {
		SudokuSquare sq = mostPromisingSquare();
		HashSet<Integer> localSet = new HashSet<Integer>(sq.getPossibleValueSet());		
		for (int val: localSet) {		
			executeSolution(new SudokuSolutionStep(sq, val));
			int cnt = countAllPossibilities(true);
			takeBackLastField();
			if (cnt > 0) 
				return new SudokuSolutionStep(sq, val);
		}		
		return null;
	}


	public void executeSolution(SudokuSolutionStep solution) {
		solutions.push(solution);
		solution.getSquare().setValue(solution.getValue());
	}
	
	public SudokuSolutionStep calculatePossibleField() {
		SudokuSolutionStep solution = getSimpleSolutionStep();
		if (solution == null) 
			solution = getComplexSolutionStep();

		if (solution == null)
			return null;
		return solution;
	}

	public boolean hasSolutionStep() {
		return (!illegal() && !finished());
	}
	
	//==============================================================================
	
	private boolean finished() {
		for (SudokuSquare[] row: allSquares) 
			for (SudokuSquare square: row) 
				if (!square.hasValue())
					return false;
		return true;
	}
	
	private boolean illegal() {
		for (SudokuSquare[] row: allSquares) 
			for (SudokuSquare square: row) 
				if (square.illegal())
					return true;
		return false;
	}
	
	private SudokuSquare mostPromisingSquare() {
		int minCnt = 9999;
		SudokuSquare mpSquare = null;
		
		for (SudokuSquare[] row: allSquares) { 
			for (SudokuSquare square: row) { 
				if (!square.hasValue()) {
					int n = square.getPossibleValueSet().size();
					if (n < minCnt) {
						minCnt = n;
						mpSquare = square;
					}
				}
			}
		}
		
		return mpSquare;
	}

	public int countAllPossibilities(boolean quick) {
		if (finished() )
			return 1;
		if (illegal() )
			return 0;
		
		int cnt=0;
		SudokuSolutionStep solution = getSimpleSolutionStep();
		if (solution != null) {
			executeSolution(solution);
			cnt = countAllPossibilities(quick);
			takeBackLastField();
			return cnt;
		}
		
		SudokuSquare sq = mostPromisingSquare();
		if (sq == null)
			return 0;
		
		HashSet<Integer> localSet = new HashSet<Integer>(sq.getPossibleValueSet());		
		for (int val: localSet) {
			executeSolution(new SudokuSolutionStep(sq, val));
			cnt = cnt + countAllPossibilities(quick);
			takeBackLastField();
			if (cnt > 1 && quick)
				return cnt;
		}
		return cnt;
	}
}
