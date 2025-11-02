import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Cell extends Rectangle {
  static int size = 35;
  private String type;
  private int elevation;
  private Collectible item;

    private double rainfall = 0.0;
    private double temperature = 0.0; 
    private double windX = 0.0;
    private double windY = 0.0;

    private boolean has_weather = false;

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

  public void setWeatherData(String attribute, double value){
    has_weather = true;
    if(attribute.equals("rain")){
      this.rainfall = value;
    }
    else if(attribute.equals("temp")){
      this.temperature = value;
    }
    else if(attribute.equals("windx")){
      this.windX = value;
    }
    else if(attribute.equals("windy")){
      this.windY = value;
    }
  }


  public void paint(Graphics g, Point mousePos) {
//for painting terrains
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

    // to visualise the effects of weather on cells
    if(has_weather){
      paintWeatherEffects(g);
    }

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
 
  private void paintWeatherEffects(Graphics g) {

    String dominantWeather = getDominantWeather();

    switch (dominantWeather) {
      case "RAIN":
          
              g.setColor(new Color(0, 0, 255, 100));
              g.drawRect(x, y, size, size);
              g.drawRect(x + 1, y + 1, size - 2, size - 2);
          
          break;
          
          case "HEAT":
          g.setColor(Color.RED);
          //  multiple arcs to make it bolder
          g.drawArc(x + 5, y + size - 10, size - 10, 8, 0, 180);
          g.drawArc(x + 5, y + size - 11, size - 10, 8, 0, 180);  // Slight offset
          g.drawArc(x + 6, y + size - 10, size - 10, 8, 0, 180);  // Another offset
          break;
          
      case "WIND":
              g.setColor(Color.RED);
              g.drawString("→", x + size - 15, y + 15);
          
          break;
          
      case "NONE":
          // No dominant weather to show
          break;
  }
}

private String getDominantWeather() {
  // Normalize wind strength value to make its range 0.0 to 1.0
  double maxPossibleWind = Math.sqrt(1.0 * 1.0 + 1.0 * 1.0);//range 0-1.4
  double normalizedWind = Math.sqrt(windX * windX + windY * windY) / maxPossibleWind;//from0-1
  
  // Find which is relatively the highest
  if (rainfall >= temperature && rainfall >= normalizedWind && rainfall > 0.45) {
      return "RAIN";
  } else if (temperature >= rainfall && temperature >= normalizedWind && temperature > 0.55 ) {
      return "HEAT";
  } else if (normalizedWind >= rainfall && normalizedWind >= temperature && normalizedWind > 0.5) {
      return "WIND";
  } else {
      return "NONE";
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
  //getter methods for weather related vars
  public double getRainfall() { 
    return rainfall;
   }
  public double getTemperature() {
     return temperature; 
    }
  public double getWindX() { 
    return windX;
   }
  public double getWindY() { 
    return windY; 
  }
  public boolean hasWeather() { 
    return has_weather;
   }
}