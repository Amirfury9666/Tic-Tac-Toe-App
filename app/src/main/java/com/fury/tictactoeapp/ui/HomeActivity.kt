package com.fury.tictactoeapp.ui

import android.os.Bundle
import android.text.BoringLayout
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.fury.tictactoeapp.game.Board
import com.fury.tictactoeapp.game.Cell
import com.fury.tictactoeapp.R
import com.fury.tictactoeapp.base.BaseActivity
import com.fury.tictactoeapp.databinding.ActivityHomeBinding
import com.fury.tictactoeapp.extension.setTextOnView
import com.fury.tictactoeapp.extension.string
import kotlin.random.Random

/***
 * Created By Amir Fury on December 3 2021
 *
 * Email: Fury.amir93@gmail.com
 * */

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val boardCells = Array(3) { arrayOfNulls<ImageView>(3) }

    private var board = Board()

    override val layoutRes: Int get() = R.layout.activity_home

    override fun getToolbar(binding: ActivityHomeBinding): Toolbar? = null

    override fun onActivityReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        drawBoard(binding)

        binding.resetButton.setOnClickListener {
            board = Board()
            binding.wonTv.text = ""
            mapBoardToUi()
        }
    }

    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.player -> {
                        boardCells[i][j]?.apply {
                            setImageResource(R.drawable.drawable_circle)
                            isEnabled = false
                        }
                    }
                    Board.computer -> {
                        boardCells[i][j]?.apply {
                            setImageResource(R.drawable.ic_cross)
                            isEnabled = false
                        }
                    }
                    else -> {
                        boardCells[i][j]?.apply {
                            setImageResource(0)
                            isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun drawBoard(binding: ActivityHomeBinding) {
        for (i in boardCells.indices) {
            for (j in boardCells.indices) {
                boardCells[i][j] = ImageView(this)
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 250
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5

                }
                boardCells[i][j]?.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.colorPrimary
                    )
                )
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))
                binding.gridBoard.addView(boardCells[i][j])
            }
        }
    }

    private inner class CellClickListener(private val i: Int, private val j: Int) :
        View.OnClickListener {
        override fun onClick(p0: View?) {
            if (!board.isGameOver) {
                val cell = Cell(i, j)
                board.placeMove(cell, Board.player)
                board.miniMax(0, Board.computer)
                board.computerMove?.let {
                    board.placeMove(it, Board.computer)
                }
                mapBoardToUi()
            }
            when {
                board.hasComputerWon() -> binding.wonTv.setTextOnView(string(R.string.computerWon))
                board.hasPlayerWon() -> binding.wonTv.setTextOnView(string(R.string.youWon))
                board.isGameOver -> binding.wonTv.setTextOnView(string(R.string.gameTied))
            }
        }
    }
}