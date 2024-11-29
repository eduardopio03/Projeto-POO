package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends BadConsumable {

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

    if(getRoom().getBoardMap().get(nextPosition).get(0) instanceof JumpMan) {
        ImageTile tile = getRoom().getBoardMap().get(nextPosition).get(0);
        consume((JumpMan) tile);
    }
    if(getRoom().getBoardMap().get(nextPosition).size() > 1) {
        if(getRoom().getBoardMap().get(nextPosition).get(1) instanceof JumpMan) {
            ImageTile tile = getRoom().getBoardMap().get(nextPosition).get(1);
            consume((JumpMan) tile);
        }
    }
}


    public void moveDown() {
        move(Direction.DOWN);
    }

}
