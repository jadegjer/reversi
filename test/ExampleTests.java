import org.junit.Assert;
import org.junit.Test;

import model.BasicReversi;
import model.CellModel;
import model.ReversiModel;
import view.ReversiTextView;
import view.TextView;

/**
 * A test class to give provide the user an example of how the game is played.
 */
public class ExampleTests {

  ReversiModel model;
  TextView t;

  /**
   * Sets up the game with a sideSize of 4.
   */
  private void setUpGame() {
    this.model = new BasicReversi(4);
    this.model.startGame();
    t = new ReversiTextView(model);
    System.out.println(t.toString());
  }

  // test if BasicReversi constructor creates board of correct size
  @Test
  public void testReversiBoardSize() {
    // test there are 4 cells on the top row
    Assert.assertEquals(this.model.getBoard().get(0).size(), 4);
    // test height of board is 7
    Assert.assertEquals(this.model.getBoard().size(), 7);
  }

  // test if reversi initialized white as first player when startGame is called
  @Test
  public void testReversiGameStart() {
    this.setUpGame();
    Assert.assertEquals(this.model.whoseTurn(), CellModel.CellStatus.WHITE);
  }

  // test passing the turn when it's the players turn
  @Test
  public void testReversiPassingTurnValid() {
    this.setUpGame();
    this.model.passTurn(CellModel.CellStatus.WHITE);
    Assert.assertEquals(this.model.whoseTurn(), CellModel.CellStatus.BLACK);
  }

  // test when the game is over because both players passed in a row and white has won
  @Test
  public void testReversiGameOverTwoPassesInARow() {
    this.setUpGame();
    this.model.playADisc(this.model.getBoard().get(2).get(1), CellModel.CellStatus.WHITE, true);
    this.model.passTurn(CellModel.CellStatus.BLACK);
    this.model.passTurn(CellModel.CellStatus.WHITE);
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertEquals(this.model.whoWon(), CellModel.CellStatus.WHITE);
  }






}
