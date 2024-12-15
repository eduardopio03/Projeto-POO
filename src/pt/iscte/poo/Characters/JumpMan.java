package pt.iscte.poo.Characters;

import java.util.List;
import objects.DoorClosed;
import objects.Stairs;
import objects.Wall;
import pt.iscte.poo.Consumables.Consumable;
import pt.iscte.poo.Interactables.Bomb;
import pt.iscte.poo.Interactables.Interactable;
import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

    private Room room;  // Interação com o tabuleiro
    private int hasBomb;   //0: não tem a bomba | 1: tem a bomba | 2: não tem a bomba depois de largar
    private int lives;

    public JumpMan(Point2D initialPosition, int initialHealth, Room room) {
        super(initialPosition, initialHealth, 100);
        this.room = room;
        this.hasBomb = 0;
        this.lives = 3;
    }

    public void setRoom(Room newRoom) {
        this.room = newRoom;
    }

    public Room getRoom() {
        return room;
    }

    public int hasBomb() {
        return hasBomb;
    }

    public void setHasBomb(int value) {
        this.hasBomb = value;
    }

    public int getLives() {
        return lives;
    }

    public int resetLives() {
        return lives = 3;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decreaseLives() {
        setLives(getLives()-1);;
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

            // Se for um movimento para cima, verifica se há suporte ou se está numa escada
            if (direction == Direction.UP) {
                if (!isOnStairs(super.getPosition())) {
                    System.out.println("Movimento para cima inválido: sem escada!");
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

            // Verifica e consome consumíveis e interactables na nova posição
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
    
        // Verifica se a posição abaixo é válida e se não há suporte
        while (getRoom().isMoveValid(belowPosition) && !hasSupport(belowPosition)) {
            getRoom().updatePosition(getPosition(), belowPosition, this);
            super.setPosition(belowPosition);
            belowPosition = belowPosition.plus(Direction.DOWN.asVector());
        }
        System.out.println("Falling: " + getPosition());
    }

    private boolean hasSupport(Point2D position) {
    // Verifica elementos na posição abaixo
    List<GameElement> elementsBelow = getRoom().getBoardMap().get(position);
    if (elementsBelow != null) {
        for (GameElement element : elementsBelow) {
            if (element instanceof Wall || element instanceof Stairs) {
                return true; // Tem suporte: chão ou escada abaixo
            }
        }
    }
    return false; // Sem suporte
}

    private boolean isOnStairs(Point2D position) {
        List<GameElement> elementsAtPosition = getRoom().getBoardMap().get(position);
        if (elementsAtPosition != null) {
            for (GameElement element : elementsAtPosition) {
                if (element instanceof Stairs) {
                    return true; // Está em uma escada
                }
            }
        }
        return false; // Não está numa escada
    }

    //Verifica se o JumpMan chegou à porta
    public boolean reachedDoor() {
        List<GameElement> elements = getRoom().getBoardMap().get(getPosition());
        if (elements != null && !elements.isEmpty()) {
            GameElement element = elements.get(0);
            return element instanceof DoorClosed;
        }
        return false;
    }

    public boolean reachedPrincess() {
        List<GameElement> elements = getRoom().getBoardMap().get(getPosition());
        if (elements != null && !elements.isEmpty()) {
            GameElement element = elements.get(0);
            return element instanceof Princess;
        }
        return false;
    }

    public boolean reachedBomb() {
        return getRoom().getBoardMap().get(getPosition()).get(0) instanceof Bomb;
    }

    public void pickBomb() {
    	setHasBomb(1);
    }
    
    
    public void dropBomb() {
    	Bomb bomb = new Bomb(getPosition(), false, getRoom());
    	getRoom().addObject(getPosition(),bomb);
    	ImageGUI.getInstance().addImage(bomb);
    	setHasBomb(2);
    }
    


    
}
