package pt.iscte.poo.Interactables;

import java.util.List;
import pt.iscte.poo.Characters.Enemy;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameElement implements Interactable {

  private int ticks;
  private Room room;

  public Bomb(Point2D point, boolean isTransposable, Room room) {
    super(point, isTransposable);
    this.ticks = 0;
    this.room = room;
  }

  @Override
  public String getName() {
    return "Bomb";
  }

  @Override
  public int getLayer() {
    return 1;
  }

  public void incrementTicks() {
    this.ticks++;
  }

  public int getTicks() {
    return ticks;
  }

  public Room getRoom() {
    return room;
  }

  public void explode() {
    Point2D bombPosition = this.getPosition();
    getRoom().removeElementsOnExplosion(bombPosition);

    // Remover a bomba da GUI e do mapa
    ImageGUI.getInstance().removeImage(this);
    List<GameElement> elementsAtBombPosition = getRoom().getBoardMap().get(bombPosition);
    if (elementsAtBombPosition != null) {
        elementsAtBombPosition.remove(this);
    }
  }

  @Override
  public void interact(JumpMan jumpMan) {
    if (getRoom().getEngine().getJumpMan().hasBomb() == 0) {
      jumpMan.pickBomb();
      ImageGUI.getInstance().removeImage(this);
      jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
      ImageGUI.getInstance().setStatusMessage("Atingido por uma bomba! Vida atual: " + jumpMan.getHealth());
    }
    //morre e o nivel da reset
    if (getRoom().getEngine().getJumpMan().hasBomb() == 2) {
      explode();
    }
  }

  public void interact(Enemy enemy) {
    enemy.takeDamage(100);
    if (enemy.isDead()) {
        ImageGUI.getInstance().removeImage(enemy);
        getRoom().getBoardMap().get(enemy.getPosition()).remove(enemy);
        ImageGUI.getInstance().setStatusMessage("Inimigo morto!");
    }
    ImageGUI.getInstance().removeImage(this);
    getRoom().getBoardMap().get(this.getPosition()).remove(this);
  }

}
