package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private Point2D heroStartingPosition = new Point2D(1, 1);
    private JumpMan jumpMan;

    public Room() {
        jumpMan = new JumpMan(heroStartingPosition, 100); // Passando a posição inicial e a saúde inicial
        ImageGUI.getInstance().addImage(jumpMan); // Adicionando o personagem à GUI
        // Implementar a leitura do ficheiro de configuração para adicionar mais elementos à sala
    }

    public void moveJumpMan(Direction direction) {
        jumpMan.move(direction);
        ImageGUI.getInstance().update(); // Atualiza a GUI com a nova posição
    }
}
