package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {
	
	private Room currentRoom = new Room();
	private int lastTickProcessed = 0;
	
	public GameEngine() {
		ImageGUI.getInstance().update();
	}

	@Override
public void update(Observed source) {
    if (ImageGUI.getInstance().wasKeyPressed()) {
        int keyCode = ImageGUI.getInstance().keyPressed();
        System.out.println("Key pressed: " + keyCode);

        try {
            // Usa o método directionFor para obter a direção
            Direction direction = Direction.directionFor(keyCode);
            System.out.println("Moving Manel in direction: " + direction);
            currentRoom.moveManel(direction);
        } catch (IllegalArgumentException e) {
            // Tecla pressionada não corresponde a uma direção
            System.out.println("Invalid key pressed: " + keyCode);
        }
    }

    int t = ImageGUI.getInstance().getTicks();
    while (lastTickProcessed < t) {
        processTick();
    }
    ImageGUI.getInstance().update();
}


	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
	}



}
