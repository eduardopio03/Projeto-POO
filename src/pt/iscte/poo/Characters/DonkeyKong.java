package pt.iscte.poo.Characters;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends Character {

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
        int ticks = ImageGUI.getInstance().getTicks();
        if (ticks % 3 == 0) {
            moveRandomly();
        }
    }

    private void moveRandomly() {
        
    }
        

    

}
