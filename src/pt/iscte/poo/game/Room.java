package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import objects.Stairs;
import objects.Wall;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private JumpMan jumpMan;

    public Room() {
        File roomFile = new File("room0.txt");
        fileReader(roomFile);
    }

    public void moveJumpMan(Direction direction) {
        jumpMan.move(direction);
        ImageGUI.getInstance().update(); 
    }

    private void fileReader(File file) {
    try (Scanner scan = new Scanner(file)) {
        int y = 0; // Linha no arquivo

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char symbol = line.charAt(x);
                Point2D point = new Point2D(x, y);

                switch (symbol) {
                    case 'W': // Parede
                        ImageTile wall = new Wall(point);
                        ImageGUI.getInstance().addImage(wall);
                        break;

                    case 'S': // Escada
                        ImageTile stair = new Stairs(point);
                        ImageGUI.getInstance().addImage(stair);
                        break;

                    case 'H': // JumpMan
                        jumpMan = new JumpMan(point,10);
                        ImageGUI.getInstance().addImage(jumpMan);
                        break;

                    default:
                       // Espaço vazio ignorado
                       // Ver no vídeo qual a imagem que se usa no fundo
                        break;
                }
            }
            y++; //Próxima linha
        }
    } catch (FileNotFoundException e) {
        System.err.println("Arquivo não encontrado: " + file.getName());
    }
}

    

}
