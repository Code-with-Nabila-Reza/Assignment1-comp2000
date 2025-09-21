import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Cell extends Rectangle {
  static int size = 35;
  private String type;
  private int elevation;
  private Collectible item;

  public Cell(int x, int y) {
    super(x, y, size, size);
    
    // Assign random properties for demonstration
    String[] types = {"Grass", "Water", "Rock", "Sand", "Forest"};
    this.type = types[(int)(Math.random() * types.length)];
    this.elevation = (int)(Math.random() * 100);

    if (Math.random() < 0.2) {//20% chnace of having collectible
      
      Collectible[] possibleCollectibles = {
          new LuckyBell(),
          new GoldenCollar(), 
          new MagicFeather()        };
          
      int randomIndex = (int)(Math.random() * possibleCollectibles.length);
      item = possibleCollectibles[randomIndex];
  } else {
      item = null;
  }

  }

  public void paint(Graphics g, Point mousePos) {

    if (type.equals("Grass")) {
      g.setColor(new Color(144,238,144));
  } 
  else if (type.equals("Water")) {
      g.setColor(new Color(173,216,230));
  }
   else if (type.equals("Sand")) {
      g.setColor(new Color(238,232,170));
  } 
  else if (type.equals("Rock")) {
      g.setColor(Color.GRAY);
  } 
  else if (type.equals("Forest")) {
      g.setColor(new Color(34, 139, 34));
  } 
  

    if(contains(mousePos)) {
      g.setColor(Color.GRAY);
    }
     
    g.fillRect(x, y, size, size);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, size, size);

   //collectible items
   if (item != null) {
    if (item.getName().equals("MagicFeather")) {
        g.setColor(new Color(255,20,147));
    } 
    else if (item.getName().equals("LuckyBell")) {
        g.setColor(Color.BLUE);
    } 
    else if (item.getName().equals("GoldenCollar")) {
        g.setColor(new Color(255,215,0));
    } 
    else {
        g.setColor(Color.WHITE); // default if new item added
    }
    
    g.fillOval(x + 10, y + 10, 15, 15);
    g.setColor(Color.BLACK);
    g.drawOval(x + 10, y + 10, 15, 15);
}
  
  }

  public boolean contains(Point p) {
    if(p != null) {
      return super.contains(p);
    } else {
      return false;
    }
  }

  // Getter methods for the new properties
  public String getType() { 
    return type; 
  }

  public int getElevation() { 
    return elevation; 
  }

  public Collectible getItem(){
    return item;
  }
  public void setItem(Collectible i){
    item = i;
  }
}