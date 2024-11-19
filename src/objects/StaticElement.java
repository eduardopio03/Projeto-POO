package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class StaticElement implements ImageTile {

    private Point2D position;

    public StaticElement(Point2D startPosition) {
        this.position = startPosition;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

}
