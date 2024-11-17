package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends GoodConsumable {

  public GoodMeat(Point2D point) {
    super(point);
  }

  @Override
  public String getName() {
    return "GoodMeat";
  }

  @Override
  public int getLayer() {
    return 1;
  }

  @Override
  public void consume(JumpMan jumpMan) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'consume'");
  }

}
