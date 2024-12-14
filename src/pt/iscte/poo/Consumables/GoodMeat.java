package pt.iscte.poo.Consumables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends GoodConsumable {

  private Room room;
  private int ticksToApodrecer = 10;

  public GoodMeat(Point2D point, Room room) {
    super(point);
    this.room = room;
  }

  @Override
  public String getName() {
    return "GoodMeat";
  }

  @Override
  public int getLayer() {
    return 1;
  }

  public int getTicksToApodrecer() {
    return ticksToApodrecer;
  }

  public void setTicksToApodrecer(int ticksToApodrecer) {
    this.ticksToApodrecer = ticksToApodrecer;
  }

  public void decreaseTicksToApodrecer() {
    ticksToApodrecer--;
    if (ticksToApodrecer == 0) {
      apodrecer();
    }
  }

  @Override
  public void consume(JumpMan jumpMan) {
    jumpMan.increaseHealth(10);
    ImageGUI.getInstance().removeImage(this);
    jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
    ImageGUI.getInstance().setStatusMessage("Carne consumida! Vida atual: " + jumpMan.getHealth() + " Vidas restantes: " + jumpMan.getLives());
  }

  // A carne apodrece se não for consumida em 10 ticks
  public void apodrecer() {
    Point2D position = this.getPosition();
    ImageGUI.getInstance().removeImage(this);
    room.getBoardMap().get(position).remove(this);

    BadMeat badMeat = new BadMeat(position, room);
    room.getBoardMap().get(position).add(badMeat);
    ImageGUI.getInstance().addImage(badMeat);
  }
}
