package pt.iscte.poo.game;

import pt.iscte.poo.Characters.DonkeyKong;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {
	
    private JumpMan jumpMan;
	private Room currentRoom;
	private int lastTickProcessed = 0;
	
	public GameEngine() {
		ImageGUI.getInstance().update();
        this.jumpMan = new JumpMan(new Point2D(0,0), 10, currentRoom); // Jumpman é o mesmo para todos os rooms
        this.currentRoom = new Room(this); //Cria room com a GameEngine como argumento
        jumpMan.setMapHandler(currentRoom); //Atualiza o mapHnadler já que na criação era null
	}

    public JumpMan getJumpMan() {
        return jumpMan;
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
		
        if (lastTickProcessed % 1 == 0) {
            for(DonkeyKong d: currentRoom.getDonkeyKong()) { //Faz todos os donkeyKongs da lista moverem 
                d.moveRandomly();
            }
        }

        lastTickProcessed++;
	}



}
