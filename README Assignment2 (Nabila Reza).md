# Weather-Enhanced Animal Grid Game

## Overview
 I have enhanced the grid-based game from Assignment 1 by implementing:
- Real-time weather data integration from an HTTP server
- Weather effects that impact actor movement and points
- Strategy pattern for actor-specific weather responses
- Observer pattern for real-time weather updates
- Java streams and lambdas for weather analysis
- Visual weather indicators on the grid

The game features a grid-based environment where actors (Cat, Bird, Dog) navigate through cells with different terrain types affected by real-time weather data from an external server. Each actor responds differently to weather conditions based on their characteristics.

## How to compile and Run

### Prerequisites
- Java 11 or Java 21
- Internet connection (for getting weather data)

### Compilation:
For compiling the game open terminal or command prompt in the folder with all your .java files and run:

     javac *.java

### Run/Execution:
- java Main

## Game description:

- Animals (actors) can be selected and moved from one cell to another within the grid
- Each cell has different terrain types and real-time weather data
- Weather conditions (rain, temperature, wind) affect actors differently based on their characteristics
- Actors collect points from terrain types and collectible items, modified by **weather penalties**
- **Real-time weather analysis** shows grid-wide statistics using Java streams
- Visual weather effects are shown on a cell only for **extreme weather conditions** (rain ≥ 0.45, temp ≥ 0.55, wind magnitude ≥ 0.50).
- Points are tracked in Real-time in right panel

## Points System:

### Terrain Points:
- Water → -2 points
- Grass → +2 points
- Rock → -1 point
- Sand → -1 point
- Forest → +1 point
### Collectible Points:
- Magic Feather → +20 points
- Lucky Bell → +10 points
- Golden Collar → +15 points
### Weather Penalties:
- Cat: **Rain** > 0.45 (-2 points), **Heavy rain** > 0.6 (-3 points), **Extreme temperatures** (-1 point)
- Bird: **Wind** > 0.5 (-2 points), **Strong wind** > 0.7 (-3 points), **Heavy rain** (-1 point)
- Dog: **Heavy rain** > 0.6 (-1 point)

## Game Controls:
- Arrow Keys: Move selected animals (up, down, left, right)
- TAB Key: Switch between different animals
- W Key: Works as a toggle button to show **grid-wide weather statistics** 
- Mouse Hover: View cell details and weather information with weather warnings for Actors on the right panel for the selected cell 
  
The selected animal is highlighted with a red border.

### Visual Effects:
- Heavy Rain: Blue border and rain lines 
- Heat Waves: Red heat waves at bottom of cell
- Extreme Wind: Red wind direction arrows
- Items: Colored circles representing collectibles

## Design Patterns Implemented:

## 1. Strategy Pattern 
- **Location**: `WeatherStrategy` interface and implementations (`CatWeatherStrategy`, `BirdWeatherStrategy`, `DogWeatherStrategy`)
- **Purpose**: Enables different weather response behaviors for each actor type without modifying their core logic.

### Bad approach (complex conditional logic):


    public class Actor {
    public void applyWeatherEffects(String actorType, double rain, double temp, double wind) {
        if (actorType.equals("Cat")) {
            if (rain > 0.45) points -= 2;
            if (temp > 0.55) points -= 1;
        } else if (actorType.equals("Bird")) {
            if (wind > 0.5) points -= 2;
            // ... more complex conditions
        }
        // Hard to maintain and extend
    }
    }

### Good approach with Strategy Pattern:

     public interface WeatherStrategy {
    int applyWeatherEffects(int points, double rainfall, double temperature, double windStrength);
     }

    public class CatWeatherStrategy implements WeatherStrategy {
    @Override
    public int applyWeatherEffects(int points, double rainfall, double temperature, double windStrength) {
        if (rainfall > 0.45) points -= 2;
        if (temperature > 0.55) points -= 1;
        return points;
    }
    }

    // Each actor sets its strategy:
    cat.setWeatherStrategy(new CatWeatherStrategy());
    bird.setWeatherStrategy(new BirdWeatherStrategy());

