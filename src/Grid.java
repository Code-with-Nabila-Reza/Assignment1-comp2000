import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Optional;

public class Grid implements WeatherObserver{
  //gets notified for weather updates
  Cell[][] cells = new Cell[20][20];
  
  public Grid() {
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        cells[i][j] = new Cell(10+Cell.size*i, 10+Cell.size*j);
      }
    }
  }
  
  @Override
  public void on_weather_update(WeatherInfo info){
    //http coordinate system center 0,0 on grid

int Col = info.get_x() + (cells.length)/2;
int Row = (cells[0].length / 2) - info.get_y(); 

    //grid boundcheck for calculated cell
    if (Col >= 0 && Col < cells.length && Row >= 0 && Row < cells[0].length) {
      Cell cell = cells[Col][Row];
      if (cell != null) {
        //updates weather data
          cell.setWeatherData(info.get_attribute(), info.get_value());
      }
  } 
  else {
    //if cell outside grid 
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
      // weather info
      paintWeatherDetails(g, cell, 440);
  }

     else {
        g.drawString("No cell at mouse position", 730, 30);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.PLAIN , 12));
        // Show instructions when no cell is hovered
        g.drawString("Instructions: ", 730, 95);
        g.drawString("1. Hover over cells to see", 730, 112);
        g.drawString("Cell information, weather data ", 730, 124);
        g.drawString("and effects on actors.", 730, 136);
        
        g.drawString("2. Press W for grid-wide", 730, 148);
        g.drawString("weather statistics.", 730, 160);
        g.drawString("3. Press TAB to switch actors", 730, 172);
        g.drawString("4. Press arrow keys to move", 730, 184);

        g.setColor(Color.BLACK);
        if (mousePos != null) {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString("Mouse: (" + mousePos.x + ", " + mousePos.y + ")", 730, 55);
        }
    }
  }

  private void paintWeatherDetails(Graphics g, Cell cell, int startY) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.BOLD, 16));
    g.drawString("Cell Weather Details: ", 730, startY);

    g.setFont(new Font("Arial", Font.PLAIN, 12));
    g.setColor(Color.DARK_GRAY);
    g.drawString("Press W for grid weather stats", 730, startY + 15);

    
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.PLAIN, 14));
    
    int yOffset = startY + 40;
    
    if (cell.hasWeather()) {
      // Show exact numerical values
      double rainfall = cell.getRainfall();
      double temperature = cell.getTemperature();
      double windX = cell.getWindX();
      double windY = cell.getWindY();
      double windStrength = Math.sqrt(windX * windX + windY * windY);
      double normalizedWind = windStrength / Math.sqrt(2);
      
      g.drawString("Rainfall: " + String.format("%.3f", rainfall), 730, yOffset);
      yOffset += 20;
      
      g.drawString("Temperature: " + String.format("%.3f", temperature), 730, yOffset);
      yOffset += 20;
      
      g.drawString("Wind Strength: " + String.format("%.3f", normalizedWind), 730, yOffset);
      yOffset += 35;
      
      // Actor-specific warnings section
      g.setColor(Color.DARK_GRAY);
      g.setFont(new Font("Arial", Font.BOLD, 16));
      g.drawString("Actor Warnings:", 730, yOffset);
      yOffset += 25;
      
      g.setFont(new Font("Arial", Font.PLAIN, 14));
      
      // Cat warnings
      g.setColor(Color.BLACK);
      g.drawString("Cat:  ", 730, yOffset);
      boolean catWarning = false;
      if (rainfall > 0.45 && rainfall<=0.6) {
          g.setColor(Color.RED);
          g.drawString("  Avoid rain! (-2 points)", 750, yOffset);
          catWarning = true;
      }
      if (rainfall > 0.6) {
        g.setColor(Color.RED);
        g.drawString("  Avoid rain! (-3 points)", 750, yOffset);
        catWarning = true;
    }
      if (temperature > 0.55 || temperature < 0.4) {
          g.setColor(Color.RED);
          g.drawString("  Extreme temperature! (-1 point)", 750, yOffset + (catWarning ? 30 : 0));
          catWarning = true;
      }
      if (!catWarning) {
          g.setColor(Color.GREEN);
          g.drawString("  Safe conditions", 750, yOffset);
      }
      yOffset += 35; 
      
      // Bird warnings
      g.setColor(Color.BLACK);
      g.drawString("Bird: ", 730, yOffset);
      boolean birdWarning = false;
      if (normalizedWind > 0.5 && normalizedWind <=0.8) {
          g.setColor(Color.RED);
          g.drawString("  Strong wind! (-2 points)", 750, yOffset);
          birdWarning = true;
      }
      if (normalizedWind > 0.8) {
          g.setColor(Color.RED);
          g.drawString("  Very strong wind! (-3 points)", 750, yOffset + (birdWarning ? 15 : 0));
          birdWarning = true;
      }
      if (rainfall > 0.7) {
          g.setColor(Color.ORANGE);
          g.drawString("  Heavy rain! (-1 point)", 750, yOffset + (birdWarning ? 30 : 0));
          birdWarning = true;
      }
      if (!birdWarning) {
          g.setColor(Color.GREEN);
          g.drawString(" Safe conditions", 750, yOffset);
      }
      yOffset += 35;
      
      // Dog warnings
      g.setColor(Color.BLACK);
      g.drawString("Dog: ", 730, yOffset);
      if (rainfall > 0.6) {
          g.setColor(Color.ORANGE);
          g.drawString(" Heavy rain! (-1 point)", 755, yOffset);
      } else {
          g.setColor(Color.GREEN);
          g.drawString(" All clear!", 755, yOffset);
      }
      yOffset += 35;
      
      // Dominant condition
      g.setColor(Color.BLACK);
      g.setFont(new Font("Arial", Font.BOLD, 14));
      g.drawString("Overall Condition: " + getDominantCondition(cell), 730, yOffset);
      
  } else {
      g.drawString("Weather data hasn't been assigned", 730, yOffset);
      g.drawString("to this cell", 730, yOffset+15);
  }
}

