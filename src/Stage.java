import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;


public class Stage {
  Grid grid;
  ArrayList<Actor> actors = new ArrayList<>();

  private boolean showWeatherAnalysis = false;

  public Stage() {
    grid = new Grid();

    /// Create actors normally (no constructor changes)
    Actor cat = new Cat(grid.cellAtColRow(0, 0).get());
    Actor dog = new Dog(grid.cellAtColRow(0, 15).get());
    Actor bird = new Bird(grid.cellAtColRow(12, 9).get());
    
    // SET STRATEGIES AFTER CREATION
    cat.setWeatherStrategy(new CatWeatherStrategy());
    dog.setWeatherStrategy(new DogWeatherStrategy());
    bird.setWeatherStrategy(new BirdWeatherStrategy());
    
    actors.add(cat);
    actors.add(dog);
    actors.add(bird);
  }

  public void paint(Graphics g, Point mouseLoc) {
    // paint the grid first
    grid.paint(g, mouseLoc);

    // now loop through all actors and paint them
    for (Actor actor : actors) {
      actor.paint(g);
    }
    paintActorsPoints(g);
     
    // we show weather analysis when toggled
    if (showWeatherAnalysis) {
      grid.paintWeatherAnalysis(g);
  }


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
        yPosition += 20;
    }
}

public Grid getGrid(){
  return grid;
}

public  ArrayList<Actor> getActors(){
  return actors;
}

public void toggleWeatherAnalysis() {
  showWeatherAnalysis = !showWeatherAnalysis;
}

public boolean isShowingWeatherAnalysis() {
  return showWeatherAnalysis;
}
}
