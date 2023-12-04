package player;

import java.util.Optional;

import model.CellModel;
import model.Coordinate;
import model.ReversiModel;
import strategy.CaptureMost;
import view.ReversiView;

/**
 * Represents a mock model of a HumanPlayer for testing purposes.
 */
public class MockHuman extends HumanPlayer {

  /**
   * A mock model of a human player.
   * @param discColor the disc color associated with the player.
   * @param view  the view to be utilized the controller when deciding which cell to play.
   */
  public MockHuman(CellModel.CellStatus discColor, ReversiView view) {
    super(discColor, view);
  }

  /**
   * Returns coordinates of best move with Capture Most strategy.
   *
   * @param model the model of Reversi being used for the game.
   * @return coordinates of best move with Capture Most strategy.
   */
  @Override
  public Optional<Coordinate> play(ReversiModel model) {

    return new CaptureMost().chooseCoordinate(model, this.getDiscColor());
  }
}
