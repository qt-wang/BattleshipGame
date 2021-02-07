package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Integer> s = new SimpleShipDisplayInfo<Integer>(2, 1);
    assertEquals(1, s.getInfo(new Coordinate(1, 1), true));
    assertEquals(2, s.getInfo(new Coordinate(1, 1), false));
  }

}











