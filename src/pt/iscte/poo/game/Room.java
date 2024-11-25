package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import objects.DoorClosed;
import objects.Floor;
import objects.Stairs;
import objects.Trap;
import objects.Wall;
import pt.iscte.poo.Characters.DonkeyKong;
import pt.iscte.poo.Characters.MapHandler;
import pt.iscte.poo.Characters.Princess;
import pt.iscte.poo.Consumables.GoodMeat;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room implements MapHandler{

    private GameEngine engine;
    private List<DonkeyKong> donkeyKongs = new ArrayList<>();

    private Map<Point2D, String> boardMap = new HashMap<>();

    public Room(GameEngine engine) {
        this.engine = engine;
        File roomFile = new File("room2.txt");
        background();
        fileReader(roomFile);
    }

    public List<DonkeyKong> getDonkeyKong() {
        return donkeyKongs;
    }

    public void moveJumpMan(Direction direction) {
        engine.getJumpMan().move(direction);
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
                            Wall wall = new Wall(point);
                            ImageGUI.getInstance().addImage(wall);
                            boardMap.put(point, "Wall");
                            break;

                        case 'S': // Escada
                            Stairs stairs = new Stairs(point);
                            ImageGUI.getInstance().addImage(stairs);
                            boardMap.put(point, "Stairs");
                            break;

                        case 'H': // JumpMan
                            engine.getJumpMan().setPosition(point); // Atualiza a posição inicial
                            ImageGUI.getInstance().addImage(engine.getJumpMan());
                            boardMap.put(point, "JumpMan");
                            break;
                        

                        case '0': //porta
                            DoorClosed door = new DoorClosed(point);
                            ImageGUI.getInstance().addImage(door);
                            boardMap.put(point, "DoorClosed");
                            break;

                        case 't': //Trap
                            Trap trap = new Trap(point);
                            ImageGUI.getInstance().addImage(trap);
                            boardMap.put(point, "Trap");
                            break;

                        case 'G': // donkeyKong
                            DonkeyKong donkeyKong = new DonkeyKong(point, 100,this); 
                            ImageGUI.getInstance().addImage(donkeyKong); 
                            donkeyKongs.add(donkeyKong); // Adiciona um donkeyKong novo à lista
                            boardMap.put(point, "DonkeyKong");
                            break;

                        case 'm': // goodMeat
                            GoodMeat meat = new GoodMeat(point);
                            ImageGUI.getInstance().addImage(meat);
                            boardMap.put(point, "GoodMeat");
                            break;

                        case 'P': // princesa
                            Princess princess = new Princess(point);
                            ImageGUI.getInstance().addImage(princess);
                            boardMap.put(point, "Princess");
                            break;

                        default:
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

    public Map<Point2D, String> getBoardMap() {
        return boardMap;
    }

    @Override
    public boolean isMoveValid(Point2D position) {
        if (!ImageGUI.getInstance().isWithinBounds(position)) {
            return false; // Fora dos limites
        }

        String target = boardMap.get(position);
        return target == null || !target.equals("Wall");
    }

    @Override
    public void updatePosition(Point2D oldPosition, Point2D newPosition, String elementName) {
        boardMap.remove(oldPosition);
        boardMap.put(newPosition, elementName);
    }
    
}
