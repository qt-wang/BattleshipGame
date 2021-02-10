package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    Coordinate c1 = new Coordinate(1, 2);
    HashSet<Coordinate> h1 = RectangleShip.makeCoords(c1, 1, 3);
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    Coordinate e1 = new Coordinate(1, 2);
    Coordinate e2 = new Coordinate(2, 2);
    Coordinate e3 = new Coordinate(3, 2);
    expected.add(e1);
    expected.add(e2);
    expected.add(e3);
    assertEquals(true, h1.contains(e1));
    assertEquals(true, h1.contains(e2));
    assertEquals(true, h1.contains(e3));
    assertEquals(expected, h1);
  }

  @Test
  public void test_RectangleShip_occupy() {
    RectangleShip<Character> r1 = new RectangleShip<Character>("submarine", new Coordinate(2, 3), 1, 3, 's', '*');
    assertEquals("submarine", r1.getName());
    assertEquals(true, r1.occupiesCoordinates(new Coordinate(2, 3)));
    assertEquals(true, r1.occupiesCoordinates(new Coordinate(3, 3)));
    assertEquals(true, r1.occupiesCoordinates(new Coordinate(4, 3)));
    assertEquals(false, r1.occupiesCoordinates(new Coordinate(2, 4)));
    assertEquals(false, r1.occupiesCoordinates(new Coordinate(5, 3)));

    RectangleShip<Character> r2 = new RectangleShip<Character>(new Coordinate(1, 2), 's', '*');
    assertEquals(true, r2.occupiesCoordinates(new Coordinate(1, 2)));
    assertEquals(false, r2.occupiesCoordinates(new Coordinate(1, 3)));
    assertEquals(false, r2.occupiesCoordinates(new Coordinate(2, 1)));
  }

  @Test
  public void test_RectangleShip_hit(){
    RectangleShip<Character> r1 = new RectangleShip<Character>("submarine", new Coordinate(2, 3), 1, 3, 's', '*');
    assertEquals(false, r1.wasHitAt(new Coordinate(2, 3)));
    assertEquals(false, r1.wasHitAt(new Coordinate(3, 3)));
    assertEquals(false, r1.wasHitAt(new Coordinate(4, 3)));
    assertThrows(IllegalArgumentException.class, ()->r1.wasHitAt(new Coordinate(5, 3)));

    r1.recordHitAt(new Coordinate(3,3));
    assertEquals(true, r1.wasHitAt(new Coordinate(3, 3)));
    assertEquals(false, r1.wasHitAt(new Coordinate(2, 3)));
    assertEquals(false, r1.wasHitAt(new Coordinate(4, 3)));             
  }

  @Test
  public void test_RectangleShip_isSunk() {
    RectangleShip<Character> r1 = new RectangleShip<Character>("submarine", new Coordinate(2, 3), 1, 3, 's', '*');
     assertEquals(false, r1.isSunk());
     r1.recordHitAt(new Coordinate(2,3));
     r1.recordHitAt(new Coordinate(3,3));
     assertEquals(false, r1.isSunk());
     r1.recordHitAt(new Coordinate(4,3));
     assertEquals(true, r1.isSunk());
     
  }

  @Test
  public void test_getCoordinates(){
    RectangleShip<Character> r = new RectangleShip<Character>("submarine", new Coordinate(2, 3), 1, 3, 's', '*');
    Set<Coordinate>  expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(3, 3));
    expected.add(new Coordinate(2, 3));
    expected.add(new Coordinate(4, 3));
    assertEquals(expected, r.getCoordinates());
    
  }
}
 











