package strategy;

import java.util.List;
import java.util.Optional;

import model.Cell;
import model.CellModel;
import model.Coordinate;
import model.ReversiModel;

/**
 * A Reversi strategy that avoids choosing cells adjacent to the corners of the game board.
 * If a valid move is found that is not adjacent to a corner, it is chosen. Otherwise, the
 * strategy falls back to a backup plan.
 */
public class AvoidCornerAdjacent implements ReversiStrategy {

  private final ReversiStrategy backupPlan;

  /**
   * Constructs an AvoidCornerAdjacent strategy with the specified backup plan.
   *
   * @param backupPlan The backup strategy to use when no suitable move is found
   */
  public AvoidCornerAdjacent(ReversiStrategy backupPlan) {
    this.backupPlan = backupPlan;
  }

  /**
   * Chooses a coordinate for the next move based on the AvoidCornerAdjacent strategy.
   * If a valid move is found that is not adjacent to a corner, it is chosen.
   * Otherwise, the strategy falls back to the backup plan.
   *
   * @param model  The ReversiModel representing the current state of the game.
   * @param player The player making the move.
   * @return An Optional containing the chosen coordinate if a move is made,
   *         or empty if no valid move is found.
   */
  @Override
  public Optional<Coordinate> chooseCoordinate(ReversiModel model, CellModel.CellStatus player) {
    for (List<Cell> row : model.getBoard()) {
      for (Cell cell : row) {

        if (!(isAdjacentToCorner(cell.getCoordinate(), model))) {
          if (model.playADisc(cell, player, false)) {

            return Optional.of(cell.getCoordinate());
          }
        }
      }
    }
    return backupPlan.chooseCoordinate(model, player);
  }

  /**
   * Checks if a given coordinate is adjacent to any of the corners on the game board.
   *
   * @param move  the coordinate to check
   * @param model the ReversiModel representing the current state of the game
   * @return true if the coordinate is adjacent to any corner, false otherwise
   */
  private boolean isAdjacentToCorner(Coordinate move, ReversiModel model) {
    for (Coordinate corner : model.getCorners()) {
      if (isNeighbor(move, corner)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if two coordinates are neighbors in the hexagonal grid.
   *
   * @param a the first coordinate
   * @param b the second coordinate
   * @return true if the coordinates are neighbors, false otherwise
   */
  private boolean isNeighbor(Coordinate a, Coordinate b) {

    int diffQ = Math.abs(a.q - b.q);
    int diffR = Math.abs(a.r - b.r);
    int diffS = Math.abs(a.s - b.s);

    return (diffQ + diffR + diffS == 2);
  }


}


