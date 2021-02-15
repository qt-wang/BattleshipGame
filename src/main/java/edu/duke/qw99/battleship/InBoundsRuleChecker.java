package edu.duke.qw99.battleship;
/**
 *This class inherits PlacementRuleChecker. It aims to check whether the ship is in bound.
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  /**
   *Construct a InBoundsRuleChecker with the next PlacementRuleChecker.
   */
  public InBoundsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }

  /**
   *Check if the ship is in bounds.
   *@param theShip is the specified ship.
   *@param theBoard is the specified board.
   *@return a string showing the ship is out of bounds. null if the ship is in bounds.  
   */
	@Override
	protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
	  Iterable<Coordinate> coordinateSet = theShip.getCoordinates();
    for(Coordinate c : coordinateSet){
      if(c.getRow() >= theBoard.getHeight()){
        return "bottomOut";
      }
      else if(c.getRow() < 0){
        return "topOut";
      }
      else if(c.getColumn() >= theBoard.getWidth()){
        return "rightOut";
      }
      else if(c.getColumn() < 0){
        return "leftOut";
      }
    }
    return null;
  }
}








