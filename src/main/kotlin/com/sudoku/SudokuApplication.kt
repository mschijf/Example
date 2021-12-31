package com.sudoku

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SudokuApplication

fun main(args: Array<String>) {
    runApplication<SudokuApplication>(*args)
}

