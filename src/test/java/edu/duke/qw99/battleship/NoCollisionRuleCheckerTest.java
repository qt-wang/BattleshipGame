package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_NoCollision() {
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> d = v.makeDestroyer(new Placement(new Coordinate(2, 2), 'V'));
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(3, 1), 'H'));
    NoCollisionRuleChecker<Character> n = new NoCollisionRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(5, 5, n);
    b.tryAddShip(s);
    assertEquals(false, n.checkPlacement(d, b));
    Ship<Character> s2 = v.makeSubmarine(new Placement(new Coordinate(3, 3), 'H'));
    assertEquals(true, n.checkPlacement(s2, b));
  }

  @Test
  public void test_bothRules() {
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> d = v.makeDestroyer(new Placement(new Coordinate(2, 2), 'V'));
    Ship<Character> s = v.makeSubmarine(new Placement(new Coordinate(3, 1), 'H'));
    Ship<Character> c = v.makeCarrier(new Placement(new Coordinate(4, 4),'V'));
    InBoundsRuleChecker<Character> i = new InBoundsRuleChecker<Character>(null);
     NoCollisionRuleChecker<Character> n = new NoCollisionRuleChecker<Character>(i);
    Board<Character> b = new BattleShipBoard<Character>(5, 5, n);
    assertEquals(false, n.checkPlacement(c, b));
    assertEquals(true, n.checkPlacement(d, b));
    b.tryAddShip(d);
    assertEquals(false, n.checkPlacement(s, b));
     
  }

}











