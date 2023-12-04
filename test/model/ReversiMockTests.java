package model;

import org.junit.Assert;
import org.junit.Test;

import controller.ReversiMockControllerImplementation;
import player.HumanPlayer;
import player.Player;
import view.ReversiView;
import view.SimpleReversiView;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.WHITE;

/**
 * Class for all model implementation tests.
 */
public class ReversiMockTests {

  BasicReversi model = new BasicReversi(4);
  ReversiView viewBlack = new SimpleReversiView(model);
  ReversiView viewWhite = new SimpleReversiView(model);
  Player playerWhite = new HumanPlayer(WHITE, viewWhite);
  Player playerBlack = new HumanPlayer(BLACK, viewBlack);
  ReversiMockControllerImplementation controllerBlack = new ReversiMockControllerImplementation(
          model, viewBlack, playerBlack);
  ReversiMockControllerImplementation controllerWhite = new ReversiMockControllerImplementation(
          model, viewWhite, playerWhite);

  private void initConditions() {
    model.startGame();
    controllerBlack.play();
    controllerWhite.play();
  }

  // test that switch player sends correct notifications to corresponding controllers
  @Test
  public void testSwitchPlayerSendsNotification() {
    this.initConditions();
    model.switchPlayer();
    Assert.assertTrue(controllerBlack.turnBegun);
    Assert.assertTrue(controllerWhite.turnEnded);
  }

  // confirm that make move gets called correctly
  @Test
  public void testMakeMove() {
    controllerBlack.initiateMovement();
    Assert.assertEquals(controllerBlack.moveMade, "M");
  }

}
