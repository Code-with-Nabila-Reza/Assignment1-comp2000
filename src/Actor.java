import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

public abstract class Actor implements Movable{
  ArrayList<Polygon> shapes = new ArrayList<>();
  protected Cell loc;
  protected Inventory<Collectible> ActInventory = new  Inventory<>();
  protected int points = 0;
  protected String name;

   // strategy pattern-- each actor has a weather strategy
   protected WeatherStrategy weatherStrategy = new DefaultWeatherStrategy(); 

  public void paint(Graphics g) {
    for (Polygon p : shapes) {
        Polygon moved = new Polygon();
        for (int i = 0; i < p.npoints; i++) {
            moved.addPoint(p.xpoints[i] + loc.x, p.ypoints[i] + loc.y);
        }
        g.drawPolygon(moved);
        g.fillPolygon(moved);
    }
}

  public Cell getCellLocation(){
    return loc;
  }

  //each cell has points based on its type
      //cell might have collectibles that have their own points
 
  public int getPointsEarnedFromCell(Cell c){
    String type = c.getType();
    if (type.equals("Water")) {
        return -2;
    }
     else if (type.equals("Grass")) {
        return 2;
    }
     else if (type.equals("Rock")) {
        return -1;
    } 
    else if (type.equals("Sand")) {
        return -1;
    }
     else if (type.equals("Forest")) {
        return 1;
    }
     else {
        return 0;
    }

  }


  @Override
  public void move(Cell destinationCell, Grid grid){
    if(grid.cellIsInsideGrid(destinationCell) ){
      int baseCellPoints = getPointsEarnedFromCell(destinationCell);

      int weatherModifiedPoints = applyWeatherEffects(baseCellPoints, destinationCell);

      modifyPoints(weatherModifiedPoints);//collecting point from cell type

      //points<--collectible
      //we collect item points are gained 
      //if cell has collectible it gets stored in inventory 
      //the cell collectible then gets set to null
      Collectible item = destinationCell.getItem();
            if (item != null) {
                collectItem(item);
                destinationCell.setItem(null);
            }

      this.loc = destinationCell;
    }
  }

  // STRATEGY PATTERN-> Setter method
  public void setWeatherStrategy(WeatherStrategy strategy) {
    this.weatherStrategy = strategy;
}
  // Weather effects on movement
  

  private int applyWeatherEffects(int newCellPoint, Cell cell) {
    // getting weather data from cell
    double rainfall = cell.getRainfall();
    double temperature = cell.getTemperature();
    double maxPossibleWind = Math.sqrt(1.0 * 1.0 + 1.0 * 1.0);//1.4
   double normalizedWind = Math.sqrt(cell.getWindX() * cell.getWindX() + cell.getWindY() * cell.getWindY()) / maxPossibleWind;//windstrength
    //call particular weather startegy for that specific actor
    return weatherStrategy.applyWeatherEffects(newCellPoint, rainfall, temperature, normalizedWind);
    }


  public void collectItem(Collectible item){
    ActInventory.addItem(item);
  }

  public Inventory<Collectible> getInventory(){
    return ActInventory;
  }

  public int getPoints(){
    return points + ActInventory.getTotalPoints();
  }

  public void modifyPoints(int p){
    points += p;
  }

  public String getName(){
    return name;
  }

}
