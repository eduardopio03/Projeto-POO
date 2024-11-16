package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class MovableElement implements Movable, ImageTile {
    private Point2D position; // Posição do elemento no jogo


    public MovableElement(Point2D startPosition) {
        this.position = startPosition;
    }

    @Override
    public void move(Direction direction) {
        //TODO: Implementar o movimento do elemento
    }

    public Point2D getPosition() {
        return position;
    }
}
