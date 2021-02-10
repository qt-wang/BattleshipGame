package edu.duke.qw99.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  
  public InBoundsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }

	@Override
	protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
	  Iterable<Coordinate> coordinateSet = theShip.getCoordinates();
    for(Coordinate c : coordinateSet){
      if(!(c.getRow() >= 0 && c.getRow() < theBoard.getHeight() && c.getColumn() >= 0 && c.getColumn() < theBoard.getWidth())){
        return false;
      }
    }
		return true;
	}

}












