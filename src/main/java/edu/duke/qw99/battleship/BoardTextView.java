package edu.duke.qw99.battleship;
/**
 *This class handles textual display of a Board(i.e., converting it to a string to
 *to the user).
 *It supports two ways to display the Board:
 *one for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {
  /**
   *The Board to display
   */
  private final Board toDisplay;
  /**
   *Constructs a BoardView, given the board it will display.
   *@param toDisplay is the Board to display
   */
  public BoardTextView(Board toDisplay){
    this.toDisplay = toDisplay;
    if(toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26){
      throw new IllegalArgumentException(
                                         "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  
  public String displayMyOwnBoard(){
    StringBuilder ans = new StringBuilder();
    String header = makeHeader();
    ans.append(header);
    for(int i = 0; i < toDisplay.getHeight(); i++){
      String s = Character.toString('A' + i);
      ans.append(s);
      for(int j = 0; j < toDisplay.getWidth(); j++){
        ans.append(" ");
        if(j != toDisplay.getWidth() - 1){
          ans.append("|");
        }
        else{
          ans.append(s);
          ans.append("\n");
        }
      }
    }
    ans.append(header);
    return ans.toString();
  }

  /**
   *This makes the header line, e.g. 0|1|2|3|4\n
   *@return the String that is the header line for the given board.  
   */
  String makeHeader(){
    StringBuilder ans = new StringBuilder(" ");
    String sep = "";
    for(int i = 0; i < toDisplay.getWidth(); i++){
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }
}







