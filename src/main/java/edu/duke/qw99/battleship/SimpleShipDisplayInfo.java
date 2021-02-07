package edu.duke.qw99.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  T myData;
  T  onHit;

  public SimpleShipDisplayInfo(T data, T hit){
    myData = data;
    onHit = hit;
  }
    
  @Override
	public T getInfo(Coordinate where, boolean hit) {
	  if(hit){
      return onHit;
    }else{
      return myData;
    }
	}
  
}












