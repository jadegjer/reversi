package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the Reversi game for testing purposes.
 * This class extends BasicReversi and overrides the playADisc method to record
 * the coordinates of the cells that would be played during the game.
 */
public class MockReversi extends BasicReversi {

  private final List<Coordinate> transcript;

  /**
   * Constructs a new MockReversi with the specified size.
   *
   * @param size The size of the Reversi game board.
   */
  public MockReversi(int size) {
    super(size);
    this.transcript = new ArrayList<Coordinate>();
  }

  /**
   * Adds to the transcript the cell being clicked.
   *
   * @param clickCell     the hexagonal cell the user clicked on for their intended turn.
   * @param playerCalling color of player calling
   * @param move          whether to move or not - irrelevant in this context
   * @return false - not relevant
   */
  @Override
  public boolean playADisc(Cell clickCell, CellModel.CellStatus playerCalling, boolean move) {
    this.transcript.add(clickCell.getCoordinate());
    return false;
  }


  /**
   * Get the transcript of cell coordinates.
   *
   * @return transcript of cell coordinates
   */
  public List<Coordinate> getTranscript() {
    return this.transcript;
  }


}
