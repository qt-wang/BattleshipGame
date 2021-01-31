package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
    Board b1 = new BattleShipBoard(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  
  @Test
  public void test_display_empty_2by2() {
    emptyBoardHelper(2, 2, " 0|1\n",
                     "A | A\n"+
                     "B | B\n");
  }

  @Test
  public void test_invalid_board_size(){
    Board wideBoard = new BattleShipBoard(11, 20);
    Board tallBoard = new BattleShipBoard(10, 27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_empty_3by2(){
    emptyBoardHelper(2, 3, " 0|1\n",
                     "A | A\n"+
                     "B | B\n"+
                     "C | C\n");
  }

  @Test
  public void test_display_empty_3by5(){
    emptyBoardHelper(5, 3, " 0|1|2|3|4\n",
                     "A | | | | A\n"+
                     "B | | | | B\n"+
                     "C | | | | C\n");
  }
                     
}
