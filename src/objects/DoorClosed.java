package objects;

import pt.iscte.poo.utils.Point2D;

public class DoorClosed extends StaticElement {


  public DoorClosed(Point2D point) {
    super(point, true);
  } 

  @Override
  public String getName() {
    return "DoorClosed";
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
