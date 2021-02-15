package edu.duke.qw99.battleship;

/**
 *This class inherits ShipDisplayInfo. It has two generic fields, myData and onHit, which are utilized to sbe shown on a board.
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  T myData;
  T  onHit;

  /**
   *Construct a SimpleShipDisplayInfo with a specified data and a specified hit.  
   */
  public SimpleShipDisplayInfo(T data, T hit){
    myData = data;
    onHit = hit;
  }

  /**
   *Get the Info shown on a board.
   *@param where is a specified coordinate.
   *@param hit is a symbol which indicates the coordinate was hit or not.
   *@return onHit if the coordinate was hit. myData if the coordinate was not hit.  
   */
  @Override
	public T getInfo(Coordinate where, boolean hit) {
	  if(hit){
      return onHit;
    }else{
      return myData;
    }
	}
  
}












