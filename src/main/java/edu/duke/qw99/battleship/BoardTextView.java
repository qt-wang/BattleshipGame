package edu.duke.qw99.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board(i.e., converting it to a string
 * to to the user). It supports two ways to display the Board: one for the
 * player's own board, and one for the enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn){
    StringBuilder ans = new StringBuilder();
    String header = makeHeader();
    ans.append(header);
    for (int row = 0; row < toDisplay.getHeight(); row++) {
      String s = Character.toString('A' + row);
      ans.append(s);
      ans.append(" ");
      for (int column = 0; column < toDisplay.getWidth(); column++) {
        Coordinate c = new Coordinate(row, column);
        if (getSquareFn.apply(c) != null) {
          ans.append(getSquareFn.apply(c));
        } else {
          ans.append(" ");
        }
        if (column != toDisplay.getWidth() - 1) {
          ans.append("|");
        } else {
          ans.append(" ");
          ans.append(s);
          ans.append("\n");
        }
      }
    }
    ans.append(header);
    return ans.toString();
  }

  /**
   *Display my own board.
   *@return a string that shows my board.  
   */  
  public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }

   /**
   *Display my enemy's board.
   *@return a string that shows my enemy's board.  
   */  
  public String displayEnemyBoard(){
     return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
  }
  
  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board.
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder(" ");
    String sep = " ";
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   *Display my own board on the right, and display my enemy's board on the left.
   *@param enemyView is the view of the enemy's board.
   *@param myHeader is a string shown above my board.  
   *@param enemyHeader is s string shown above my enemy's board. 
   *@return a string that shows my board with my enemy's board.  
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String[] linesMyBoard = this.displayMyOwnBoard().split("\n");
    String[] linesEnemyBoard = enemyView.displayEnemyBoard().split("\n");
    StringBuilder ans = new StringBuilder();
    int w = this.toDisplay.getWidth();
    int h = this.toDisplay.getHeight();
    ans.append("     " + myHeader);
    for (int i = 0; i < 2 * w + 32 - 5 - myHeader.length(); i++) {
      ans.append(" ");
    }
    ans.append(enemyHeader);
    ans.append("\n");
    for (int j = 0; j < h+2; j++) {
      ans.append(linesMyBoard[j]);
      if(j == 0 || j == h+1){
        ans.append("  ");
      }
      for (int m = 0; m < 26; m++) {
          ans.append(" ");
        }
      ans.append(linesEnemyBoard[j]);
      ans.append("\n");
    }
    return ans.toString();
  }
}










