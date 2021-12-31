package com.sudoku.controller

import com.sudoku.controller.model.SudokuBoardModel
import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import com.github.jknack.handlebars.io.TemplateLoader
import com.sudoku.game.SudokuBoard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class SudokuHTMLController @Autowired constructor(private val gameService: GameService) {

    @GetMapping("/")
    fun index(): String {
        val loader: TemplateLoader = ClassPathTemplateLoader("/handlebars", ".hbs")
        val handlebars = Handlebars(loader)
        val template: Template = handlebars.compile("sudoku")
        return template.apply(SudokuBoardModel(SudokuBoard()))
    }

}


