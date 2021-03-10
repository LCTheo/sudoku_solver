package resolver;

import java.util.Arrays;
import java.util.HashSet;

public class Variable {


    private final HashSet<Variable> neighbors;
    private final HashSet<Integer> domains;
    private final int xPos;
    private final int yPos;

    public Variable(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        domains = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        neighbors = new HashSet<>();
    }

    public Variable(int xPos, int yPos, Integer assignment) {
        this.xPos = xPos;
        this.yPos = yPos;

        domains = new HashSet<>();
        domains.add(assignment);
        neighbors = new HashSet<>();
    }

    public void addNeighbors(Variable newNeighbors){
        neighbors.add(newNeighbors);
    }

    public void removeNeighbors(Variable newNeighbors){
        neighbors.remove(newNeighbors);
    }

    public HashSet<Variable> getNeighbors() {
        return neighbors;
    }

    public HashSet<Integer> getDomains() {
        return domains;
    }

    public void removeFromDomains (int value){
        domains.remove(value);
    }

    public void addToDomains (int value){
        domains.add(value);
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void Printvoisin(){
        for(Variable voisin : neighbors){
            System.out.print("x "+voisin.getxPos() + " y " +voisin.getyPos()+"; ");
        }
    }
}
