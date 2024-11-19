package pt.iscte.poo.Characters;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class JumpMan extends Character {

   
    public JumpMan(Point2D initialPosition, int initialHealth) {
        super(initialPosition, initialHealth);
    }
    
    @Override
    public String getName() {
        return "JumpMan"; 
    }

    @Override
    public int getLayer() {
        return 1; 
    }

    @Override
    public void move(Direction direction) {
        if (direction != null) {
            // Cálculo da nova posição
            Point2D newPosition = super.getPosition().plus(direction.asVector());
            // Verificação dos limites do tabuleiro
            if (ImageGUI.getInstance().isWithinBounds(newPosition)) {
                super.setPosition(newPosition);
            } else {
                System.out.println("Movimento inválido: fora dos limites do tabuleiro.");
            }
        } else {
            throw new IllegalArgumentException("Direção inválida: null");
        }
    }
}
