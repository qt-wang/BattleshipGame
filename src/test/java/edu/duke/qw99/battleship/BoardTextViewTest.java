package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
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
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);
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

  @Test
  public void test_display_nonempty_3by4(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(4, 3);
    BoardTextView v1 = new BoardTextView(b1);
    // b1.tryAddShip(new BasicShip(new Coordinate(1, 2)));
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>("submarine", c, 1, 1, new SimpleShipDisplayInfo<Character>('s', '*'));
    b1.tryAddShip(rs);
    String expected = v1.makeHeader()+
                      "A | | | A\n"+
                      "B | |s| B\n"+
                      "C | | | C\n"+
                      v1.makeHeader();
    assertEquals(v1.displayMyOwnBoard(), expected);
  }
                     
}











