package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;

public class Sword extends Consumable {

    public Sword(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
