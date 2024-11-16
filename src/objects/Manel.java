package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Manel implements ImageTile {

	private Point2D position;
	
	public Manel(Point2D initialPosition){
		position = initialPosition;
	}
	
	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void move(Direction direction) {
        // Base de movimento com ajuste conforme a direção
        switch (direction) {
            case UP:
                position = position.plus(new Vector2D(0, -1)); // Move para cima
                break;
            case DOWN:
                position = position.plus(new Vector2D(0, 1)); // Move para baixo
                break;
            case LEFT:
                position = position.plus(new Vector2D(-1, 0)); // Move para a esquerda
                break;
            case RIGHT:
                position = position.plus(new Vector2D(1, 0)); // Move para a direita
                break;
            default:
                throw new IllegalArgumentException("Direção desconhecida: " + direction);
        }
    }
	
}
