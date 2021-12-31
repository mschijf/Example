package com.sudoku.game;

import java.util.HashSet;

public class SudokuSquare {
	private HashSet<Integer> possibleValues;
	private HashSet<SudokuTuple> tuples;
	private int	theValue;
	
	public SudokuSquare() {
		theValue=0;
		possibleValues = new HashSet<Integer>();
		
		initPossibilities();
		
		tuples = new HashSet<SudokuTuple>();
	}
	
	public void addTuple(SudokuTuple aTuple) {
		tuples.add(aTuple);
	}
	
	
	public void updatePossibleValues(int aValue) {	
		possibleValues.remove(new Integer(aValue));
	}
	
	
	public void setValue(int aValue) {
		theValue = aValue;
		recalcPossibilities() ;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasExactlyOnePossibleValue() {
		return (possibleValues.size() == 1);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPossibleValue() {
		return possibleValues.iterator().next().intValue();
	}

	/**
	 * 
	 * @param aValue
	 * @return
	 */
	public boolean isPossibleValue(int aValue) {
		return possibleValues.contains(new Integer(aValue));
	}

	public int getAcceptedValue() {
		return theValue;
	}
	

	public String possibilityString() {
		String s = "";
		for (Integer v: possibleValues)
			s = s + v.toString() + " ";
		return s;
	}

	public void initPossibilities() {
		possibleValues.clear();
		for (int i=1; i<=9; ++i) 
			possibleValues.add(new Integer(i));
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