**Implementation**:
- Each actor maintains a reference to a `WeatherStrategy`
- Weather effects are delegated to the strategy object
- Easy to add new actor types with unique weather behaviors
  
### Why this is good design:
- Open/Closed Principle: New weather behaviors can be added without modifying existing actors
- Single Responsibility: Weather logic separated from actor movement logic
- Runtime Flexibility: Strategies can be changed dynamically
- Testable: Each strategy can be tested independently

## 2. Observer Pattern
- **Location**: `WeatherObserver` interface, `Client` (Subject), `Grid` (Observer)
- **Purpose**: Handles real-time weather updates from the server and notifies the grid when new data arrives.

### Bad approach (tight coupling):

    public class Client {
    private Grid grid;
    
    public void receiveWeatherData() {
        // Directly call grid method
        grid.updateWeather(data);
        // Client knows too much about Grid implementation
    }
    }
### Good approach with Observer Pattern:

    public interface WeatherObserver {
    void on_weather_update(WeatherInfo info);
    }

    public class Grid implements WeatherObserver {
    @Override
    public void on_weather_update(WeatherInfo info) {
        // Grid decides how to handle weather updates
    }
    }

    public class Client {
    private List<WeatherObserver> observers = new ArrayList<>();
    
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }
    
    private void notifyObservers(WeatherInfo info) {
        observers.forEach(observer -> observer.on_weather_update(info));
    }
    }


**Implementation**:
- `Client` class acts as subject that receives weather data
- `Grid` implements `WeatherObserver` to receive updates
- Loose coupling between data reception and processing

### Why this is good design:
- Loose Coupling: Client doesn't need to know about Grid implementation
- Extensible: Multiple observers can be added without changing Client
- Separation of Concerns: Data reception separated from data processing
- Event-driven: Real-time updates without polling


## Stream Operations and Lambdas

### Key Stream Operations Demonstrated: Weather Analysis Panel (Grid.java)

#### Bad approach (imperative loops):

```java
public void analyzeWeather() {
    int cellsWithWeather = 0;
    double totalRain = 0;
    
    for (int i = 0; i < cells.length; i++) {
        for (int j = 0; j < cells[i].length; j++) {
            if (cells[i][j].hasWeather()) {
                cellsWithWeather++;
                totalRain += cells[i][j].getRainfall();
                // Complex nested loops with manual counting
            }
        }
    }
}
```
#### Good approach with Streams and Lambdas: 

```java
// Count cells with weather data using flatMap and filter
long cellsWithWeather = Arrays.stream(cells)
    .flatMap(Arrays::stream)
    .filter(Cell::hasWeather)
    .count();

// Find cells that affect actors with complex lambda condition
long actorAffectingCells = Arrays.stream(cells)
    .flatMap(Arrays::stream)
    .filter(cell -> cell.hasWeather() && 
           (cell.getRainfall() > 0.45 || 
            cell.getTemperature() > 0.55 || 
            getNormalizedWind(cell) > 0.5))
    .count();

// Calculate averages using mapToDouble
double avgRain = Arrays.stream(cells)
    .flatMap(Arrays::stream)
    .filter(Cell::hasWeather)
    .mapToDouble(Cell::getRainfall)
    .average()
    .orElse(0.0);
   ```
### Why this is good design:

- Declarative Style: Focus on what to compute, not how
- Readability: Clear data transformation pipelines
- Parallelization Ready: Easy to parallelize for performance
- Less Errors: Reduced error-prone iteration code
- Functional Programming: Lambdas enable concise behavior parameterization

### Lambda Expressions Used

- Method References: `Cell::hasWeather`, `Cell::getRainfall`
- Predicate Lambdas: Complex filtering conditions for weather effects
- Comparator Lambdas: Custom comparisons for finding extreme weather cells

## Weather Data Interpretation:
The application receives real-time weather data from http://13.238.167.130/weather with four attributes (0.0-1.0 range):

### Rainfall (0.0 - 1.0)

