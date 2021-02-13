package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checker() {
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> d = v.makeDestroyer(new Placement(new Coordinate(4, 4), 'V'));
    InBoundsRuleChecker<Character> i = new InBoundsRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(3, 3, i);
    assertEquals("the ship goes off the bottom of the board", i.checkPlacement(d, b));
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(0,0), 'V'));
    assertEquals(null, i.checkPlacement(s, b));
    Ship<Character> s2 = v.makeSubmarine(new Placement(new Coordinate(-1,0), 'V'));
    assertEquals("the ship goes off the top of the board", i.checkPlacement(s2, b));
    Ship<Character> s3 = v.makeSubmarine(new Placement(new Coordinate(0,-1), 'V'));
    assertEquals("the ship goes off the left of the board", i.checkPlacement(s3, b));
  }

}











