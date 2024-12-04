package pt.iscte.poo.Characters;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends Enemy {

  private MapHandler mapHandler;

  public Bat(Point2D startPosition, int initialHealth, int initialAttack, MapHandler mapHandler) {
    super(startPosition, initialHealth, initialAttack);
    this.mapHandler = mapHandler;
  }

  @Override
  public void move(Direction direction) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'move'");
  }

  @Override
  public String getName() {
    return "Bat";
  }

  @Override
  public int getLayer() {
    return 2;
  }

}
