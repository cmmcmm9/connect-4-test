package com.morrissey.connect4

/**
 * Class to manage the [Board] state of a given Tic-Tac-Toe game.
 * Allows to:
 * * Check the winner of a given [Board] via [checkWinner]
 * * Check if there are any moves left on a given [Board] via [anyMovesLeft]
 * * Check if the game is over for a given [Board] via [isGameOver]
 */
class TicTacToe {

    /**
     * Check the given [board] for the winner, [Board.WinState].
     * A win is defined by:
     * * 2x2 box
     * * All four corners
     * * Diagonal, horizontal, or vertical 4 in a row
     * @param board The [Board] to check for a [Board.WinState]
     * @return The [Board.WinState] of the given [board]. Either [Board.WinState.X], [Board.WinState.O] or no win state
     * represented by [Board.WinState.NONE]. Note that for a draw or in progres board, [Board.WinState.NONE] will be returned.
     */
    fun checkWinner(board: Board): Board.WinState {
        // Check four corners
        val fourCorners = setOf(
            board.getCellState(0, 0),
            board.getCellState(0, 3),
            board.getCellState(3, 0),
            board.getCellState(3, 3),
        )

        if (fourCorners.size == 1 && fourCorners.first() != Board.CellState.EMPTY) {
            return fourCorners.first().toWinState()
        }

        // Check diagonals

        // Top left to bottom right
        val topLeftDiagonal = (0 until 4).map { board.getCellState(it, it) }.toSet()

        if (topLeftDiagonal.size == 1 && topLeftDiagonal.first() != Board.CellState.EMPTY) {
            return topLeftDiagonal.first().toWinState()
        }

        // Top right to bottom left
        val topRightDiagonal = (0 until 4).map { board.getCellState(it, 3 - it) }.toSet()

        if (topRightDiagonal.size == 1 && topRightDiagonal.first() != Board.CellState.EMPTY) {
            return topRightDiagonal.first().toWinState()
        }

        // 2x2 square
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                val squareState = setOf(
                    board.getCellState(row, col),
                    board.getCellState(row + 1, col),
                    board.getCellState(row, col + 1),
                    board.getCellState(row + 1, col + 1),
                )

                if (squareState.size == 1 && squareState.first() != Board.CellState.EMPTY) {
                    return squareState.first().toWinState()
                }
            }
        }

        // Horizontal
        for (row in 0 until 4) {
            val horizontalState = (0 until 4).map { board.getCellState(row, it) }.toSet()

            if (horizontalState.size == 1 && horizontalState.first() != Board.CellState.EMPTY) {
                return horizontalState.first().toWinState()
            }
        }

        // Vertical
        for (col in 0 until 4) {
            val verticalState = (0 until 4).map { board.getCellState(it, col) }.toSet()

            if (verticalState.size == 1 && verticalState.first() != Board.CellState.EMPTY) {
                return verticalState.first().toWinState()
            }
        }

        return Board.WinState.NONE
    }

    /**
     * Check if the given [board] has any moves left.
     * A move is available if any cell is EMPTY [Board.CellState.EMPTY] value.
     * @param board the [Board] to check if there are any moves left
     * @return true if there are moves left, otherwise false
     */
    fun anyMovesLeft(board: Board): Boolean {
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                if (board.getCellState(row, col) == Board.CellState.EMPTY) {
                    return true
                }
            }
        }

        return false
    }

    /**
     * Check if the given [board] game is over.
     * The game is over if there is a winner, or if there are no moves left.
     * @param board The current [Board] to check if the game is over
     * @return true if the game is over, otherwise false
     * @see [checkWinner] and [anyMovesLeft]
     */
    fun isGameOver(board: Board): Boolean {
        val winnerFound = checkWinner(board) != Board.WinState.NONE
        val noMovesLeft = !anyMovesLeft(board)

        return winnerFound || noMovesLeft
    }
}