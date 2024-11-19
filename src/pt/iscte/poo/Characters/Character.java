package pt.iscte.poo.Characters;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class Character implements ImageTile, Movable {
    
    private Point2D position;
    private int health;

    public Character(Point2D startPosition, int initialHealth) {
        this.position = startPosition;
        this.health = initialHealth;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


}
