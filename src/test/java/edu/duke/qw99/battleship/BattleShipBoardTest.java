package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions(){
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  private <T> void  checkWhatIsAtBoard(BattleShipBoard<T> b, T [][] expected){
    for(int i = 0; i < b.getHeight(); i++){
      for(int j = 0; j < b.getWidth(); j++){
        Coordinate c = new Coordinate(j, i);
        assertEquals(expected[j][i], b.whatIsAtForSelf(c));
      }
    }
  }

  @Test
  public void test_whatIsAt(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    Character[][] expected1 = { {null, null}, {null, null} };
    checkWhatIsAtBoard(b1, expected1);
    // BasicShip bs1 = new BasicShip(new Coordinate(0, 0));
    RectangleShip<Character> bs1 = new RectangleShip<Character>("submarine", new Coordinate(0, 0), 1, 1,
                                                                new SimpleShipDisplayInfo<Character>('s', '*'), new SimpleShipDisplayInfo<Character>('s', '*'));
    b1.tryAddShip(bs1);
    Character[][] expected2 = { { 's', null }, { null, null } };
    checkWhatIsAtBoard(b1, expected2);
  }

  @Test
  public void test_invalidAdd(){
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> c = v.makeCarrier(new Placement(new Coordinate(2, 2), 'D'));
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    assertEquals("the ship goes off the bottom of the board", b.tryAddShip(c));
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(3, 3), 'H'));
    assertEquals("the ship goes off the right of the board", b.tryAddShip(s));
    Ship<Character> s2 = v.makeSubmarine(new Placement(new Coordinate(1, 1), 'H'));
    assertEquals(null, b.tryAddShip(s2));
    Ship<Character> s3 = v.makeSubmarine(new Placement(new Coordinate(1, 2), 'H'));
    assertEquals("the ship overlaps another ship", b.tryAddShip(s3));
  }

  @Test
  public void test_fireAt(){
    V1ShipFactory v = new V1ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(2, 2), 'H'));
    b.tryAddShip(s);
    b.fireAt(new Coordinate(2, 2));
    assertSame(s, b.fireAt(new Coordinate(2, 2)));
    assertFalse(s.isSunk());
    b.fireAt(new Coordinate(2, 3));
    assertSame(s, b.fireAt(new Coordinate(2, 3)));
    assertTrue(s.isSunk());
    assertSame(null, b.fireAt(new Coordinate(2, 4)));
    
  }

  @Test
  public void test_whatIsForEnemy(){
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(2, 2), 'H'));
    Board<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
    b.tryAddShip(s);
    b.fireAt(new Coordinate(3, 2));
    assertEquals('X', b.whatIsAtForEnemy(new Coordinate(3, 2)));
  }
    
}













