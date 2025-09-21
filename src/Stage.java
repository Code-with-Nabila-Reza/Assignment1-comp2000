import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;


public class Stage {
  Grid grid;
  ArrayList<Actor> actors = new ArrayList<>();

  public Stage() {
    grid = new Grid();

    // create actors and add them to the list
    actors.add(new Cat(grid.cellAtColRow(0, 0).get()));
    actors.add(new Dog(grid.cellAtColRow(0, 15).get()));
    actors.add(new Bird(grid.cellAtColRow(12, 9).get()));  
  }

  public void paint(Graphics g, Point mouseLoc) {
    // paint the grid first
    grid.paint(g, mouseLoc);

    // now loop through all actors and paint them
    for (Actor actor : actors) {
      actor.paint(g);
    }
    paintActorsPoints(g);
  }

   void paintActorsPoints(Graphics g) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.BOLD, 16));
    g.drawString("Actor Statistics:", 730, 220);
   
    g.setFont(new Font("Arial", Font.PLAIN, 14));
 
    int yPosition = 250;//start

    for (Actor actor : actors) {//display--actor info
        g.drawString(actor.getName() + ": " + actor.getPoints() + " points", 730, yPosition);
        yPosition += 20;
        
        g.drawString("Items: " + actor.getInventory().size(), 730, yPosition);
        yPosition += 20;
        //space before next actor
        yPosition += 10;
    }
}

public Grid getGrid(){
  return grid;
}

public  ArrayList<Actor> getActors(){
  return actors;
}
}
