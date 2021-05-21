import java.awt.*;

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

    // ****************public methods*******************
    // We have the Constructor ADI_Prey().Under that we should create add method to game board, setTitile method
    public ADI_Prey(String ratImagePath, int panelWidth, int panelHeight) {
        super(ratImagePath);

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


}
