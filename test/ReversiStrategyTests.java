import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.BasicReversi;
import model.Cell;
import model.Coordinate;
import model.MockReversi;
import model.ReversiModel;
import player.AIPlayer;
import player.Player;
import strategy.AvoidCornerAdjacent;
import strategy.CaptureMost;
import strategy.PlayToCorner;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.WHITE;

/**
 * Class to test different various reversi strategies.
 */
public class ReversiStrategyTests {

  Player whitePlayerCorners = new AIPlayer(WHITE, new PlayToCorner(new CaptureMost()));
  Player blackPlayerCorners = new AIPlayer(BLACK, new PlayToCorner(new CaptureMost()));

  Player whitePlayerCaptureMost = new AIPlayer(WHITE, new CaptureMost());
  Player blackPlayerCaptureMost = new AIPlayer(BLACK, new CaptureMost());

  Player whitePlayerAvoidsCornerAdjacent =
          new AIPlayer(WHITE, new AvoidCornerAdjacent(new CaptureMost()));
  Player blackPlayerAvoidsCornerAdjacent =
          new AIPlayer(BLACK, new AvoidCornerAdjacent(new CaptureMost()));
  ReversiModel model = new BasicReversi(4);
  MockReversi mockModel = new MockReversi(4);
  List<Coordinate> corners;

  private void initData() {
    model.startGame();
    mockModel.startGame();
    corners = mockModel.getCorners();
  }


  // tests that PlayToCorner really checks every corner
  @Test
  public void testPlayToCornerChecksAllCorners() {
    this.initData();
    whitePlayerCorners.play(mockModel);

    List<Coordinate> coordinateList =
            new ArrayList<Coordinate>(mockModel.getTranscript().subList(0,6));
    Assert.assertEquals(corners, coordinateList);
  }

  // checks that capture most checks all coordinates
  @Test
  public void testCaptureMostChecksAll() {
    this.initData();
    whitePlayerCaptureMost.play(mockModel);
    List<Coordinate> allCoordinates = new ArrayList<Coordinate>();
    for (List<Cell> row : mockModel.getBoard()) {
      for (Cell c : row) {
        allCoordinates.add(c.getCoordinate());
      }
    }
    Assert.assertEquals(mockModel.getTranscript(), allCoordinates);
  }

  // test Capture most gets upper leftmost cell even if there are other valid moves
  @Test
  public void testCaptureMostUpperLeftmost() {
    this.initData();
    mockModel.playADisc(mockModel.getClickedCell(whitePlayerCaptureMost.play(model).get()),
            WHITE,false);
    Assert.assertEquals(
            mockModel.getTranscript().get(0),
            mockModel.getBoard().get(1).get(2).getCoordinate());
  }

  // tests CaptureMost returns coordinate that captures most even there is a valid move
  // checked beforehand
  @Test
  public void testCaptureMostCapturesMostAfter() {
    this.initData();
    model.playADisc(model.getBoard().get(4).get(1), WHITE, true);
    mockModel.playADisc(
            model.getClickedCell(blackPlayerCaptureMost.play(model).get()),
            BLACK, false);
    Assert.assertEquals(
            mockModel.getTranscript().get(0),
            mockModel.getBoard().get(5).get(0).getCoordinate());

  }

  // tests CaptureMost returns coordinate that captures most even there is a valid move
  // checked afterward
  @Test
  public void testCaptureMostCapturesMostBefore() {
    this.initData();
    model.playADisc(model.getBoard().get(2).get(1), WHITE, true);
    mockModel.playADisc(
            model.getClickedCell(blackPlayerCaptureMost.play(model).get()),
            BLACK, false);
    Assert.assertEquals(
            mockModel.getTranscript().get(0),
            mockModel.getBoard().get(1).get(0).getCoordinate());

  }

  // verify that PlayToCorners plays to corners when one is available
  @Test
  public void testPlayToCornerPlaysToCorner() {
    this.initData();
    for (int i = 0; i < 5; i++) {
      model.playADisc(model.getClickedCell(whitePlayerCorners.play(model).get()),
              WHITE,true);
      model.playADisc(model.getClickedCell(blackPlayerCorners.play(model).get()),
              BLACK,true);
    }
    model.playADisc(model.getClickedCell(whitePlayerCorners.play(model).get()),
            WHITE,true);
    mockModel.playADisc(model.getClickedCell(blackPlayerCorners.play(model).get()),
            BLACK, false);
    mockModel.playADisc(model.getClickedCell(blackPlayerCaptureMost.play(model).get()),
            BLACK, false);
    // ensure capture most and play corner produce 2 different outcomes
    Assert.assertNotEquals(mockModel.getTranscript().get(0), mockModel.getTranscript().get(1));
    Assert.assertEquals(
            mockModel.getTranscript().get(0),
            mockModel.getBoard().get(0).get(3).getCoordinate());

  }

  // verify that avoidCornerAdjacent really avoids corners when possible
  @Test
  public void testAvoidCornerAdjacent() {
    this.initData();
    model.playADisc(model.getClickedCell(whitePlayerAvoidsCornerAdjacent.play(model).get()),
              WHITE,true);
    mockModel.playADisc(model.getClickedCell(blackPlayerAvoidsCornerAdjacent.play(model).get()),
              BLACK,true);
    mockModel.playADisc(model.getClickedCell(blackPlayerCaptureMost.play(model).get()),
            BLACK,true);
    // ensure capture most and avoid corner produce 2 different outcomes
    Assert.assertNotEquals(mockModel.getTranscript().get(0), mockModel.getTranscript().get(1));
    Assert.assertEquals(
            mockModel.getTranscript().get(0),
            mockModel.getBoard().get(2).get(1).getCoordinate());

  }







}
