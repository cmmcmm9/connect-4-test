# Tic-tac-toe / Connect 4 test

## Problem:
Implement a solver for a custom 4×4 board game. The program accepts a board state and determines whether a player has won. 
A win occurs when any of the following patterns are fully occupied by the same player:

* Vertical
* Horizontal
* Diagonal
* All Four Corner
* 2x2 box

## Approach
The solver uses a brute force pattern matching strategy, which is efficient given the small, fixed solution set. 
Each pattern is checked against the board until a match is found.

The board is represented by the [Board](src/main/kotlin/connect4/Board.kt) class, which provides convenience methods for 
retrieving cell states. Cell values are represented using the Board.CellState enum (X, O, EMPTY). 
Win results are represented using the Board.WinState enum.

To determine whether the game is over, the solver:

* Checks all winning patterns.
* If no winner is found, checks whether any moves remain (CellState.EMPTY). 
* If no moves remain, the game ends in a tie.

## Test
JUnit 5 is used to validate:

* Four corner win condition (including boundary cases)
* Horizontal and vertical wins 
* Diagonal wins 
* 2×2 box wins 
* Game‑over detection (winner or no moves left)
* Detection of remaining moves 
* No winner board states

## Notes
If time permitted, I would include more tests for edge cases such as:
* 3 in a row does not pass for vertical or horizontal
* 3 in a row does not pass for diagonal
* A 2x1 or 1x2 box does not pass
* Add a simple test case for the Board class

