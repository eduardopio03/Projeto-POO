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
import objects.Wall;
import pt.iscte.poo.Characters.Bat;
import pt.iscte.poo.Characters.DonkeyKong;
import pt.iscte.poo.Characters.MapHandler;
import pt.iscte.poo.Characters.Princess;
import pt.iscte.poo.Consumables.BadMeat;
import pt.iscte.poo.Consumables.Banana;
import pt.iscte.poo.Consumables.GoodMeat;
import pt.iscte.poo.Consumables.Sword;
import pt.iscte.poo.Interactables.Bomb;
import pt.iscte.poo.Interactables.HiddenTrap;
import pt.iscte.poo.Interactables.Trap;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room implements MapHandler{

    private GameEngine engine;
    private List<DonkeyKong> donkeyKongs = new ArrayList<>();
    private List<Banana> bananas = new ArrayList<>();
    private List<Bat> bats = new ArrayList<>();

    private Map<Point2D, List<GameElement>> boardMap = new HashMap<>();

    public Room(GameEngine engine, File file) {
        this.engine = engine;
        background();
        fileReader(file);
    }

    public List<DonkeyKong> getDonkeyKong() {
        return donkeyKongs;
    }

    public List<Banana> getBananas() {
        return bananas;
    }

    public List<Bat> getBats() {
        return bats;
    }

    public void moveDonkeyKongs() {
        for (DonkeyKong donkeyKong : getDonkeyKong()) {
            donkeyKong.moveRandomly();
            donkeyKong.launchBanana();
        }
    }

    public void moveBats() {
        for (Bat bat : getBats()) {
            bat.moveRandomly();
        }
    }

    public GameEngine getEngine() {
        return engine;
    }

    public void moveJumpMan(Direction direction) {
        getEngine().getJumpMan().move(direction);
        ImageGUI.getInstance().update(); 
    }

    private void fileReader(File file) {
        Point2D jumpManPosition = null;
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
                            jumpManPosition = point;
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
                        
                        case 'B': // bomba
                            Bomb bomb = new Bomb(point, true);
                            ImageGUI.getInstance().addImage(bomb);
                            addObject(point, bomb);
                            break;

                        case 'h': // hidden trap
                            HiddenTrap hTrap = new HiddenTrap(point);
                            ImageGUI.getInstance().addImage(hTrap);
                            addObject(point, hTrap);
                            break;

                        case 'b': // morcego
                            Bat bat = new Bat(point, 100, 10, this);
                            ImageGUI.getInstance().addImage(bat);
                            addObject(point, bat);
                            bats.add(bat);
                            break;

                        case 'M': // morcego
                            BadMeat badMeat = new BadMeat(point);
                            ImageGUI.getInstance().addImage(badMeat);
                            addObject(point, badMeat);
                            break;
                        
                        case ' ': // Espaço vazio
                            Floor floor = new Floor(point);
                            ImageGUI.getInstance().addImage(floor);
                            addObject(point, floor);
                            break;

                        default:
                            System.err.println("Caractere desconhecido '" + symbol + "' na linha " + y + ", coluna " + x + ".");
                            Floor defaultFloor = new Floor(point);
                            ImageGUI.getInstance().addImage(defaultFloor);
                            addObject(point, defaultFloor);
                            break;
                    }
                }
                y++; //Próxima linha
            }
            if (jumpManPosition != null) {
                getEngine().getJumpMan().setPosition(jumpManPosition);
                ImageGUI.getInstance().addImage(getEngine().getJumpMan());
                addObject(jumpManPosition, getEngine().getJumpMan());
            }
        } 
        catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + file.getName());
            Scanner input = new Scanner(System.in);
            System.out.println("Indique o nome do ficheiro a ler para o próximo nível:");
            String fileName = input.nextLine();
            fileReader(new File(fileName));
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

    public void updateBananas() {
        List<Banana> bananasToRemove = new ArrayList<>();
        for (Banana banana : getBananas()) {
            if (banana.isOutOfBounds()) {
                bananasToRemove.add(banana);
            } else {
                banana.moveDown();
            }
        }

        for (Banana banana : bananasToRemove) {
            getBananas().remove(banana);
            ImageGUI.getInstance().removeImage(banana);
        }
    }

    public void removeElements() {
        getBoardMap().clear();
        getBananas().clear();
        getDonkeyKong().clear();
    }

    public void removeElementsOnExplosiobn(Point2D bombPosition) {
        List<Point2D> neighbourhoodPoints = bombPosition.getNeighbourhoodPoints();
        for(Point2D p: neighbourhoodPoints) {
            getBoardMap().remove(p);
        }
    }
    
}

