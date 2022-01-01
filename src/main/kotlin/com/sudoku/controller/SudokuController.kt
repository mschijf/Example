package com.sudoku.controller

import com.sudoku.controller.model.SudokuBoardModel
import com.sudoku.controller.model.SudokuInfoModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

const val REQUESTPATH_BASE = "sudoku/v1"

@RestController
@RequestMapping(REQUESTPATH_BASE)
class SudokuController @Autowired constructor(private val gameService: GameService) {

    @GetMapping("/board/")
    fun getBoard(): SudokuBoardModel {
        return gameService.getBoard()
    }

    @PostMapping("/board/")
    fun newBoard(httpServletResponse: HttpServletResponse): SudokuBoardModel {
        return gameService.getNewBoard()
    }

    @PostMapping("/move/takeback/")
    fun takeBackLastMove(httpServletResponse: HttpServletResponse): SudokuBoardModel {
        return gameService.takeBackLastMove()
    }

    @PostMapping("/move/compute/")
    fun computeAndExecuteNextMove(httpServletResponse: HttpServletResponse): SudokuBoardModel {
        return gameService.computeAndExecuteNextMove()
    }

    @GetMapping("/board/info/")
    fun getInfo(httpServletResponse: HttpServletResponse): SudokuInfoModel {
        return gameService.calculateAllSolutions()
    }
}


