package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Stair implements ImageTile {

  private final Point2D point;

  public Stair(Point2D point) {
    this.point = point;
  }

  @Override
  public String getName() {
    return "Stair";
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
