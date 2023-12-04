package view;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.CellModel;
import model.ReadOnlyReversi;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.WHITE;

/**
 * Implementation of the ReversiView interface using JFrame.
 * This view displays the Reversi game using a JReversiPanel.
 */
public class SimpleReversiView extends JFrame implements ReversiView {


  JReversiPanel panel;

  /**
   * Constructs a SimpleReversiView for the specified ReadOnlyReversi model.
   *
   * @param model The ReadOnlyReversi model to be displayed.
   */
  public SimpleReversiView(ReadOnlyReversi model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JReversiPanel panel = new JReversiPanel(this, model);
    this.panel = panel;
    this.setContentPane(panel);
    this.setLocationRelativeTo(null);
    this.pack();
  }


  /**
   * Displays or hides the Reversi game window.
   *
   * @param show If true, the window is displayed; if false, it is hidden.
   */
  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public JReversiPanel getPanel() {
    return this.panel;
  }

  @Override
  public void indicateTurn(CellModel.CellStatus playerColor) {
    if (playerColor == BLACK) {
      this.setTitle("REVERSI -- PLAYER BLACK -- YOUR TURN");
    }
    if (playerColor == WHITE) {

      this.setTitle("REVERSI -- PLAYER WHITE -- YOUR TURN");
    }
  }

  @Override
  public void unIndicateTurn(CellModel.CellStatus playerColor) {
    if (playerColor == BLACK) {
      this.setTitle("REVERSI -- PLAYER BLACK -- WHITE TURN");
    }
    if (playerColor == WHITE) {
      this.setTitle("REVERSI -- PLAYER WHITE -- BLACK TURN");
    }
  }

  /**
   * Alerts the user they have attempted an invalid move.
   */
  public void alertUser(String message) {
    JOptionPane.showMessageDialog(
            null, message,
            "Very Urgent Message", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Upon the game ending, display the game winning message which includes: the reason the game
   * is over, which player won, and the ending score.
   *
   * @param message     the message to be displayed regarding who won.
   * @param reasonEnded the reason as to why the game ended according to the rules of Reversi.
   */
  public void displayWinner(String message, String reasonEnded) {
    JOptionPane.showMessageDialog(
            null, message,
            "Game Over! " + reasonEnded, JOptionPane.INFORMATION_MESSAGE);
  }
}



