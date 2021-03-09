package resolver;

import display.Display;

import java.util.ArrayList;
import java.util.HashSet;

public class Resolver {

    private final Display display;

    public Resolver(Display display) {
        this.display = display;

    }

    public Integer[][] resolve(Integer[][] grid){
        Variable[][] csp = new Variable[9][9];
        Integer[][] assignment = new Integer[9][9];
        int x = 0;
        int y = 0;

        while (x< 9){
            while (y< 9){
                csp[x][y] = new Variable(x, y);
                assignment[x][y] = grid[x][y];
                y++;
            }
            y = 0;
            x++;
        }
        x = 0;
        y = 0;

        while (x< 9){
            while (y< 9){
                int i = 0;
                while (i< 9){
                    if(!csp[x][y].equals(csp[i][y])){
                        csp[x][y].addNeighbors(csp[i][y]);
                    }
                    if(!csp[x][y].equals(csp[x][i])){
                        csp[x][y].addNeighbors(csp[x][i]);
                    }
                    i++;
                }

                i = 0;
                int j = 0;
                if(x< 3){
                    if(y< 3){
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i][j])){
                                    csp[x][y].addNeighbors(csp[i][j]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                    else if (y< 6){
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i][j+3])){
                                    csp[x][y].addNeighbors(csp[i][j+3]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                    else {
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i][j+6])){
                                    csp[x][y].addNeighbors(csp[i][j+6]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                }
                else if (x< 6){
                    if(y< 3){
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i+3][j])){
                                    csp[x][y].addNeighbors(csp[i+3][j]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                    else if (y< 6){
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i+3][j+3])){
                                    csp[x][y].addNeighbors(csp[i+3][j+3]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                    else {
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i+3][j+6])){
                                    csp[x][y].addNeighbors(csp[i+3][j+6]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                }
                else {
                    if(y< 3){
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i+6][j])){
                                    csp[x][y].addNeighbors(csp[i+6][j]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                    else if (y< 6){
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i+6][j+3])){
                                    csp[x][y].addNeighbors(csp[i+6][j+3]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                    else {
                        while (i< 3){
                            while (j< 3){
                                if(!csp[x][y].equals(csp[i+6][j+6])){
                                    csp[x][y].addNeighbors(csp[i+6][j+6]);
                                }
                                j++;
                            }
                            i++;
                            j=0;
                        }
                    }
                }
                y++;
            }
            y = 0;
            x++;
        }
        return recursive_Backtracking(assignment, csp);
    }

    private Integer[][] recursive_Backtracking(Integer[][] assignment, Variable[][] csp){
        Integer[][] result;
        if (isComplete(assignment)){
            return assignment;
        }
        Variable var = null;
        try {
            var = selectUnassignedVariable(csp, assignment);
        } catch (Exception e) {
            System.out.println("no unassigned Variable");
        }
        ArrayList<Integer> values = orderDomainValue(var, assignment, csp);
        for (Integer value : values){
            assign(assignment, var, value);
            if (isConsistent(assignment, csp)){
                result = recursive_Backtracking(assignment, csp);
                if (result != null){
                    return result;
                }

            }
            unassign(assignment, var);
        }
        return null;
    }

    private boolean isConsistent(Integer[][] assignment, Variable[][] csp) {
        int x = 0;
        int y = 0;

        while (x< 9){
            while (y< 9){
                if (assignment[x][y] != null){
                    for(Variable neighbor : csp[x][y].getNeighbors()){
                        if(assignment[x][y].equals(assignment[neighbor.getxPos()][neighbor.getyPos()])){
                            return false;
                        }
                    }
                }
                y++;
            }
            y = 0;
            x++;
        }
        return true;
    }

    private Variable selectUnassignedVariable(Variable[][] csp, Integer[][] assignment) throws Exception {
        int x = 0;
        int y = 0;

        while (x< 9){
            while (y< 9){
                if (assignment[x][y] == null){
                    return csp[x][y];
                }
                y++;
            }
            y = 0;
            x++;
        }
        throw new Exception();
    }

    private boolean isComplete(Integer[][] assignment){
       int x = 0;
       int y = 0;

       while (x< 9){
           while (y< 9){
               if (assignment[x][y] == null){
                   return false;
               }
               y++;
           }
           y = 0;
           x++;
       }
       return true;
    }

    private Integer[][] assign(Integer[][] assignment, Variable variable, int value){
        int xPos = variable.getxPos();
        int yPos = variable.getyPos();

        assignment[xPos][yPos] = value;

        return assignment;
    }

    private Integer[][] unassign(Integer[][] assignment, Variable variable){
        int xPos = variable.getxPos();
        int yPos = variable.getyPos();

        assignment[xPos][yPos] = null;
        return assignment;
    }

    /**
     * Ordonne les valeurs du domaine de la variable de telle sorte à avoir les valeurs les moins contraignantes en premier
     * @param variable variable dont on trie le domaine
     * @param assignment valeurs des variables déjà assigné
     * @param csp tableau contenant les variables du problème
     * @return liste ordonné de valeur
     */
    private ArrayList<Integer> orderDomainValue(Variable variable, Integer[][] assignment, Variable[][] csp){
        ArrayList<Integer> orderedDomain = new ArrayList<>();
        ArrayList<Integer> orderedScore = new ArrayList<>();
        // pour chaque valeur du domaine on calcule un score correspondant au nombre de variable dont le domaine est réduit si la valeur est assigné à la variable de départ.
        for (Integer value: variable.getDomains()) {
            int score = 0;
            // pour chaque voisin de la variable de départ on assigne la valeur et regarde si l'assignement est consistant
            for (Variable neighbor: variable.getNeighbors()) {
                if (assignment[neighbor.getxPos()][neighbor.getyPos()] == null){
                    assignment[neighbor.getxPos()][neighbor.getyPos()] = value;
                    // si l'assignement est consistant cela veux dire que le domaine de la variable voisine sera réduite si on attribu la valeur à la variable de départ.
                    // on ajoute alors 1 au score de cette valeur
                    if(isConsistent(assignment, csp)){
                        score++;
                    }
                    assignment[neighbor.getxPos()][neighbor.getyPos()] = null;
                }
            }

            // on trie les valeur dans une liste en fonction du score calculé
            int i = 0;
            while (i < orderedScore.size() && score > orderedScore.get(i)){
                i++;
            }
            orderedScore.add(i, score);
            orderedDomain.add(i, value);
        }
        return orderedDomain;
    }
}

