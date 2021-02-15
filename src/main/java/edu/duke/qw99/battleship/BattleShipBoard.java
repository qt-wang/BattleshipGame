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

  /**
   *Gets the width of the board.
   *@return the width of the board.  
   */  
  public int getWidth() {
    return width;
  }

  /**
   *Gets the height of the board.
   *@return the height of the board.  
   */ 
  public int getHeight() {
    return height;
  }

  /**
   *Constructs a BattleShipBoard with the specified width, height and missInfo.
   *@param w is the width of the newly constructed board.
   *@param h is the height of the newly constructed board.
   *@param missInfo is a symbol to mark one coordinate as a miss.  
   */  
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null)), missInfo);
  }
  
  
  /**
   *Constructs a BattleShipBoard with the specified width, height, placementchecker and missInfo
   *@param w is the width of the newly constructed board.
   *@param h is the height of the newly constructed board.
   *@param p is a placementRuleChecker to check whether the placement is legal.
   *@throws IllegalArgumentException if the width or height are less than or equal to zero.  
   *@param missInfo is a symbol to mark one coordinate as a miss.  
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

  /**
   *Try to add a ship to the BattleShipBoard.
   *@param toAdd is the ship we want to add.
   *@return if the placement is legal, return null. If the placement is illegal, return a string to illustrate the reason.  
   */
  public String tryAddShip(Ship<T> toAdd){
    if (placementChecker.checkPlacement(toAdd, this) == null) {
      myShips.add(toAdd);
      return null;
    }
    return placementChecker.checkPlacement(toAdd, this);
  }

  /**
   *Figure out what symbol should be on a specified coordinate on my board.
   *@param where is the coordinate.
   *@return the infomation on the specified coordinate
   */
  public T whatIsAtForSelf(Coordinate where)  {
        return whatIsAt(where, true);
  }

   /**
   *Figure out what symbol should be on a specified coordinate on my enemy's board.
   *@param where is the coordinate.
   *@return the infomation on the specified coordinate
   */
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   *Fire at a specified coordinate.
   *@param c is a specified coordinte we want to fire at.
   *@return if we miss, return null. If we do not miss, return the ship we hit.  
   */
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

  /**
   *Figure out the information on a specified coordinate on a board.
   *@param where is the specified coordinate.
   *@param isSelf is a boolean value to denote whether I want my board or my enemy's board.
   *@return the information on a specified coordinate on a board.  
   */
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

  /**
   *Check whether a player is lost or not.
   *@return true means he loses. false means he does not lose.  
   */
  public boolean isLose(){
    //If all ships has sunk, he will lose.
    //Otherwise, he does not lose.
    for(Ship<T> s : myShips){
      if(s.isSunk() == false){
        return false;
      }
    }
    return true;
  }


}












