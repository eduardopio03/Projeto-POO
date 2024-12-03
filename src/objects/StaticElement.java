package objects;

import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.utils.Point2D;

public abstract class StaticElement extends GameElement {

    public StaticElement(Point2D startPosition,boolean isTransposable) {
        super(startPosition,isTransposable);
    }

}
