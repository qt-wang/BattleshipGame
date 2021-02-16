package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_cdnt_and_orientation() {
    Coordinate c1 = new Coordinate(10, 20);
    Placement p1 = new Placement(c1, 'v');
    assertEquals(c1, p1.getWhere());
    assertEquals('V', p1.getOrientation());
  }

  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 3);
    Coordinate c3 = new Coordinate(1, 2);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c1, 'V');
    Placement p3 = new Placement(c2, 'v');
    Placement p4 = new Placement(c2, 'a');
    Placement p5 = new Placement(c3, 'v');
    assertEquals(p1, p1);
    assertEquals(p1, p2);
    assertEquals(p1, p5);
    assertNotEquals(p1, p3);
    assertNotEquals(p1, p4);
    assertNotEquals(p1, c1);
  }

  @Test
  public void test_toString(){
     Coordinate c1 = new Coordinate(1, 2);
     Placement p1 = new Placement(c1, 'v');
     assertEquals("B2V", p1.toString());
  }

  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 3);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c1, 'V');
    Placement p3 = new Placement(c1, 'a');
    Placement p4 = new Placement(c2, 'v');
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
  }

  @Test
  public void test_string_constructor_valid_cases(){
    Placement p1 = new Placement("D4v");
    assertEquals(new Coordinate("D4"), p1.getWhere());
    assertEquals('V', p1.getOrientation());
  }

  @Test
  public void test_string_constructor_error_cases(){
    assertThrows(IllegalArgumentException.class, () -> new Placement("A"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("@0"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12"));
  }
}











