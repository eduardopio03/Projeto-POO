package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;

public abstract class Character extends MovableElement {
    
    private int health; // Vida do personagem

    public Character(Point2D startPosition, int initialHealth) {
        super(startPosition);
        this.health = initialHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void decreaseHealth(int damage) {
        health -= damage;
    }

    public void increaseHealth(int heal) {
        health += heal;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void die() {
        health = 0;
    }
}
