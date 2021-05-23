import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ADI_GameBoard extends JPanel {

    String message;
    boolean drawMessage;
    ADI_Snake adi_snake;
    ADI_Prey adi_prey;


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
        setPreferredSize(ADI_Config.panelSize);
        setBounds(0, 0, ADI_Config.panelWidth, ADI_Config.panelHeight);
        setBackground(Color.black);
        setFocusable(true);
        drawMessage = false;
        message = "";

    }

    public ADI_Snake getAdi_snake() {
        return adi_snake;
    }

    public ADI_Prey getAdi_prey() {
        return adi_prey;
    }

    public void setSnake(ADI_Snake adi_snake) {
        this.adi_snake = adi_snake;
        setSnakeBorders();
    }

    public void setPrey(ADI_Prey adi_prey) {
        this.adi_prey = adi_prey;
        adi_snake.setPrey(adi_prey);
    }

    public void setSnakeBorders() {
        adi_snake.setBorders(new Rectangle2D.Double(0.0, 0.0, getWidth(), getHeight()));
    }

    public void showMessage(String message) {
        drawMessage = true;
        this.message = message;
        repaint();
    }

    public void removeMessage() {
        drawMessage = false;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (drawMessage) {

            g2d.setFont(g2d.getFont().deriveFont(48f));
            g2d.drawString(message, 10, getHeight() / 2);
            return;
        }

        adi_snake.draw(g2d);
        adi_prey.draw(g2d);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ADI_Config.panelWidth, ADI_Config.panelHeight);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

}


