package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  
  @Test
  public void test_display_empty_2by2() {
    emptyBoardHelper(2, 2, "  0|1\n",
                     "A  |  A\n"+
                     "B  |  B\n");
  }

  @Test
  public void test_invalid_board_size(){
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_empty_3by2(){
    emptyBoardHelper(2, 3, "  0|1\n",
                     "A  |  A\n"+
                     "B  |  B\n"+
                     "C  |  C\n");
  }

  @Test
  public void test_display_empty_3by5(){
    emptyBoardHelper(5, 3, "  0|1|2|3|4\n",
                     "A  | | | |  A\n"+
                     "B  | | | |  B\n"+
                     "C  | | | |  C\n");
  }

  @Test
  public void test_display_nonempty_3by4(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    BoardTextView v1 = new BoardTextView(b1);
    // b1.tryAddShip(new BasicShip(new Coordinate(1, 2)));
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>("submarine", c, 1, 1, new SimpleShipDisplayInfo<Character>('s', '*'), new SimpleShipDisplayInfo<Character>('s', '*'));
    b1.tryAddShip(rs);
    String expected = v1.makeHeader()+
                      "A  | | |  A\n"+
                      "B  | |s|  B\n"+
                      "C  | | |  C\n"+
                      v1.makeHeader();
    assertEquals(v1.displayMyOwnBoard(), expected);
  }

  @Test
  public void test_displayMyOwnBoard(){
    String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(1, 0), 'H'));
    Ship<Character> d = v.makeDestroyer(new Placement(new Coordinate(0, 3), 'V'));
    b1.tryAddShip(s);
    b1.tryAddShip(d);
    assertEquals(myView, view.displayMyOwnBoard());  
  }

  @Test
  public void test_displayEnemyBoard(){
    String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(1, 0), 'H'));
    Ship<Character> d = v.makeDestroyer(new Placement(new Coordinate(0, 3), 'V'));
    b1.tryAddShip(s);
    b1.tryAddShip(d);
    b1.fireAt(new Coordinate(1, 2));
     String enemyView =
      "  0|1|2|3\n" +
      "A  | | |  A\n" +
      "B  | |X|  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";
    assertEquals(enemyView, view.displayEnemyBoard());  
  }

  @Test
  public void test_displayMyBoardWithEnemyNextToIt(){
     String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    BattleShipBoard<Character> b2 = new BattleShipBoard<>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    BoardTextView viewEnemy = new BoardTextView(b2);
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(1, 0), 'H'));
    Ship<Character> d = v.makeDestroyer(new Placement(new Coordinate(0, 3), 'V'));
    b1.tryAddShip(s);
    b2.tryAddShip(d);
    String enemyView =
      "     Your ocean                         Player B's ocean\n" +
      "  0|1|2|3                              0|1|2|3\n"+
      "A  | | |  A                          A  | | |  A\n" +
      "B s|s| |  B                          B  | | |  B\n" +
      "C  | | |  C                          C  | | |  C\n" +
      "  0|1|2|3                              0|1|2|3\n";
    String myHeader = "Your ocean";
    String enemyHeader = "Player B's ocean";
    assertEquals(enemyView, view.displayMyBoardWithEnemyNextToIt(viewEnemy, myHeader, enemyHeader));

    String enemyView2 =
      "     Your ocean                         Player B's ocean\n" +
      "  0|1|2|3                              0|1|2|3\n"+
      "A  | | |  A                          A  | | |  A\n" +
      "B s|s| |  B                          B  | |X|  B\n" +
      "C  | | |  C                          C  | | |  C\n" +
      "  0|1|2|3                              0|1|2|3\n";
    b2.fireAt(new Coordinate(1, 2));
    assertEquals(enemyView2, view.displayMyBoardWithEnemyNextToIt(viewEnemy, myHeader, enemyHeader));
    
  }
}












