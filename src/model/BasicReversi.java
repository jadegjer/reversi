package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.ReversiController;
import controller.ReversiControllerImplementation;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.WHITE;

/**
 * The model representing a basic game of Reversi with a hexagonal board.
 * The model contains information about whether hexagonal cells on the game board are occupied by
 * players or left blank, determines valid moves, when the game ends, and who wins. The rules of
 * Reversi are enforced within this class. The game board is updated by receiving instructions from
 * the controller.
 */
public class BasicReversi implements ReversiModel, ReadOnlyReversi {

  private final int sideSize;
  private final int length;
  private final List<List<Cell>> board;
  private boolean gameOn;
  private CellModel.CellStatus playerTurn;
  private int passesInARow;
  private int numTilesFlipped;
  private ReversiController blackController;
  private ReversiController whiteController;
  private String reasonOver;


  /**
   * Constructs the model for a basic game of Reversi and initializes the game into a state where
   * it is ready to start.
   *
   * @param sideSize the input size for how long a singular side of the entire hexagon board is,
   *                 such that the side is __ hexagons long.
   */
  public BasicReversi(int sideSize) {
    if (sideSize < 2) {
      throw new IllegalArgumentException("Size of board inadequate");
    }
    this.gameOn = false;
    this.sideSize = sideSize;
    this.length = (this.sideSize * 2) - 1;
    this.board = this.makeBoard();
    this.playerTurn = WHITE;
  }

  /**
   * Starts the game of Reversi by setting the initial game board pieces and making it a player's
   * turn.
   */
  public void startGame() {
    if (this.gameOn) {
      throw new IllegalStateException("Game already underway!! :)");
    }
    this.gameOn = true;
    for (List<Cell> row : this.board) {
      for (Cell cell : row) {
        this.initBoardColors(cell);
        cell.addSamePlaneCells(this.board);
      }
    }
    this.passesInARow = 0;

  }

  /**
   * Initializes the starting board to have the correct starting pieces for each team in the correct
   * locations.
   *
   * @param cell represents a hexagon cell within the entire hexagonal board.
   */
  private void initBoardColors(Cell cell) {
    int cellQ = cell.getCoordinate().q;
    int cellR = cell.getCoordinate().r;
    int cellS = cell.getCoordinate().s;

    if ((cellQ == 0 && cellR == -1 && cellS == 1)
            || (cellQ == 1 && cellR == 0 && cellS == -1)
            || (cellQ == -1 && cellR == 1 && cellS == 0)) {
      cell.flipToBlack();
    }

    if ((cellQ == 1 && cellR == -1 && cellS == 0)
            || (cellQ == 0 && cellR == 1 && cellS == -1)
            || (cellQ == -1 && cellR == 0 && cellS == 1)) {
      cell.flipToWhite();
    }
  }

  /**
   * Makes a blank hexagonal board with the correct dimensions.
   *
   * @return the blank hexagonal board needed for a game of Reversi.
   */
  private List<List<Cell>> makeBoard() {
    List<List<Cell>> gameBoard = new ArrayList<List<Cell>>();
    int r = this.sideSize - this.length;
    int qInit = 0;
    int width = sideSize;
    for (int curLen = 0; curLen < this.length; curLen++) {
      List<Cell> row = new ArrayList<Cell>();
      for (int curWid = 0; curWid < width; curWid++) {
        row.add(new CellModel(qInit + curWid, r, CellModel.CellStatus.BLANK));
      }
      gameBoard.add(row);
      if (r < 0) {
        qInit--;
        width++;
      } else {
        width--;
      }
      r++;
    }
    return gameBoard;
  }

  /**
   * Makes a player pass their turn and pass possession of the turn on to the other player.
   */
  public void passTurn(CellModel.CellStatus playerCalling) {
    this.isPlayersTurn(playerCalling);
    this.isGameOn();
    this.passesInARow++;
    this.switchPlayer();


  }

  /**
   * Switches the turn from the current player to the other player.
   */
  protected void switchPlayer() {
    this.isGameOn();
    if (this.playerTurn.equals(BLACK)) {
      this.playerTurn = WHITE;
      this.whiteController.notifyTurnBegin();
      this.blackController.notifyTurnEnd();
    } else if (this.playerTurn.equals(WHITE)) {
      this.playerTurn = BLACK;
      this.blackController.notifyTurnBegin();
      this.whiteController.notifyTurnEnd();
    } else {
      throw new IllegalStateException("Not a valid player to switch from...");
    }
  }

