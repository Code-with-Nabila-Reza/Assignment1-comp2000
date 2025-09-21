# Animal Grid Game

## Overview
I have enhanced the grid-based game from week 5 by implementing:
- a movement system with keyboard controls
- collectible items with points
- different terrain types
- point collection system for each Actor based on collectible items and terrain types.

## How to compile and Run
### Prerequisites
- Java 11 or Java 21

### Compilation:
For compiling the game open terminal or comand prompt in the folder with all your 
.java files and run: 
- javac *.java

### Run/Execution:
- java Main

## Game description:
- An animal(actor) can be selected and moved from one cell to another within the grid.
- Each cell have different terrain types assigned to it.
- Each terrain type has different points associated that animals can collect when they
  move to that terrain(cell) on the grid.
- Some cells have collectible items for the animals(Cat, Dog, Bird) to collect
  and gain points from.
- It is possible to switch between different animals and continue the game.

## Points System:
### Terrain Points:
- Water → -2 points
- Grass → +2 points
- Rock → -1 point
- Sand → -2 points
- Forest → +1 point
### Collectible Points: 
- Magic Feather → +20 points
- Lucky Bell → +10 points
- Golden Collar → +15 points

## Game Controls:
- Arrow Keys: Move selected animals (up, down, left, right)
- TAB Key: Switch between different animals
- The selected animal is highlighted with a red border

## Design Examples and Good OOP Principles: 

### 1. Inheritance - Code Reuse and Specialization

#### Bad approach (code duplication):
    public class MagicFeather {
    private int points = 20;
    // + repeated code for drawing, adding to inventory, etc.
    }

    public class LuckyBell {
    private int points = 10;
    // Same repeated code again...
    }

#### Good approach with inheritance:
    public abstract class Collectible {
    protected int points;
    protected String Cname;
    //.........
    // common code for all Collectibles written once
    }

    public class GoldenCollar extends Collectible {
    //use super keyword to implement the already written methods in Collectible class
    //also method override can be used to write the modified version of the existing methods
    }

#### Why this is good design:

- Less code duplication → common functionality in base class
- Easy to add new Collectibles → just extend Collectible class
- Easy to maintain → fix bugs in one place
- Specialized behavior through method overriding

## 2. Interfaces - Flexible Behavior Assurance

#### Bad approach (inconsistent implementation):

    // Different movement methods in different classes
    public class Bird {
    public void flyTo(Cell cell) { ... }
    }

    public class Cat {
    public void walkTo(Cell cell) { ... }
    }
    
#### Good approach with interfaces:

    public interface Movable {
    public void move(Cell destinationCell, Grid grid);
    }

    public abstract class Actor implements Movable {
    // All actors must implement move()
    @Override
    public void move(Cell destinationCell, Grid grid) {
        // Common movement logic
       if(grid.cellIsInsideGrid(destinationCell) ){
            this.loc = destinationCell;
            // Handle points and item collection
        }
    }
    }
#### Why this is good design:

- Clear contracts → all movable objects(actors/animals) implement the same method
- Consistent behavior → same method name for all actors
- Flexible → can add new movement types without breaking existing code
- Function being independant of specific subclasses → movement logic separate from actor specifics

### 3. Generics - Type Safety and Reusability:

#### Bad approach (unsafe casting):

    public class Inventory {
    private List<Object> items; // item could be anything
    
    public void addItem(Object item) {
        items.add(item); // there is no type checking
    }
    //risky as we might get errors
    public int getTotalPoints() {
        int total = 0;
        for (Object item : items) {
            if (item instanceof Collectible) {
                total += ((Collectible)item).getPoints(); // Unsafe cast
            }
        }
        return total;
    }
    }
#### Good approach with generics:

    public class Inventory<T extends Collectible> {
    private List<T> ItemsCollected; 
    // The compiler will make sure that all  items 
    //that are added are Collectibles or its subclasses
    
#### Why this is good design:

- Type safety → compiler prevents wrong items from being added
- No casting → clean code without runtime checks
- Reusable → works with any Collectible subclass
- Clear intent → Inventory<Collectible> shows what it contains

## Uniqueness and Creativity:
#### This implementation represents a substantial improvement over the original week 5 code by introducing new features and making strong use of inheritance, interfaces, generics and other OOP concepts.

### Inheritance:
Designed a Collectible hierarchy (Collectible → MagicFeather, LuckyBell, GoldenCollar) that reuses common code while allowing specialization.
Reduced duplication by using super to handle shared functionality across different collectible types. The Actor class and its subclasses (Cat, Dog, Bird) were originally provided in the Week 5 base code. However, I extended the Actor class with new functionality (such as point handling, inventory management, and movement logic). Thanks to inheritance, these features automatically applied to all subclasses without rewriting them.
### Interfaces:
Implemented the Movable interface to ensure all actors (Cat, Dog, Bird) follow a consistent movement behaviour.
Enabled the Actor class to inherit this behaviour, ensuring each subclass can move and interact with the grid in a unified way.
### Generics:
Developed a generic Inventory system (Inventory<T extends Collectible>) to provide type-safe item storage.
Eliminated the need for unsafe casting and ensured compile-time guarantees that only valid collectibles can be added.
### Polymorphism:
- Treating different actors uniformly through Actor references
- Dynamic method usage for painting and movement
### Encapsulation:
- Each class manages its own state and behavior
- Clean separation of concerns between grid, cells, actors, and items
### New Features:
- Added interactive movement with keyboard controls (Arrow keys + Tab for switching animals).
- Implemented a points system that combines terrain-based scoring with collectible item bonuses.
- Created visual differentiation for terrain and collectibles using custom colors (via RGB).
- Introduced a comprehensive UI with statistics display, showing each animal’s collected points and progress.


## Files Overview:

- Main.java → Game window, input handling, game loop
- Stage.java → Game state management and coordination
- Grid.java → Cell management, grid operations
- Cell.java → Individual cell behavior, terrain types
- Actor.java → Base actor functionality
- Bird/Cat/Dog.java → Specific actor implementations
- Collectible.java → Base collectible item
- LuckyBell/GoldenCollar/MagicFeather.java → Specific item types
- Inventory.java → Generic inventory management
- Movable.java → Movement interface
