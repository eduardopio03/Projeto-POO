package pt.iscte.poo.game;

import java.io.File;
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
    private int roomNumber;
    
    public GameEngine() {
        ImageGUI.getInstance().update();
        this.jumpMan = new JumpMan(new Point2D(0,0), 100, currentRoom); // Jumpman é o mesmo para todos os rooms
        this.currentRoom = new Room(this, new File("room" + roomNumber +".txt")); //Cria room com a GameEngine como argumento
        jumpMan.setRoom(currentRoom); //Atualiza o mapHnadler já que na criação era null
    }

    public JumpMan getJumpMan() {
        return jumpMan;
    }

    public void setRoom(Room room) {
        this.currentRoom = room;
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
        if (jumpMan.isDead()) {
            ImageGUI.getInstance().showMessage("Game Over", "JumpMan died");
            ImageGUI.getInstance().dispose();
        }
    }

    private void processTick() {
        System.out.println("Tic Tac : " + lastTickProcessed);
    
        if (getJumpMan().reachedDoor()) {
            roomNumber++;
            getJumpMan().getRoom().removeElements(); //Remove todos os elementos das listas
            ImageGUI.getInstance().clearImages(); //Faz clear das imagens no board
            setRoom(new Room(this, new File("room" + roomNumber + ".txt"))); //Cria um novo room
            getJumpMan().setRoom(currentRoom);
            ImageGUI.getInstance().update();
        }
    
        else if(getJumpMan().reachedPrincess()) {
            ImageGUI.getInstance().showMessage("You Won!", "You saved the Princess");
            getJumpMan().getRoom().removeElements();
            ImageGUI.getInstance().dispose();
            System.exit(0); //Termina a Main
        }
        
        jumpMan.fall();
        
        if (lastTickProcessed % 2 == 0) {
            // Mover DonkeyKongs
            currentRoom.moveDonkeyKongs();
        }
    
        // Atualizar a movimentação das bananas
        currentRoom.updateBananas();
    
        // Mover morcegos
        currentRoom.moveBats();
    
        lastTickProcessed++;
    }
}