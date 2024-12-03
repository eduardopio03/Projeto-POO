package objects;

import pt.iscte.poo.utils.Point2D;

public class Floor extends StaticElement {

	public Floor(Point2D point) {
		super(point,false);
	}

	@Override
	public String getName() {
		return "Floor";
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}


}
