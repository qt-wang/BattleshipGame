package edu.duke.qw99.battleship;

public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  // more stuff
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  public String checkPlacement (Ship<T> theShip, Board<T> theBoard) {
    //if we fail our own rule: stop the placement is not legal
    /*  if (!checkMyRule(theShip, theBoard)) {
      return false;
      }*/
    if(checkMyRule(theShip, theBoard) == "bottomOut"){
      return "the ship goes off the bottom of the board";
    }
    if(checkMyRule(theShip, theBoard) == "topOut"){
      return "the ship goes off the top of the board";
    }
    if(checkMyRule(theShip, theBoard) == "rightOut"){
      return "the ship goes off the right of the board";
    }
    if(checkMyRule(theShip, theBoard) == "leftOut"){
      return "the ship goes off the left of the board";
    }
    if(checkMyRule(theShip, theBoard) == "collision"){
      return "the ship overlaps another ship";
    }
    //other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard); 
    }
    //if there are no more rules, then the placement is legal
    return null;
  }

}












