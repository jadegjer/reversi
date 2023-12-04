import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.BasicReversi;
import model.Cell;
import model.CellModel;
import model.ReversiModel;
import view.ReversiTextView;
import view.TextView;

/**
 * A test class to test the Reversi Model in more detail than the Example Tests.
 */
public class ReversiModelTests {

  ReversiModel model = new BasicReversi(4);
  TextView t;

  /**
   * Sets up the initial conditions to be used in future tests.
   */
  private void initConditions() {
    model.startGame();
    t = new ReversiTextView(model);
  }


  // test board cannot be mutated through getBoard method
  @Test
  public void testReversiGetBoard() {
    List<List<Cell>> mutatedList = this.model.getBoard();
    mutatedList.remove(0);
    Assert.assertEquals(this.model.getBoard().size(), 7);

  }

  // test BasicReversi constructor throws exception when sideSize is less than 2
  @Test(expected = IllegalArgumentException.class)
  public void testReversiInvalidSizeSize() {

    ReversiModel invalidModel = new BasicReversi(1);
  }



  // test passing the turn when it's not the players turn
  @Test(expected = IllegalStateException.class)
  public void testReversiPassingTurnInvalid() {
    this.initConditions();
    this.model.passTurn(CellModel.CellStatus.BLACK);
  }

  // test playing a disc in an invalid empty cell
  @Test(expected = IllegalStateException.class)
  public void testReversiPlayingDiscInvalidEmpty() {
    this.initConditions();
    this.model.playADisc(
            this.model.getBoard().get(2).get(0), CellModel.CellStatus.WHITE, true);
  }

  // test playing a disc in a valid spot when it is not the players turn
  @Test(expected = IllegalStateException.class)
  public void testReversiPlayingDiscWrongPlayer() {
    this.initConditions();
    this.model.playADisc(
            this.model.getBoard().get(2).get(1), CellModel.CellStatus.BLACK, true);
  }

  // test getScore works as intended during initial state of game
  @Test
  public void testGetScoreInitial() {
    this.initConditions();
    Assert.assertEquals(this.model.getScore(CellModel.CellStatus.WHITE), 3);
    Assert.assertEquals(this.model.getScore(CellModel.CellStatus.BLACK), 3);
  }

  // test getScore throw exception when a BLANK color is used
  @Test(expected = IllegalArgumentException.class)
  public void testGetScoreInvalid() {
    this.initConditions();
    this.model.getScore(CellModel.CellStatus.BLANK);
  }


  // test playing a disc in a valid empty cell
  @Test
  public void testReversiPlayAValidDiscRightPlayer() {
    this.initConditions();
    Assert.assertEquals(this.model.getScore(CellModel.CellStatus.WHITE), 3);
    this.model.playADisc(
            this.model.getBoard().get(2).get(1), CellModel.CellStatus.WHITE, true);
    Assert.assertEquals(this.model.getScore(CellModel.CellStatus.WHITE), 5);
  }

  // test playing a disc in an already filled cell throws exception
  @Test(expected = IllegalStateException.class)
  public void testReversiPlayADiscInFilledCell() {
    this.initConditions();
    this.model.playADisc(
            this.model.getBoard().get(2).get(2), CellModel.CellStatus.WHITE, true);
  }

  // test playing a disc that flips other discs diagonally
  @Test
  public void testReversiPlayAValidDiscRightPlayerDiagonal() {
    this.initConditions();
    Assert.assertEquals(this.model.getScore(CellModel.CellStatus.WHITE), 3);
    this.model.playADisc(
            this.model.getBoard().get(2).get(4), CellModel.CellStatus.WHITE, true);
    Assert.assertEquals(this.model.getScore(CellModel.CellStatus.WHITE), 5);
  }


  // test that number of passes resets when the other player plays
  @Test
  public void testGameNotOverTwoPassesNotConsecutive() {
    this.initConditions();
    this.model.passTurn(CellModel.CellStatus.WHITE);
    this.model.playADisc(
            this.model.getBoard().get(2).get(1), CellModel.CellStatus.BLACK, true);
    this.model.passTurn(CellModel.CellStatus.WHITE);
    Assert.assertFalse(this.model.isGameOver());
  }

  // small board to test full game
  ReversiModel model2 = new BasicReversi(3);

  /**
   * Sets up a new initialization of Reversi to be used in tests.
   */
  private void init2() {
    this.model2.startGame();
    TextView t = new ReversiTextView(model2);
  }

  // test whoWon when game isnt over
  @Test(expected = IllegalStateException.class)
  public void testWhoWonWhenGameNotOver() {
    this.initConditions();
    this.model.whoWon();
  }

  // test playDisc when game hasnt started
  @Test(expected = IllegalStateException.class)
  public void testPlayDiscGameNotStarted() {
    this.model.playADisc(
            this.model2.getBoard().get(1).get(0), CellModel.CellStatus.WHITE, true);
  }

  // test passTurn when game hasnt started
  @Test(expected = IllegalStateException.class)
  public void testPassTurnGameNotStarted() {
    this.model.passTurn(CellModel.CellStatus.WHITE);
  }

  // test isGameOver when game hasn't started
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverGameNotStarted() {
    this.model.isGameOver();
  }


  // test reversiTextView returns the correct inital board
  @Test
  public void testToStringInit() {
    this.initConditions();
    String board =
            "   _ _ _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + "   _ _ _ _ \n";
    Assert.assertEquals(t.toString(), board);
  }

  // test reversiTextView returns the correct board after a move is made
  @Test
  public void testToStringAfterMove() {
    this.initConditions();
    this.model.playADisc(
            this.model.getBoard().get(2).get(1), CellModel.CellStatus.WHITE, true);
    String board =
            "   _ _ _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + " _ O O O _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + "   _ _ _ _ \n";
    Assert.assertEquals(t.toString(), board);
  }

  // test class invariant : playerTurn must be black or white, never blank
  @Test
  public void testClassInvariant() {
    // when the game is started, the player is automatically set to white
    Assert.assertEquals(model.whoseTurn(), CellModel.CellStatus.WHITE);

    // whenever the player is switch, it can only be switched to white or black, never blank.
    // this ensures that the playerTurn will never end up blank.
    model.passTurn(CellModel.CellStatus.WHITE);
    Assert.assertEquals(model.whoseTurn(), CellModel.CellStatus.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testClassInvariantCont() {
    // model only switches player if the player entered is the same as playerTurn,
    // which can already only be white or black. This further ensures that the
    // player will never end up blank.
    model.passTurn(CellModel.CellStatus.BLANK);
  }


}
