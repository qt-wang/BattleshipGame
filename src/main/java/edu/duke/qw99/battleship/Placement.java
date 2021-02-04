package edu.duke.qw99.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;

  public Placement(Coordinate cdnt, char c) {
    this.where = cdnt;
    this.orientation = Character.toUpperCase(c);
  }

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
    
  public Coordinate getWhere() {
    return where;
  }

  public char getOrientation() {
    return orientation;
  }

  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Placement p = (Placement) o;
      return where.equals(p.where) && orientation == p.orientation;
    }
    return false;
  }

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


