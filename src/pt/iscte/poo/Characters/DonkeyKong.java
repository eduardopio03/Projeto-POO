package pt.iscte.poo.Characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pt.iscte.poo.Consumables.Banana;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends Enemy {

    private static final Random RANDOM = new Random();
    private final MapHandler mapHandler;

    public DonkeyKong(Point2D startPosition, int initialHealth, int initialAttack, MapHandler mapHandler) {
        super(startPosition, initialHealth, initialAttack);
        this.mapHandler = mapHandler;
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void move(Direction direction) {
        Point2D nextPosition = getPosition().plus(direction.asVector());
        if (ImageGUI.getInstance().isWithinBounds(nextPosition) && mapHandler.isMoveValid(nextPosition)) {
            // Atualizar o tabuleiro
            mapHandler.updatePosition(getPosition(), nextPosition, this);
            // Atualizar a posição do DonkeyKong
            setPosition(nextPosition);
        }
    }

    public void moveRandomly() {
        List<Direction> validDirections = new ArrayList<>();
        
        // Verificar quais direções são válidas
        for (Direction direction : new Direction[] { Direction.LEFT, Direction.RIGHT }) {
            Point2D newPosition = getPosition().plus(direction.asVector());
            
            // Se o movimento for válido (não for parede e dentro dos limites)
            if (mapHandler.isMoveValid(newPosition)) {
                validDirections.add(direction);
            }
        }

        // Se houver direções válidas, escolher uma aleatoriamente
        if (!validDirections.isEmpty()) {
            int randomIndex = RANDOM.nextInt(validDirections.size());
            move(validDirections.get(randomIndex));  // Mover para a direção sorteada
        }
    }

    public void launchBanana() {
        Banana banana = new Banana(getPosition(), (Room) mapHandler);
        ((Room) mapHandler).getBananas().add(banana);
        ImageGUI.getInstance().addImage(banana);
    }

    @Override
    public void interact(JumpMan jumpMan) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'interact'");
    }
}