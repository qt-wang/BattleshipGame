package edu.duke.qw99.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> coordinateSet = theShip.getCoordinates();
    for (Coordinate c : coordinateSet) {
      if (theBoard.whatIsAt(c) != null) {
        return false;
      }
    }
    return true;
  }

}
