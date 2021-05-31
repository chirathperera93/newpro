import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
// 1. We have to do extends this class by JFrame because Snake should be in game board (UI)
public class ADI_Snake {






    private ArrayList<ADI_SnakePart> adi_snakeBody;
    private ADI_Prey adi_prey;
    private ADI_Direction direction;
    private ADI_SnakePart snakeHead;
    private ADI_SnakeAction adi_snakeAction;
    private Rectangle2D.Double borders;




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

    public void setSnakeActionListener(ADI_SnakeAction act) {
        adi_snakeAction = act;
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
        // add to his brothers :D
        adi_snakeBody.add(part);


    }

    // add a normal snake part
    public void addNormalPart() {
        addPart(new ADI_SnakePart());
    }

    public ArrayList<ADI_SnakePart> parts() {
        return adi_snakeBody;
    }

    public void setDirection(ADI_Direction dir) {
        direction = dir;
    }

    public ADI_Direction getDirection() {
        return direction;
    }

    public void step() {
        // move the head
        // then every part will replace the next part's place
        double nextX = snakeHead.getX(), nextY = snakeHead.getY();
        // get the curren direction of head
        // and it will be the next direction to others
        ADI_Direction nextDirection = snakeHead.getDirection(), currentDirection;
        // set the new head direction
        // which is the whole snake direction
        //System.out.println(nextDirection);
        snakeHead.setDirection(direction);
        // move the head
        snakeHead.step();

        // follow the head
        for (int i = 1; i < adi_snakeBody.size(); i++) {

            // test for collision by the way?
            ADI_SnakePart nextPart = adi_snakeBody.get(i);
            // store the current direction
            currentDirection = nextPart.getDirection();
            // set the next direction
            nextPart.setDirection(nextDirection);
            // move to that direction
            nextPart.step();
            // and reset the next to current
            // so the next direction will directed to teh current
            nextDirection = currentDirection;
        }

        checkCollisions();
    }

    public void checkCollisions() {
        // check collision and call a something
        // or make an Event derived class to do so

        if (adi_snakeAction == null)
            return;


        if (snakeHead.collidesWith(adi_prey))
            adi_snakeAction.snakeHitsPrey();

        // head collides with others already
        // its previous part has the same coordinates
        for (int i = 1; i < adi_snakeBody.size(); i++)
            if (snakeHead.collidesWith(adi_snakeBody.get(i)))
                adi_snakeAction.snakeHitsItself();


        if (!borders.contains(snakeHead.getBounds()))
            adi_snakeAction.snakeHitsBorders();

        for (ADI_SnakePart adi_snakePart : adi_snakeBody) {
            if (adi_snakePart == snakeHead)
                continue;

            if (borders.contains(adi_snakePart.getBounds()))
                adi_snakeAction.partOutOfBorders(adi_snakePart);
        }
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

    public void redraw() {
        for (ADI_SnakePart adi_snakePart : adi_snakeBody) {
            adi_snakePart.redraw();
        }
    }

}
