import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import java.util.Optional;

public class Grid {
  Cell[][] cells = new Cell[20][20];
  
  public Grid() {
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        cells[i][j] = new Cell(10+Cell.size*i, 10+Cell.size*j);
      }
    }
  }

  public void paint(Graphics g, Point mousePos) {
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        cells[i][j].paint(g, mousePos);
      }
    }
    
    // Paint the cell details in the right panel
    paintCellDetails(g, mousePos);
  }

  private void paintCellDetails(Graphics g, Point mousePos) {
    // Clear the details area (right side of screen)
    g.setColor(Color.WHITE);
    g.fillRect(720, 0, 304, 720);
    g.setColor(Color.BLACK);
    g.drawRect(720, 0, 304, 720);
    
    // Get the cell at mouse position using our cellAtPoint method
    Optional<Cell> cellOpt = cellAtPoint(mousePos);
    
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.BOLD, 16));
    
    if (cellOpt.isPresent()) {
        Cell cell = cellOpt.get();
        
        // Display cell information
        g.drawString("Cell Details:", 730, 30);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        
        g.drawString("Position: (" + cell.x + ", " + cell.y + ")", 730, 60);
        g.drawString("Type: " + cell.getType(), 730, 85);
        g.drawString("Elevation: " + cell.getElevation() + "m", 730, 110);
        
        // Calculate grid coordinates
        int col = (cell.x - 10) / Cell.size;
        int row = (cell.y - 10) / Cell.size;
        g.drawString("Grid Position: [" + col + ", " + row + "]", 730, 135);
        
        if (cell.getItem() != null) {
          g.drawString("Item: " + cell.getItem().getName(), 730, 160);
          g.drawString("Points: " + cell.getItem().getPoints(), 730, 185);
      } 
        else {
          g.drawString("Item: None", 730, 160);
      }
  }
     else {
        g.drawString("No cell at mouse position", 730, 30);
        if (mousePos != null) {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString("Mouse: (" + mousePos.x + ", " + mousePos.y + ")", 730, 55);
        }
    }
  }

  public Optional<Cell> cellAtColRow(int c, int r) {
    // Check bounds before accessing array
    if (c < 0 || c >= cells.length || r < 0 || r >= cells[0].length) {
        return Optional.empty();
    }
    
    return Optional.of(cells[c][r]);
  }

  public Optional<Cell> cellAtPoint(Point p) {
    // Handle null point
    if (p == null) {
        return Optional.empty();
    }
    
    // Calculate which cell this point corresponds to
    int col = (p.x - 10) / Cell.size;
    int row = (p.y - 10) / Cell.size;
    
    // Check if the calculated indices are within bounds
    if (col < 0 || col >= cells.length || row < 0 || row >= cells[0].length) {
        return Optional.empty();
    }
    
    // Return the cell wrapped in Optional
    return Optional.of(cells[col][row]);
  }

  public boolean cellIsInsideGrid(Cell desCell){
    int col = (desCell.x - 10) / Cell.size;
    int row = (desCell.y - 10) / Cell.size;
    boolean isInside = (col>=0 && col < cells.length && row >= 0 && row < cells[0].length);
    return isInside;
  }
  
  public Cell getCell(int col, int row) {
    if (col < 0 || col >= cells.length || row < 0 || row >= cells[0].length) {
        return null;
    }
    return cells[col][row];
}

}