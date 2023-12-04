package strategy;

import java.util.Optional;

import model.CellModel;
import model.Coordinate;
import model.ReversiModel;

/**
 * The ReversiStrategy interface represents a strategy for making moves in a Reversi game.
 * Implementing classes provide logic for choosing a coordinate on the game board based on the
 * current game state.
 */
public interface ReversiStrategy {

  /**
   * Chooses a coordinate on the Reversi game board based on the current game state.
   *
   * @param model  The ReversiModel representing the current state of the game.
   * @param player The player for whom a move is being chosen (BLACK or WHITE).
   * @return An Optional containing the chosen coordinate if a valid move is found,
   *         or empty otherwise.
   */
  Optional<Coordinate> chooseCoordinate(ReversiModel model, CellModel.CellStatus player);
}
