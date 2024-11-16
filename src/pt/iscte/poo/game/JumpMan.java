package pt.iscte.poo.game;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

    private Point2D position;
    private int health; // Atributo de saúde incorporado, se necessário

    public JumpMan(Point2D initialPosition, int initialHealth) {
        super(initialPosition, initialHealth);
        this.position = initialPosition;
        this.health = initialHealth; // Inicializa a saúde
    }

    
    public String getName() {
        return "JumpMan"; // Nome da imagem ou objeto representado
    }

    @Override
    public Point2D getPosition() {
        return position; // Retorna a posição atual
    }

    @Override
    public int getLayer() {
        return 1; // Define a camada de exibição
    }

    public void move(Direction direction) {
        if (direction != null) {
            position = position.plus(direction.asVector()); // Atualiza a posição conforme a direção
        } else {
            throw new IllegalArgumentException("Direção inválida: null");
        }
    }

    // Método adicional para manipular saúde (se necessário)
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0; // Garante que a saúde não seja negativa
            System.out.println("Manel foi derrotado!");
        }
    }
}
