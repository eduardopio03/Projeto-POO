package pt.iscte.poo.Characters;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

    private MapHandler mapHandler; // Interação com o tabuleiro

    public JumpMan(Point2D initialPosition, int initialHealth, int initialAttack, MapHandler mapHandler) {
        super(initialPosition, initialHealth, initialAttack);
        this.mapHandler = mapHandler;
    }

    public void setMapHandler(MapHandler map) {
        this.mapHandler = map;
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

        if (mapHandler.isMoveValid(newPosition)) {
            mapHandler.updatePosition(super.getPosition(), newPosition, this);
            super.setPosition(newPosition);
        } else {
            System.out.println("Movimento inválido para " + newPosition);
        }
    }


    
}
