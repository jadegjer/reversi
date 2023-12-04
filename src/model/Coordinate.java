package model;


/**
 * Represents a coordinate in a hexagonal grid using axial coordinates (q, r).
 * The s coordinate is derived from q and r and follows the constraint s = -q - r.
 */
public class Coordinate {
  public final int q;
  public final int r;
  public final int s;

  /**
   * Represents the coordinate of a cell. The s coordinate is derived
   * from the q and r.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   */
  public Coordinate(int q, int r) {
    this.q = q;
    this.r = r;
    this.s = (-q) - r;
  }


}