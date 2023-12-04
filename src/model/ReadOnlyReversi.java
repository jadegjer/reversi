package model;

import java.util.List;

/**
 * Reversi interface that is unable to mutate the game.
 */
public interface ReadOnlyReversi {


  /**
   * Return the board being used for the game of Reversi.
   *
   * @return the board being used for the game of Reversi.
   */
  List<List<Cell>> getBoard();

  /**
   * Calculates the score of the game for the given player, which equates to how many hexagons
   * on the board are the player's color.
   *
   * @param playerColor the color of the player's hexagonal cells.
   * @return the score associated with the correct player.
   */
  int getScore(CellModel.CellStatus playerColor);

  /**
   * Determines if the game of Reversi is over or not. A game is over if there have been two passes
   * committed in a row, or if the board has no more blank hexagonal cells.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Determines which player won the game of Reversi, based on which player has the higher score.
   *
   * @return the color of the player's cells that won the game.
   */
  CellModel.CellStatus whoWon();

  /**
   * Determines which player is currently allowed to move.
   *
   * @return the player whose turn it is
   */
  CellModel.CellStatus whoseTurn();

  /**
   * Returns the cell clicked on by the user based off of the coordinate system.
   */
  Cell getClickedCell(Coordinate coordinate);

  /**
   * Checks if there are any valid moves left for the current player to make on the board.
   *
   * @return true if there is a valid move remaining, false if there are no valid moves remaining.
   */
  boolean anyValidMoves();

  /**
   * Returns the size for how long a singular side of the entire hexagon board is.
   *
   * @return an integer representing the size (in terms of hexagonal cells) of a singular side
   *         of the board.
   */
  int getSideSize();

  /**
   * Returns the reason as to why the game of Reversi has ended according to the game's rules.
   *
   * @return a String description of why the game ended
   */
  String getReasonEnded();
}
