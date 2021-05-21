import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Write a description of class Snake here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */


// 1. We have to do extends this class by JFrame because Snake should be in game board (UI)
public class ADI_Snake {

    private ArrayList<ADI_SnakePart> adi_snakeBody;
    private ADI_Prey adi_prey;
    private ADI_Direction direction;
    private ADI_SnakePart snakeHead;

    private Rectangle2D.Double borders;

    /**
     * Constructor for objects of class Snake
     */

    // ****************public methods*******************
    // We have the Constructor ADI_Snake().Under that we should create add method to game board, setTitle method3
    public ADI_Snake() {
        // default length with 6 parts
        // head + 5 parts
        this(ADI_Config.defaultLength);
    }

    // with specific parts
    public ADI_Snake(int length) {
        // list of parts
        adi_snakeBody = new ArrayList<ADI_SnakePart>();

        // set default direction
        direction = direction.UP;


        // add head
        snakeHead = new ADI_SnakePart("resources/head.png");
        addPart(snakeHead);

        for (int i = 0; i < length; i++)
            addNormalPart();
    }

    public void setPrey(ADI_Prey prey) {
        adi_prey = prey;
    }


    public void setBorders(Rectangle2D.Double rect) {
        borders = rect;
    }

    public void addPart(ADI_SnakePart part) {
        // add part after the last one
        if (part == null) return;
        if (direction == null) direction = ADI_Direction.UP;

        int size = adi_snakeBody.size();
        double lastX = 0, lastY = 0;
        ADI_SnakePart lastPart = null;

        if (size > 0) {
            lastPart = adi_snakeBody.get(adi_snakeBody.size() - 1);
            lastX = lastPart.getX();
            lastY = lastPart.getY();
        }
        // move to the last coordinates
        part.moveTo(lastX, lastY);
        // place the part at the right location
        // no last part so step with step value of 0
        // for both vertical or horizontal step
        // then reset last direction
        if (lastPart == null) {
            // set default direction reverse
            part.setDirection(reverseDirection(direction));
            part.step(0, 0);
            part.setDirection(direction);
        } else {
            // set direction to reverse to add to last
            part.setDirection(reverseDirection(lastPart.getDirection()));
            part.step();
            part.setDirection(lastPart.getDirection());
        }
        // add to his borthers :D
        adi_snakeBody.add(part);
        // debug
        //System.out.println("Part Added at: " + part.getX() + ", " + part.getY());
        //System.out.println("Direction: " + part.getDirection());


    }

    // add a normal snake part
    public void addNormalPart() {
        addPart(new ADI_SnakePart());
    }


    public ADI_Direction reverseDirection(ADI_Direction adi_direction) {
        ADI_Direction d = ADI_Direction.DOWN;

        switch (adi_direction) {
            case UP:
                d = ADI_Direction.DOWN;
                break;
            case DOWN:
                d = ADI_Direction.UP;
                break;
            case LEFT:
                d = ADI_Direction.RIGHT;
                break;
            case RIGHT:
                d = ADI_Direction.LEFT;
                break;
        }

        return d;
    }

    // move the whole snake
    public void moveTo(double x, double y) {
        for (ADI_SnakePart adi_snakePart : adi_snakeBody) {
            adi_snakePart.moveTo(x + adi_snakePart.getX(), y + adi_snakePart.getY());
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (ADI_SnakePart adi_snakePart : adi_snakeBody) {
            adi_snakePart.draw(graphics2D);
        }
    }


}
