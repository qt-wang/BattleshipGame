package edu.duke.qw99.battleship;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicShip<T> implements Ship<T>{
  //private final Coordinate myLocation;
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo){
    myPieces = new HashMap<Coordinate, Boolean>();
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    for(Coordinate c : where){
      myPieces.put(c, false);
    }
    }
    
  protected void checkCoordinateInThisShip(Coordinate c){
    if(myPieces.containsKey(c) == false){
      throw new IllegalArgumentException("This coordinate is not in this ship");
    }
    else{
      return;
    }
  }
  
	@Override
	public boolean occupiesCoordinates(Coordinate where) {
    //return where.equals(myLocation);
    return myPieces.containsKey(where);
	}

	@Override
	public boolean isSunk() {
    for(Map.Entry<Coordinate, Boolean> entry : myPieces.entrySet()){
      boolean hit = entry.getValue();
      if(hit == false){
        return false;
      }
    }
		return true;
	}

	@Override
	public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
	}

	@Override
	public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
		return myPieces.get(where);
	}

	@Override
	public T  getDisplayInfoAt(Coordinate where, boolean myShip) {
    if(myShip == true){
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    else{
      return enemyDisplayInfo.getInfo(where, wasHitAt(where));
    }
	}

  @Override
  public Iterable<Coordinate> getCoordinates(){
    return myPieces.keySet();
  }
}