  /**
   * Return the board being used for the game of Reversi.
   *
   * @return the board being used for the game of Reversi.
   */
  public List<List<Cell>> getBoard() {
    return new ArrayList<List<Cell>>(this.board);
  }

  /**
   * Allows a player to play the disc on the hexagonal cell they click if it is a valid move by
   * the rules of Reversi.
   *
   * @param clickCell the hexagonal cell the user clicked on for their intended turn.
   */
  public boolean playADisc(Cell clickCell, CellModel.CellStatus playerCalling, boolean move) {
    this.numTilesFlipped = 0;

    if (move) {
      this.isPlayersTurn(playerCalling);
    }
    this.isGameOn();
    if (clickCell.getCellStatus() != CellModel.CellStatus.BLANK) {
      if (move) {
        throw new IllegalStateException("Cell already full");
      } else {
        return false;
      }
    }

    boolean moveValid = false;
    for (List<Cell> cellsOnPlane : clickCell.getSamePlaneCells()) {
      if (this.flipRow(clickCell, cellsOnPlane, move)) {
        moveValid = true;
      }
    }


    if (!moveValid) {
      if (move) {
        throw new IllegalStateException("Invalid move!!! :(((((((((");
      } else {
        return false;
      }
    }

    if (move) {
      if (this.playerTurn == BLACK) {
        clickCell.flipToBlack();
      } else {
        clickCell.flipToWhite();
      }
      this.switchPlayer();
      this.passesInARow = 0;
    }
    return true;
  }

  /**
   * Flips all the cells between the clicked on cell and the player-owned cell if it is valid.
   *
   * @param clickCell    the hexagonal cell the user clicked on for their intended turn.
   * @param cellsOnPlane the cells on the same plane as the clicked cell. i.e. on the q plane,
   *                     r plane, and s plane.
   * @param move         when true, move the discs, when false, just return if flip row is valid
   * @return whether the move is valid, which will result in the cells being flipped or not based
   *         on the result.
   */
  private boolean flipRow(Cell clickCell, List<Cell> cellsOnPlane, boolean move) {
    this.isGameOn();
    boolean containsValidMove = false;
    int firstCell;
    int lastCell;
    for (Cell c : cellsOnPlane) {
      // if the cells are the same color
      if (c.getCellStatus() == this.playerTurn) {
        // if it is not the given cell or one right next to it
        if (Math.abs(cellsOnPlane.indexOf(clickCell) - cellsOnPlane.indexOf(c)) > 1) {
          // if the clicked cell comes first, it is the first cell. Vice versa
          if (cellsOnPlane.indexOf(clickCell) < cellsOnPlane.indexOf(c)) {
            firstCell = cellsOnPlane.indexOf(clickCell) + 1;
            lastCell = cellsOnPlane.indexOf(c) - 1;
          } else {
            firstCell = cellsOnPlane.indexOf(c) + 1;
            lastCell = cellsOnPlane.indexOf(clickCell) - 1;
          }
          // determine if a run is valid. run is valid if every cell between the clicked cell
          // and the cell in question are the opposite color as the current player.
          boolean validRun = true;
          for (int curIdx = firstCell; curIdx <= lastCell; curIdx++) {
            if (cellsOnPlane.get(curIdx).getCellStatus() == playerTurn
                    || cellsOnPlane.get(curIdx).getCellStatus() == CellModel.CellStatus.BLANK) {
              validRun = false;
            }
          }
          // if a run is valid, flip every cell between the clicked cell and cell in question
          // to the color of the current player.
          if (validRun) {
            containsValidMove = true;
            for (int curIdx = firstCell; curIdx <= lastCell; curIdx++) {
              if (move) {
                if (playerTurn == BLACK) {
                  cellsOnPlane.get(curIdx).flipToBlack();
                } else {
                  cellsOnPlane.get(curIdx).flipToWhite();
                }
              }
              this.numTilesFlipped++;
            }

          }
        }
      }
    }
    return containsValidMove;
  }

  /**
   * Calculates the score of the game for the given player, which equates to how many hexagons
   * on the board are the player's color.
   *
   * @param playerColor the color of the player's hexagonal cells.
   * @return the score associated with the correct player.
   * @throws IllegalArgumentException if playerColor is not WHITE or BLACK
   */
  public int getScore(CellModel.CellStatus playerColor) {
    this.isGameOn();
    if (playerColor == CellModel.CellStatus.BLANK) {
      throw new IllegalArgumentException("Cannot return blank score");
    }
    int score = 0;
    for (List<Cell> row : this.board) {
      for (Cell cell : row) {
        if (cell.getCellStatus().equals(playerColor)) {
          score++;
        }
      }
    }
    return score;
  }

