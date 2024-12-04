package pt.iscte.poo.Consumables;

import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.utils.Point2D;

public abstract class BadConsumable extends GameElement implements Consumable {

    public BadConsumable(Point2D position) {
        super(position,true);
    }
}
