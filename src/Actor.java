import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

public abstract class Actor implements Movable{
  ArrayList<Polygon> shapes = new ArrayList<>();
  protected Cell loc;
  protected Inventory<Collectible> ActInventory = new  Inventory<>();

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

  @Override
  public void move(Cell destinationCell, Grid grid){
    if(grid.cellIsInsideGrid(destinationCell) && canBeMoved(destinationCell)){
       this.loc = destinationCell;
    }
  }

  public void collectItem(Collectible item){
    ActInventory.addItem(item);
  }

  public Inventory<Collectible> getInventory(){
    return ActInventory;
  }

}
