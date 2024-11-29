package pt.iscte.poo.Characters;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

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
            switch (getRoom().getBoardMap().get(newPosition).get(0).getName()) {
                case "Sword":
                    ImageTile sword = getRoom().getBoardMap().get(newPosition).get(0); 
                    super.increaseAttack(10);  //Operação que faz a alteração dos stats
                    ImageGUI.getInstance().removeImage(sword); //Remove o elemento
                    getRoom().getBoardMap().get(newPosition).remove(0); //Remove do boarmap
                    break;
                
                case "GoodMeat":
                    ImageTile meat = getRoom().getBoardMap().get(newPosition).get(0);
                    super.increaseHealth(10);
                    ImageGUI.getInstance().removeImage(meat);
                    getRoom().getBoardMap().get(newPosition).remove(0);
                    break;
            
                default:
                    break;
            }
        } else {
            System.out.println("Movimento inválido para " + newPosition);
        }
    }


    
}
