package controller;

import model.CellModel;
import model.ReversiModel;

/**
 * Represents the 'Pass' command utilized by a player to confirm they would like to pass.
 */
public class Pass implements ReversiFeature {

  private final CellModel.CellStatus playerCalling;

  /**
   * Constructs the 'Pass' command by determining which player is calling the pass.
   *
   * @param playerCalling the disc color associated with the player calling the pass
   */
  public Pass(CellModel.CellStatus playerCalling) {
    this.playerCalling = playerCalling;
  }

  @Override
  public void goNow(ReversiModel model) {
    model.passTurn(this.playerCalling);
  }
}
