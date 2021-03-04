package resolver;

import display.Display;

import java.util.ArrayList;
import java.util.HashMap;
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
        csp[0][0].Printvoisin();
        System.out.println("test");
        return recursive_Backtracking(assignment, csp);
    }

    private Integer[][] recursive_Backtracking(Integer[][] assignment, Variable[][] csp){
        Integer[][] result;
        if (isComplete(assignment)){
            return assignment;
        }
        Variable var = selectUnassignedVariable(csp, assignment);
        HashSet<Integer> values = var.getDomains();
        for (Integer value : values){
            assign(assignment, var, value);
            System.out.println("pos : "+ var.getxPos() + " "+var.getyPos()+ " value "+ value);
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

    /**
     * Sélection d'un variable selon l'algorithme MRV (Most Remaining Value)
     * @param csp Tableau de variable qui permet de récupérer ou d'assigner une variable à une position donnée
     * @param assignment Tableau d'entier permettant de savoir si une variable à une position donnée est assignée ou non
     * @return La variable choisie selon l'algorithme MRV (et Degree Heuristic selon son appel)
     */
    private Variable selectUnassignedVariable(Variable[][] csp, Integer[][] assignment) {
        int x = 0;
        int y = 0;
        //Nombre de valeurs légales de la variable précédente (par défaut initialisé à 9 car c'est le nombre de valeurs légales le plus élevé théoriquement)
        int legal_values = 9;
        //Nombre de valeurs légales de la variable courante
        int new_legal_values = 0;
        //Boolean de test de légalité d'une valeur pour une variable
        boolean legal = true;
        //Variable à retourner
        Variable choosen = null;
        //Parcours du tableau pour trouver toutes les cases vides (non-assignées)
        while (x< 9){
            while (y< 9){
                if (assignment[x][y] == null) {
                    /* Pour chaque case vide, pour chaque valeur du domaine (de 1 à 9) on regarde l'ensemble des voisins de la variable.
                    /   Si le voisin n'est pas null (il possède donc une valeur assignée), on vérifie que notre valeur courante ne correspond pas à la valeur assignée au voisin
                    /   Si les valeurs correspondent, alors la valeur courante ne pourra pas être légale et donc on passe à la suite
                     */
                    for (int values : csp[x][y].getDomains()) {
                        for (Variable neighbor : csp[x][y].getNeighbors()) {
                            if (assignment[neighbor.getxPos()][neighbor.getyPos()] != null) {
                                if (values == assignment[neighbor.getxPos()][neighbor.getyPos()]) {
                                    legal = false;
                                    break;
                                }
                            }
                        }
                        /*Une fois que tous les voisins sont passés,
                        / Si le test de légalité est vrai (ce qui veut dire que notre valeur courante n'est pas la même que la valeur de l'un des voisins)
                        / Alors le nombre de valeurs légales de la variable courante est augmentée
                        */
                        if(legal){
                            new_legal_values++;
                        }
                        //La légalité est remise à sa valeur par défaut pour la prochaine valeur à tester
                        legal = true;
                    }
                    /*Une fois que toutes les valeurs sont passées pour la variable courante,
                    / On regarde si le nombre de valeurs légales de la variable courante est inférieur au nombre de valeurs légales de la variable précédente
                    */
                    if(new_legal_values<legal_values){
                        //On met à jour le nombre de valeurs légales de la variable précédente car la nouvelle valeur est "mieux"
                        legal_values = new_legal_values;
                        //On considère également que la variable courante est donc celle qui sera retenue pour être retournée car elle comporte le nombre de valeurs légales le moins élevé
                        choosen = csp[x][y];
                        //En cas d'égalité entre le nombre de valeurs légales de la variable précédente et de la variable courante, appel de Degree_heuristic pour faire un choix
                    } else if(new_legal_values==legal_values){
                        choosen = degree_heuristic(choosen, csp[x][y], assignment);
                    }
                }
                y++;
                new_legal_values = 0;
            }
            y = 0;
            x++;
        }
        return choosen;
    }

    /**
     * Méthode pour l'alorithme Degree Heuristic
     * @return la variable avec le plus grand nombre de contraintes
     */
    private Variable degree_heuristic(Variable var1, Variable var2, Integer[][] assignment){
        //Nombre de contraintes pour la variable 2
        int constaints_var2 = 0;
        //Nombre de contraintes pour la variable 1
        int constaints_var1 = 0;

        //On compte le nombre de contraintes de la variable 2 (nombre de voisins non-assignés)
        for (Variable neighbor : var2.getNeighbors()) {
            if(assignment[neighbor.getxPos()][neighbor.getyPos()] == null){
                constaints_var2++;
            }
        }
        //On compte le nombre de contraintes de la variable 1 (nombre de voisins non-assignés)
        for (Variable neighbor : var1.getNeighbors()) {
            if(assignment[neighbor.getxPos()][neighbor.getyPos()] == null){
                constaints_var1++;
            }
        }
        // On retourne la variable avec le plus de contraintes trouvées)
        if (constaints_var2 > constaints_var1) {
            return var2;
        } else {
            return var1;
        }
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
}

