package edu.duke.qw99.battleship;

import java.util.HashMap;
import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  private String name;

  /**
   *Make a coordinate set that contains all coordinates a ship occupies.
   *@param upperLeft is the specified upperleft coordinate of a ship.
   *@param width is the specified width of a ship.  
   *@param width is the specified height of a ship. 
   **/
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> ans = new HashSet<Coordinate>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coordinate c = new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + j);
        ans.add(c);
      }
    }
    return ans;
  }

  static HashMap<Integer, Coordinate> mapPiecesWithIndex(Coordinate upperLeft, int width, int height){
    HashMap<Integer, Coordinate> ans = new HashMap<Integer, Coordinate>();
    int row = upperLeft.getRow();
    int col = upperLeft.getColumn();
    int count = 0;
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        count++;
        ans.put(count, new Coordinate(row + i, col + j));
      }
    }
    return ans;
  }

  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> sdpi,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), sdpi, enemyDisplayInfo, mapPiecesWithIndex(upperLeft, width, height));
    this.name = name;
  }

  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  /**
   *Get the name of the RectangleShip.
   *@return the name of the RectangleShip name
   */
  public String getName() {
    return name;
  }

}









