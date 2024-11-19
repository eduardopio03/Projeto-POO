package objects;

import pt.iscte.poo.utils.Point2D;

public class Stairs extends StaticElement {

  public Stairs(Point2D point) {
    super(point);
  }

  @Override
  public String getName() {
    return "Stairs";
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
