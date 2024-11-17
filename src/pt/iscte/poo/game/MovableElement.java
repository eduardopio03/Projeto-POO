package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class MovableElement implements ImageTile {
    private Point2D position;

    public MovableElement(Point2D startPosition) {
        this.position = startPosition;
    }

    public abstract void move(Direction direction);

    public Point2D getPosition() {
        return position;
    }

    protected void setPosition(Point2D position) {
        this.position = position; // Controla internamente a atualização da posição
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
