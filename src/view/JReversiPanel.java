package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Cell;
import model.ReadOnlyReversi;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.BLANK;

/**
 * A JPanel for the Reversi game with hexagonal grid representation.
 * Handles user input, displays the game board, and interacts with a ReadOnlyReversi model.
 */
public class JReversiPanel extends JPanel implements ActionListener {

  private final int BOARD_SIZE; // how many cells on one side of the board

  private final int INIT_SIZE = 325; // size of the window
  private final JFrame frame;
  private final JButton backgroundButton;
  private final ReadOnlyReversi model; // the model to be rendered
  private Dimension windowDimension; // the dimensions of the current window
  private Point origin; // the origin of the current window
  private List<List<HexButton>> viewBoard; // the model's corresponding GUI board
  private boolean anyButtonSelectedHuh; // if there is currently a cell selected on the board
  private HexButton buttonSelected; // which button was selected last


  /**
   * Constructs a JReversiPanel with the given JFrame and ReadOnlyReversi model.
   * Initializes the panel dimensions, frame, game board size, model, and various UI elements.
   *
   * @param frame The JFrame associated with the panel.
   * @param model The ReadOnlyReversi model representing the game state.
   */
  public JReversiPanel(JFrame frame, ReadOnlyReversi model) {
    this.setPreferredSize(new Dimension(INIT_SIZE * 2, (int) (INIT_SIZE * Math.sqrt(3))));
    this.frame = frame;
    this.frame.setPreferredSize(this.getPreferredSize());
    this.BOARD_SIZE = model.getSideSize();
    this.model = model;
    //this.model.startGame();
    this.windowDimension = this.getPreferredSize();
    this.drawHexGridLoop(true);
    this.backgroundButton = new JButton("background");
    this.setUpBackground();
    this.setLayout(null);

  }

  /**
   * Sets up background button.
   */
  private void setUpBackground() {
    this.backgroundButton.setActionCommand("Background Button");
    this.backgroundButton.addActionListener(this);
    this.backgroundButton.setBorderPainted(false);
    this.add(backgroundButton);
    backgroundButton.setBounds(0, 0, windowDimension.width, windowDimension.height);
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.windowDimension = frame.getSize();
    g.setColor(new Color(102, 12, 0));
    g.fillRect(0, 0, this.windowDimension.width, this.windowDimension.height);
    this.drawHexGridLoop(false);
    this.backgroundButton.setBounds(0, 0, frame.getWidth(), frame.getHeight());
    this.updateMoves();
  }


  /**
   * Draws a hexagonal grid within the specified window dimensions. The grid can be either created
   * or updated based on the 'create' parameter. Calculates the positions and sizes
   * of hexagonal buttons within the grid and manages their creation or updates.
   *
   * @param create true if the board must be created, false if it must merely be updated
   */
  private void drawHexGridLoop(boolean create) {
    int windowWidth = this.windowDimension.width; // width of current window
    int windowHeight = this.windowDimension.height - 28; // height of current window
    this.origin = new Point(windowWidth / 2, windowHeight / 2); // origin of current window
    double xSize = windowWidth / ((Math.sqrt(3) * (BOARD_SIZE * 2 - 1))); // hex "size" on x plane
    double ySize = (double) windowHeight / (BOARD_SIZE * 3 - 1); // hex "size" on y plane
    double xOff = (xSize * Math.sqrt(3)) / 2; // x offset according to width
    double yOff = (ySize / 2); // y offset according to height
    int sizeOfRow = BOARD_SIZE; // Number of cells in each row, starting from the board size
    int length = (BOARD_SIZE * 2) - 1; // Number of rows in a board
    int place = BOARD_SIZE - length; // Which row we are currently at
    List<List<HexButton>> newBoard = new ArrayList<List<HexButton>>();
    for (int row = 0; row < length; row++) {
      List<HexButton> boardRow = new ArrayList<HexButton>();
      for (int col = 0; col < sizeOfRow; col++) {
        this.createButtonProperties(xSize, ySize, xOff, yOff, row, col,
                sizeOfRow, create, boardRow);
      }

      if (place < 0) {
        sizeOfRow++;
      } else {
        sizeOfRow--;
      }
      place++;
      newBoard.add(boardRow);
    }
    if (create) {
      this.viewBoard = newBoard;

    }

  }

