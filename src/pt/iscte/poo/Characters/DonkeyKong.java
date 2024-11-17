package pt.iscte.poo.Characters;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends Enemy {

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
        //TODO: Implementar o movimento do DonkeyKong
    }

}
