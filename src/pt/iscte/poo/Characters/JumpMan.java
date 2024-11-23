package pt.iscte.poo.Characters;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

    private MapHandler mapHandler; // Interação com o tabuleiro

    public JumpMan(Point2D initialPosition, int initialHealth, MapHandler mapHandler) {
        super(initialPosition, initialHealth);
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
        return 1;
    }

    @Override
    public void move(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direção inválida!");
        }

        Point2D newPosition = super.getPosition().plus(direction.asVector());

        if (mapHandler.isMoveValid(newPosition)) {
            mapHandler.updatePosition(super.getPosition(), newPosition, getName());
            super.setPosition(newPosition);
        } else {
            System.out.println("Movimento inválido para " + newPosition);
        }
    }


    
}
