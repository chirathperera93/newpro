import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Prey represents any prey object.
 *
 * @author
 */

// 1. We have to do extends this class by JFrame because Prey should be in game board (UI)
public class ADI_Prey extends ADI_GraphicsItem {
    /**
     * Constructor for objects of class Prey
     */
    ArrayList<Point2D> positions;
    Random random;

    // ****************public methods*******************
    // We have the Constructor ADI_Prey().Under that we should create add method to game board, setTitle method
    public ADI_Prey(String ratImagePath, int panelWidth, int panelHeight) {
        super(ratImagePath);
        positions = createPoints(panelWidth, panelHeight);
        random = new Random();

    }

    public ADI_Prey(int panelWidth, int panelHeight) {
        this("resources/apple.png", panelWidth, panelHeight);
    }

    public ADI_Prey(Dimension panelSize) {
        this("resources/apple.png", panelSize.width, panelSize.height);
    }

    public ADI_Prey() {
        this("resources/apple.png", 0, 0);
    }

    public ArrayList<Point2D> createPoints(int w, int h) {
        ArrayList<Point2D> all = new ArrayList<Point2D>();

        // minus getWidth or getHeight
        // so it will no go out of borders
        for (int x = 0; x < w - getWidth(); x += getWidth()) {
            for (int y = 0; y < h - getHeight(); y += getWidth()) {
                all.add(new Point2D.Double(x, y));
            }
        }

        return all;
    }

    public void escapeFrom(ADI_Snake adi_snake) {
        ArrayList<Point2D> current = positions;

        for (ADI_SnakePart adi_snakePart : adi_snake.parts()) {
            current.remove(new Point2D.Double(adi_snakePart.getX(), adi_snakePart.getY()));
        }

        int randomPosition = random.nextInt(current.size() - 1);


        // sometimes it goes out of borders
        // and inside the snake

        moveTo(current.get(randomPosition));
    }
}
