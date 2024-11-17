package pt.iscte.poo.Characters;

import pt.iscte.poo.gui.ImageGUI;
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
    
    @Override
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

    @Override
    public void move(Direction direction) {
        if (direction != null) {
            // Calcula a nova posição com base na direção
            Point2D newPosition = position.plus(direction.asVector());
            
            // Verifica se a nova posição está dentro dos limites do tabuleiro
            if (ImageGUI.getInstance().isWithinBounds(newPosition)) {
                position = newPosition; // Atualiza a posição se estiver dentro dos limites
            } else {
                System.out.println("Movimento inválido: fora dos limites do tabuleiro.");
            }
        } else {
            throw new IllegalArgumentException("Direção inválida: null");
        }
    }
    


    // Método adicional para manipular saúde (se necessário)
    @Override
    public int getHealth() {
        return health;
    }

    @Override
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
