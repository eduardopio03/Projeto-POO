package pt.iscte.poo.Interactables;

import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class HiddenTrap extends GameElement implements Interactable {

  public HiddenTrap(Point2D point) {
    super(point, false);
  }

  @Override
  public String getName() {
    return "Wall";  
  }

  @Override
  public int getLayer() {
    return 1;
  }

  @Override
  public void interact(JumpMan jumpMan) {
    jumpMan.takeDamage(10);
    ImageGUI.getInstance().setStatusMessage("Atingido por uma armadilha escondida! Vida atual: " + jumpMan.getHealth() + " Vidas restantes: " + jumpMan.getLives());
    // Como ja foi descoberta, torna-se numa Trap normal
    Trap trap = new Trap(getPosition());
    ImageGUI.getInstance().addImage(trap);
  }

}
