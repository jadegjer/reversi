package controller;

import model.ReversiModel;

/**
 * Interface representing all the commands that can be executed
 * within a game of Reversi.
 */
public interface ReversiFeature {

  /**
   * Executes the action command requested by the player (i.e. 'Move' or 'Pass').
   *
   * @param model The Reversi model representing the game state.
   */
  void goNow(ReversiModel model);
}
