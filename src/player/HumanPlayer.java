package player;

import java.util.Optional;

import controller.ReversiController;
import model.CellModel;
import model.Coordinate;
import model.ReversiModel;
import view.JReversiPanel;
import view.ReversiView;

/**
 * Represents a Human user playing the game of Reversi.
 */
public class HumanPlayer implements Player {
  private final CellModel.CellStatus discColor;
  private final ReversiView view;
  private final JReversiPanel panel;

  /**
   * Constructs a Human player capable of partaking in the game of Reversi.
   *
   * @param discColor the disc color associated with the player.
   * @param view  the view to be utilized the controller when deciding which cell to play.
   */
  public HumanPlayer(CellModel.CellStatus discColor, ReversiView view) {
    this.view = view;
    this.panel = view.getPanel();
    this.discColor = discColor;
  }

  @Override
  public Optional<Coordinate> play(ReversiModel model) {

    if (!this.panel.isAButtonSelected()) {
      throw new IllegalArgumentException("No Cell Selected!");
    }

    if (model.playADisc(
            panel.getButtonSelected().getCorrespondingCell(), this.discColor, false)) {
      return Optional.of(this.panel.getButtonSelected().getCoordinate());
    }

    throw new IllegalStateException("Invalid Move!");
  }


  @Override
  public CellModel.CellStatus getDiscColor() {
    return this.discColor;
  }

  @Override
  public void notifyPlayer(ReversiController controller, boolean turn) {
    if (turn) {
      this.view.indicateTurn(this.getDiscColor());
    } else {
      this.view.unIndicateTurn(this.getDiscColor());
    }
  }
}
