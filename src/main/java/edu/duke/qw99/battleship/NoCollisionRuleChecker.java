package edu.duke.qw99.battleship;
/**
 *This class inherits PlacementRuleChecker. It aims to check whether the ship collides with another ship.
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
   /**
   *Construct a NoCollisionRuleChecker with the next PlacementRuleChecker.
   */
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   *Check if the ship collides with anoher ship.
   *@param theShip is the specified ship.
   *@param theBoard is the specified board.  
   *@return a string showing collision. null if the ship does not collide with another ship.  
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> coordinateSet = theShip.getCoordinates();
    for (Coordinate c : coordinateSet) {
      if (theBoard.whatIsAtForSelf(c) != null) {
        return "collision";
      }
    }
    return null;
  }

}





