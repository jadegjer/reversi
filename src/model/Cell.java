package model;

import java.util.List;

/**
 * Interface to represent a cell within the game board of Reversi.
 */
public interface Cell {

  /**
   * Flips this cell's status to black.
   */
  void flipToBlack();

  /**
   * Flips this cell's status to white.
   */
  void flipToWhite();

  /**
   * Get the current status of a cell.
   *
   * @return Blank, white or black.
   */
  CellModel.CellStatus getCellStatus();

  /**
   * Adds all the hexagonal cells on the same planes as this cell.
   *
   * @param board the game board used for the game of Reversi.
   */
  void addSamePlaneCells(List<List<Cell>> board);

  /**
   * Returns all the hexagonal cells on the same planes as this cell.
   *
   * @return all the hexagonal cells on the same planes as this cell.
   */
  List<List<Cell>> getSamePlaneCells();

  /**
   * Returns the coordinates of the cell.
   *
   * @return the coordinates of the cell.
   */
  Coordinate getCoordinate();
}
