package pt.iscte.poo.Characters;

import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.utils.Point2D;

public abstract class Enemy extends GameElement implements Movable {

  private int health;
  private int attack;

  public Enemy(Point2D position, int health, int attack) {
      super(position,true);
      this.health = health;
      this.attack = attack;
  }

  public int getHealth() {
      return health;
  }

  public int getAttack() {
      return attack;
  }

  public void takeDamage(int value) {
      health -= value;
  }

  public void increaseHealth(int value) {
      health += value;
  }

  public void decreaseAttack(int value) {
      attack -= value;
  }
    
  public void increaseAttack(int value) {
      attack += value;
  }

   public boolean isDead() {
      return getHealth() <= 0;
  }
}
