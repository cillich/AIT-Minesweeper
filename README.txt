README

Minesweeper Requirements:
- Game area is 5x5 using lines to split up the board
- 3 mines are enough
- Toggle button to determine what happens when you click on the board
	- Place a flag
	- Try a field
- If you click on a field in the “Try a field” mode
	- If you hit a mine, the game ends
	- If you do not hit a mine, a number appears that represents
	  the number of mines nearby
- If you click on a field in the “Place a flag” mode
	- If you flagged a field where there is no mine, the game ends
	- If you flagged a field where there is a mine and there are no
	  more unflagged mines left, the game ends and the player wins
- Display messages to the user using SnackBars

😬