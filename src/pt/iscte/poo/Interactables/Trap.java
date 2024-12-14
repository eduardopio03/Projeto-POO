package pt.iscte.poo.Interactables;

import objects.StaticElement;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Trap extends StaticElement implements Interactable {


  public Trap(Point2D point) {
    super(point,false);
  }

  @Override
  public String getName() {
    return "Trap";
  }

  @Override
  public Point2D getPosition() {
    return super.getPosition();
  }

  @Override
  public int getLayer() {
    return 1;
  }

  @Override
  public void interact(JumpMan jumpMan) {
    jumpMan.takeDamage(10);
    ImageGUI.getInstance().setStatusMessage("Atingido por uma armadilha! Vida atual: " + jumpMan.getHealth() + " Vidas restantes: " + jumpMan.getLives());
  }

}
