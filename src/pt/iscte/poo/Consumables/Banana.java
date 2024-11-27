package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.Characters.MapHandler;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends BadConsumable {

    private final MapHandler mapHandler;

    public Banana(Point2D position, MapHandler mapHandler) {
        super(position);
        this.mapHandler = mapHandler;
    }

    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void consume(JumpMan jumpMan) {
        
    }

    @Override
    public void move(Direction direction) {
    Point2D nextPosition = getPosition().plus(direction.asVector());

    // Atualizar o tabuleiro antes de alterar a posição
    if (mapHandler != null) { // Verificar se o MapHandler está configurado
        mapHandler.updatePosition(getPosition(), nextPosition, this);

    }

    // Atualizar a posição da Banana
    setPosition(nextPosition);
}


    public void moveDown() {
        move(Direction.DOWN);
    }

}
