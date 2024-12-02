package pt.iscte.poo.game;

import java.util.ArrayList;
import java.util.List;
import pt.iscte.poo.Characters.DonkeyKong;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.Consumables.Banana;
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
        this.jumpMan = new JumpMan(new Point2D(0,0), 100, 10, currentRoom); // Jumpman é o mesmo para todos os rooms
        this.currentRoom = new Room(this); //Cria room com a GameEngine como argumento
        jumpMan.setRoom(currentRoom); //Atualiza o mapHnadler já que na criação era null
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
		
        jumpMan.fall();
        
        if (lastTickProcessed % 2 == 0) {
            for(DonkeyKong d: currentRoom.getDonkeyKong()) { //Faz todos os donkeyKongs da lista moverem 
                d.moveRandomly();
                //Criar uma nova banana na posição atual do donkeyKong
                Banana banana = new Banana(d.getPosition(),currentRoom);
                currentRoom.getBananas().add(banana);
                ImageGUI.getInstance().addImage(banana);
            }
        }

        //Movimentação das bananas
        List<Banana> bananasToRemove = new ArrayList<>();
        for (Banana banana : currentRoom.getBananas()) {
            Point2D nextPosition = banana.getPosition().plus(Direction.DOWN.asVector());

            //Verifica se a proxima posiçãpo esta fora dos limites do tabuleiro
            if (!ImageGUI.getInstance().isWithinBounds(nextPosition)) {
                bananasToRemove.add(banana);
            } else {
                //Atualiza a posicao da banana
                banana.moveDown();
                currentRoom.updatePosition(banana.getPosition(), nextPosition, banana);
            }
        }

        //Remove as bananas que sairam do tabuleiro
        for (Banana banana : bananasToRemove) {
            currentRoom.getBananas().remove(banana);
            ImageGUI.getInstance().removeImage(banana);
        }

        lastTickProcessed++;
	}



}
