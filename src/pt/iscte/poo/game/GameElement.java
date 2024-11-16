package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;


public abstract class GameElement implements ImageTile {

    private String name;
    private Point2D position;
    private int layer;

    public GameElement(String name, Point2D position, int layer) {
        this.name = name;
        this.position = position;
        this.layer = layer;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return layer;
    }
}
