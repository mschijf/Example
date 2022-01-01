package com.sudoku.game;

import java.util.HashSet;

public class SudokuSquare {
	private HashSet<Integer> possibleValues;
	private HashSet<SudokuTuple> tuples;
	private int	theValue;
	
	public SudokuSquare() {
		possibleValues = new HashSet<>();
		tuples = new HashSet<SudokuTuple>();

		theValue=0;
		initPossibilities();
	}
	
	public void addTuple(SudokuTuple aTuple) {
		tuples.add(aTuple);
	}

	public void updatePossibleValues(int aValue) {
		possibleValues.remove(aValue);
	}

	public void setValue(int aValue) {
		theValue = aValue;
		recalcPossibilities() ;
	}

	public boolean hasExactlyOnePossibleValue() {
		return (possibleValues.size() == 1);
	}
	
	public int getPossibleValue() {
		return possibleValues.stream().findFirst().get();
	}

	public boolean isPossibleValue(int aValue) {
		return possibleValues.contains(aValue);
	}

	public int getAcceptedValue() {
		return theValue;
	}

	public String possibilityString() {
		StringBuilder s = new StringBuilder();
		for (Integer v: possibleValues)
			s.append(v + " ");
		return s.toString();
	}

	public void initPossibilities() {
		possibleValues.clear();
		for (int i=1; i<=9; ++i) 
			possibleValues.add(i);
	}

	public void recalcPossibilities() {
		if (theValue > 0) {
			possibleValues.clear();
			for(SudokuTuple tuple: tuples)
				tuple.updateValue(theValue);
		}
	}

	public boolean hasValue() {
		return theValue > 0;
	}
	
	public boolean illegal() {
		return (theValue <= 0) && (possibleValues.isEmpty());
	}

	public HashSet<Integer> getPossibleValueSet() {
		return possibleValues;
	}
}
