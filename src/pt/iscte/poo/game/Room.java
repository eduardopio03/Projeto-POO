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
import objects.StaticElement;
import objects.Wall;
import pt.iscte.poo.Characters.Bat;
import pt.iscte.poo.Characters.DonkeyKong;
import pt.iscte.poo.Characters.Enemy;
import pt.iscte.poo.Characters.JumpMan;
import pt.iscte.poo.Characters.MapHandler;
import pt.iscte.poo.Characters.Princess;
import pt.iscte.poo.Consumables.BadMeat;
import pt.iscte.poo.Consumables.Banana;
import pt.iscte.poo.Consumables.Consumable;
import pt.iscte.poo.Consumables.GoodMeat;
import pt.iscte.poo.Consumables.Sword;
import pt.iscte.poo.Interactables.Bomb;
import pt.iscte.poo.Interactables.HiddenTrap;
import pt.iscte.poo.Interactables.Trap;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room implements MapHandler{

    private GameEngine engine;
    private List<DonkeyKong> donkeyKongs = new ArrayList<>();
    private List<Banana> bananas = new ArrayList<>();
    private List<Bat> bats = new ArrayList<>();
    private Point2D initialJumpManPosition;
    private List<DonkeyKong> donkeyKongsToRemove = new ArrayList<>();

    private Map<Point2D, List<GameElement>> boardMap = new HashMap<>();

    public Room(GameEngine engine, File file) {
        this.engine = engine;
        background();
        fileReader(file);
    }

    public void removeDonkeyKongsAtPosition(Point2D position) {
        List<GameElement> elements = boardMap.get(position);
        if (elements != null) {
            elements.removeIf(element -> {
                if (element instanceof DonkeyKong) {
                    donkeyKongs.remove(element);
                    return true;
                }
                return false;
            });
        }
    }

    public List<Bomb> getBombs() {
        List<Bomb> bombs = new ArrayList<>();
        for (List<GameElement> elements : boardMap.values()) {
            for (GameElement element : elements) {
                if (element instanceof Bomb) {
                    bombs.add((Bomb) element);
                }
            }
        }
        return bombs;
    }

    public List<Consumable> getMeats() {
        List<Consumable> meats = new ArrayList<>();
        for (List<GameElement> elements : boardMap.values()) {
            for (GameElement element : elements) {
                if (element instanceof GoodMeat) {
                    meats.add((Consumable) element);
                }
            }
        }
        return meats;
    }

    public List<DonkeyKong> getDonkeyKongs() {
        return donkeyKongs;
    }

    public List<Banana> getBananas() {
        return bananas;
    }

    public List<Bat> getBats() {
        return bats;
    }

    public List<DonkeyKong> getDonkeyKongsToRemove() {
        return donkeyKongsToRemove;
    }

    public void moveDonkeyKongs() {
        List<DonkeyKong> donkeyKongsToMove = new ArrayList<>(getDonkeyKongs()); // Cria uma cópia da lista para evitar a modificação durante a iteração
        for (DonkeyKong donkeyKong : donkeyKongsToMove) {
            donkeyKong.moveRandomly();
            donkeyKong.launchBanana();
        }
    }

    public void moveBats() {
        List<Bat> batsToMove = new ArrayList<>(bats); // Cria uma cópia da lista para evitar a modificação durante a iteração
        for (Bat bat : batsToMove) {
            bat.moveRandomly();
        }
    }

    public GameEngine getEngine() {
        return engine;
    }

    public void setJumpManInitialPosition(Point2D p) {
        this.initialJumpManPosition = p;
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
                            setJumpManInitialPosition(point);
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
                            GoodMeat meat = new GoodMeat(point,this);
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
                            Bomb bomb = new Bomb(point, true, this);
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

                        case 'M': // carne estragada
                            BadMeat badMeat = new BadMeat(point,this);
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
            //Se faltar alguma linha no arquivo
            if (y < 14) {
                System.out.println("Falta uma linha no ficheiro");
                ImageGUI.getInstance().dispose();
                removeElements();
                System.exit(0);
            }
            scan.close();
        } 
        catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + file.getName());
            Scanner input = new Scanner(System.in);
            System.out.println("Indique o nome do ficheiro a ler para o próximo nível:");
            String fileName = input.nextLine();
            fileReader(new File(fileName));
        }   
    }

    public Point2D getInitialJumpManPosition() {
        return initialJumpManPosition;
    }

    private void background() {
        for (int x = 0; x < 10; x++) { //Coloco Floor em todas as posições
            for (int y = 0; y < 10; y++) {
                Point2D point = new Point2D(x, y);
                Floor floor = new Floor(point);
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

        List<GameElement> target = getBoardMap().get(position);

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
        getBoardMap().computeIfAbsent(newPosition, k -> new ArrayList<>()).add(elementName);
    }

    
    public void addObject(Point2D position, GameElement image) {
        // Inicializar a lista, se necessário
        if (!getBoardMap().containsKey(position)) {
            getBoardMap().put(position, new ArrayList<>());
        }
    
        // Adicionar somente se o elemento ainda não estiver presente
        List<GameElement> elements = getBoardMap().get(position);
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
        List<Banana> bananasToUpdate = new ArrayList<>(bananas); // Cria uma cópia da lista para evitar a modificação durante a iteração
        for (Banana banana : bananasToUpdate) {
            if (banana.isOutOfBounds()) {
                bananasToRemove.add(banana);
            } else {
                banana.moveDown();
            }
        }
    
        for (Banana banana : bananasToRemove) {
            bananas.remove(banana);
            ImageGUI.getInstance().removeImage(banana);
        }
    }

    public void removeElements() {
        getBoardMap().clear();
        getBananas().clear();
        getDonkeyKongs().clear();
        
    }

    public void removeElementsOnExplosion(Point2D bombPosition) {
        List<Point2D> neighbourhoodPoints = bombPosition.getNeighbourhoodPoints();
        neighbourhoodPoints.add(bombPosition); // Inclui a posição da bomba na explosão
    
        for (Point2D point : neighbourhoodPoints) {
            List<GameElement> elements = boardMap.get(point);
            if (elements != null) {
                // Cria uma cópia para evitar ConcurrentModificationException
                List<GameElement> elementsCopy = new ArrayList<>(elements);
    
                for (GameElement element : elementsCopy) {
                    if (element instanceof Enemy || element instanceof JumpMan) {
                        // Remove da GUI
                        ImageGUI.getInstance().removeImage(element);
                        if (element instanceof JumpMan) {
                            getEngine().getJumpMan().takeDamage(getEngine().getJumpMan().getHealth());
                        }
    
                        // Remove das listas específicas
                        if (element instanceof DonkeyKong) {
                            getDonkeyKongs().remove(element);
                        } else if (element instanceof Bat) {
                            getBats().remove(element);
                        }
    
                        // Remove do boardMap
                        elements.remove(element);
                    }
                    if(!(element instanceof StaticElement)) {
                        ImageGUI.getInstance().removeImage(element);
                    }
                }
            }
        }
    }
    

    public void addDonkeyKongToRemove(DonkeyKong donkeyKong) {
        getDonkeyKongsToRemove().add(donkeyKong);
    }

    public void removeDonkeyKongs() {
        for (DonkeyKong donkeyKong : getDonkeyKongsToRemove()) {
            getDonkeyKongs().remove(donkeyKong);
        }
        getDonkeyKongsToRemove().clear();
    }

    public void removeImagesBananas(List<Banana> imagesToRemove) {
        for(Banana g: imagesToRemove) {
            ImageGUI.getInstance().removeImage(g);
        }
    }
    
}

