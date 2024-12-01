package pt.iscte.poo.Characters;

import pt.iscte.poo.game.GameElement;
import pt.iscte.poo.utils.Point2D;

public interface MapHandler {
    boolean isMoveValid(Point2D position); // Verifica se o movimento é válido
    void updatePosition(Point2D oldPosition, Point2D newPosition, GameElement elementName); // Atualiza o tabuleiro
}

