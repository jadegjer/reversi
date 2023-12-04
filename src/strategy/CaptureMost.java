package strategy;

import java.util.List;
import java.util.Optional;

import model.Cell;
import model.CellModel;
import model.Coordinate;
import model.ReversiModel;

/**
 * A Reversi strategy that chooses the move resulting in the capture of the most opponent tiles.
 * It evaluates each possible move and selects the one with the highest number of captured tiles.
 */
public class CaptureMost implements ReversiStrategy {


  /**
   * Chooses a coordinate for the next move based on the CaptureMost strategy.
   * It evaluates each possible move and selects the one with the highest number of captured tiles.
   *
   * @param model  reversiModel representing the current state of the game
   * @param player player making the move
   * @return An Optional containing the chosen coordinate if a move is made,
   *         or empty if no valid move is found.
   */
  @Override
  public Optional<Coordinate> chooseCoordinate(ReversiModel model, CellModel.CellStatus player) {
    Cell bestCell = null;
    int mostFlipped = 0;
    for (List<Cell> row : model.getBoard()) {
      for (Cell cell : row) {
        if (model.playADisc(cell, player, false)) {
          if (mostFlipped < model.getLastTurnTilesFlipped()) {
            bestCell = cell;
            mostFlipped = model.getLastTurnTilesFlipped();
          }
        }
      }
    }
    if (bestCell != null) {
      return Optional.of(bestCell.getCoordinate());
    }
    return Optional.empty();
  }
}