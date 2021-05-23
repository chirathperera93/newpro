public interface ADI_SnakeAction {
    void snakeHitsPrey();
    void snakeHitsItself();
    void snakeHitsBorders();
    void partOutOfBorders(ADI_SnakePart part);
}
