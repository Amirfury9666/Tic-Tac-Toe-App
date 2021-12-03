package com.fury.tictactoeapp.game

/***
 * Created By Amir Fury on December 3 2021
 *
 * Email: Fury.amir93@gmail.com
 * */


class Board {

    companion object {
        const val player = "O"
        const val computer = "X"
    }

    val board = Array(3) { arrayOfNulls<String>(3) }

    private val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j].isNullOrEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }


    fun hasComputerWon(): Boolean {
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == computer ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == computer
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == computer ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == computer
            ) {
                return true
            }
        }
        return false
    }

    fun hasPlayerWon(): Boolean {
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == player ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == player
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == player ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == player
            ) {
                return true
            }
        }
        return false
    }

    val isGameOver: Boolean
        get() = hasComputerWon() || hasPlayerWon() || availableCells.isEmpty()

    var computerMove: Cell? = null
    fun miniMax(depth: Int, player: String): Int {
        if (hasComputerWon()) return +1
        if (hasPlayerWon()) return -1

        if (availableCells.isEmpty()) return 0

        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        for (i in availableCells.indices) {
            val cell = availableCells[i]
            if (player == computer) {
                placeMove(cell, computer)
                val currentScore = miniMax(depth + 1, player)
                max = Math.max(currentScore, max)

                if (currentScore >= 0) {
                    if (depth == 0) computerMove = cell
                }

                if (currentScore == 1) {
                    board[cell.i][cell.j] = ""
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) computerMove = cell
                }

            } else if (player == player) {
                placeMove(cell, player)
                val currentScore = miniMax(depth + 1, computer)
                min = Math.min(currentScore, min)

                if (min == -1) {
                    board[cell.i][cell.j] = ""
                    break
                }
            }
            board[cell.i][cell.j] = ""
        }

        return if (player == computer) max else min
    }


    fun placeMove(cell: Cell, player: String) {
        board[cell.i][cell.j] = player
    }
}