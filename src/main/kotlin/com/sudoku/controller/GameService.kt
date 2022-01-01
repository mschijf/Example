package com.sudoku.controller

import com.sudoku.controller.model.SudokuBoardModel
import com.sudoku.controller.model.SudokuInfoModel
import com.sudoku.game.SudokuBoard
import org.springframework.stereotype.Service

@Service
class GameService {
    var board = SudokuBoard()

    fun getBoard(): SudokuBoardModel {
        return SudokuBoardModel(board)
    }

    fun getNewBoard(): SudokuBoardModel {
        board = SudokuBoard()
        return SudokuBoardModel(board)
    }

    fun takeBackLastMove(): SudokuBoardModel {
        board.takeBackLastField()
        return SudokuBoardModel(board)
    }

    fun computeAndExecuteNextMove(): SudokuBoardModel {
        val sudokuSolutionStep = board.calculatePossibleField()
        board.executeSolution(sudokuSolutionStep)
        return SudokuBoardModel(board)
    }

    fun calculateAllSolutions() : SudokuInfoModel {
        return SudokuInfoModel(board.countAllPossibilities(false))
    }
}