package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends BadConsumable {

    public Banana(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void consume(JumpMan jumpMan) {
        
    }

    @Override
    public void move(Direction direction) {
        Point2D nextPosition = getPosition().plus(direction.asVector());
        setPosition(nextPosition);
    }

    public void moveDown() {
        move(Direction.DOWN);
    }

}
