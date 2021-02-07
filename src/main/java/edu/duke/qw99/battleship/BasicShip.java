package edu.duke.qw99.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T>{
  //private final Coordinate myLocation;
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo){
    myPieces = new HashMap<Coordinate, Boolean>();
    this.myDisplayInfo = myDisplayInfo;
    for(Coordinate c : where){
      myPieces.put(c, false);
    }
    }
    

	@Override
	public boolean occupiesCoordinates(Coordinate where) {
    //return where.equals(myLocation);
    return myPieces.containsKey(where);
	}

	@Override
	public boolean isSunk() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recordHitAt(Coordinate where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean wasHitAt(Coordinate where) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T  getDisplayInfoAt(Coordinate where) {
		return myDisplayInfo.getInfo(where, false);
	}
  
}












