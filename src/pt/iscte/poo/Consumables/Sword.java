package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GoodConsumable {

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

    @Override
    public void consume(JumpMan jumpMan) {
        jumpMan.increaseAttack(100);
        ImageGUI.getInstance().removeImage(this);
        jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
        ImageGUI.getInstance().setStatusMessage("Espada apanhada! Ataque atual: " + jumpMan.getAttack() + " Vidas restantes: " + jumpMan.getLives());
    }
}
