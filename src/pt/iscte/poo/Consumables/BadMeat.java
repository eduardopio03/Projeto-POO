package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class BadMeat extends BadConsumable{

  private Room room;

  public BadMeat(Point2D position, Room room) {
    super(position);
    this.room = room;
  }

  @Override
  public void consume(JumpMan jumpMan) {
    jumpMan.takeDamage(20);
    ImageGUI.getInstance().removeImage(this);
    jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
    ImageGUI.getInstance().setStatusMessage("Carne estragada! Vida atual: " + jumpMan.getHealth());

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