private String getDominantCondition(Cell cell) {
  if (!cell.hasWeather()) return "No data";
  
  double rainfall = cell.getRainfall();
  double temperature = cell.getTemperature();
  double windX = cell.getWindX();
  double windY = cell.getWindY();
  double windStrength = Math.sqrt(windX * windX + windY * windY);
  double normalizedWind = windStrength / Math.sqrt(2);
  
  if (rainfall >= temperature && rainfall >= normalizedWind && rainfall > 0.45) {
      return "RAINY";
  } else if (temperature >= rainfall && temperature >= normalizedWind && temperature > 0.55) {
      return "HEAT WAVE";
  } else if (normalizedWind >= rainfall && normalizedWind >= temperature && normalizedWind > 0.5) {
      return "WINDY";
  } else {
      return "CALM";
  }
}


public void paintWeatherAnalysis(Graphics g) {
    // background 
    g.setColor(new Color(240, 240, 240)); 
    g.fillRect(720, 410, 304, 330);  // below existing panel
    g.setColor(Color.BLACK);
    g.drawRect(720, 410, 304, 330);
    
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.BOLD, 16));
    g.drawString("Weather Analysis ", 730, 430);

    g.setFont(new Font("Arial", Font.PLAIN, 12));
    g.setColor(Color.DARK_GRAY);
    g.drawString("Press W for cell based weather stats", 730, 430 + 15);
    g.drawString("when hovered over cells", 730, 430 + 28);
    
    g.setFont(new Font("Arial", Font.PLAIN, 14));
    int yOffset = 490;
    
    // stats for weather
    long cellsWithWeather = Arrays.stream(cells)
        .flatMap(Arrays::stream)
        .filter(Cell::hasWeather)
        .count();//counts the number of cells that has weather info
    
    long actorAffectingCells = Arrays.stream(cells)
        .flatMap(Arrays::stream)
        .filter(cell -> cell.hasWeather() && 
               (cell.getRainfall() > 0.45 || cell.getTemperature() > 0.55 || 
                ((Math.sqrt(cell.getWindX() * cell.getWindX() + cell.getWindY() * cell.getWindY()))/Math.sqrt(2)) > 0.5))
        .count();//cells that affect actors
    
    double avgRain = Arrays.stream(cells)
        .flatMap(Arrays::stream)
        .filter(Cell::hasWeather)
        .mapToDouble(Cell::getRainfall)//maps to stream<double>
        .average()
        .orElse(0.0);
    
    double avgTemp = Arrays.stream(cells)
        .flatMap(Arrays::stream)
        .filter(Cell::hasWeather)
        .mapToDouble(Cell::getTemperature)
        .average()
        .orElse(0.0);
    
    double avgWind = Arrays.stream(cells)
        .flatMap(Arrays::stream)
        .filter(Cell::hasWeather)
        .mapToDouble(cell -> ((Math.sqrt(cell.getWindX() * cell.getWindX() + cell.getWindY() * cell.getWindY()))/Math.sqrt(2)))
        .average()
        .orElse(0.0);
    
    // display the stats
    //total cells in grid 20*20->400
    g.drawString("Cells with data: " + cellsWithWeather + "/400", 730, yOffset);
    yOffset += 20;
    g.drawString("Cells affecting actors: " + actorAffectingCells, 730, yOffset);
    yOffset += 20;
    g.drawString(String.format("Avg rainfall: %.3f", avgRain), 730, yOffset);
    yOffset += 20;
    g.drawString(String.format("Avg temperature: %.3f", avgTemp), 730, yOffset);
    yOffset += 20;
    g.drawString(String.format("Avg wind: %.3f", avgWind), 730, yOffset);
    yOffset += 30;
    
    
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
