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
    assertEquals(false, i.checkPlacement(d, b));
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(0,0), 'V'));
    assertEquals(true, i.checkPlacement(s, b));
  }

}











