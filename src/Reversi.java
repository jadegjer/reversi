import java.util.ArrayList;
import java.util.List;

import controller.ReversiController;
import controller.ReversiControllerImplementation;
import model.BasicReversi;
import model.CellModel;
import model.ReversiModel;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import strategy.AvoidCornerAdjacent;
import strategy.CaptureMost;
import strategy.PlayToCorner;
import view.ReversiView;
import view.SimpleReversiView;

import static model.CellModel.CellStatus.BLACK;
import static model.CellModel.CellStatus.WHITE;

/**
 * The main class for the Reversi game. It initializes the game model and view,
 * then displays the game window.
 */
public final class Reversi {

  /**
   * The main method that initializes the Reversi game by creating a BasicReversi model
   * with a specified size, creating a SimpleReversiView, and displaying the game window.
   *
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    ReversiModel model = new BasicReversi(4);
    ReversiView viewBlack = new SimpleReversiView(model);
    ReversiView viewWhite = new SimpleReversiView(model);
    List<Player> players = addPlayers(args, viewBlack, viewWhite);
    ReversiController reversiController1 = new ReversiControllerImplementation(
            model, viewBlack, players.get(0));
    ReversiController reversiController2 = new ReversiControllerImplementation(
            model, viewWhite, players.get(1));
    viewBlack.display(true);
    viewWhite.display(true);
    model.startGame();
    reversiController1.play();
    reversiController2.play();

  }

  /**
   * Creates a list of two players (human or AI) that will be playing the game of Reversi.
   *
   * @param args      arguments passed in by the user to dictate what type of players (human or AI)
   *                  will be partaking in the game of Reversi.
   * @param viewBlack the GUI used for the player assigned the black discs.
   * @param viewWhite the GUI used for the player assigned the white discs.
   * @return a list of two players (AI or human).
   */
  private static List<Player> addPlayers(String[] args, ReversiView viewBlack,
                                         ReversiView viewWhite) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Invalid Input");
    }
    List<Player> playerList = new ArrayList<Player>();
    playerList.add(constructPlayer(args[0], viewBlack, BLACK));
    playerList.add(constructPlayer(args[1], viewWhite, WHITE));
    return playerList;
  }

  /**
   * Creates a single player (AI or Human) for the game of Reversi.
   *
   * @param arg   the argument passed in by the user to dictate whether to add a human player or an
   *              AI by stating the specified strategy.
   * @param view  the GUI that corresponds with the color disc the player has.
   * @param color the color disc the player has.
   * @return a player (human or AI) to be used in the game of Reversi.
   */
  private static Player constructPlayer(String arg, ReversiView view, CellModel.CellStatus color) {
    Player player;
    switch (arg) {
      case "human":
        player = new HumanPlayer(color, view);
        break;
      case "captureMost":
        player = new AIPlayer(color, new CaptureMost());
        break;
      case "avoidCorner":
        player = new AIPlayer(color, new AvoidCornerAdjacent(new PlayToCorner(new CaptureMost())));
        break;
      case "playToCorner":
        player = new AIPlayer(color, new PlayToCorner(new CaptureMost()));
        break;
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
    return player;
  }
}
