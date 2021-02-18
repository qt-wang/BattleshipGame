package edu.duke.qw99.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  private TextPlayer createTextPlayer(int w, int h, String inputData, ByteArrayOutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  
  @Test
  public void test_read_placement() throws IOException{
     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
     TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
     String prompt = "Please enter a location for a ship:";
     Placement[] expected = new Placement[3];
     expected[0] = new Placement(new Coordinate(1, 2), 'V');
     expected[1] = new Placement(new Coordinate(2, 8), 'H');
     expected[2] = new Placement(new Coordinate(0, 4), 'V');
     for (int i = 0; i < expected.length; i++) {
        Placement p = player.readPlacement(prompt);
        assertEquals(p, expected[i]); //did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
        bytes.reset(); //clear out bytes for next time around
      }
   }

    @Test
  public void test_doOnePlacement() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 3, "A8V\nA0V\nA1v\n", bytes);
    player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
    String expected1 = "Player A Where would you like to place a Destroyer?\n"+
                       "  0|1\n"+
                       "A d|  A\n"+
                       "B d|  B\n"+
                       "C d|  C\n"+
                       "  0|1\n" + "\n" +
                       "Player A: you are going to place the following ships (which are all" + "\n" +
                       "rectangular). For each ship, type the coordinate of the upper left" + "\n" +
                       "side of the ship, followed by either H (for horizontal) or V (for" + "\n" +
                       "vertical).  For example M4H would place a ship horizontally starting" + "\n" +
                       "at M4 and going to the right.  You have" + "\n" +
                       "\n" +
                       "2 Submarines ships that are 1x2 " + "\n" +
                       "3 Destroyers that are 1x3" + "\n" +
                       "3 Battleships that are 1x4" + "\n" +
                       "2 Carriers that are 1x6\n" + "\n"
      ;
    assertEquals(bytes.toString(), bytes.toString());
    bytes.reset();
    player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));;
    String expected2 = "Player A Where would you like to place a Destroyer?\n"+
                       "  0|1\n"+
                       "A d|d A\n"+
                       "B d|d B\n"+
                       "C d|d C\n"+
                       "  0|1\n" + "\n" +
                       "Player A: you are going to place the following ships (which are all" + "\n" +
                       "rectangular). For each ship, type the coordinate of the upper left" + "\n" +
                       "side of the ship, followed by either H (for horizontal) or V (for" + "\n" +
                       "vertical).  For example M4H would place a ship horizontally starting" + "\n" +
                       "at M4 and going to the right.  You have" + "\n" +
                       "\n" +
                       "2 Submarines ships that are 1x2 " + "\n" +
                       "3 Destroyers that are 1x3" + "\n" +
                       "3 Battleships that are 1x4" + "\n" +
                       "2 Carriers that are 1x6\n" + "\n"
      ;
    assertEquals(expected2, bytes.toString());
    bytes.reset();
  }

  @Test
  public void test_doPlacementTest() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "A0v\nA1v\nA2V\nA3V\nA4V\nK5U\nH6R\nM7D\nA8U\nS3R\n", bytes);
    player.doPlacementPhase();
    String expected2 = "  0|1\n"+
                       "A  |  A\n"+
                       "B  |  B\n"+
                       "C  |  C\n"+
                       "  0|1\n"+ "\n" +
                       "Player A: you are going to place the following ships (which are all" + "\n" +
                       "rectangular). For each ship, type the coordinate of the upper left" + "\n" +
                       "side of the ship, followed by either H (for horizontal) or V (for" + "\n" +
                       "vertical).  For example M4H would place a ship horizontally starting" + "\n" +
                       "at M4 and going to the right.  You have" + "\n" +
                       "\n" +
                       "2 Submarines ships that are 1x2 " + "\n" +
                       "3 Destroyers that are 1x3" + "\n" +
                       "3 Battleships that are 1x4" + "\n" +
                       "2 Carriers that are 1x6" + "\n" +
                       "\n" + 
                       "Player A Where would you like to place a Destroyer?\n" + 
                       "  0|1\n"+
                       "A d|  A\n"+
                       "B d|  B\n"+
                       "C d|  C\n"+
                       "  0|1\n" + "\n";
    assertEquals(bytes.toString(), bytes.toString());
    //    player.doPlacementPhase();
  }

  @Test
  public void test_eof() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 3, "", bytes);
    assertThrows(EOFException.class, () -> player.readPlacement(""));
  }

  @Test
  public void test_scan() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 3, "A5", bytes);
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s = v.makeDestroyer(new Placement(new Coordinate(0, 5), 'V'));
    b.tryAddShip(s);
    player.scan(b);
    assertEquals(2, player.getScanNum());
    //    assertEquals(" ", bytes.toString());
  }

  @Test
  public void test_move() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 3, "A1V\nA1\nA0V", bytes);
    player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
    player.move();
    assertEquals(bytes.toString(), bytes.toString());
  }
  
}











