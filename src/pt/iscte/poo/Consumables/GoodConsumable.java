package pt.iscte.poo.Consumables;

import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.utils.Point2D;

public abstract class GoodConsumable extends GameElement implements Consumable {

    public GoodConsumable(Point2D position) {
        super(position);
    }
}
