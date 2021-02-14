package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate[] expectedLocs){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, 'X');
    b.tryAddShip(testShip);
    assertEquals(expectedName, testShip.getName());
    for(Coordinate c : expectedLocs){
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
      assertEquals(null, testShip.getDisplayInfoAt(c, false));
    }
  }

  @Test
  public void test_createDestroyer() {
    Placement p = new Placement(new Coordinate(1, 2), 'V');
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeDestroyer(p);
    Coordinate[] c = {new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2)};
    checkShip(s, "Destroyer", 'd', c);
  }

  @Test
   public void test_createSubmarine() {
    Placement p = new Placement(new Coordinate(1, 2), 'H');
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeSubmarine(p);
    Coordinate[] c = {new Coordinate(1, 2), new Coordinate(1, 3)};
    checkShip(s, "Submarine", 's', c);
  }

  @Test
   public void test_createCarrier() {
    Placement p = new Placement(new Coordinate(1, 2), 'H');
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeCarrier(p);
    Coordinate[] c = {new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 5)};
    checkShip(s, "Carrier", 'c', c);
  }

    @Test
   public void test_createBattleship() {
    Placement p = new Placement(new Coordinate(1, 2), 'V');
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeBattleship(p);
    Coordinate[] c = {new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2)};
    checkShip(s, "Battleship", 'b', c);
  }

  @Test
   public void test_illegal_orientation() {
    Placement p = new Placement(new Coordinate(1, 2), 'M');
    V1ShipFactory v = new V1ShipFactory();
    assertThrows(IllegalArgumentException.class, () -> v.makeBattleship(p));
  }

}












