Zen Game 1.0 - Garcia Christophe

The project:
* It consists in the virtualization of the Zen game The Initiate on computer. 

* It can be played in HH (Human-Human), HA (Human-Auto), and AA (Auto-Auto) modes.

* 3 languages are available so that more people can understand the game:
English, French and Spanish.

* Backup files are located in the "data" folder at the same location than the "src" folder.
They are of the form "configX.txt" where X represents a number (e.g. 4th part is saved, 
file: data/config4.txt). If the player decides to resume a previously saved game, then 
this one will be saved with the same filename it had.

* The command to launch the execution of the project is the following: 
	- ant run (location: ZenGame folder).

* When the player has to enter the coordinates of the pawn he wants
move, he can write "history" which will display the history of the moves players have
made during the game, "files" which will offer additional options, or "rules" which will
rewrite the rules of the game.

Its limits:
The game in its first version cannot yet be played on a graphical version.
Apart from the welcome window and the options window, the graphical interfaces
have not yet been realized. We will add these last interfaces so that the game is 
graphically playable in the second version (coming soon).