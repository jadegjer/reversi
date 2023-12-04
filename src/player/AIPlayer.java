package player;

import java.util.Optional;

import controller.ReversiController;
import model.CellModel;
import model.Coordinate;
import model.ReversiModel;
import strategy.ReversiStrategy;

/**
 * Represents an AI player playing the game of Reversi.
 */
public class AIPlayer implements Player {
  private final CellModel.CellStatus discColor;
  private final ReversiStrategy strategy;

  /**
   * Constructs an AI player capable of partaking in the game of Reversi.
   *
   * @param discColor the disc color associated with the player.
   * @param strategy  the strategy to be utilized the player when making decisions of where to play.
   */
  public AIPlayer(CellModel.CellStatus discColor, ReversiStrategy strategy) {
    this.discColor = discColor;
    this.strategy = strategy;
  }

  @Override
  public Optional<Coordinate> play(ReversiModel model) {
    return this.strategy.chooseCoordinate(model, this.discColor);
  }

  @Override
  public CellModel.CellStatus getDiscColor() {
    return this.discColor;
  }

  @Override
  public void notifyPlayer(ReversiController controller, boolean turn) {

    if (turn) {
      controller.initiateMovement();
      controller.unindicate(this);
    }

  }
}
