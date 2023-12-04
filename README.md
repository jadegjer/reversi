# REVERSI README

# Overview: 
The codebase is intended to eventually complete a two-player playable game of Reversi. Regarding background knowledge,
it can be assumed that those working with the codebase have an understanding of the MVC model and how the design structure
caters to eventually implementing a graphical user interface. The codebase is defined for easy extensibility due to the use
of interfaces that will allow for different Reversi models, views, controllers, as well as the ability to add different types
of players, such as AI. The prerequisites necessary for this codebase are simply the correct Java version needed for the game
as well as knowledge about the rules of Reversi.

# Quick Start:
A user would get started using this codebase by first creating their model of BasicReversi with their intended size of the game board.
The size input represents the size of a single side of the hexagonal board in the game of Reversi. For example, to create a board that has a side size of 5 hexagons, it would be created as follows:

**Basic Reversi reversiModel = new BasicReversi(5);**

The model has now been created with a game board that is now ready for the game to begin with the two players involved.
Upon calling startGame(), the board will be initialized into a state where each of the player’s pieces is on the board
while giving the turn to the player with the white discs. Moves such as playing a disc and passing will become valid upon calling this method:

**reversiModel.startGame();**

To view the simple text rendering of the game using the text view, the user should then complete these two lines of code.

**ReversiTextView view = new ReversiTextView(reversiModel);**
**System.out.println(view.toString**

# Key Components:
The model represents the logic and enforces the rules behind the game of Reversi. The model contains information 
about whether hexagonal cells on the game board are occupied by players or left blank, determines valid moves, when
the game ends, and who wins. Therefore, the model is “driven” because it simply contains the information and logic
behind the game of Reversi, but cannot enforce actions, as it instead responds to the controller’s instructions. 
The key subcomponents of the model are the BasicReversi class and the CellModel class. BasicReversi contains the 
main logic and valid rules behind a regular game of Reversi. This class exists to enforce the rules of Reversi and 
to update the game board based on instructions from the controller. CellModel represents each hexagonal cell on the 
game board and contains the data regarding whether a cell is occupied by a player or left blank. Hence, this exists 
for updating an individual cell when the game is being played.

Currently, the view renders the game board of Reversi in a simple textual view, rather than the GUI visual view that 
will be implemented in the future. The view is “driven” as it simply renders the view of the game board based on the 
Reversi model’s information being updated as it listens to directions from the controller. Regarding this simple 
implementation of view, the key subcomponent is the ReversiTextView class. The view shows player discs using Xs and Os,
while using - to represent unoccupied hexagonal cells. This exists to offer a simple text rendering of the game of Reversi 
before a GUI is implemented. 

The controller is considered the component that “drives” the control-flow of the system. The controller will interpret the
user’s inputs and give directions to the model, which will then pass the following information regarding what moves were 
made onto the view. Since the controller has not been implemented yet, there currently is no key subcomponent, but upon 
future implementation, the class ReversiTextualController and the interface ReversiCommand will be integral parts.
ReversiTextualController will handle the user inputs and direct the instructions to the correct methods within BasicReversi. 
ReversiCommand handles all commands that can be executed within a game of Reversi, where each command will evenutally receive
all their own classes for later implementation.

The last key component revolves around the player. The player represents the two players partaking in the game of Reversi.
The player is driven as it responds to the model determining which player’s turn it is. A key subcomponent of the player is
the Player interface. The interface is integral to the game as it allows for the creation of both AI and human players within the codebase. 

# Source Organization:
The controller, mode, and view can each be found in their respective packages of the same name within the codebase. The player
interface with its respective AI and Human classes fall outside of the MVC packages. These all lie within the src folder of
Reversi, while all tests completed for the Reversi game fall under the test folder. 

# Class Invariant:
This version of Reversi enforces the following class invariant: the playerTurn field within the BasicReversi class of the model
package must always be either the WHITE or BLACK enumeration values, while never being the BLANK enumeration value.

# Coordinate System Used For Hexagonal Board
Since this game of Reversi employs a hexagonal board, simple x and y coordinates would not have worked like it would for a rectangular
board. Hence, this board enforces the **cube coordinates sytem**. Each hexagonal cell within the board each has q, r, and s coordinates.
These coordinates when added together will always equate to 0. 

# How Scoring Works
A player's score is determined by the amount of tiles/cells that are occupied by their color. Hence, if player 1 has 6 black tiles on the
board, while player 2 has 4 white tiles, then player 1 would be winning in the moment as they have the higher score. 

# Changes for Part 2
- **Checking to see if the current player has any legal moves** <br>
    A new method has been added to the model interface that checks if the current player can legally make any moves given the state of the
    game board. This method proves useful as if the player has no valid moves, then they must pass their turn. 
- **The contents of the cell at a given coordinate** <br>
    This method returns the cell that is currently clicked based off of the cube coordinate system. Upon returning the cell that is being 
    clicked, then the contents of its coordinates, CellStatus (black, white, or blank), and the hexagonal cells on the same plane will all
    be accessible. 
- **Hexagon class becoming HexButton class** <br>
    Instead of having a separate class for hexagons and another to turn those hexagons into buttons, it was decided to combine the two. 
    Since every hexagon will be a button within the game board, it made the most sense to design every hexagon as a button. 

# Player within Reversi
  A BasicPlayer in the game of Reversi will have a disc color as well as a strategy. Hence, a BasicPlayer will make their move on their turns
  based off of the chosen strategy. The controller will handle interacting with the actual model, but the controller will take the coordinates
  of the tile of which the player chooses to make their move from the BasicPlayer class. 

# Keyboard Interactions
The view allows for players to indicate their intentions of their turn with the following two commands: <br>
- **'P'/'p' to pass** <br>
    If the user wishes to pass, then they should type the letter 'P'/'p' on their keyboard. Currently without implementation of the controller,
    the only action of this is the console printing out that the "Player has passed :(".
- **'M'/'m' to move** <br>
    If the user wants to confirm that they aim to attempt a move, then they should type the letter 'M'/m on their keyboard. Currently without
    implementation of the controller, the only action of this is the console printing out that the "PLAYER ATTEMPTED A MOVE!! :)".

# New Classes for Part 2
- **ReversiStrategy Interface** <br>
  The interface represents any strategy that can be used for the game of Reversi. The only method within the interface returns Optional<Coordinate>.
  Hence, either the coordinate of the cell that should be played based on the strategy will be returned, or Optional.empty() if there is no cell
  that corresponds with the strategy.
  - **CaptureMost** <br>
      This staregy focuses on capturing the most possible tiles/cells within a single move. Hence, the only information the method worries about
      is deciding which cell will result in the most tiles being flipped. 
  - **PlayToCorner** <br>
      Capturing a corner cell in Reversi proves to be a big advantage as this cell can never be captured and often acts as an important spot
      to capture more pieces. Hence, this strategy solely focuses on capturing a corner cell spot whenever a move for one of these spots is 
      valid and available. 
  - **AvoidCornerAdjacent** <br>
      As explained above, capturing a corner is a very advisable move, hence a string strategy is to avoid capturing the tiles next to the corner
      spots. Therefore, the other player will not be able to capture the corner spot if the tiles next to the corner can be avoided. Hence, this
      strategy only focuses on avoiding the tiles next to the corner tiles throughout the board.
  - **PromptUser** <br>
      This strategy asks the user where they want to play, rather than using any specific strategy. The method determines if a button is currently
      clicked on the view and if it is, then that is the cell the user has selected. Then the method determines if it is a valid move, where if it is 
      then its coordinates will be returned, but if not, then an IllegalStateException is thrown for an invalid move. 
- **JReversiPanel** <br>
      A JPanel that represents the hexagonal board representation of the game of Reversi. This class has the capability to handle user input (clicking,
      pressing keys), display the game board, and to interact with the ReadOnlyReversi model. 
- **HexButton** <br>
      Each hexagon on the game board is a button as mentioned above, hence this class in the view package paints each hexagon as a button. Hence, the 
      JReversiPanel class can much more easily add all the hexagons to the game board already as buttons, rather than having to add button functionality
      separately.
- **SimpleReversiView** <br>
      The purpose of this class is to construct the view so it is in a place where it is prepared to be displayed. Upon setting the requirements for the
      panel and frame, the view will be in a state where it is ready to be displayed where the method **display(boolean show)** within the class can be
      called.
      
# How to Correctly Utilize Arguments for Command Line
To create a successful game of Reversi, the command line needs **exactly** 2 arguments. Each of these arguments represents the type of player that will be partaking in the game. There are 4 possible options for the type of player to be added to the game, 1 being a human player and 3 being AI players. To add the type of player into the game, enter the title provided to each description into the command line.
“human”: to add a physical user into the game where the user controls the moves.
- “captureMost”:  The first type of AI Player utilizes the strategy of capturing the most possible tiles on each turn.
- “avoidCorner”:  Another type of AI Player that utilizes the strategy of avoiding the tile next to any corner on the board. Since it is seen as advantageous to capture the corner tile, this AI will avoid at all costs the chance to let the opposing player capture the corner. If it is not possible for the AI to make a valid move that avoids the corner, it will default to the “captureMost” strategy.
- “playToCorner”:  The final type of AI Player utilizes the strategy of capturing the corner tile whenever it’s possible. If there is no corner to be captured as a valid move on a turn, this strategy will default to the “captureMost” strategy.

# Updates for Part 3
- **BasicPlayer transformed into AIPlayer and HumanPlayer** <br>
The previously implemented BasicPlayer class has been split into two separate classes: AIPlayer and HumanPlayer. Since AI and humans have very different methods of selecting a move, it made much more sense to separate the two into different classes. 
- **Removal of PromptUser strategy** <br>
With the introduction of HumanPlayer, there no longer became a necessity for PromptUser since the human user selects which cell they would like to play their disc to. Hence, the HumanPlayer class essentially replaced the functionality provided by PromptUser
- **Display Messages on GUI** <br>
     - **Winning Message** <br>
Once the game is declared over, a winning message is displayed on the screen declaring which player won, the winning score, and the losing score. 
     - **Whose Turn it is** <br>
Whether playing against an AI or a human opponent, it can be determined whose turn it is by looking at the tab of the window where it states whose turn it is. 
     - **Error Messages** <br>
If the user does anything other than pass or select a valid cell to play a disc, they will receive an urgent message letting them know the move they are doing is invalid and for them to try again. This can include trying to play in an occupied cell, forgetting to select a cell, or choosing a cell that is not a valid move. 

# New Classes for Part 3
- **ReversiFeature Interface** <br>
This interface represents all possible commands that can be executed in the game of Reversi: pass and move. There is one method within the interface that serves the purpose of executing the desired command. 
- **Move** <br>
This class handles when a player decides to make a move on the board. If it is a valid move, the move will be executed, but if not then an error message will appear on the player’s view.
- **Pass** <br>
This class handles when a player has decided not to make a move and rather passes their turn. The pass command is executed by the singular method within the class.
- **ReversiController Interface** <br>
Represents a controller that can be utilized by one player for a basic game of Reversi. Each player will have their own controller for the game of Reversi. Hence, since Reversi is a two-player game, there will be two controllers. 
- **ReversiControllerImplementation** <br>
The implementation of the ReversiController interface, which creates a controller which is ready for use by a player for Reversi. The controller gives instructions to the model which will alter the state of the game board.
Hence, the controller is the true driver of the program as it is in charge of the "control-flow". Once the controller can give instructions to the model, the model passes on this information to the view so it can be displayed.
