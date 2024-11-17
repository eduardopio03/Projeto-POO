package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Stairs implements ImageTile {

  private final Point2D point;

  public Stairs(Point2D point) {
    this.point = point;
  }

  @Override
  public String getName() {
    return "Stairs";
  }

  @Override
  public Point2D getPosition() {
    return point;
  }

  @Override
  public int getLayer() {
    //TODO
    return 0;
  }

}
