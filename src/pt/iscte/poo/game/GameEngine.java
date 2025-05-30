package pt.iscte.poo.game;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.Consumables.Banana;
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
    private boolean gameStarted = false; // Variável de controle para verificar se o jogo foi iniciado

    
    public GameEngine() {
        ImageGUI.getInstance().update();
        this.jumpMan = new JumpMan(new Point2D(0,0), 100, null); // Jumpman é o mesmo para todos os rooms
    }

    public void startGame() {
        this.currentRoom = new Room(this, new File("room" + roomNumber +".txt")); // Cria room com a GameEngine como argumento
        jumpMan.setRoom(currentRoom); // Atualiza o mapHandler já que na criação era null
        jumpMan.setPosition(currentRoom.getInitialJumpManPosition());
        ImageGUI.getInstance().addImage(jumpMan);
        gameStarted = true;
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

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public void update(Observed source) {
        if (!gameStarted) {
            startGame();
        }

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
            setRoomNumber(getRoomNumber()+1);
            getJumpMan().getRoom().removeElements(); // Remove todos os elementos das listas
            ImageGUI.getInstance().clearImages(); // Faz clear das imagens no board
            setRoom(new Room(this, new File("room" + getRoomNumber() + ".txt"))); // Cria um novo room
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
    
        getJumpMan().fall();
    
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
            getJumpMan().setHasBomb(0);
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
    getJumpMan().setPosition(getRoom().getInitialJumpManPosition());
    getJumpMan().increaseHealth(100); // Restaura a vida para 100
    getJumpMan().setHasBomb(0); // Reseta a quantidade de bombas
    
    // Remove as bananas
    List<Banana> bananasToRemove = new ArrayList<>(getRoom().getBananas());
    for (Banana banana : bananasToRemove) {
        Point2D position = banana.getPosition();
        List<GameElement> elements = getRoom().getBoardMap().get(position);
        if (elements != null) {
            elements.remove(banana);
        }
        ImageGUI.getInstance().removeImage(banana);
    }
    getRoom().getBananas().clear();
    getRoom().removeDonkeyKongs();
    
    // Atualiza a interface gráfica
    ImageGUI.getInstance().setStatusMessage("Vida atual: " + jumpMan.getHealth());
    
    // Adiciona o JumpMan de volta à GUI
    ImageGUI.getInstance().addImage(getJumpMan());

    ImageGUI.getInstance().update(); 
}

    
    public void resetGame() {
        getRoom().removeElements(); // Remove todos os elementos do nível atual
        ImageGUI.getInstance().clearImages(); // Limpa as imagens do tabuleiro
        setRoomNumber(0); // Reseta o número do nível
        setRoom(new Room(this, new File("room0.txt"))); // Recarrega o primeiro nível
        getJumpMan().setRoom(currentRoom); // Atualiza o Room do JumpMan
        getJumpMan().setPosition(getRoom().getInitialJumpManPosition()); // Reseta a posição do JumpMan
        getJumpMan().increaseHealth(100); // Reseta a vida do JumpMan para 100
        getJumpMan().resetLives(); // Reseta as vidas do JumpMan
        getJumpMan().resetAttack(); // Reseta o ataque do JumpMan
        getJumpMan().setHasBomb(0); // Reseta a bomba do JumpMan
        ImageGUI.getInstance().setStatusMessage("Vida atual: " + getJumpMan().getHealth() + " Vidas restantes: " + getJumpMan().getLives()); // Atualiza a mensagem de status
        lastTickProcessed = ImageGUI.getInstance().getTicks(); // Atualiza o lastTickProcessed
        ImageGUI.getInstance().update(); // Atualiza a GUI
    }
}