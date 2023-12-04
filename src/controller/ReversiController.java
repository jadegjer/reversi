package controller;

import player.Player;

/**
 * Interface representing a controller for the game of Reversi.
 */
public interface ReversiController {

  /**
   * The primary method for beginning and playing a game of Reversi for each player.
   */
  void play();

  /**
   * Notifies a player when it is time for their turn, hence when the other player completes their
   * turn.
   */
  void notifyTurnBegin();

  /**
   * Notifies a player when their turn is over, hence when that player completes their turn.
   */
  void notifyTurnEnd();

  /**
   * Makes the intended move provided by the player.
   *
   * @param decision denoting whether to move or to pass.
   */
  void makeMove(String decision);

  /**
   * Initiates a move for an AI Player since the AI cannot push a key like a human can.
   */
  void initiateMovement();

  /**
   * Determines the winner of the game and directs the view to display the winner.
   */
  void findWinner();

  /**
   * Directs the view to show that is no longer the player's turn.
   *
   * @param player the player who has just completed their turn.
   */
  void unindicate(Player player);
}
