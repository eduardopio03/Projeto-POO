package pt.iscte.poo.Characters;

import pt.iscte.poo.Consumables.Consumable;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

    private Room room; // Interação com o tabuleiro

    public JumpMan(Point2D initialPosition, int initialHealth, int initialAttack, Room room) {
        super(initialPosition, initialHealth, initialAttack);
        this.room = room;
    }

    public void setRoom(Room newRoom) {
        this.room = newRoom;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String getName() {
        return "JumpMan";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void move(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direção inválida!");
        }

        Point2D newPosition = super.getPosition().plus(direction.asVector());

        if (getRoom().isMoveValid(newPosition)) {
            getRoom().updatePosition(super.getPosition(), newPosition, this);
            super.setPosition(newPosition);

            //Verifica se há algum comsumable na posição em que o jumpman vai se mover
            if (getRoom().isMoveValid(newPosition)) {
                getRoom().updatePosition(super.getPosition(), newPosition, this);
                super.setPosition(newPosition);

                //Verifica se há algum consumable na nova posição
                if (!getRoom().getBoardMap().get(newPosition).isEmpty()) {
                    ImageTile tile = getRoom().getBoardMap().get(newPosition).get(0);

                    if (tile instanceof Consumable) {
                        ((Consumable) tile).consume(this);
                    }
                }
            }
            
        } else {
            System.out.println("Movimento inválido para " + newPosition);
        }
    }


    
}
