package edu.duke.qw99.battleship;

/**
 *This class make all kinds of ships.
 */
public class V1ShipFactory implements AbstractShipFactory<Character> {

  /**
   *Create a ship.
   *@param where is a placement of the ship you want to create.
   *@param w is the width of the ship you want to create.
   *@param h is the height of the ship you want to create.  
   *@param letter is a symbol that represents the ship on a board.
   *@param name is the name og the ship you want to create.  
   */
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name){
    RectangleShip<Character> ship;
    if(where.getOrientation() == 'V'){
      ship = new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*');
    } else if( where.getOrientation() == 'H'){
      ship = new RectangleShip<Character>(name, where.getWhere(), h, w, letter, '*' );
    } else{
          throw new IllegalArgumentException("Invalid orientation");
    }
    return ship;
  }

  protected Ship<Character> createBattleship(Placement where, char letter, String name){
    battleshipShip<Character> ship;
    if(where.getOrientation() != 'U' && where.getOrientation() != 'R' && where.getOrientation() != 'D' && where.getOrientation() != 'L'){
       throw new IllegalArgumentException("Invalid orientation");
    }
    ship = new battleshipShip<Character>(name, where, letter, '*');
    return ship;
  }

    protected Ship<Character> createCarrier(Placement where, char letter, String name){
    carrierShip<Character> ship;
    if(where.getOrientation() != 'U' && where.getOrientation() != 'R' && where.getOrientation() != 'D' && where.getOrientation() != 'L'){
       throw new IllegalArgumentException("Invalid orientation");
    }
    ship = new carrierShip<Character>(name, where, letter, '*');
    return ship;
  }

  /**
   * Make a submarine.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */
	@Override
	public Ship<Character> makeSubmarine(Placement where) {
	  return createShip(where, 1, 2, 's', "Submarine");
	}

  /**
   * Make a battleship.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
	@Override
	public Ship<Character> makeBattleship(Placement where) {
	  return createBattleship(where, 'b', "Battleship");
	}

   /**
   * Make a carrier.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
	@Override
	public Ship<Character> makeCarrier(Placement where) {
	  return createCarrier(where,'c', "Carrier");
	}

   /**
   * Make a destroyer.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the destroyer.
   */
	@Override
	public Ship<Character> makeDestroyer(Placement where) {
		return createShip(where, 1, 3, 'd', "Destroyer");
	}

}





