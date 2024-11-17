package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class NPC implements ImageTile, Movable {

    private Point2D position;
    
    public NPC(Point2D startPosition) {
        this.position = startPosition;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}
