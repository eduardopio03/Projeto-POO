package pt.iscte.poo.Interactables;

import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameElement {

  public Bomb(Point2D point) {
    super(point, true);
  }

  @Override
  public String getName() {
    return "Bomb";
  }

  @Override
  public int getLayer() {
    return 1;
  }

}
