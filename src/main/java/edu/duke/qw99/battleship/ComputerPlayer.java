package edu.duke.qw99.battleship;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

/**
 *This class defines all behaviours and attributes of a computer player.
 */
public class ComputerPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  private int moveNum;

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
  }

  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }

  /**
   *Construct a computer player.
   *@param name is the name of the computer player.
   *@param theBoard is the computer player's board.
   *@param out is printstream.
   *@param shipFctory is an abstract ship factory.
   */
  public ComputerPlayer(String name, Board<Character> theBoard, PrintStream out,
      AbstractShipFactory<Character> shipFctory) {
    this.name = name;
    this.theBoard = theBoard;
    this.out = out;
    this.shipFactory = shipFctory;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationList();
    setupShipCreationMap();
    this.moveNum = 3;
    this.view = new BoardTextView(theBoard);
  }

  /**
   *Create a random coordinate.
   *@return a random coordinate.  
   */
  public Coordinate randomCoordinate() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    sb.append((char) (random.nextInt(20) + 'A'));
    sb.append(Integer.toString(random.nextInt(10)));
    return new Coordinate(sb.toString());
  }

  /**
   *Create a random placement.
   *@return a random placement.  
   */
  public Placement randomPlacement() {
    String str = "VHUDLR";
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    sb.append((char) (random.nextInt(20) + 'A'));
    sb.append(Integer.toString(random.nextInt(10)));
    sb.append(str.charAt(random.nextInt(6)));
    return new Placement(sb.toString());
  }

   /*
   * This function places a ship according to a random placement, and  adds a ship to the board.
   * 
   * @param shipName is the name of the ship you want to place.
   * 
   * @param createFn is lambda expression. It can create a ship according the
   * input.
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) {
    Ship<Character> ship = null;
    String str;
    while (true) {
      Placement p = randomPlacement();
      if (shipName == "Submarine") {
        if (p.getOrientation() != 'H' && p.getOrientation() != 'V') {
          continue;
        }
        ship = createFn.apply(p);
        str = theBoard.tryAddShip(ship);
        if (str != null) {
          continue;
        } else {
          break;
        }
      } else if (shipName == "Destroyer") {
        if (p.getOrientation() != 'H' && p.getOrientation() != 'V') {
          continue;
        }
        ship = createFn.apply(p);
        str = theBoard.tryAddShip(ship);
        if (str != null) {
          continue;
        } else {
          break;
        }
      } else if (shipName == "Battleship") {
        if (p.getOrientation() != 'U' && p.getOrientation() != 'D' && p.getOrientation() != 'R'
            && p.getOrientation() != 'L') {
          continue;
        }
        ship = createFn.apply(p);
        str = theBoard.tryAddShip(ship);
        if (str != null) {
          continue;
        } else {
          break;
        }
      } else if (shipName == "Carrier") {
        if (p.getOrientation() != 'U' && p.getOrientation() != 'D' && p.getOrientation() != 'R'
            && p.getOrientation() != 'L') {
          continue;
        }
        ship = createFn.apply(p);
        str = theBoard.tryAddShip(ship);
        //If the ship cannot be added to the placement, then create another random placement again.
        if (str != null) {
          continue;
        } else {
          break;
        }

      }
    }
  }

  /**
   *This function adds all those 10 ships to the board, but does not show the board.
   */
  public void doPlacement() {
    for (String s : shipsToPlace) {
      doOnePlacement(s, shipCreationFns.get(s));
    }
  }

  /**
   * This function allows computer player to fire at a random coordinate. It does not show anything.
   * 
   * @param enemyBoard     is my enemy's board.
   */
  public void fire(Board<Character> enemyBoard) {
    Coordinate c = randomCoordinate();
    Ship<Character> ship = enemyBoard.fireAt(c);
    if (ship == null) {
      out.println("Computer missed!\n");
    } else {
      out.println("Computer hit a " + ship.getName() + "!\n");
    }
  }

  /**
   *Move a computer player's ship to another new random place.  
   */
  public void move() {
    Coordinate c;
    while (true) {
      c = randomCoordinate();
      if (theBoard.searchShip(c) == null) {
        continue;
      } else {
        break;
      }
    }
    Ship<Character> ship = theBoard.searchShip(c);
    Ship<Character> newShip = ship;
    while (true) {
      Placement p = randomPlacement();
      if (ship.getName() == "Submarine") {
        if (p.getOrientation() != 'H' && p.getOrientation() != 'V') {
          continue;
        }
        newShip = shipFactory.makeSubmarine(p);
        break;
      } else if (ship.getName() == "Destroyer") {
        if (p.getOrientation() != 'H' && p.getOrientation() != 'V') {
          continue;
        }
        newShip = shipFactory.makeDestroyer(p);
        break;
      } else if (ship.getName() == "Battleship") {
        if (p.getOrientation() != 'U' && p.getOrientation() != 'D' && p.getOrientation() != 'R'
            && p.getOrientation() != 'L') {
          continue;
        }
        newShip = shipFactory.makeBattleship(p);
        break;
      } else if (ship.getName() == "Carrier") {
        if (p.getOrientation() != 'U' && p.getOrientation() != 'D' && p.getOrientation() != 'R'
            && p.getOrientation() != 'L') {
          continue;
        }
        newShip = shipFactory.makeCarrier(p);
        break;
      }
    }
    theBoard.moveShip(ship, newShip, c);
    moveNum--;
  }

  /**
   *Include all choices that a computer player has. Computer olayer could pick one of them randomly.
   *@param enemyBoard is the enemy's board.
   */
  public void doAll(Board<Character> enemyBoard) {
    // doPlacement();
    while (true) {
      Random ran = new Random();
      int choice = ran.nextInt(2);
      if (choice == 0) {
        fire(enemyBoard);
        break;
      } else {
        if (choice == 1) {
          if (moveNum > 0) {
            move();
            out.println("Computer uses a special action.");
            break;
          } else {
            continue;
          }
        }
      }
    }
  }
}
