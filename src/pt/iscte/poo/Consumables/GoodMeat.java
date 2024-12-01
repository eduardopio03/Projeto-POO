package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.gui.ImageGUI;
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
    jumpMan.increaseHealth(10);
    ImageGUI.getInstance().removeImage(this);
    jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
    ImageGUI.getInstance().setStatusMessage("Carne consumida! Vida atual: " + jumpMan.getHealth());
  }

}