  /**
   * Calculates the dimensions and position of a hexagonal button. If the 'create' parameter is
   * true, a new button is created with specified dimensions and coordinates. If 'create' is false,
   * the bounds of the existing button are updated.
   *
   * @param xSize     hex "size" according to the current width of the window
   * @param ySize     hex "size" according to the current height of the window
   * @param xOff      x offset according to the current width of the window
   * @param yOff      x offset according to the current height of the window
   * @param row       the current row of the board
   * @param col       the current column
   * @param sizeOfRow the size of the current row
   * @param create    if a button shall be created
   * @param boardRow  the row of the board the button shall be placed upon
   */
  private void createButtonProperties(
          double xSize, double ySize, double xOff, double yOff, int row, int col,
          int sizeOfRow, boolean create, List<HexButton> boardRow) {
    int hexWidth = (int) (xSize * Math.sqrt(3));
    int hexHeight = (int) (ySize * 2);
    int x = this.calculateXCoord(col, sizeOfRow, xOff);
    int y = this.calculateYCoord(row, yOff);
    if (create) {
      this.createButton(row, col, x, y, hexWidth, hexHeight, boardRow);
    } else {
      this.viewBoard.get(row).get(col).setBounds(x, y, hexWidth, hexHeight);
    }
  }

  /**
   * Creates a button with q and r coordinates based on the current row and column of the board
   * and sets its bounds accordingly.
   *
   * @param row       the current row
   * @param col       the current column
   * @param x         the x coordinate the button will be placed on
   * @param y         the y coordinate the button will be placed on
   * @param hexWidth  the width of the button
   * @param hexHeight the height of the button
   * @param boardRow  the row of the board the button shall be placed upon
   */
  private void createButton(int row, int col, int x, int y,
                            int hexWidth, int hexHeight, List<HexButton> boardRow) {
    Cell correspondingCell = this.model.getBoard().get(row).get(col);
    HexButton hexButton =
            new HexButton(correspondingCell);
    hexButton.setActionCommand("Hexagon Button");
    hexButton.addActionListener(this);
    boardRow.add(hexButton);
    add(boardRow.get(col));
    boardRow.get(col).setBounds(x, y, hexWidth, hexHeight);
  }

  /**
   * Calculates the x coordinate of a button.
   *
   * @param col     the current column
   * @param rowSize the size of the current row
   * @param xOff    x offset according to the current width of the window
   * @return the x coordinate of a button
   */
  private int calculateXCoord(int col, int rowSize, double xOff) {
    return (int) ((this.origin.x + xOff * (col * 2 + 1 - rowSize)) - xOff);
  }

  /**
   * Calculates the y coordinate of a button.
   *
   * @param row  the current row
   * @param yOff y offset according to the current height of the window
   * @return the y coordinate of a button
   */
  private int calculateYCoord(int row, double yOff) {
    return (int) ((this.origin.y + yOff * (row - BOARD_SIZE) * 3) + yOff);
  }

  /**
   * Renders the board based on the current state of the game.
   */
  public void updateMoves() {

    List<List<Cell>> modelBoard = model.getBoard();
    for (int row = 0; row < modelBoard.size(); row++) {
      for (int col = 0; col < modelBoard.get(row).size(); col++) {
        Cell modelCell = modelBoard.get(row).get(col);
        HexButton viewCell = this.viewBoard.get(row).get(col);
        if (modelCell.getCellStatus() != BLANK) {
          viewCell.fillHex();
          if (modelCell.getCellStatus() == BLACK) {
            viewCell.flipToBlack();
          } else {
            viewCell.flipToWhite();
          }
        }
      }
    }
    repaint();
  }


  /**
   * Processes the action event triggered by a button click. If a hexagonal button has been clicked
   * and it is already highlighted, unhighlight said button. Otherwise, highlight said button and
   * unhighlight all other hexagonal buttons. If the board has been clicked, unhighlight all
   * hexagonal buttons.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {
      case "Hexagon Button":
        HexButton clickedButton = (HexButton) e.getSource();
        if (clickedButton.isHexSelected()) {
          clickedButton.unclickHex();
          this.anyButtonSelectedHuh = false;
        } else {
          this.unclickAll();
          clickedButton.clickHex();
          this.buttonSelected = clickedButton;
          this.anyButtonSelectedHuh = true;
        }
        this.requestFocusInWindow();
        break;
      case "Background Button":
        this.unclickAll();
        this.anyButtonSelectedHuh = false;
        this.requestFocusInWindow();
        break;

      default: //do nothing yet
    }
  }

  /**
   * Unclick any currently clicked buttons on the board.
   */
  private void unclickAll() {
    for (List<HexButton> row : this.viewBoard) {
      for (HexButton button : row) {
        if (button.isHexSelected()) {
          button.unclickHex();
        }
      }
    }
  }


  /**
   * Returns the button currently selected on the panel.
   *
   * @return the button currently selected on the panel
   */
  public HexButton getButtonSelected() {
    return this.buttonSelected;
  }

  /**
   * Returns the button currently selected on the panel.
   *
   * @return the button currently selected on the panel
   */
  public boolean isAButtonSelected() {
    return this.anyButtonSelectedHuh;
  }

}
