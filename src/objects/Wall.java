package objects;

import pt.iscte.poo.utils.Point2D;

public class Wall extends StaticElement {

	public Wall(Point2D point) {
		super(point);
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
		return super.getPosition();
	}

	
}
