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
            // Obter a direção correspondente à tecla 
            Direction direction = Direction.directionFor(keyCode);
            System.out.println("Moving JumpMan in direction: " + direction);
            currentRoom.moveJumpMan(direction);
        } catch (IllegalArgumentException e) {
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
		
        if (lastTickProcessed % 3 == 0) {
            currentRoom.getDonkeyKong().moveRandomly();
        }

        lastTickProcessed++;
	}



}
