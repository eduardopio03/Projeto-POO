package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Trap implements ImageTile{

  private Point2D point;

  public Trap(Point2D point) {
    this.point = point;
  }

  @Override
  public String getName() {
    return "Trap";
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
