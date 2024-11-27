package pt.iscte.poo.Characters;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public interface MapHandler {
    boolean isMoveValid(Point2D position); // Verifica se o movimento é válido
    void updatePosition(Point2D oldPosition, Point2D newPosition, ImageTile elementName); // Atualiza o tabuleiro
}

