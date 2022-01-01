package com.sudoku.game;
import java.util.HashSet;
import java.util.Iterator;

public class SudokuTuple {
	HashSet<SudokuSquare> squareSet;
	
	public SudokuTuple() {
		squareSet = new HashSet<SudokuSquare>();
	}
	
	public void addSquare(SudokuSquare aSquare) {
		squareSet.add(aSquare);
		aSquare.addTuple(this);
	}
	
	public void updateValue(int value) {
		for (SudokuSquare square: squareSet)
			square.updatePossibleValues(value);
	}

	public SudokuSolutionStep findSolvableSquare() {
		for (int i=1; i<=9; ++i) {
			SudokuSquare sq = null;
			for (SudokuSquare tmpSq: squareSet) {
				if (tmpSq.isPossibleValue(i)) {
					if (sq == null) {
						sq = tmpSq;
					} else {
						sq = null;
						break;
					}
				}
			}

//			Iterator<SudokuSquare> it = squareSet.iterator();
//			sq = null;
//			boolean goOn = true;
//			while (it.hasNext() && goOn) {
//				SudokuSquare tmpSq=it.next();
//				if (tmpSq.isPossibleValue(i)) {
//					if (sq == null)
//						sq = tmpSq;
//					else
//						goOn = false;
//				}
//			}
//			if (goOn && sq != null)
			if (sq != null)
				return new SudokuSolutionStep(sq, i);
		}
		return null;
	}

	public boolean illegal() {
		HashSet<Integer> usedValues = new HashSet<Integer>(); 
		for (SudokuSquare square: squareSet) {
			int val = square.getAcceptedValue();
			if (val != 0) {
				if (usedValues.contains(val))
					return false;
				usedValues.add(val);			
			}
		}
		
		return false;
	}
}
