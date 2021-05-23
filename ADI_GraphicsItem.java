import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ADI_GraphicsItem implements Shape {

    private BufferedImage itemImage;
    private Graphics2D drawingGraphics, itemGraphics;
    private AffineTransform affineTransform;


    public ADI_GraphicsItem(String imagePath, double x, double y) {
        itemImage = ADI_ImageHelper.loadImage(imagePath);
        // in case we want to edit the image itself
        itemGraphics = itemImage.createGraphics();
        affineTransform = AffineTransform.getTranslateInstance(x, y);
        moveTo(x, y);
    }

    public ADI_GraphicsItem(String imagePath) {
        this(imagePath, 0, 0);
    }

    public double getX() {
        return affineTransform.getTranslateX();
    }

    public double getY() {
        return affineTransform.getTranslateY();
    }

    public int getWidth() {
        return itemImage.getWidth();
    }

    public int getHeight() {
        return itemImage.getHeight();
    }

    public void moveTo(double x, double y) {
        affineTransform.translate(x - getX(), y - getY());
    }

    public void moveTo(Point2D position) {
        affineTransform.translate(position.getX() - getX(), position.getY() - getY());
    }


    public void draw(Graphics2D graphics2D) {
        if (graphics2D == null)
            return;

        if (drawingGraphics == null && drawingGraphics != graphics2D)
            drawingGraphics = graphics2D; //to redraw

        graphics2D.drawImage(itemImage, affineTransform, null);
    }

    public void redraw() {
        if (drawingGraphics != null)
            draw(drawingGraphics);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
    }

    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean contains(double x, double y) {
        return getBounds2D().contains(x, y);
    }

    @Override
    public boolean contains(Point2D point) {
        return getBounds2D().contains(point.getX(), point.getY());
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return getBounds2D().intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return getBounds2D().intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return getBounds2D().contains(x, y, w, h);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return getBounds2D().contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public PathIterator getPathIterator(AffineTransform transform) {
        return getBounds2D().getPathIterator(transform);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform transform, double flatness) {
        return getBounds2D().getPathIterator(transform, flatness);
    }
}