  /**
   * Determines if the game of Reversi is over or not. A game is over if there have been two passes
   * committed in a row, or if the board has no more blank hexagonal cells.
   *
   * @return true if the game is over, false otherwise.
   */
  public boolean isGameOver() {
    this.isGameOn();
    if (this.passesInARow >= 2) {
      this.reasonOver = "2 Passes in a Row!";
      return true;
    }
    boolean filled = true;
    for (List<Cell> row : this.board) {
      for (Cell cell : row) {
        if (cell.getCellStatus() == CellModel.CellStatus.BLANK) {
          filled = false;
        }
      }
    }
    if (filled) {
      this.reasonOver = "All Cells Filled!";
      return true;
    }
    if (!this.anyValidMoves()) {
      this.reasonOver = "No More Valid Moves!";
    }
    return false;
  }

  /**
   * Determines if the game has been started already. If it has not been, it throws an
   * IllegalStateException to warn the user the game has not begun yet.
   */
  private void isGameOn() {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started!!!!!!!");
    }
  }

  /**
   * Determines which player won the game of Reversi, based on which player has the higher score.
   *
   * @return the color of the player's cells that won the game.
   */
  public CellModel.CellStatus whoWon() {
    if (this.isGameOver()) {
      int whitePoints = this.getScore(WHITE);
      int blackPoints = this.getScore(BLACK);
      if (whitePoints < blackPoints) {
        return BLACK;
      }
      if (blackPoints < whitePoints) {
        return WHITE;
      } else {
        return CellModel.CellStatus.BLANK;
      }
    } else {
      throw new IllegalStateException("Game is not over yet!!!");
    }
  }

  @Override
  public CellModel.CellStatus whoseTurn() {
    this.isGameOn();
    return this.playerTurn;
  }

  @Override
  public Cell getClickedCell(Coordinate coordinate) {
    Cell clickedCell = null;
    for (List<Cell> row : this.board) {
      for (Cell cell : row) {
        if (cell.getCoordinate().equals(coordinate)) {
          clickedCell = cell;
        }
      }
    }
    if (clickedCell != null) {
      return clickedCell;
    } else {
      throw new IllegalArgumentException("No cell corresponding to coordinates");
    }
  }

  /**
   * Determines if the player attempting a move is the current player. If not,
   * throws an exception.
   *
   * @param playerCalling the player calling a method
   * @throws IllegalStateException    if the player calling is not the current player
   * @throws IllegalArgumentException if the player called is blank
   */
  private void isPlayersTurn(CellModel.CellStatus playerCalling) {
    if (playerCalling == CellModel.CellStatus.BLANK) {
      throw new IllegalArgumentException("Player cannot be blank :(");
    } else if (this.playerTurn != playerCalling) {
      throw new IllegalStateException("Not this player's turn!!");
    }
  }

  /**
   * Determines if the current player has any valid moves to be made.
   *
   * @return true if the current player can make a move
   */
  public boolean anyValidMoves() {
    for (List<Cell> row : this.board) {
      for (Cell cell : row) {
        if (this.playADisc(cell, BLACK, false)
                || this.playADisc(cell, WHITE, false)) {
          return true;
        }
      }
    }
    return false;
  }


  @Override
  public int getLastTurnTilesFlipped() {
    return this.numTilesFlipped;
  }

  @Override
  public int getSideSize() {
    return this.sideSize;
  }

  @Override
  public String getReasonEnded() {
    return this.reasonOver;
  }

  @Override
  public List<Coordinate> getCorners() {
    int sideIndex = this.sideSize - 1;
    return Arrays.asList(new Coordinate(0, -sideIndex),
            new Coordinate(sideIndex, -sideIndex),
            new Coordinate(-sideIndex, 0),
            new Coordinate(sideIndex, 0),
            new Coordinate(-sideIndex, sideIndex),
            new Coordinate(0, sideIndex));
  }

  @Override
  public void listenForTurn(
          ReversiControllerImplementation controller, CellModel.CellStatus discColor) {
    if (discColor == BLACK) {
      this.blackController = controller;
    }
    if (discColor == WHITE) {
      this.whiteController = controller;
    }
  }

}