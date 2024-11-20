package pt.iscte.poo.Characters;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

    Room currentRoom;
   
    public JumpMan(Point2D initialPosition, int initialHealth, Room room) {
        super(initialPosition, initialHealth);
        this.currentRoom = room;
    }
    
    @Override
    public String getName() {
        return "JumpMan"; 
    }

    @Override
    public int getLayer() {
        return 1; 
    }

    @Override
    public void move(Direction direction) {
        if (direction != null) {
            Point2D newPosition = super.getPosition().plus(direction.asVector());

            //Verificar se esta dentro dos limites do tabuleiro
            if (ImageGUI.getInstance().isWithinBounds(newPosition)) {
                //Verificar se não é uma parede
                if (!currentRoom.getBoardMap().containsKey(newPosition) ||
                    !currentRoom.getBoardMap().get(newPosition).equals("Wall")){
                
                    //Atualizar a posição no mapa
                    currentRoom.getBoardMap().remove(super.getPosition());
                    super.setPosition(newPosition);
                    currentRoom.getBoardMap().put(newPosition, "JumpMan");
                }
            }
            else {
                System.out.println("Movimento inválido");
            }
        }  
        else 
            throw new IllegalArgumentException("Direção inválida");
    }
}


