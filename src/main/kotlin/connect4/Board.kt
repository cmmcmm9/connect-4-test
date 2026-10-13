package com.morrissey.connect4

/**
 * Represents the playable 4x4 board. The [cells] represent the board state.
 * @param cells The array [CellState]s representing the board. Default an empty board via [CellState.EMPTY]
 * @throws IllegalArgumentException if the [cells] given is not 4x4.
 */
class Board(
    private val cells: Array<Array<CellState>> = Array(4) { Array(4) { CellState.EMPTY } },
) {

    init {
        if (cells.size != 4) {
            throw IllegalArgumentException("The number of rows should be four. Given ${cells.size}.")
        }

        val invalidColumns = cells.filter { it.size != 4 }

        if (invalidColumns.isNotEmpty()) {
            throw IllegalArgumentException("The number of columns should be four. Given ${invalidColumns.size} columns with invalid sizes.")
        }
    }

    /**
     * Retrieve the [CellState] for the given [row] and [col]
     * @param row the row to retrieve the [CellState] for
     * @param col the column to retrieve the [CellState] for
     */
    fun getCellState(row: Int, col: Int): CellState {
        return cells[row][col]
    }

    /**
     * Return the current board size
     */
    fun getSize(): Int {
        return cells.size
    }

    /**
     * Enum representing the states of the cell in the board
     */
    enum class CellState {
        X, O, EMPTY;

        /**
         * Convert the [CellState] to a [WinState]
         */
        fun toWinState(): WinState {
            return when (this) {
                X -> WinState.X
                O -> WinState.O
                else -> throw IllegalArgumentException("Invalid state")
            }
        }
    }

    /**
     * Enum representing the win states
     */
    enum class WinState {
        X, O, NONE
    }
}
