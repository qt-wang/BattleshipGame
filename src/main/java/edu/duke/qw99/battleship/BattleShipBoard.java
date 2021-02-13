package edu.duke.qw99.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashSet<Coordinate> enemyMisses;
  final T missInfo;
  
  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null)), missInfo);
  }


  /**
   *Constructs a BattleShipBoard with the specified width and height
   *@param w is the width of the newly constructed board.
   *@param h is the height of the newly constructed board.
   *@throws IllegalArgumentException if the width or height are less than or equal to zero.  
   */
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> p, T missInfo){
    if(w <= 0){
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if(h <= 0){
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    placementChecker = p;
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
  }

  public String tryAddShip(Ship<T> toAdd){
    if (placementChecker.checkPlacement(toAdd, this) == null) {
      myShips.add(toAdd);
      return null;
    }
    return placementChecker.checkPlacement(toAdd, this);
  }

  public T whatIsAtForSelf(Coordinate where)  {
    return whatIsAt(where, true);
  }
  
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  public Ship<T> fireAt(Coordinate c){
    for(Ship<T> s : myShips){
      if(s.occupiesCoordinates(c) == true){
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;    
  }

  protected T whatIsAt(Coordinate where, boolean isSelf){
    for(Ship<T> s:myShips){
      if(s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where, isSelf);
      }
      else{
        if(isSelf == false){
          if(enemyMisses.contains(where)){
            return missInfo;
          }
        }
      }
    }
    return null;
  }


}












