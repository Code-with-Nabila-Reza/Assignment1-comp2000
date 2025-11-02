import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
    public static void main(String[] args) throws Exception {
      Main window = new Main();
      window.run();
    }

    class Canvas extends JPanel implements MouseMotionListener, KeyListener {
      Stage stage = new Stage();

      private Client weatherClient;

      private Actor selectedActor;
      private int sActorIndex = 0;

      public Canvas() {
        setPreferredSize(new Dimension(1024, 720));  // Changed to 1024x720
        addMouseMotionListener(this);  // Add mouse tracking
        addKeyListener(this);
        setFocusable(true);//added for the key listener
        setFocusTraversalKeysEnabled(false);
        selectedActor = stage.getActors().get(0);
        //getActors--arraylist of actors
        requestFocusInWindow();

            weatherClient = new Client();//weatherClient--publisher
            weatherClient.addObserver(stage.getGrid()); 
            // Grid observes weather
            weatherClient.WeatherStream();
             // Starts receiving weather data

            selectedActor = stage.getActors().get(0);
            requestFocusInWindow();

        }

      @Override
      public void paint(Graphics g) {
        stage.paint(g, getMousePosition());
        
        if (selectedActor != null) {
          g.setColor(java.awt.Color.RED);
          Cell loc = selectedActor.getCellLocation();
          g.drawRect(loc.x, loc.y, Cell.size, Cell.size);
      }
      }
      
      @Override
      public void mouseMoved(MouseEvent e) {
        repaint();  // Trigger repaint when mouse moves
      }
      
      @Override
      public void mouseDragged(MouseEvent e) {
        repaint();  // Trigger repaint when mouse is dragged
      }
      
      @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                moveSelectedActor(-1, 0);
            } else if (key == KeyEvent.VK_RIGHT) {
                moveSelectedActor(1, 0);
            } else if (key == KeyEvent.VK_UP) {
                moveSelectedActor(0, -1);
            } else if (key == KeyEvent.VK_DOWN) {
                moveSelectedActor(0, 1);
            } else if (key == KeyEvent.VK_TAB) {
                sActorIndex = (sActorIndex + 1) % stage.getActors().size();
                selectedActor = stage.getActors().get(sActorIndex);
                requestFocusInWindow();
            }
            else if (key == KeyEvent.VK_W) {
              // toggles weather analysis panel
              stage.toggleWeatherAnalysis();
              repaint();  // refreshes to show or hide the panel
            }
            repaint();
        }

        void moveSelectedActor(int dx, int dy) {
          Cell currentCell = selectedActor.getCellLocation();
          int col = (currentCell.x - 10) / Cell.size;
          int row = (currentCell.y - 10) / Cell.size;
          int newCol = col + dx;
          int newRow = row + dy;
          
          if (newCol >= 0 && newCol < 20 && newRow >= 0 && newRow < 20) {
              Cell targetCell = stage.getGrid().getCell(newCol, newRow);
              selectedActor.move(targetCell, stage.getGrid());
              
          }
      }

      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {}


    }

    private Main() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Canvas canvas = new Canvas();
      this.setContentPane(canvas);
      this.pack();
      this.setVisible(true);
    }

    public void run() {
      while(true) {
        try {
          Thread.sleep(16);  // Add small delay for better performance (~60 FPS)
        } catch (InterruptedException e) {
          break;
        }
        repaint();
      }
    }
}
