package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Wall implements ImageTile {

	private final Point2D point;

	public Wall(Point2D point) {
		this.point = point;
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
	public Point2D getPosition() {
		return point;
	}

	
}
