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
    List<Point2D> neighbourhoodPoints = bombPosition.getNeighbourhoodPoints();
    neighbourhoodPoints.add(bombPosition);

    for (Point2D point : neighbourhoodPoints) {
        List<GameElement> elements = getRoom().getBoardMap().get(point);
        if (elements != null) {
            elements.removeIf(element -> {
                if (element instanceof Enemy || element instanceof JumpMan) {
                    ImageGUI.getInstance().removeImage(element);
                    return true;
                }
                return false;
            });
            System.out.println("Inimigos explodidos em : " + point);
        }
        getRoom().removeDonkeyKongsAtPosition(point); // Remover DonkeyKongs na posição
    }

    ImageGUI.getInstance().removeImage(this);
    getRoom().getBoardMap().remove(bombPosition);
}

  @Override
  public void interact(JumpMan jumpMan) {
    jumpMan.pickBomb();
    ImageGUI.getInstance().removeImage(this);
    jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
    ImageGUI.getInstance().setStatusMessage("Atingido por uma bomba! Vida atual: " + jumpMan.getHealth());
  }

}
