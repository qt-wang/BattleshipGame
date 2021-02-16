package edu.duke.qw99.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;

  /**
   *Construct a placement with a specified coordinate and a specified c.
   *@param cdnt is a specified coordinate.
   *@param c is a specified character, which means the orientation. The legal c is 'V' or 'H'.  
   */
  public Placement(Coordinate cdnt, char c) {
    this.where = cdnt;
    this.orientation = Character.toUpperCase(c);
  }

  /**
   *Construct a placement with a specified string.
   *@param s is a specified string. the legal format is like "A3V".  
   */
  public Placement(String s){
    if(s.length() < 3){
      throw new IllegalArgumentException("Invalid placement");
    }
    String str_cdnt = s.substring(0, s.length() - 1);
    Coordinate cdnt = new Coordinate(str_cdnt);
    this.where = cdnt;
    if(Character.toUpperCase(s.charAt(s.length() - 1)) < 'A' || Character.toUpperCase(s.charAt(s.length() - 1)) > 'Z'){
      throw new IllegalArgumentException("Invalid orientation");
    }
    this.orientation =Character.toUpperCase(s.charAt(s.length() - 1));
  }

  /**
   *Get the coordinate from the placement.
   *@return the coordinate of the placement.  
   */
  public Coordinate getWhere() {
    return where;
  }

  /**
   *Get the orientation from the placement.
   *@return the orientation of the placement.  
   */
  public char getOrientation() {
    return orientation;
  }

  /**
   *Check if two placement is equal
   *@param a specified object.
   *@return true if two are equal. false if two are not equal.  
   */
  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Placement p = (Placement) o;
      return where.equals(p.where) && orientation == p.orientation;
    }
    return false;
  }

  /**
   *Convert the placement into a string.
   */
  @Override
  public String toString(){
    StringBuilder ans = new StringBuilder();
    ans.append((char)('A' + where.getRow()));
    ans.append(Integer.toString(where.getColumn()));
    ans.append(orientation);
    return ans.toString();
  }

  @Override
  public int hashCode(){
    return toString().hashCode();
  }
}


