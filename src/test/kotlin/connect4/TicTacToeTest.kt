package connect4

import com.morrissey.connect4.Board
import com.morrissey.connect4.TicTacToe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TicTacToeTest {
    private val ticTacToe = TicTacToe()

    private val fourCornersIndices = listOf(0 to 0, 0 to 3, 3 to 0, 3 to 3)

    @Test
    fun fourCorners_ShouldWin() {
        val cells = createBoardEmptyState()
        val board = Board(cells)

        // Test X should win
        fourCornersIndices.forEach {
            val (row, col) = it

            cells[row][col] = Board.CellState.X
        }

        var result = ticTacToe.checkWinner(board)
        var expected = Board.WinState.X

        assertEquals(expected, result, "Four corners X did not win")

        // Test O should win
        fourCornersIndices.forEach {
            val (row, col) = it

            cells[row][col] = Board.CellState.O
        }

        result = ticTacToe.checkWinner(board)
        expected = Board.WinState.O

        assertEquals(expected, result, "Four corners O did not win")
    }

    @Test
    fun fourCorners_ShouldNotWin() {
        val cells = createBoardEmptyState()
        val board = Board(cells)

        for (i in 0 until 3) {
            val cellState = Board.CellState.entries[i]

            fourCornersIndices.forEach {
                val (row, col) = it

                cells[row][col] = cellState
            }

            // toggle cell state with different value, ensure does not win
            for (k in 0 until 3) {
                val cornerToChange = fourCornersIndices[k]

                val differentCellState = Board.CellState.entries.first { it != cellState }

                cells[cornerToChange.first][cornerToChange.second] = differentCellState

                val expected = Board.WinState.NONE
                val actual = ticTacToe.checkWinner(board)

                assertEquals(expected, actual, "Four corners incorrectly assigned win state")
            }
        }
    }

    @Test
    fun diagonal_TopLeft_ShouldWin() {
        // Try both X and O for winnings
        listOf(Board.CellState.X, Board.CellState.O).forEach { cellState ->
            val cells = createBoardEmptyState()
            val board = Board(cells)

            // top left win state
            for (i in 0 until 4) {
                cells[i][i] = cellState
            }

            val expected = cellState.toWinState()
            val actual = ticTacToe.checkWinner(board)

            assertEquals(expected, actual, "Diagonal top left did not win correctly")
        }
    }

    @Test
    fun diagonal_TopRight_ShouldWin() {
        // Try both X and O for winnings
        listOf(Board.CellState.X, Board.CellState.O).forEach { cellState ->
            val cells = createBoardEmptyState()
            val board = Board(cells)

            // top right win state
            for (i in 0 until 4) {
                cells[i][3 - i] = cellState
            }

            val expected = cellState.toWinState()
            val actual = ticTacToe.checkWinner(board)

            assertEquals(expected, actual, "Diagonal top right did not win correctly")
        }
    }

    @Test
    fun horizontal_ShouldWin() {
        for (row in 0 until 4) {
            val cells = createBoardEmptyState()
            val board = Board(cells)

            // Try both X and O for winning
            listOf(Board.CellState.X, Board.CellState.O).forEach { cellState ->
                // fill row with current win state
                for (col in 0 until 4) {
                    cells[row][col] = cellState
                }

                val expected = cellState.toWinState()
                val actual = ticTacToe.checkWinner(board)

                assertEquals(expected, actual, "Horizontal win did not win correctly")
            }
        }
    }

    @Test
    fun vertical_ShouldWin() {
        for (col in 0 until 4) {
            val cells = createBoardEmptyState()
            val board = Board(cells)

            // Try both X and O for winnings
            listOf(Board.CellState.X, Board.CellState.O).forEach { cellState ->
                // fill col with current win state
                for (row in 0 until 4) {
                    cells[row][col] = cellState
                }

                val expected = cellState.toWinState()
                val actual = ticTacToe.checkWinner(board)

                assertEquals(expected, actual, "Vertical win did not win correctly")
            }
        }
    }

    @Test
    fun twoByTwo_ShouldWin() {
        listOf(Board.CellState.X, Board.CellState.O).forEach { cellState ->
            for (row in 0 until 3) {
                val cells = createBoardEmptyState()
                val board = Board(cells)

                for (col in 0 until 3) {
                    cells[row][col] = cellState
                    cells[row + 1][col] = cellState
                    cells[row][col + 1] = cellState
                    cells[row + 1][col + 1] = cellState
                }

                val expected = cellState.toWinState()
                val actual = ticTacToe.checkWinner(board)

                assertEquals(expected, actual, "Two by two did not win correctly")
            }
        }
    }

    @Test
    fun noWinner_ShouldReturnNone() {
        // Ensure a none win state board returns no winner
        val board = Board(createNoWinnerNotEmptyState())

        val expected = Board.WinState.NONE
        val actual = ticTacToe.checkWinner(board)

        assertEquals(expected, actual, "No winner board state did not return correctly")
    }

    @Test
    fun emptyBoard_ShouldHaveMovesLeft() {
        val board = Board()

        assertTrue(ticTacToe.anyMovesLeft(board), "Empty Board should have moves left")
    }

    @Test
    fun singleEmptySpace_ShouldHaveMovesLeft() {
        val cells = createBoardEmptyState()
        val board = Board(cells)

        for (row in 0 until 4) {
            for (col in 0 until 4) {
                cells[row][col] = Board.CellState.X
            }
        }

        // toggle first row
        cells[0][0] = Board.CellState.EMPTY

        assertTrue(ticTacToe.anyMovesLeft(board), "Single Empty Cell, Board should have moves left")
    }

    @Test
    fun notEmptyBoard_ShouldHaveMovesLeft() {
        val cells = createBoardEmptyState()
        val board = Board(cells)

        for (row in 0 until 4) {
            for (col in 0 until 4) {
                cells[row][col] = Board.CellState.X
            }
        }

        assertFalse(ticTacToe.anyMovesLeft(board), "Board should not have moves left")
    }

    @Test
    fun gameIsOver_WithWinner() {
        val cells = createBoardEmptyState()
        val board = Board(cells)

        // Make first row horizontal a win condition, but fill the rest with no wins. Should be game over
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                cells[row][col] = Board.CellState.X
            }
        }

        assertTrue(ticTacToe.isGameOver(board))
    }

    @Test
    fun gameIsOver_NoWinner_NoMovesLeft() {
        val cells = createNoWinnerNotEmptyState()
        val board = Board(cells)

        assertTrue(ticTacToe.isGameOver(board))
    }

    private fun createBoardEmptyState(): Array<Array<Board.CellState>> {
        return Array(4) { Array(4) { Board.CellState.EMPTY } }
    }

    private fun createNoWinnerNotEmptyState(): Array<Array<Board.CellState>> {
        return Array(4) { row ->
            Array(4) { col ->
                if ((row + col) % 2 == 0) {
                    Board.CellState.X
                } else {
                    Board.CellState.O
                }
            }
        }.apply {
            // Break diagonals
            this[0][0] = Board.CellState.O
            this[0][3] = Board.CellState.X
        }
    }
}