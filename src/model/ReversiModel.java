package model;

import java.util.List;

import controller.ReversiControllerImplementation;

/**
 * An interface representing a model of the game of Reversi.
 */
public interface ReversiModel extends ReadOnlyReversi {

  /**
   * Starts the game of Reversi by setting the initial game board pieces and making it a player's
   * turn.
   */
  void startGame();

  /**
   * Makes a player pass their turn and pass possession of the turn on to the other player.
   */
  void passTurn(CellModel.CellStatus playerCalling);

  /**
   * Allows a player to play the disc on the hexagonal cell they click if it is a valid move by
   * the rules of Reversi.
   *
   * @param clickCell the hexagonal cell the user clicked on for their intended turn.
   */
  boolean playADisc(Cell clickCell, CellModel.CellStatus playerCalling, boolean move);

  /**
   * Returns how many tiles were flipped in the last turn.
   *
   * @return numTilesFlipped
   */
  int getLastTurnTilesFlipped();

  /**
   * How many hexagons are on each side of the Reversi Board.
   *
   * @return the sideSize of the Reversi board
   */
  int getSideSize();

  /**
   * Retrieves the corner coordinates of a board.
   *
   * @return list of corner coordinates
   */
  List<Coordinate> getCorners();

  /**
   * Assigns the correct controller to the correct player based off of which player's turn it is.
   *
   * @param reversiControllerImplementation the controller associated with a player.
   * @param discColor                       the color disc associated with a player.
   */
  void listenForTurn(ReversiControllerImplementation reversiControllerImplementation,
                     CellModel.CellStatus discColor);
}
