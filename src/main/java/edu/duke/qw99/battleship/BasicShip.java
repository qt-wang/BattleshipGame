package edu.duke.qw99.battleship;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicShip<T> implements Ship<T>{
  //private final Coordinate myLocation;
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  

  /**
   *Construct a BasicShip.
   *@param where is a coordinate set, which contains all coordinates this ship occupies,
   *@param myDisplayInfo represents how to mark a miss and a hit of my ships.
   *@param enemyDisplayInfo represents how to mark a miss and a hit of my enemy's ships.
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo){
    myPieces = new HashMap<Coordinate, Boolean>();
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    for(Coordinate c : where){
      myPieces.put(c, false);
    }
    }

  /**
   *Check whether a coordinate is in this ship.
   *@param c is a specified coordinate.  
   *@throws if the specied coordinate is not in this ship, throw an IllegalArgumentException.  
   */
  protected void checkCoordinateInThisShip(Coordinate c){
    if(myPieces.containsKey(c) == false){
      throw new IllegalArgumentException("This coordinate is not in this ship");
    }
    else{
      return;
    }
  }

  /**
   *Check whether this ship occupies a specified coordinate.
   *@param where is a specified coordinate.
   *@return true if the ship occupies the coordinate. false if the ship does not occupies the coordinate.  
   */
	@Override
	public boolean occupiesCoordinates(Coordinate where) {
    //return where.equals(myLocation);
    return myPieces.containsKey(where);
	}

  /**
   *Check whether this ship is sunk or not.
   *@return true if the ship is sunk. false if the ship is not sunk.  
   */
	@Override
	public boolean isSunk() {
    //If all coordinates the ship occupies has been hit, it will sink.
    //Otherwise, it will not sink.
    for(Map.Entry<Coordinate, Boolean> entry : myPieces.entrySet()){
      boolean hit = entry.getValue();
      if(hit == false){
        return false;
      }
    }
		return true;
	}

  /**
   *Record the coordinate that has been hit.
   *@param where is the hit coordinate.  
   */
	@Override
	public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
	}

  /**
   *Check whether a coordinate was hit or not.
   *@param where is a coordinate.
   *@return true if the specified coordinate was hit. false if the specified coordinate was not hit.  
   */
	@Override
	public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
		return myPieces.get(where);
	}

  /**
   *Get the info at a specified coordinate on a board.
   *@param where is a specified coordinate.
   *@param myShip is a symbol to denote whether I want my board or my enemy's board.
   *@return the hit or miss info at the specified coordinate on my board or my enemy's board. 
   */
	@Override
	public T  getDisplayInfoAt(Coordinate where, boolean myShip) {
    if(myShip == true){
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    else{
      return enemyDisplayInfo.getInfo(where, wasHitAt(where));
    }
	}

  /**
   *Get all coordinate that the ship occupies.
   *@return the coordinate set that contains all occupied coordinates.
   */
  @Override
  public Iterable<Coordinate> getCoordinates(){
    return myPieces.keySet();
  }
}












