package edu.duke.qw99.battleship;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public String  tryAddShip(Ship<T> toAdd);

  public T whatIsAtForSelf(Coordinate where);
  
  public Ship<T> fireAt(Coordinate c);

  public T whatIsAtForEnemy(Coordinate where);

  public boolean isLose();

  public boolean moveShip(Ship<T> from, Ship<T> to, Coordinate des);
    
  public Ship<T> searchShip(Coordinate c);
}













