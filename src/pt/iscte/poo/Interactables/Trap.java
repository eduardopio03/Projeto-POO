package pt.iscte.poo.Interactables;

import objects.StaticElement;
import pt.iscte.poo.utils.Point2D;

public class Trap extends StaticElement {


  public Trap(Point2D point) {
    super(point,false);
  }

  @Override
  public String getName() {
    return "Trap";
  }

  @Override
  public Point2D getPosition() {
    return super.getPosition();
  }

  @Override
  public int getLayer() {
    return 1;
  }

}
