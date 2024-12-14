package pt.iscte.poo.game;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.Consumables.Consumable;
import pt.iscte.poo.Consumables.GoodMeat;
import pt.iscte.poo.Interactables.Bomb;
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
    private boolean levelReset = false; // Variável de controle para verificar se o nível foi reiniciado
    
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

            if (Direction.isDirection(keyCode)) {
                // Obter a direção correspondente à tecla 
                Direction direction = Direction.directionFor(keyCode);
                System.out.println("Moving JumpMan in direction: " + direction);
                getRoom().moveJumpMan(direction);
            }
                
            if(keyCode == KeyEvent.VK_B) {
                //Verifica se o jumpman tem a bomba
                if(getJumpMan().hasBomb() != 1) {
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
        if (getJumpMan().isDead()) {
            if (!levelReset) { // Verifica se o nível já foi resetado
                getJumpMan().decreaseLives();
                levelReset = true; // Define como true após resetar o nível
            }
        } else {
            levelReset = false; // Reseta a variável se o JumpMan não estiver morto
        }
    }

    private void processTick() {
        System.out.println("Tic Tac : " + lastTickProcessed);
    
        if (getJumpMan().reachedDoor()) {
            roomNumber++;
            getJumpMan().getRoom().removeElements(); // Remove todos os elementos das listas
            ImageGUI.getInstance().clearImages(); // Faz clear das imagens no board
            setRoom(new Room(this, new File("room" + roomNumber + ".txt"))); // Cria um novo room
            getJumpMan().setRoom(getRoom());
            ImageGUI.getInstance().update();
            lastTickProcessed++; // Atualiza o lastTickProcessed após resetar o nível
            return; // Sai do método para evitar processamento adicional
        } else if (getJumpMan().reachedPrincess()) {
            ImageGUI.getInstance().showMessage("You Won!", "You saved the Princess");
            getJumpMan().getRoom().removeElements();
            ImageGUI.getInstance().dispose();
            System.exit(0); // Termina a Main
        }
    
        getJumpMan().fall(); // Certifique-se de que o método fall() está sendo chamado
    
        if (lastTickProcessed % 2 == 0) {
            // Mover DonkeyKongs
            getRoom().moveDonkeyKongs();
        }
    
        // Atualizar a movimentação das bananas
        getRoom().updateBananas();
    
        // Mover morcegos
        getRoom().moveBats();
    
        // Atualizar bombas só depois do jumpMan largar
        if(getJumpMan().hasBomb() == 2) {
            updateBombs();
        }
        
        // Atualizar carnes
        updateMeats();
    
        // Remover DonkeyKongs após a iteração
        getRoom().removeDonkeyKongs();
    
        lastTickProcessed++;
    }

private void updateBombs() {
    List<Bomb> bombs = getRoom().getBombs();
    for (Bomb bomb : bombs) {
        bomb.incrementTicks();
        if (bomb.getTicks() >= 5) {
            bomb.explode();
        }
    }
}

private void updateMeats() {
    List<Consumable> goodMeats = getRoom().getMeats();
    for (Consumable goodMeat : goodMeats) {
        if (goodMeat instanceof GoodMeat) {
            ((GoodMeat) goodMeat).decreaseTicksToApodrecer();
        }
    }
}

public void resetLevel() {
    // Reposiciona o JumpMan na posição inicial
    getJumpMan().setPosition(currentRoom.getInitialJumpManPosition());
    getJumpMan().increaseHealth(100); // Restaura a vida para 100
    getJumpMan().setHasBomb(0); // Reseta a quantidade de bombas
    
    // Remove as bananas
    getRoom().removeImagesBananas(getRoom().getBananas());
    getRoom().getBananas().clear();
    
    // Recarrega as bananas
    getRoom().updateBananas();
    
    // Atualiza a interface gráfica
    ImageGUI.getInstance().setStatusMessage("Vida atual: " + jumpMan.getHealth());
    ImageGUI.getInstance().update(); 
}

    
    public void resetGame() {
        currentRoom.removeElements(); // Remove todos os elementos do nível atual
        ImageGUI.getInstance().clearImages(); // Limpa as imagens do tabuleiro
        roomNumber = 0; // Reseta o número do nível
        currentRoom = new Room(this, new File("room0.txt")); // Recarrega o primeiro nível
        this.jumpMan = new JumpMan(new Point2D(0, 0), 100, currentRoom);
        setRoom(new Room(this, new File("room" + roomNumber + ".txt")));
        jumpMan.setRoom(currentRoom); // Atualiza o Room do JumpMan
        jumpMan.setPosition(getRoom().getInitialJumpManPosition()); // Reseta a posição do JumpMan
        jumpMan.increaseHealth(100); // Reseta a vida do JumpMan para 100
        jumpMan.resetLives(); // Reseta as vidas do JumpMan
        jumpMan.setHasBomb(0); // Reseta a bomba do JumpMan
        lastTickProcessed = ImageGUI.getInstance().getTicks(); // Atualiza o lastTickProcessed
        ImageGUI.getInstance().update(); // Atualiza a GUI
    }
}