package edu.duke.qw99.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

/**
 *This class defines all behaviours and attributes of a player.
 */
public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  private int scanNum;
  private int moveNum;

  public int getScanNum() {
    return scanNum;
  }

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
   * Construct a TextPlayer.
   */
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFctory) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFctory;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationList();
    setupShipCreationMap();
    this.scanNum = 3;
    this.moveNum = 3;
  }

  /**
   * This function prints out the prompt message and reads from input
   * 
   * @param prompt is a message.
   * @return a placement that was read by inputReader from the input.
   */
  public Placement readPlacement(String prompt) throws IOException {
    String s;
    while (true) {
      out.println(prompt);
      s = inputReader.readLine();
      if (s == null) {
        throw new EOFException("EOF");
      }
      if (s.length() == 3 && (s.charAt(0) >= 'A' && s.charAt(0) <= 'T' || s.charAt(0) >= 'a' && s.charAt(0) <= 't')
          && (s.charAt(2) >= 'A' && s.charAt(2) <= 'Z' || s.charAt(2) >= 'a' && s.charAt(2) <= 'z')
          && s.charAt(1) >= '0' && s.charAt(1) <= '9') {
        break;
      } else {
        out.println("please input a valid placement!");
      }
    }
    return new Placement(s);
  }

  /*
   * This function place a ship according to player's input. It shows prompt
   * messages and reads from the input, and adds a ship to the board.
   * 
   * @param shipName is the name of the ship you want to place.
   * 
   * @param createFn is lambda expression. It can create a ship according the
   * input.
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    String prompt = "Player " + this.name + " Where would you like to place a " + shipName + "?";
    int flag = 0;
    while (flag == 0) {
      try {
        Placement p = readPlacement(prompt);
        Ship<Character> s = createFn.apply(p);
        String str = theBoard.tryAddShip(s);
        if (str != null) {
          throw new IllegalArgumentException(str);
        }
        flag = 1;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
      }
    }
    out.println(view.displayMyOwnBoard() + "\n" + "Player " + this.name
        + ": you are going to place the following ships (which are all" + "\n"
        + "rectangular). For each ship, type the coordinate of the upper left" + "\n"
        + "side of the ship, followed by either H (for horizontal) or V (for" + "\n"
        + "vertical).  For example M4H would place a ship horizontally starting" + "\n"
        + "at M4 and going to the right.  You have" + "\n" + "\n" + "2 Submarines ships that are 1x2 " + "\n"
        + "3 Destroyers that are 1x3" + "\n" + "3 Battleships that are 1x4" + "\n" + "2 Carriers that are 1x6" + "\n");
  }

  /**
   * This function does following things: (a) display the starting (empty) board
   * (b) print the instructions message (from the README, but also shown again
   * near the top of this file) (c) call doOnePlacement to place one ship
   */
  public void doPlacementPhase() throws IOException {
    view.displayMyOwnBoard();
    out.println(view.displayMyOwnBoard() + "\n" + "Player " + this.name
        + ": you are going to place the following ships (which are all" + "\n"
        + "rectangular). For each ship, type the coordinate of the upper left" + "\n"
        + "side of the ship, followed by either H (for horizontal) or V (for" + "\n"
        + "vertical).  For example M4H would place a ship horizontally starting" + "\n"
        + "at M4 and going to the right.  You have" + "\n" + "\n" + "2 Submarines ships that are 1x2 " + "\n"
        + "3 Destroyers that are 1x3" + "\n" + "3 Battleships that are 1x4" + "\n" + "2 Carriers that are 1x6" + "\n");
    // doOnePlacement("Destroyer", shipCreationFns.get("Destroyer"));
    for (String s : shipsToPlace) {
      doOnePlacement(s, shipCreationFns.get(s));
    }
  }

  /**
   * This function allows player to play only one turn. It shows prompt messges
   * and reads from the input and attack enemy's board.
   * 
   * @param enemyBoard     is my enemy's board.
   * @param enemyBoardView is my enemy's view.
   * @param enemyName      is my enemy's name.
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyBoardView, String enemyName)
      throws IOException {
    out.println("Player " + this.name + "'s turn:\n");
    String myHeader = "Your ocean";
    String enemyHeader = "Player " + enemyName + "'s ocean";
    out.println(this.view.displayMyBoardWithEnemyNextToIt(enemyBoardView, myHeader, enemyHeader));
    String s;
    while (true) {
      out.println("Player " + this.name + ": please write a coordinate to fire at\n");
      s = inputReader.readLine();
      if ((s.charAt(0) >= 'A' && s.charAt(0) <= 'T' || s.charAt(0) >= 'a' && s.charAt(0) <= 't') && s.length() == 2
          && s.charAt(1) >= '0' && s.charAt(1) <= '9') {
        break;
      } else {
        out.println("Please input a valid coordinate");
      }
    }
    Coordinate c = new Coordinate(s);
    Ship<Character> ship = enemyBoard.fireAt(c);
    if (ship == null) {
      out.println("You missied!\n");
    } else {
      out.println("You hit a " + ship.getName() + "!\n");
    }
  }

  /**
   *Move a player's ship to another place.  
   */
  public void move() throws IOException {
    String s;
    Coordinate c;
    while (true) {
      out.println("Player " + this.name + ": please input a coordinate which is a part of the ship you want to move");
      s = inputReader.readLine();
      //Check if the coordinate user inputs is valid.
      if ((s.charAt(0) >= 'A' && s.charAt(0) <= 'T' || s.charAt(0) >= 'a' && s.charAt(0) <= 't') && s.length() == 2
          && s.charAt(1) >= '0' && s.charAt(1) <= '9') {
        c = new Coordinate(s);
        //Check whether there is a ship at the coordinate user inputs.
        if (theBoard.searchShip(c) == null) {
          out.println("No ship at this coordinate, please change a coodinate.");
          continue;
        }
        break;
      } else {
        out.println("Please input a valid coordinate");
      }
    }
    Ship<Character> ship = theBoard.searchShip(c);
    Ship<Character> newShip = ship;
    while (true) {
      String prompt = "Player " + this.name
          + ": please input a new placement, location + orientation(please use capital letters):";
      Placement p = readPlacement(prompt);
      //Create a new ship at the placement user inputs.
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
    //If the new ship cannot be placed to the placement the user inputs.
    if (theBoard.moveShip(ship, newShip, c) == false) {
      out.println("You cannot put the ship here. You waste a move chance.");
    }
    moveNum--;
    out.println(view.displayMyOwnBoard());
  }

  /**
   *considers a certain pattern around (and including) the center (C) that the player inputs,
   *and reports on the number of squares occupied by each type of ship in that region.  
   *@param enemyBoard is the enemy's board.  
   */
  public void scan(Board<Character> enemyBoard) throws IOException {
    int submarineNum = 0;
    int destroyerNum = 0;
    int battleshipNum = 0;
    int carrierNum = 0;
    String s;
    while (true) {
      out.println("Player " + this.name + ": please input a center coordinate of a sonar scan");
      s = inputReader.readLine();
      //Check if the coordinate the player inputs is legal.
      if ((s.charAt(0) >= 'A' && s.charAt(0) <= 'T' || s.charAt(0) >= 'a' && s.charAt(0) <= 't') && s.length() == 2
          && s.charAt(1) >= '0' && s.charAt(1) <= '9') {
        break;
      } else {
        out.println("Please input a valid coordinate");
      }
    }
    Coordinate c = new Coordinate(s);
    int row = c.getRow();
    int col = c.getColumn();
    // Search in a triangle area.
    for (int i = -3; i <= 0; i++) {
      for (int j = -3 - i; j <= 3 + i; j++) {
         //Ignore those points outside the board.
        if ((row + i) < 0 || (row + i) >= enemyBoard.getHeight() || (col + j) < 0
            || (col + j) >= enemyBoard.getWidth()) {
          continue;
        } else {
          if (enemyBoard.whatIsAtForSelf(new Coordinate(row + i, col + j)) == null) {
            continue;
          }
          char shipName = enemyBoard.whatIsAtForSelf(new Coordinate(row + i, col + j));
          if (shipName == 's') {
            submarineNum++;
          }
          if (shipName == 'd') {
            destroyerNum++;
          }
          if (shipName == 'b') {
            battleshipNum++;
          }
          if (shipName == 'c') {
            carrierNum++;
          }
        }
      }
    }
    // Search in an inverse triangle area.
    for (int m = 1; m <= 3; m++) {
      for (int n = m - 3; n <= 3 - m; n++) {
        //Ignore those points outside the board.
        if ((row + m) < 0 || (row + m) >= enemyBoard.getHeight() || (col + n) < 0
            || (col + n) >= enemyBoard.getWidth()) {
          continue;
        } else {
          if (enemyBoard.whatIsAtForSelf(new Coordinate(row + m, col + n)) == null) {
            continue;
          }
          char shipName = enemyBoard.whatIsAtForSelf(new Coordinate(row + m, col + n));
          if (shipName == 's') {
            submarineNum++;
          }
          if (shipName == 'd') {
            destroyerNum++;
          }
          if (shipName == 'b') {
            battleshipNum++;
          }
          if (shipName == 'c') {
            carrierNum++;
          }
        }
      }
    }
    scanNum--;
    out.println("Submarines occupy " + submarineNum + " squares\n" + "Destroyers occupy " + destroyerNum + " squares\n"
        + "Battleships occupy " + battleshipNum + " squares\n" + "Carriers occupy " + carrierNum + " squares\n");
  }

  /**
   *Include all choices that a player has. Player could pick one of them.
   *@param enemyBoard is the enemy's board.
   *@param enemyBoardView is the view of enemy's board.
   *@param enemyName is the name of enemy.  
   */
  public void doAll(Board<Character> enemyBoard, BoardTextView enemyBoardView, String enemyName) throws IOException {
    while (true) {
      out.println("Player " + this.name + "'s turn:\n");
      String myHeader = "Your ocean";
      String enemyHeader = "Player " + enemyName + "'s ocean";
      out.println(this.view.displayMyBoardWithEnemyNextToIt(enemyBoardView, myHeader, enemyHeader));
      out.println("Possible actions for Player " + this.name + ":\n" + "F Fire at a square\n"
          + "M Move a ship to another square (" + this.moveNum + " remaining)\n" + "S Sonar scan (" + this.scanNum
          + " remaining)\n" + "Player " + this.name + ", what would you like to do?");
      String s = inputReader.readLine();
      if (s.length() != 1) {
        out.println("Invalid choice!");
        continue;
      }
      if (s.charAt(0) == 'F') {
        playOneTurn(enemyBoard, enemyBoardView, enemyName);
        break;
      } else {
        if (s.charAt(0) == 'S') {
          if (scanNum <= 0) {
            out.println("No remaining scan chance!");
            continue;
          }
          scan(enemyBoard);
          break;
        }
        if (s.charAt(0) == 'M') {
          if (moveNum <= 0) {
            out.println("No remaining move chance!");
            continue;
          }
          move();
          break;
        } else {
          out.println("Invalid choice!");
          continue;
        }
      }
    }

  }
}
