package view;

import java.util.ArrayList;
import java.util.List;

import model.ReadOnlyReversi;

/**
 * Represents a mock version of SimpleReversiView for testing purposes.
 */
public class MockReversiView extends SimpleReversiView {

  private final List<String> transcript;

  /**
   * Constructs a SimpleReversiView for the specified ReadOnlyReversi model.
   *
   * @param model The ReadOnlyReversi model to be displayed.
   */
  public MockReversiView(ReadOnlyReversi model) {
    super(model);
    this.transcript = new ArrayList<>();
  }

  /**
   * Alerts the user they have attempted an invalid move.
   */
  public void alertUser(String message) {
    this.transcript.add("Message Sent!");
  }

  /**
   * Upon the game ending, display the game winning message which includes: the reason the game
   * is over, which player won, and the ending score.
   *
   * @param message     the message to be displayed regarding who won.
   * @param reasonEnded the reason as to why the game ended according to the rules of Reversi.
   */
  public void displayWinner(String message, String reasonEnded) {
    this.transcript.add("Message Sent!");
  }

  public List<String> getTranscript() {
    return this.transcript;
  }

}
