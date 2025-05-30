package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.Characters.Movable;
import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends BadConsumable implements Movable{

    private final Room room;

    public Banana(Point2D position, Room room) {
        super(position);
        this.room = room;
    }

    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public void consume(JumpMan jumpMan) {
        jumpMan.takeDamage(10);
        ImageGUI.getInstance().removeImage(this);
        ImageGUI.getInstance().setStatusMessage("Atingido por uma banana! Vida atual: " + jumpMan.getHealth() + " Vidas restantes: " + jumpMan.getLives());
    }

    @Override
    public void move(Direction direction) {
        Point2D nextPosition = getPosition().plus(direction.asVector());

        // Atualizar o tabuleiro antes de alterar a posição
        if (getRoom() != null) { // Verificar se o MapHandler está configurado
            getRoom().updatePosition(getPosition(), nextPosition, this);
        }

        // Atualizar a posição da Banana
        setPosition(nextPosition);

        for(int i = 0; i < getRoom().getBoardMap().get(nextPosition).size(); i++) {
            if(getRoom().getBoardMap().get(nextPosition).get(i) instanceof JumpMan) {
                GameElement element = getRoom().getBoardMap().get(nextPosition).get(i);
                consume((JumpMan) element);
            }
        }

    }

    public void moveDown() {
        move(Direction.DOWN);
    }

    public boolean isOutOfBounds() {
        Point2D nextPosition = getPosition().plus(Direction.DOWN.asVector());
        return !ImageGUI.getInstance().isWithinBounds(nextPosition);
    }
}