import org.junit.Assert;
import org.junit.Test;


import controller.ReversiController;
import controller.ReversiControllerImplementation;
import model.BasicReversi;
import model.ReversiModel;
import player.AIPlayer;
import player.MockHuman;
import player.Player;
import strategy.CaptureMost;
import view.MockReversiView;
import view.ReversiView;
import view.SimpleReversiView;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.WHITE;

/**
 * Class to ReversiController works cohesively with the view and model.
 */
public class ReversiControllerTests {

  ReversiModel model = new BasicReversi(4);
  ReversiView viewBlack;
  ReversiView viewWhite;
  MockReversiView viewB;
  MockReversiView viewW;
  Player playerWhite;
  Player playerBlack;
  ReversiController controllerWhite;
  ReversiController controllerBlack;



  private void initConditions() {
    viewBlack = new SimpleReversiView(model);
    viewWhite = new SimpleReversiView(model);
    playerWhite = new MockHuman(WHITE, viewWhite);
    playerBlack = new MockHuman(BLACK, viewBlack);
    controllerWhite = new ReversiControllerImplementation(
            model, viewBlack, playerWhite);
    controllerBlack = new ReversiControllerImplementation(
            model, viewWhite, playerBlack);
    model.startGame();
    controllerBlack.play();
    controllerWhite.play();
  }

  private void initConditions2() {
    viewB = new MockReversiView(model);
    viewW = new MockReversiView(model);
    playerWhite = new AIPlayer(WHITE, new CaptureMost());
    playerBlack = new AIPlayer(BLACK, new CaptureMost());
    controllerWhite = new ReversiControllerImplementation(
            model, viewB, playerWhite);
    controllerBlack = new ReversiControllerImplementation(
            model, viewW, playerBlack);
    model.startGame();
    controllerBlack.play();
    controllerWhite.play();
  }

  // test that make move calls the model correctly
  @Test
  public void testMakeMove() {
    this.initConditions();
    controllerWhite.makeMove("M");
    // ensure that makeMove correctly places a piece
    Assert.assertEquals(model.getScore(WHITE), 5);
    // ensure that makeMove successfully switches the turn
    Assert.assertEquals(model.whoseTurn(), BLACK);
  }

  // uses mock of view to ensure popup messages are sent
  @Test
  public void testCorrectPopups() {
    this.initConditions2();
    // Ensure that winner displayed pops up when game ends
    Assert.assertEquals(viewW.getTranscript().get(0), "Message Sent!");
    // Ensure that an error message pops up when a user makes an invalid move
    controllerWhite.makeMove("M");
    Assert.assertEquals(viewW.getTranscript().get(1), "Message Sent!");
  }


}
