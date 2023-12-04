package view;


import model.CellModel;

/**
 * Interface to view a game of reversi.
 */
public interface ReversiView {


  /**
   * Displays or hides the Reversi game window.
   *
   * @param show If true, the window is displayed; if false, it is hidden.
   */
  void display(boolean show);

  /**
   * Returns the JPanel being utilized for the Reversi view.
   *
   * @return the JReversiPanel displaying the game of Reversi
   */
  JReversiPanel getPanel();

  /**
   * Indicate on the view that it is the player's turn that is represented by playerColor.
   *
   * @param playerColor the color disc of the player whose turn it is.
   */
  void indicateTurn(CellModel.CellStatus playerColor);

  /**
   * Indicate on the view that it is no longer the player's turn that is represented by playerColor.
   *
   * @param playerColor the color disc of the player whose turn it is no longer.
   */
  void unIndicateTurn(CellModel.CellStatus playerColor);

  void alertUser(String s);

  void displayWinner(String s, String reasonEnded);


}
