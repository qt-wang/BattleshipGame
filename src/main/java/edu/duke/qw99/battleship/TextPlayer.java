package edu.duke.qw99.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out ;
  final AbstractShipFactory<Character> shipFactory;
  String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

  protected void setupShipCreationMap(){
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
  }

  protected void setupShipCreationList(){
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> shipFctory ){
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
  }

  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if(s == null){
      throw new EOFException("EOF");
    }
    return new Placement(s);
  }
  
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException{
    String prompt = "Player " + this.name +  " Where would you like to place a " + shipName + "?";
    int flag = 0;
    while(flag == 0){
      try{
        Placement p = readPlacement(prompt);
    // Ship<Character> s = new BasicShip(p.getWhere());
    // RectangleShip<Character> s = new RectangleShip<Character>("submarine", p.getWhere(), 1, 1, new SimpleShipDisplayInfo<Character>('s', '*'));
        Ship<Character> s = createFn.apply(p);
        String str = theBoard.tryAddShip(s);
        if(str != null){
          throw new IllegalArgumentException(str);
        }
        flag = 1;
      }
      catch(IllegalArgumentException e){
        out.println(e.getMessage());
        }
    }
    out.println(view.displayMyOwnBoard() + "\n" +
                "Player " + this.name + ": you are going to place the following ships (which are all" + "\n" +
                "rectangular). For each ship, type the coordinate of the upper left" + "\n" +
                "side of the ship, followed by either H (for horizontal) or V (for" + "\n" +
                "vertical).  For example M4H would place a ship horizontally starting" + "\n" +
                "at M4 and going to the right.  You have" + "\n" +
                "\n" +
                "2 Submarines ships that are 1x2 " + "\n" +
                "3 Destroyers that are 1x3" + "\n" +
                "3 Battleships that are 1x4" + "\n" +
                "2 Carriers that are 1x6" + "\n");
  }

  public void doPlacementPhase() throws IOException{
    view.displayMyOwnBoard();
    out.println(view.displayMyOwnBoard() + "\n" +
                "Player " + this.name + ": you are going to place the following ships (which are all" + "\n" +
                "rectangular). For each ship, type the coordinate of the upper left" + "\n" +
                "side of the ship, followed by either H (for horizontal) or V (for" + "\n" +
                "vertical).  For example M4H would place a ship horizontally starting" + "\n" +
                "at M4 and going to the right.  You have" + "\n" +
                "\n" +
                "2 Submarines ships that are 1x2 " + "\n" +
                "3 Destroyers that are 1x3" + "\n" +
                "3 Battleships that are 1x4" + "\n" +
                "2 Carriers that are 1x6" + "\n");
    // doOnePlacement("Destroyer", shipCreationFns.get("Destroyer"));
      for(String s : shipsToPlace){
      doOnePlacement(s, shipCreationFns.get(s));
      }
  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enermyBoardView, String enemyName) throws IOException{
    out.println("Player " + this.name + "'s turn:\n");
    String myHeader = "Your ocean";
    String enemyHeader = "Player " + enemyName + "'s ocean";
    out.println(this.view.displayMyBoardWithEnemyNextToIt(enermyBoardView, myHeader, enemyHeader));
    out.println("Player " + this.name + ": please write a coordinate to fire at\n");
    String s = inputReader.readLine();
    Coordinate c = new Coordinate(s);
    Ship<Character> ship = enemyBoard.fireAt(c);
    if(ship == null){
      out.println("You missied!\n");
    }
    else{
      out.println("You hit a " + ship.getName() + "!\n");
    }
  }
}
  












