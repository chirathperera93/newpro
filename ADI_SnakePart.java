public class ADI_SnakePart extends ADI_GraphicsItem {

    private ADI_Direction adi_direction;

    public ADI_SnakePart(String partImagePath, int x, int y) {
        super(partImagePath, x, y);
        adi_direction = ADI_Direction.UP;
    }

    public ADI_SnakePart(String partImagePath) {
        this(partImagePath, 0, 0);
    }

    public ADI_SnakePart() {
        this("resources/dot.png");
    }

    public void setDirection(ADI_Direction dir) {
        adi_direction = dir;
    }

    public ADI_Direction getDirection() {
        return adi_direction;
    }

    public void step() {
        //step(5,5);
        step(getWidth(), getHeight());
    }

    public void step(int horizontalStep, int verticalStep) {
        switch (adi_direction) {
            case UP:
                moveTo(getX(), getY() - verticalStep);
                break;
            case DOWN:
                moveTo(getX(), getY() + verticalStep);
                break;
            case LEFT:
                moveTo(getX() - horizontalStep, getY());
                break;
            case RIGHT:
                moveTo(getX() + horizontalStep, getY());
                break;
        }
    }

}
