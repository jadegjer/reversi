package controller;

import model.Cell;
import model.CellModel;
import model.ReversiModel;

/**
 * Represents the 'Move' command utilized by a player to confirm where they would
 * like to play their disc.
 */
public class Move implements ReversiFeature {

  private final CellModel.CellStatus playerCalling;
  private final Cell clickedCell;

  /**
   * Constructs the 'Move' command by determining the player calling the move as well as where they
   * would like to move.
   *
   * @param playerCalling the disc color associated with the player calling the move
   * @param clickedCell   the cell on the game board that is currently selected by the user
   */
  public Move(CellModel.CellStatus playerCalling, Cell clickedCell) {
    this.playerCalling = playerCalling;
    this.clickedCell = clickedCell;
  }

  @Override
  public void goNow(ReversiModel model) {
    model.playADisc(this.clickedCell, this.playerCalling, true);
  }
}
