package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A model to represent a singular hexagonal cell on the game board of Reversi.
 */
public class CellModel implements Cell {

  private final Coordinate coordinate;
  private CellStatus cellStatus;
  private List<List<Cell>> samePlaneCells;

  /**
   * Constructs a valid cell model to place on the Reversi game board.
   *
   * @param q          the q coordinate of the cell based on the cube coordinate technique.
   * @param r          the r coordinate of the cell based on the cube coordinate technique.
   * @param cellStatus the status of the cell, whether it is blank or a player color.
   */
  public CellModel(int q, int r, CellStatus cellStatus) {
    this.coordinate = new Coordinate(q, r);
    this.cellStatus = cellStatus;
    this.samePlaneCells = new ArrayList<List<Cell>>();
  }

  @Override
  public void flipToBlack() {
    if (this.cellStatus != CellStatus.BLACK) {
      this.cellStatus = CellStatus.BLACK;
    }
  }

  @Override
  public void flipToWhite() {
    if (this.cellStatus != CellStatus.WHITE) {
      this.cellStatus = CellStatus.WHITE;
    }
  }

  @Override
  public CellStatus getCellStatus() {
    return this.cellStatus;
  }

  @Override
  public void addSamePlaneCells(List<List<Cell>> board) {
    List<Cell> sameQ = new ArrayList<Cell>();
    List<Cell> sameR = new ArrayList<Cell>();
    List<Cell> sameS = new ArrayList<Cell>();
    for (List<Cell> row : board) {
      for (Cell cell : row) {
        if (this.coordinate.q == cell.getCoordinate().q) {
          sameQ.add(cell);
        }
        if (this.coordinate.r == cell.getCoordinate().r) {
          sameR.add(cell);
        }
        if (this.coordinate.s == cell.getCoordinate().s) {
          sameS.add(cell);
        }
      }
    }
    this.samePlaneCells = new ArrayList<List<Cell>>(Arrays.asList(sameQ, sameR, sameS));
  }

  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  @Override
  public List<List<Cell>> getSamePlaneCells() {
    return samePlaneCells;
  }

  /**
   * Represents the status of the cell, whether it is blank or a player color.
   */
  public enum CellStatus { BLANK, WHITE, BLACK }

}
