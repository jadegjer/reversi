package player;

import java.util.Optional;

import controller.ReversiController;
import model.CellModel;
import model.Coordinate;
import model.ReversiModel;

/**
 * Represents a player than can play the game of Reversi.
 */
public interface Player {

  /**
   * Used for a player to play out their turn in the game of Reversi by selecting a cell.
   *
   * @param model the model of Reversi being used for the game.
   * @return the logical coordinate of the cell where the player intends to play a disc.
   */
  Optional<Coordinate> play(ReversiModel model);

  /**
   * Returns the disc color of the player, which is represented by CellStatus.
   *
   * @return the disc color of the player, which is represented by CellStatus
   */
  CellModel.CellStatus getDiscColor();

  /**
   * Notifies a player when it is their turn to play. Hence, the other player has completed their
   * turn so the upcoming player must be notified it is their turn.
   *
   * @param controller the controller associated with the player who needs to be notified.
   * @param turn       true if it is this player's their turn, false if it is not.
   */
  void notifyPlayer(ReversiController controller, boolean turn);
}
