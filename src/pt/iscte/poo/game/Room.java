package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import objects.Stairs;
import objects.Wall;
import objects.DoorClosed;
import objects.Floor;
import objects.Trap;
import pt.iscte.poo.Characters.DonkeyKong;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.Characters.Princess;
import pt.iscte.poo.Consumables.GoodMeat;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private JumpMan jumpMan;

    public Room() {
        File roomFile = new File("room0.txt");
        background();
        fileReader(roomFile);
    }

    public void moveJumpMan(Direction direction) {
        jumpMan.move(direction);
        ImageGUI.getInstance().update(); 
    }

    private void fileReader(File file) {
        try (Scanner scan = new Scanner(file)) {
            int y = 0; // Linha no arquivo
            scan.nextLine(); //Saltar a primeira linha do arquivo
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

                        case '0': //porta
                            ImageTile door = new DoorClosed(point);
                            ImageGUI.getInstance().addImage(door);
                            break;

                        case 't': //Trap
                            ImageTile trap = new Trap(point);
                            ImageGUI.getInstance().addImage(trap);
                            break;

                        case 'G': // donkeyKong
                            ImageTile donkeyKong = new DonkeyKong(point, 10);
                            ImageGUI.getInstance().addImage(donkeyKong);
                            break;

                        case 'm': // goodMeat
                            ImageTile meat = new GoodMeat(point);
                            ImageGUI.getInstance().addImage(meat);
                            break;

                        case 'P': // princesa
                            ImageTile princess = new Princess(point);
                            ImageGUI.getInstance().addImage(princess);
                            break;

                        default:
                           // Espaço vazio ignorado
                           // Ver no vídeo qual a imagem que se usa no fundo
                            break;
                    }
                }
                y++; //Próxima linha
            }
        } 
        catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + file.getName());
        }   
    }

    private void background() {
        for (int x = 0; x < 10; x++) { //Coloco Floor em todas as posições
            for (int y = 0; y < 10; y++) {
                Point2D point = new Point2D(x, y);
                ImageTile floor = new Floor(point);
                ImageGUI.getInstance().addImage(floor);
            }
        }
    }
}
