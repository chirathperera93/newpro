import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

public class ADI_GameBoard extends JPanel {

    // These all variables should be private and final because this variables is used only within this class and never be changed their values
    private final int ADI_Board_X_Cell = 30;
    private final int ADI_Board_Y_Cell = 30;
    private final int ADI_Board_Z_Cell = 10;

    // We can create abstract class to dimensions BoardDimension

    // ********************private methods****************//
    // 1. We have to create a private method to load images like LoadImages()
    // 2. We have to create a private method to locate things in game board like InitGame()
    // 3. We have to create a private method to draw the UI like doDrawing()
    // 4. We have to create a private method to move  like move()
    // 5. We have to create a private method to check collision like checkCollision()
    // 6. We have to create a private method to locate the prey like locatePrey()

    // ********************private class****************//
    // 1. We have to create a private class to listen the key pressing like KeyAdapter

    // ********************public methods****************//
    // 1. We have the public constructor to load Snake and Prey


    public ADI_GameBoard() {
        setPreferredSize(new Dimension(ADI_Board_X_Cell * ADI_Board_Z_Cell, ADI_Board_Y_Cell * ADI_Board_Z_Cell));
        setBackground(Color.black);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}


