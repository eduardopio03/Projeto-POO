package pt.iscte.poo.Characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends Enemy {

  private MapHandler mapHandler;
  private static final Random RANDOM = new Random();

  public Bat(Point2D startPosition, int initialHealth, int initialAttack, MapHandler mapHandler) {
    super(startPosition, initialHealth, initialAttack);
    this.mapHandler = mapHandler;
  }

  @Override
  public String getName() {
    return "Bat";
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
      // Atualizar a posição do morcego
      setPosition(nextPosition);
    }
  }

  public void moveRandomly() {
    List<Direction> validDirections = new ArrayList<>();

    // Verificar quais direções são válidas
    for (Direction direction : new Direction[] {
      Direction.LEFT,
      Direction.RIGHT,
      Direction.DOWN // Adicionar a direção DOWN
    }) {
      Point2D newPosition = getPosition().plus(direction.asVector());

      // Se o movimento for válido (não for parede e dentro dos limites)
      if (mapHandler.isMoveValid(newPosition)) {
        validDirections.add(direction);
      }
    }

    // Se houver direções válidas, escolher uma aleatoriamente
    if (!validDirections.isEmpty()) {
      if (validDirections.contains(Direction.DOWN)) {
        move(Direction.DOWN);
      } else {
        int randomIndex = RANDOM.nextInt(validDirections.size());
        move(validDirections.get(randomIndex));
      }
    }
  }

  @Override
  public void interact(JumpMan jumpMan) {
    jumpMan.takeDamage(20);
    ImageGUI.getInstance().removeImage(this);
    jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
    ImageGUI.getInstance().setStatusMessage("Atingido por um morcego! Vida atual: " + jumpMan.getHealth());
  }

  
}