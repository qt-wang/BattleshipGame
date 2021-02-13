package edu.duke.qw99.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  
  public InBoundsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }

	@Override
	protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
	  Iterable<Coordinate> coordinateSet = theShip.getCoordinates();
    /*    for(Coordinate c : coordinateSet){
      if(!(c.getRow() >= 0 && c.getRow() < theBoard.getHeight() && c.getColumn() >= 0 && c.getColumn() < theBoard.getWidth())){
        return false;
      }
    }
		return true;
    }*/
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








