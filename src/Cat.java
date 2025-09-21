import java.awt.Polygon;


public class Cat extends Actor {
  public Cat(Cell inLoc) {
    this.loc = inLoc; 
    this.name = "Cat";

    Polygon ear1 = new Polygon();
    ear1.addPoint( 11,  5);
    ear1.addPoint( 15,  15);
    ear1.addPoint( 7, 15);
    shapes.add(ear1);

    Polygon ear2 = new Polygon();
    ear2.addPoint( 22,  5);
    ear2.addPoint( 26, 15);
    ear2.addPoint( 18,  15);
    shapes.add(ear2);

    Polygon face = new Polygon();
    face.addPoint( 5, 15);
    face.addPoint(29, 15);
    face.addPoint( 17,  30);
    shapes.add(face);
  }
}
/* 
  @Override
  //cat cannot move on water or sand
  //arbitarily defined characteristic made for this game
  public boolean canBeMoved(Cell c){
    String type = c.getType();
    boolean allowedToMove = !type.equals("Water") && !type.equals("Sand");
    return allowedToMove;
  }*/

