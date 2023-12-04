package strategy;

import java.util.List;
import java.util.Optional;

import model.CellModel;
import model.Coordinate;
import model.ReversiModel;

/**
 * A Reversi strategy that gives preference to the corners of the game board.
 * If a valid move is found that is in a corner, it is chosen. Otherwise, the
 * strategy falls back to a backup plan.
 */
public class PlayToCorner implements ReversiStrategy {

  private final ReversiStrategy backupPlan;

  public PlayToCorner(ReversiStrategy backupPlan) {
    this.backupPlan = backupPlan;
  }

  /**
   * Chooses a coordinate for the next move based on the PlayToCorner strategy.
   * If a valid move is found that is in a corner, it is chosen.
   * Otherwise, the strategy falls back to the backup plan.
   *
   * @param model  The ReversiModel representing the current state of the game.
   * @param player The player making the move.
   * @return An Optional containing the chosen coordinate if a move is made,
   *         or empty if no valid move is found.
   */
  @Override
  public Optional<Coordinate> chooseCoordinate(ReversiModel model, CellModel.CellStatus player) {
    List<Coordinate> corners = model.getCorners();
    for (Coordinate c : corners) {
      if (model.playADisc(model.getClickedCell(c), player, false)) {
        return Optional.of(c);
      }
    }
    return backupPlan.chooseCoordinate(model, player);
  }
}
