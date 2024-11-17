package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class DoorClosed implements ImageTile{

  private Point2D point;

  public DoorClosed(Point2D point) {
    this.point = point;
  } 

  @Override
  public String getName() {
    return "DoorClosed";
  }

  @Override
  public Point2D getPosition() {
    return point;
  }

  @Override
  public int getLayer() {
    return 1;
  }

}
