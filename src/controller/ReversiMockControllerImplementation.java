package controller;

import model.ReversiModel;
import player.Player;
import view.ReversiView;

/**
 * Represents a mock of a controller for a single player for the game of Reversi.
 */
public class ReversiMockControllerImplementation extends ReversiControllerImplementation {

  public String moveMade;
  public boolean turnBegun;
  public boolean turnEnded;


  /**
   * Initiates the controller into a state where it is ready to be used for Reversi.
   *
   * @param m      model representing the game state of Reversi.
   * @param view   the view associated for the desired player
   * @param player the player who is utilizing this controller (either white or black)
   */

  public ReversiMockControllerImplementation(ReversiModel m, ReversiView view, Player player) {
    super(m, view, player);
  }


  /**
   * Indicates turn has begun.
   */
  @Override
  public void notifyTurnBegin() {
    this.turnBegun = true;
  }

  /**
   * Indicates turn has ended.
   */
  @Override
  public void notifyTurnEnd() {
    this.turnEnded = true;
  }

  /**
   * Indicates a move has been made.
   */
  @Override
  public void makeMove(String decision) {
    this.moveMade = decision;
  }

}
