package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class Consumable implements ImageTile {

    private Point2D position;

    public Consumable(Point2D position) {
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    protected void setPosition(Point2D position) {
        this.position = position;
    }

    public void consume(JumpMan jumpMan) {
        // TODO: Implement this method
    }


}
