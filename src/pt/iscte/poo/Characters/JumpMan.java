package pt.iscte.poo.Characters;

import java.util.List;
import objects.DoorClosed;
import objects.Floor;
import objects.Stairs;
import pt.iscte.poo.Consumables.Consumable;
import pt.iscte.poo.Interactables.Bomb;
import pt.iscte.poo.Interactables.Interactable;
import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

    private Room room;
    private boolean hasBomb;   // Interação com o tabuleiro
    private int lives;

    public JumpMan(Point2D initialPosition, int initialHealth, Room room) {
        super(initialPosition, initialHealth, 100);
        this.room = room;
        this.hasBomb = false;
        this.lives = 3;
    }

    public void setRoom(Room newRoom) {
        this.room = newRoom;
    }

    public Room getRoom() {
        return room;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setHasBomb(boolean value) {
        this.hasBomb = value;
    }

    public int getLives() {
        return lives;
    }

    public int resetLives() {
        return lives = 3;
    }

    public void decreaseLives() {
        lives--;
        if (lives > 0) {
            room.getEngine().resetLevel();
        } else {
            room.getEngine().resetGame();
        }
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

        // Verifica se o movimento é válido
        if (getRoom().isMoveValid(newPosition)) {

            // Se for um movimento para cima, verifica se há suporte
            if (direction == Direction.UP) {
                Point2D belowPosition = super.getPosition().plus(Direction.DOWN.asVector());
                if (!hasSupport(belowPosition)) {
                    System.out.println("Movimento para cima inválido: sem suporte abaixo!");
                    return; // Bloqueia o movimento para cima
                }
            }

            // Atualiza a posição no mapa e no jogo
            getRoom().updatePosition(super.getPosition(), newPosition, this);
            super.setPosition(newPosition);

            // Verifica a posição abaixo da nova posição do JumpMan
            Point2D positionBelow = newPosition.plus(Direction.DOWN.asVector());
            List<GameElement> elementsBelow = getRoom().getBoardMap().get(positionBelow);
            if (elementsBelow != null) {
                for (GameElement element : elementsBelow) {
                    if (element instanceof Interactable) {
                        ((Interactable) element).interact(this);
                    }
                }
            }

            // Verifica e consome consumíveis na nova posição
            List<GameElement> elementsAtNewPosition = getRoom().getBoardMap().get(newPosition);
            if (elementsAtNewPosition != null) {
                for (GameElement element : elementsAtNewPosition) {
                    if (element instanceof Consumable) {
                        ((Consumable) element).consume(this);
                    }
                    if (element instanceof Interactable) {
                        ((Interactable) element).interact(this);
                    }
                }
            }
        } else {
            System.out.println("Movimento inválido para " + newPosition);
        }
    }

    public void fall() {
        Point2D belowPosition = getPosition().plus(Direction.DOWN.asVector());

        // Verifica se a posição abaixo é válida
        while (room.isMoveValid(belowPosition) && !hasSupport(belowPosition)) {
            getRoom().updatePosition(getPosition(), belowPosition, this);
            super.setPosition(belowPosition);
            belowPosition = belowPosition.plus(Direction.DOWN.asVector());
        }
    }

    private boolean hasSupport(Point2D position) {
        // Verifica elementos na posição abaixo
        List<GameElement> elementsBelow = room.getBoardMap().get(position);
        if (elementsBelow != null) {
            for (GameElement element : elementsBelow) {
                if (element instanceof Floor || element instanceof Stairs) {
                    return true; // Tem suporte: chão ou escada abaixo
                }
            }
        }

        // Verifica se o JumpMan está numa escada
        List<GameElement> elementsHere = room.getBoardMap().get(super.getPosition());
        if (elementsHere != null) {
            for (GameElement element : elementsHere) {
                if (element instanceof Stairs) {
                    return true; // Suporte presente porque está numa escada
                }
            }
        }

        return false; // Sem suporte
    }

    //Verifica se o JumpMan chegou à porta
    public boolean reachedDoor() {
        return getRoom().getBoardMap().get(getPosition()).get(0) instanceof DoorClosed; 
    }

    public boolean reachedPrincess() {
        return getRoom().getBoardMap().get(getPosition()).get(0) instanceof Princess;
    }

    public boolean reachedBomb() {
        return getRoom().getBoardMap().get(getPosition()).get(0) instanceof Bomb;
    }

    public void pickBomb() {
    	this.hasBomb = true;
    }
    
    
    public void dropBomb() {
    	Bomb bomb = new Bomb(getPosition(), true, getRoom());
    	getRoom().addObject(getPosition(),bomb);
    	ImageGUI.getInstance().addImage(bomb);
    	setHasBomb(false);
    }
    


    
}
