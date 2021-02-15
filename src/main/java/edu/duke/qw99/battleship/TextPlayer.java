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

  /**
   *Construct a TextPlayer.
   */
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

  /**
   *This function prints out the prompt message and reads from input
   *@param prompt is a message.
   *@return a placement that was read by inputReader from the input.  
   */  
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if(s == null){
      throw new EOFException("EOF");
    }
    return new Placement(s);
  }

  /*
   *This function place a ship according to player's input. It shows prompt messages and reads from the input, and adds a ship to the board.
   *@param shipName is the name of the ship you want to place.
   *@param createFn is lambda expression. It can create a ship according the input.
   */  
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException{
    String prompt = "Player " + this.name +  " Where would you like to place a " + shipName + "?";
    int flag = 0;
    while(flag == 0){
      try{
        Placement p = readPlacement(prompt);
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
  
  /**
   *This function does following things:
   *    (a) display the starting (empty) board
   *    (b) print the instructions message (from the README,
   *        but also shown again near the top of this file)
   *    (c) call doOnePlacement to place one ship
   */
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
  
  /**
   *This function allows player to play only one turn. It shows prompt messges and reads from the input and attack enemy's board.
   *@param enemyBoard is my enemy's board.
   *@param enemyBoardView is my enemy's view.
   *@param enemyName is my enemy's name.  
   */
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
  












