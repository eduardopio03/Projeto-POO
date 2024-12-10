package pt.iscte.poo.Interactables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameElement implements Interactable {

  public Bomb(Point2D point, boolean isTransposable) {
    super(point, isTransposable);
  }

  @Override
  public String getName() {
    return "Bomb";
  }

  @Override
  public int getLayer() {
    return 1;
  }

  @Override
  public void interact(JumpMan jumpMan) {
    jumpMan.pickBomb();
    ImageGUI.getInstance().removeImage(this);
    jumpMan.getRoom().getBoardMap().get(this.getPosition()).remove(this);
    ImageGUI.getInstance().setStatusMessage("Atingido por uma bomba! Vida atual: " + jumpMan.getHealth());
  }

}
