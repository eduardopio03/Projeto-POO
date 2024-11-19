package pt.iscte.poo.Characters;

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

}
