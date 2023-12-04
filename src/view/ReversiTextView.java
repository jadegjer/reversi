package view;

import java.util.List;

import model.Cell;
import model.ReadOnlyReversi;

/**
 * A simple text-based rendering of the Reversi game.
 */
public class ReversiTextView implements TextView {

  private final ReadOnlyReversi model;

  /**
   * Constructs the game of Reversi to be rendered as a string.
   *
   * @param model the Reversi model to be rendered as a string.
   */
  public ReversiTextView(ReadOnlyReversi model) {
    this.model = model;
  }

  @Override
  public String toString() {
    String boardRep = "";
    int incr = 1;
    int sideSize = (this.model.getBoard().size() + 1) / 2;
    for (List<Cell> row : this.model.getBoard()) {
      for (int spaces = 0; spaces < Math.abs(sideSize - incr); spaces++) {
        boardRep += " ";
      }
      for (Cell cell : row) {
        switch (cell.getCellStatus()) {
          case BLACK:
            boardRep += "X ";
            break;
          case WHITE:
            boardRep += "O ";
            break;
          case BLANK:
            boardRep += "_ ";
            break;
          default:
            throw new IllegalArgumentException("Invalid CellStatus!!");
        }
      }
      boardRep += "\n";
      incr++;
    }
    return boardRep;
  }
}