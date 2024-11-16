package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;

public abstract class StaticElement extends GameElement{

  public StaticElement(String name, Point2D position, int layer) {
    super(name, position, layer);
  }
}
