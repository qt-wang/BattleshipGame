package edu.duke.qw99.battleship;

import java.util.HashSet;

public class battleshipShip<T> extends BasicShip<T> {
  private String name;

  /**
   *Make a coordinate set that contains all coordinates a ship occupies.
   *@param upperLeft is the specified upperleft coordinate of a ship.
   *@param width is the specified width of a ship.  
   *@param width is the specified height of a ship. 
   **/
  static HashSet<Coordinate> makeCoords(Placement p) {
    int row = p.getWhere().getRow();
    int col = p.getWhere().getColumn();
    HashSet<Coordinate> ans = new HashSet<Coordinate>();
    if(p.getOrientation() == 'U'){
      ans.add(new Coordinate(row, col + 1));
      ans.add(new Coordinate(row + 1, col));
      ans.add(new Coordinate(row + 1, col + 1));
      ans.add(new Coordinate(row + 1, col + 2));
    }
    if(p.getOrientation() == 'R'){
      ans.add(new Coordinate(row, col));
      ans.add(new Coordinate(row + 1, col));
      ans.add(new Coordinate(row + 1, col + 1));
      ans.add(new Coordinate(row + 2, col));
    }
    if(p.getOrientation() == 'D'){
      ans.add(new Coordinate(row, col));
      ans.add(new Coordinate(row, col + 1));
      ans.add(new Coordinate(row, col + 2));
      ans.add(new Coordinate(row + 1, col + 1));
    }
    if(p.getOrientation() == 'L'){
      ans.add(new Coordinate(row, col + 1));
      ans.add(new Coordinate(row + 1, col));
      ans.add(new Coordinate(row + 1, col + 1));
      ans.add(new Coordinate(row + 2, col + 1));
    }
    return ans;
  }

  public battleshipShip(String name, Placement p, ShipDisplayInfo<T> sdpi,  ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(p), sdpi, enemyDisplayInfo);
    this.name = name;
  }

  public battleshipShip(String name, Placement p,  T data, T onHit) {
    this(name, p, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

  /**
   *Get the name of the RectangleShip.
   *@return the name of the RectangleShip name
   */
  public String getName() {
    return name;
  }
}












