import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

public abstract class Actor implements Movable{
  ArrayList<Polygon> shapes = new ArrayList<>();
  protected Cell loc;
  protected Inventory<Collectible> ActInventory = new  Inventory<>();
  protected int points = 0;

  public void paint(Graphics g) {
    for (Polygon P : shapes){
      g.drawPolygon(P); //outline
      g.fillPolygon(P); //Filling
    }
  }

  public Cell getCellLocation(){
    return loc;
  }

  public abstract boolean canBeMoved(Cell c);
  public abstract int getPointsEarnedFromCell(Cell c);

  @Override
  public void move(Cell destinationCell, Grid grid){
    if(grid.cellIsInsideGrid(destinationCell) && canBeMoved(destinationCell)){
      //each cell has points based on its type
      //each cell has collectibles that have their own points
      int newCellPoint = getPointsEarnedFromCell(destinationCell);



      this.loc = destinationCell;
    }
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
    //p can be negative depends on the type of cell and its points
    if(p<0){
      p=0; // points aren't gonna be zero
    }
  }

}
