package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.utils.Point2D;

public class BadMeat extends BadConsumable{

  public BadMeat(Point2D position) {
    super(position);
  }

  @Override
  public void consume(JumpMan jumpMan) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'consume'");
  }

  @Override
  public String getName() {
    return "BadMeat";
  }

  @Override
  public int getLayer() {
    return 1;
  }

}
