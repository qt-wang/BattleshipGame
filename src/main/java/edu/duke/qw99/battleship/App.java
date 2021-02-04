/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.qw99.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.InputStreamReader;

public class App {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out ;

  public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    this.out = out;
  }

  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  public void doOnePlacement() throws IOException{
    Placement p = readPlacement("Where would you like to put your ship?");
    Ship<Character> s = new BasicShip(p.getWhere());
    theBoard.tryAddShip(s);
    out.println(view.displayMyOwnBoard());
  }

  public static void main(String[] args) throws IOException{
    Board<Character> b1 = new BattleShipBoard<>(10, 20);
    App app = new App(b1, new InputStreamReader(System.in), System.out);
    app.doOnePlacement();
  }
}












