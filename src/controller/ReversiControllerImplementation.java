package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Cell;
import model.CellModel;
import model.ReversiModel;
import player.Player;
import view.ReversiView;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.BLANK;
import static model.CellModel.CellStatus.WHITE;

/**
 * Represents a controller directing a single player for the game of Reversi.
 */
public class ReversiControllerImplementation implements ReversiController, KeyListener {

  private final ReversiModel model;
  private final ReversiView view;
  private final Player player;


  /**
   * Initiates the controller into a state where it is ready to be used for Reversi.
   *
   * @param m      model representing the game state of Reversi.
   * @param view   the view associated for the desired player
   * @param player the player who is utilizing this controller (either white or black)
   */
  public ReversiControllerImplementation(ReversiModel m, ReversiView view, Player player) {
    this.model = m;
    this.view = view;
    this.player = player;
  }


  /**
   * Processes the action event triggered by a key press. If key 'P' has been pressed, pass to next
   * player. If 'M' has been pressed, attempt a move at the currently highlighted cell.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyPressed(KeyEvent e) {
    String keyPressed = null;
    if (e.getKeyCode() == KeyEvent.VK_M) {
      keyPressed = "M";
    } else if (e.getKeyCode() == KeyEvent.VK_P) {
      keyPressed = "P";
    }
    if (keyPressed != null) {
      this.makeMove(keyPressed);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // do nothing
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // do nothing
  }

  @Override
  public void play() {
    this.view.getPanel().addKeyListener(this);
    this.view.getPanel().requestFocusInWindow();
    this.model.listenForTurn(this, this.player.getDiscColor());
    if (player.getDiscColor() == WHITE) {
      this.player.notifyPlayer(this, true);
    }
    if (player.getDiscColor() == BLACK) {
      this.player.notifyPlayer(this, false);
    }
  }

  @Override
  public void notifyTurnBegin() {
    if (!this.model.isGameOver()) {
      this.player.notifyPlayer(this, true);
    }
  }

  @Override
  public void notifyTurnEnd() {
    if (this.model.isGameOver()) {
      this.findWinner();
    } else {
      this.player.notifyPlayer(this, false);

    }
  }

  @Override
  public void findWinner() {
    CellModel.CellStatus winner = this.model.whoWon();
    if (winner == BLANK) {
      this.view.displayWinner("TIE GAME! Good game!  \n SCORE: "
              + this.model.getScore(BLACK), this.model.getReasonEnded());
    } else {
      String won;
      CellModel.CellStatus lost;
      if (this.model.whoWon() == BLACK) {
        won = "BLACK";
        lost = WHITE;
      } else {
        won = "WHITE";
        lost = BLACK;
      }
      this.view.displayWinner(won + " WON! :)  \nWINNING SCORE: "
                      + this.model.getScore(winner) + "\nLOSING SCORE: "
                      + this.model.getScore(lost),
              this.model.getReasonEnded());
    }
  }

  @Override
  public void unindicate(Player player) {
    this.view.unIndicateTurn(player.getDiscColor());
  }


  @Override
  public void makeMove(String decision) {
    ReversiFeature action = null;
    Cell clickCell;
    if (decision.equals("M")) {
      try {
        if (this.player.play(model).isPresent()) {
          clickCell = this.model.getClickedCell(this.player.play(model).get());
          action = new Move(this.player.getDiscColor(), clickCell);
        } else {
          action = new Pass(this.player.getDiscColor());
        }
      } catch (IllegalArgumentException | IllegalStateException ex) {
        this.view.alertUser("INVALID MOVE!! :(");
      }
    } else if (decision.equals("P")) {
      action = new Pass(this.player.getDiscColor());
    }
    if (action != null) {
      try {
        action.goNow(model);
      } catch (IllegalStateException e) {
        this.view.alertUser("NOT!! Your TURN!!!!");
      }

    }
  }

  @Override
  public void initiateMovement() {
    this.makeMove("M");
  }
}
