package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;


public abstract class GameElement implements ImageTile {

    private Point2D position;  
    private boolean isTransposable;  
    

    public GameElement(Point2D position, boolean isTransposable) {
        this.position = position;
        this.isTransposable = isTransposable;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public boolean isTransposable() {
        return isTransposable;
    }

    public void setTransposable(boolean isTransposable) {
        this.isTransposable = isTransposable;
    }

    
}
