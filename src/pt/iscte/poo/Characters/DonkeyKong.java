package pt.iscte.poo.Characters;

import java.util.Random;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends Character {

    private static final Random RANDOM = new Random();

    public DonkeyKong(Point2D startPosition, int initialHealth) {
        super(startPosition, initialHealth);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void move(Direction direction) {
        Point2D nextPosition = getPosition().plus(direction.asVector());
        if (ImageGUI.getInstance().isWithinBounds(nextPosition)) {
            setPosition(nextPosition);
        }
    }

    public void moveRandomly() {
        Direction randomDirection = Direction.values()[RANDOM.nextInt(Direction.values().length)];
        move(randomDirection);
    }
        

    

}
