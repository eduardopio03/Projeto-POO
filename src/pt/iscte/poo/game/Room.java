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
import pt.iscte.poo.Consumables.Banana;
import pt.iscte.poo.Consumables.GoodMeat;
import pt.iscte.poo.Consumables.Sword;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room implements MapHandler{

    private GameEngine engine;
    private List<DonkeyKong> donkeyKongs = new ArrayList<>();
    private List<Banana> bananas = new ArrayList<>();

    private Map<Point2D, List<GameElement>> boardMap = new HashMap<>();

    public Room(GameEngine engine) {
        this.engine = engine;
        File roomFile = new File("room1.txt");
        background();
        fileReader(roomFile);
    }

    public List<DonkeyKong> getDonkeyKong() {
        return donkeyKongs;
    }

    public List<Banana> getBananas() {
        return bananas;
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
                            addObject(point, wall);
                            break;

                        case 'S': // Escada
                            Stairs stairs = new Stairs(point);
                            ImageGUI.getInstance().addImage(stairs);
                            addObject(point, stairs);
                            break;

                        case 'H': // JumpMan
                            engine.getJumpMan().setPosition(point); // Atualiza a posição inicial
                            ImageGUI.getInstance().addImage(engine.getJumpMan());
                            addObject(point, engine.getJumpMan());
                            break;
                        

                        case '0': //porta
                            DoorClosed door = new DoorClosed(point);
                            ImageGUI.getInstance().addImage(door);
                            addObject(point, door);
                            break;

                        case 't': //Trap
                            Trap trap = new Trap(point);
                            ImageGUI.getInstance().addImage(trap);
                            addObject(point, trap);
                            break;

                        case 'G': // donkeyKong
                            DonkeyKong donkeyKong = new DonkeyKong(point, 100, 10, this); 
                            ImageGUI.getInstance().addImage(donkeyKong); 
                            donkeyKongs.add(donkeyKong); // Adiciona um donkeyKong novo à lista
                            addObject(point, donkeyKong);
                            break;

                        case 'm': // goodMeat
                            GoodMeat meat = new GoodMeat(point);
                            ImageGUI.getInstance().addImage(meat);
                            addObject(point, meat);
                            break;

                        case 'P': // princesa
                            Princess princess = new Princess(point);
                            ImageGUI.getInstance().addImage(princess);
                            addObject(point, princess);
                            break;

                        case 's': // espada
                            Sword sword = new Sword(point);
                            ImageGUI.getInstance().addImage(sword);
                            addObject(point, sword);
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

    public Map<Point2D, List<GameElement>> getBoardMap() {
        return boardMap;
    }

    @Override
    public boolean isMoveValid(Point2D position) {
        if (!ImageGUI.getInstance().isWithinBounds(position)) {
            return false; // Fora dos limites
        }

        List<GameElement> target = boardMap.get(position);

        if (target == null) {
            return true;
        }

        for (GameElement element : target) {
            if (!element.isTransposable()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updatePosition(Point2D oldPosition, Point2D newPosition, GameElement elementName) {
        // Remove o elemento da posição antiga
        List<GameElement> oldList = boardMap.get(oldPosition);
        if (oldList != null) {
            oldList.remove(elementName);
        }

        // Adiciona o elemento à nova posição
        boardMap.computeIfAbsent(newPosition, k -> new ArrayList<>()).add(elementName);
    }

    
    public void addObject(Point2D position, GameElement image) {
        // Inicializar a lista, se necessário
        if (!boardMap.containsKey(position)) {
            boardMap.put(position, new ArrayList<>());
        }
    
        // Adicionar somente se o elemento ainda não estiver presente
        List<GameElement> elements = boardMap.get(position);
        boolean exists = false;
        for (GameElement element : elements) {
            if (element.equals(image)) {
                exists = true;
                break;
            }
        }
    
        if (!exists) {
            elements.add(image);
        }
    }
    
}

