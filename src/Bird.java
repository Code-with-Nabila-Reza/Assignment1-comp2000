import java.awt.Polygon;


public class Bird extends Actor {
  public Bird(Cell inLoc) {
    this.loc = inLoc; 
    this.name = "Bird";
   
   Polygon wing1 = new Polygon();
    wing1.addPoint(5,  5);
    wing1.addPoint( 15,  17);
    wing1.addPoint( 5, 17);
    shapes.add(wing1);

    Polygon wing2 = new Polygon();
    wing2.addPoint(30, 5);
    wing2.addPoint(20, 17);
    wing2.addPoint(30, 17);
    shapes.add(wing2);
    
    Polygon body = new Polygon();
    body.addPoint( 15, 10);
    body.addPoint( 20,  10);
    body.addPoint( 20, 25);
    body.addPoint(15, 25);
    shapes.add(body);
  }
}

 /*  @Override
  //birds can fly over on all types of places
  // --grass, forest, rock, water, sand
  public boolean canBeMoved(Cell des){
     return true;
  }*/

