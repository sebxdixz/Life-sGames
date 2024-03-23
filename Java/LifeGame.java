import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LifeGame extends JPanel implements ActionListener {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static boolean[][] grid = new boolean[HEIGHT][WIDTH];
    private static JFrame frame;
    private static Timer timer;
    private static final double INITIAL_ALIVE_PROBABILITY = 0.2; // 80% of cells will be alive initially

    public LifeGame() {
        initializeGrid();
        frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH * 8, HEIGHT * 8);
        frame.add(this);
        frame.setVisible(true);

        timer = new Timer(100, this); // Update every 100 milliseconds
        timer.start();
    }

    private void initializeGrid() {
        Random random = new Random();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                grid[row][col] = random.nextDouble() < INITIAL_ALIVE_PROBABILITY;
            }
        }
    }

    private boolean[][] nextGeneration(boolean[][] grid) {
        boolean[][] newGrid = new boolean[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                int neighbors = countNeighbors(grid, row, col);
                if (grid[row][col]) {
                    newGrid[row][col] = (neighbors == 2 || neighbors == 3);
                } else {
                    newGrid[row][col] = (neighbors == 3);
                }
            }
        }
        return newGrid;
    }

    private int countNeighbors(boolean[][] grid, int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                int neighborRow = row + i;
                int neighborCol = col + j;
                if (isValidCell(neighborRow, neighborCol) && grid[neighborRow][neighborCol]) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (grid[row][col]) {
                    g.fillRect(col * 8, row * 8, 8, 8);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        grid = nextGeneration(grid);
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LifeGame::new);
    }
}