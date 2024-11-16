package pt.iscte.poo.game;

import objects.Manel;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private Point2D heroStartingPosition = new Point2D(1, 1);
    private Manel manel;

    public Room() {
        manel = new Manel(heroStartingPosition);
        ImageGUI.getInstance().addImage(manel);
        //ImageGUI.getInstance().addImage(new Wall());  TODO: Implementar a leitura do ficheiro
    }

    public void moveManel(Direction direction) {
        manel.move(direction);
        ImageGUI.getInstance().update(); // Atualiza a GUI com a nova posição
    }
}
