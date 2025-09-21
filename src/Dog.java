import java.awt.Polygon;

public class Dog extends Actor {
  public Dog(Cell inLoc) {
    this.loc = inLoc;
    this.name = "Dog";
  
    Polygon ear1 = new Polygon();
    ear1.addPoint(5, 5);
    ear1.addPoint(15, 5);
    ear1.addPoint( 5, 15);
    shapes.add(ear1);

    Polygon ear2 = new Polygon();
    ear2.addPoint( 20,  5);
    ear2.addPoint(30,  5);
    ear2.addPoint( 30, 15);
    shapes.add(ear2);

    Polygon face = new Polygon();
    face.addPoint( 8, 7);
    face.addPoint( 27,  7);
    face.addPoint( 27,  25);
    face.addPoint( 8, 25);
    shapes.add(face);
  }
}