- `0.0-0.45`: No effect
- `0.45-0.6`: Moderate rain (affects cats: -2 points)
- `>0.6`: Heavy rain (affects cats: -3 points, dogs: -1 point)

### Temperature (0.0 - 1.0)

- `<0.4`: Cold (affects cats: -1 point)
- `0.4-0.55`: Comfortable range
- `>0.55`: Hot (affects cats: -1 point)
  
### Wind (WindX and WindY, 0.0 - 1.0 each)

**Wind strength calculated as normalized vector magnitude**.
We calculated wind strength by taking the vector magnitude √(windX² + windY²) and normalizing it by dividing by the maximum vector magnitude which in this case is √2 to convert the range from [0, √2] to [0, 1]. 
- `0.0-0.5`: Calm (no effect)
- `0.5-0.7`: Breezy (affects birds: -2 points)
- `> 0.7`: Windy (affects birds: -3 points)
  
## Coordinate System:

Server coordinates centered at (0,0) whereas the grids (0,0) is on the top-left.
The server coordinates were converted by adding 10 (half the length of the grid) to the x-value and subtracting the y-value from 10, which both shifts the origin from center to top-left AND inverts the y-axis since the server's positive y points north while the grid's positive y points downward.

## Uniqueness and Creativity:

This implementation represents a substantial enhancement over Assignment 1 by integrating real-time weather data and demonstrating advanced Java concepts through meaningful gameplay mechanics.

### Design Patterns:

#### Strategy Pattern:
Implemented different weather response strategies for each actor type (CatWeatherStrategy, BirdWeatherStrategy, DogWeatherStrategy). This allows each animal to react authentically to weather conditions based on their characteristics, making the game more realistic and engaging.

#### Observer Pattern:
Created a real-time weather update system where the Grid observes weather data from the Client. This enables seamless integration of live data without tight coupling between components.

### Java Streams and Lambdas:

#### Real-time Weather Analysis:
Implemented a weather analysis panel that uses Java streams to compute:

- Cells with weather data vs total grid cells
- Cells currently affecting actors with penalties
- Average rainfall, temperature, and wind across the grid
  
#### Lambda Expressions:
Used throughout the code for:
- Filtering cells with specific weather conditions
- Mapping weather data to numerical values for analysis
- Complex conditional logic in stream operations

### Strategic Gameplay:
Players must consider both terrain types and weather conditions when moving animals, adding depth to decision-making:
- Avoid moving cats through rainy areas
- Protect birds from strong winds
- Utilize dogs' weather resilience
  
### Technical Integration:
#### HTTP Client Integration:
Seamlessly integrated real-time weather data from external server while maintaining game performance and responsiveness.

### Enhanced Client Implementation:

The `Client.java` file was significantly enhanced from the Week 11 workshop version to serve as a publisher in the Observer pattern. Rather than using the provided code directly, I modified it to:
- Act as an observable subject that notifies multiple observers
- Handle real-time weather data parsing and distribution 
- Integrate seamlessly with the game's coordinate system and weather effects

### Coordinate System Handling:
Converted server coordinate system (center-origin) to grid system (top-left origin) with proper bounds checking.

## Files Overview:

### New Files for Assignment 2:

- WeatherStrategy.java → Strategy pattern interface
- CatWeatherStrategy.java, BirdWeatherStrategy.java, DogWeatherStrategy.java → Weather behavior implementations
- DefaultWeatherStrategy.java → Fallback weather behavior
- WeatherObserver.java → Observer pattern interface
- WeatherInfo.java → Weather data container class
- Client.java → HTTP client for weather data fetching

### Existing Files from Assignment 1:

- Main.java → Game window, input handling, game loop
- Stage.java → Game state management and coordination
- Grid.java → Enhanced with weather analysis and observation
- Cell.java → Enhanced with weather data and visual effects
- Actor.java → Enhanced with weather strategy pattern
- Bird/Cat/Dog.java → Specific actor implementations
- Collectible.java → Base collectible item
- LuckyBell/GoldenCollar/MagicFeather.java → Specific item types
- Inventory.java → Generic inventory management
- Movable.java → Movement interface

