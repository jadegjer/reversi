package view;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JButton;

import model.Cell;
import model.Coordinate;

/**
 * HexButton represents a hexagonal button in the Reversi game.
 * Each hexagon has a specific coordinate on the game board, a selected state, and the ability to be
 * filled with a colored tile.
 **/
public class HexButton extends JButton {
  private final Cell correspondingCell; // corresponding cell to this hexbutton
  private final Coordinate coordinates; // q, r, and s coordinates of this hexButton
  private boolean hexSelected; // true if the hexagon is currently selected on the board
  private Color tileColor; // color of the current tile in a hexagon
  private boolean hexFilled; // true if the hexagon has a tile in it
  private Rectangle bounds; // the current boards of this hexagon

  /**
   * Constructs a HexButton with the specified hexagonal coordinate.
   * Initializes the hexagon's state and appearance.
   *
   * @param correspondingCell The corresponding cell of this HexButton.
   */
  public HexButton(Cell correspondingCell) {
    this.correspondingCell = correspondingCell;
    this.coordinates = correspondingCell.getCoordinate();
    this.hexSelected = false;
    this.hexFilled = false;
    this.bounds = this.getBounds();
    setContentAreaFilled(false);
    setFocusPainted(false);
    setBorderPainted(false);

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    this.bounds = this.getBounds();

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    Path2D.Double path = new Path2D.Double();
    this.drawHex(path);
    // makes cell dim or lit
    this.colorCell(g2d);
    // fills cell
    g2d.fill(path);

    // draws outline of cell
    g.setColor(new Color(102, 12, 0));
    g2d.setStroke(new BasicStroke(2));
    g2d.draw(path);

    // places tile in correct color if there is one
    this.checkForTile(g2d);
  }


  /**
   * Draws the outline for this hexagon based on its bounds.
   *
   * @param path the path for the outline
   */
  private void drawHex(Path2D.Double path) {
    path.moveTo(this.bounds.width / 2.0, 0);
    path.lineTo(this.bounds.width, this.bounds.height / 4.0);
    path.lineTo(this.bounds.width, 3.0 * this.bounds.height / 4.0);
    path.lineTo(this.bounds.width / 2.0, this.bounds.height);
    path.lineTo(0, 3.0 * this.bounds.height / 4.0);
    path.lineTo(0, this.bounds.height / 4.0);
    path.closePath();
  }

  /**
   * Colors the hexagon based on whether it is currently selected.
   *
   * @param g2d the graphics
   */
  private void colorCell(Graphics2D g2d) {
    if (this.hexSelected) {
      g2d.setColor(Color.MAGENTA);
    } else {
      g2d.setColor(new Color(152, 54, 135));
    }
  }

  /**
   * If this hexagon is filled, render a tile in the correct color on the hexagon.
   *
   * @param g2d the graphics
   */
  private void checkForTile(Graphics2D g2d) {
    if (this.hexFilled) {
      g2d.setColor(tileColor);
      g2d.fillOval(this.bounds.width / 8, (int) (this.bounds.height / 5.5),
              (int) (this.bounds.width / 1.3), (int) (this.bounds.height / 1.5));
    }
  }

  /**
   * Select this hexagon.
   */
  public void clickHex() {
    this.hexSelected = true;
    repaint();
  }

  /**
   * Determines whether this hexagon is currently selected.
   *
   * @return true if hexagon is currently selected, false otherwise
   */
  public boolean isHexSelected() {
    return this.hexSelected;
  }

  /**
   * Unselect this hexagon.
   */
  public void unclickHex() {
    this.hexSelected = false;
    repaint();
  }

  /**
   * Turns fillHex true.
   */
  public void fillHex() {
    hexFilled = true;
  }

  /**
   * Flip the tile of this button to black.
   */
  public void flipToBlack() {
    this.tileColor = Color.BLACK;
  }

  /**
   * Flip the tile of this button to white.
   */
  public void flipToWhite() {
    this.tileColor = Color.WHITE;
  }

  /**
   * Get the corresponding cell of this hexagon.
   *
   * @return the corresponding cell of this hexagon
   */
  public Cell getCorrespondingCell() {
    return this.correspondingCell;
  }

  /**
   * Get the Coordinate of this hexagon.
   *
   * @return the Coordinate of this hexagon
   */
  public Coordinate getCoordinate() {
    return this.coordinates;
  }

  /**
   * Determines whether a hexagon contains a tile.
   *
   * @return true if hexagon contains a tile.
   */
  public boolean isHexFilled() {
    return hexFilled;
  }

}
