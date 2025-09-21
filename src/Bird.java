import java.awt.Polygon;


public class Bird extends Actor {
  public Bird(Cell inLoc) {
    this.loc = inLoc; 
    this.name = "Bird";

   Polygon wing1 = new Polygon();
    wing1.addPoint(loc.x + 5, loc.y + 5);
    wing1.addPoint(loc.x + 15, loc.y + 17);
    wing1.addPoint(loc.x + 5, loc.y + 17);
    shapes.add(wing1);

    Polygon wing2 = new Polygon();
    wing2.addPoint(loc.x + 30, loc.y + 5);
    wing2.addPoint(loc.x + 20, loc.y + 17);
    wing2.addPoint(loc.x + 30, loc.y + 17);
    shapes.add(wing2);
    
    Polygon body = new Polygon();
    body.addPoint(loc.x + 15, loc.y + 10);
    body.addPoint(loc.x + 20, loc.y + 10);
    body.addPoint(loc.x + 20, loc.y + 25);
    body.addPoint(loc.x + 15, loc.y + 25);
    shapes.add(body);
  }

  @Override
  //birds can fly over on all types of places
  // --grass, forest, rock, water, sand
  public boolean canBeMoved(Cell des){
     return true;
  }
}
