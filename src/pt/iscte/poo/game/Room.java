package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import objects.Wall;
import objects.Stair;
import objects.Floor;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private Point2D heroStartingPosition = new Point2D(9, 8);
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

    private void fileReader(File file) { //Não terminado
        int x = 0;
        int y = 0;
        ImageTile wall;
        ImageTile stair;
        try {
            Scanner scan = new Scanner(file);
            scan.nextLine();
            while (scan.hasNextLine()) {
                Point2D point = new Point2D(x, y);
                switch(scan.next()) { //Não terminado
                    case " ":
                        continue;
                    case "W":
                        wall = new Wall(point);
                    case "S":
                        stair = new Stair(point);
                    default:
                        throw new IllegalArgumentException();
                }
                ImageGUI.getInstance().addImage(wall); // Não funciona
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

}
