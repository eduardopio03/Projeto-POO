package pt.iscte.poo.Interactables;

import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.utils.Point2D;

public class HiddenTrap extends GameElement {

  public HiddenTrap(Point2D point) {
    super(point, false);
  }

  @Override
  public String getName() {
    return "Wall";  
  }

  @Override
  public int getLayer() {
    return 1;
  }

}
