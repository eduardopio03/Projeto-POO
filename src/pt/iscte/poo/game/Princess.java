package pt.iscte.poo.game;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Princess extends NPC {
    
    public Princess(Point2D startPosition) {
        super(startPosition);
    }

    @Override
    public String getName() {
        return "Princess";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void move(Direction direction) {
        // A princesa não se move
    }

}
