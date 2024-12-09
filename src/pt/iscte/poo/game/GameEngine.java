package pt.iscte.poo.game;

import java.io.File;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import java.awt.event.KeyEvent;

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

    public Room getRoom() {
        return currentRoom;
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
                getRoom().moveJumpMan(direction);
                
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid key pressed: " + keyCode);
            }
            if(keyCode == KeyEvent.VK_B) {
                //Verifica se o jumpman tem a bomba
                if(!getJumpMan().hasBomb()) {
                    System.out.println("Jumpman doesn't carry a bomb");
                }
                else {
                    getJumpMan().dropBomb();
                }
            }
        }

        int t = ImageGUI.getInstance().getTicks();
        while (lastTickProcessed < t) {
            processTick();
        }

        ImageGUI.getInstance().update();
        if (getJumpMan().isDead() && getJumpMan().getLives() <= 0) {
            ImageGUI.getInstance().showMessage("Game Over", "JumpMan died");
            getJumpMan().getRoom().removeElements();
            ImageGUI.getInstance().dispose();
            System.exit(0);
        }
    }

    private void processTick() {
        System.out.println("Tic Tac : " + lastTickProcessed);
    
        if (getJumpMan().reachedDoor()) {
            roomNumber++;
            getJumpMan().getRoom().removeElements(); //Remove todos os elementos das listas
            ImageGUI.getInstance().clearImages(); //Faz clear das imagens no board
            setRoom(new Room(this, new File("room" + roomNumber + ".txt"))); //Cria um novo room
            getJumpMan().setRoom(getRoom());
            ImageGUI.getInstance().update();
        }
    
        else if(getJumpMan().reachedPrincess()) {
            ImageGUI.getInstance().showMessage("You Won!", "You saved the Princess");
            getJumpMan().getRoom().removeElements();
            ImageGUI.getInstance().dispose();
            System.exit(0); //Termina a Main
        }
        
        getJumpMan().fall();
        
        if (lastTickProcessed % 2 == 0) {
            // Mover DonkeyKongs
            getRoom().moveDonkeyKongs();
        }
    
        // Atualizar a movimentação das bananas
        getRoom().updateBananas();
    
        // Mover morcegos
        getRoom().moveBats();

        //O jumpMan apanha a bomba
        getJumpMan().pickUpBomb();
    
        lastTickProcessed++;
    }
}