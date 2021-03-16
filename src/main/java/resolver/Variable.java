package resolver;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Classe représentant les variables qui composent la reprentation du probleme
 */
public class Variable {

    // Set de variable représentant les voisins de la variable. équivaut à la liste des contraintes binaires
    private final HashSet<Variable> neighbors;

    // Set d'entier corespondant au domain de la variable
    private final HashSet<Integer> domains;

    // Position x de la variable dans le sudoku
    private final int xPos;

    // Position y de la variable dans le sudoku
    private final int yPos;

    /**
     * Constructeur de la classe
     * @param xPos Position x de la variable dans le sudoku
     * @param yPos Position y de la variable dans le sudoku
     */
    public Variable(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        domains = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        neighbors = new HashSet<>();
    }

    /**
     * Constructeur alternatif de la classe pour les variable déjà assigné au départ
     * @param xPos Position x de la variable dans le sudoku
     * @param yPos Position y de la variable dans le sudoku
     * @param value Valeur assigné à la variable
     */
    public Variable(int xPos, int yPos, Integer value) {
        this.xPos = xPos;
        this.yPos = yPos;

        domains = new HashSet<>();
        domains.add(value);
        neighbors = new HashSet<>();
    }

    /**
     * Donne la liste des voisin de la variable
     * @return Set des voisin de la variable
     */
    public HashSet<Variable> getNeighbors() {
        return neighbors;
    }

    /**
     * Ajoute un nouveau voisin a la variable
     * @param newNeighbors Voisin à ajouter
     */
    public void addNeighbors(Variable newNeighbors){
        neighbors.add(newNeighbors);
    }

    /**
     * Donne le domaine de la varaiable
     * @return Set des enntier composant le domaine
     */
    public HashSet<Integer> getDomains() {
        return domains;
    }

    /**
     * Retire la valeur donnée au domaine de la variable
     * @param value Valeur a retirer du domaine
     */
    public void removeFromDomains (int value){
        domains.remove(value);
    }

    /**
     * Retourne la position x de la variable dans le sudoku
     * @return Position x de la variable
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Retourne la position y de la variable dans le sudoku
     * @return Position y de la variable
     */
    public int getyPos() {
        return yPos;
    }

}
