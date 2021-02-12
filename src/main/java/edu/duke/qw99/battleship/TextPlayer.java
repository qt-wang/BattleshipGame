package edu.duke.qw99.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out ;
  final AbstractShipFactory<Character> shipFactory;
  String name;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> shipFctory){
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFctory;
  }

  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  public void doOnePlacement() throws IOException{
    Placement p = readPlacement("Player " + this.name +  " Where would you like to place a Destroyer?");
    // Ship<Character> s = new BasicShip(p.getWhere());
    // RectangleShip<Character> s = new RectangleShip<Character>("submarine", p.getWhere(), 1, 1, new SimpleShipDisplayInfo<Character>('s', '*'));
    Ship<Character> s = shipFactory.makeDestroyer(p);
    theBoard.tryAddShip(s);
    out.println(view.displayMyOwnBoard());
  }

  public void doPlacementPhase() throws IOException{
    view.displayMyOwnBoard();
    out.println(view.displayMyOwnBoard() + "\n" +
                "Player A: you are going to place the following ships (which are all" + "\n" +
                "rectangular). For each ship, type the coordinate of the upper left" + "\n" +
                "side of the ship, followed by either H (for horizontal) or V (for" + "\n" +
                "vertical).  For example M4H would place a ship horizontally starting" + "\n" +
                "at M4 and going to the right.  You have" + "\n" +
                "\n" +
                "2 Submarines ships that are 1x2 " + "\n" +
                "3 Destroyers that are 1x3" + "\n" +
                "3 Battleships that are 1x4" + "\n" +
                "2 Carriers that are 1x6" + "\n");
    doOnePlacement();
  }
  

}
  












