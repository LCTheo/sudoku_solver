package resolver;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe principale du programme. Elle comprend la totalité des algorithme de résolution de probleme.
 * Son fonctionnement est basé sur les problemes a satisfaction de contrainte et a pour algorithme principal backtracking
 */
public class Resolver {

    /**
     * Fonction principale de la classe resolver. Elle initialise les variable interne comme le csp et l'assignement des variable et lance la résolution.
     * @param grid Valeur initiale des variable du sudoku représenté dans un tableau
     * @return Assignement complet et consistant de la grille de sudoku
     */
    public Integer[][] resolve(Integer[][] grid){
        Variable[][] csp = new Variable[9][9];
        Integer[][] assignment = new Integer[9][9];
        int x = 0;
        int y = 0;

        // Chargement de la grille de départ dans les variables du programme
        while (x< 9){
            while (y< 9){
                if(grid[x][y] != null){
                    csp[x][y] = new Variable(x, y, grid[x][y]);
                }else {
                    csp[x][y] = new Variable(x, y);
                }

                y++;
            }
            y = 0;
            x++;
        }
        x = 0;
        y = 0;

        // Atribution des contraintes binaires pour chaque variable
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
        // Préprocessing avec l'algorithme AC-3 pour réduire le domaine des variables
        AC_3(csp);
        return recursive_Backtracking(assignment, csp);
    }

    /**
     * Fonction de résolution du problème. Il s'agit de l'algorithme de résolution recursive backtracking
     * @param assignment Assignement des variables initialement vide
     * @param csp Tableau de variable représentant le probleme à résoudre
     * @return Assignement complet et consistant si une solution existe
     */
    private Integer[][] recursive_Backtracking(Integer[][] assignment, Variable[][] csp){
        Integer[][] result;
        if (isComplete(assignment)){
            return assignment;
        }
        Variable var = selectUnassignedVariable(csp, assignment);
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

    /**
     * Fonction vérifiant si un assignement de variable est consitant avec les contraintes du probleme
     * @param assignment Assignement de variable dont on doit vérifier la consistance
     * @param csp Ensemble de variable représentant le problème
     * @return True si consistant false sinon
     */
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
        // Nombre de valeurs légales de la variable précédente (par défaut initialisé à 9 car c'est le nombre de valeurs légales le plus élevé théoriquement)
        int legal_values = 9;
        // Nombre de valeurs légales de la variable courante
        int new_legal_values = 0;
        // Boolean de test de légalité d'une valeur pour une variable
        boolean legal = true;
        // Variable à retourner
        Variable choosen = null;
        // Parcours du tableau pour trouver toutes les cases vides (non-assignées)
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
                        /* Une fois que tous les voisins sont passés,
                        /   Si le test de légalité est vrai (ce qui veut dire que notre valeur courante n'est pas la même que la valeur de l'un des voisins)
                        /   Alors le nombre de valeurs légales de la variable courante est augmentée
                        */
                        if(legal){
                            new_legal_values++;
                        }
                        // La légalité est remise à sa valeur par défaut pour la prochaine valeur à tester
                        legal = true;
                    }
                    /* Une fois que toutes les valeurs sont passées pour la variable courante,
                    /   On regarde si le nombre de valeurs légales de la variable courante est inférieur au nombre de valeurs légales de la variable précédente
                    */
                    if(new_legal_values<legal_values){
                        // On met à jour le nombre de valeurs légales de la variable précédente car la nouvelle valeur est "mieux"
                        legal_values = new_legal_values;
                        // On considère également que la variable courante est donc celle qui sera retenue pour être retournée car elle comporte le nombre de valeurs légales le moins élevé
                        choosen = csp[x][y];
                        // En cas d'égalité entre le nombre de valeurs légales de la variable précédente et de la variable courante, appel de Degree_heuristic pour faire un choix
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
        // Nombre de contraintes pour la variable 2
        int constaints_var2 = 0;
        // Nombre de contraintes pour la variable 1
        int constaints_var1 = 0;

        // On compte le nombre de contraintes de la variable 2 (nombre de voisins non-assignés)
        for (Variable neighbor : var2.getNeighbors()) {
            if(assignment[neighbor.getxPos()][neighbor.getyPos()] == null){
                constaints_var2++;
            }
        }
        // On compte le nombre de contraintes de la variable 1 (nombre de voisins non-assignés)
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

    /**
     * Fonction vérifiant si l'assignement donné est complet
     * @param assignment Assignement dont on doit vérifier la complétude
     * @return True si complet false sinon
     */
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

    /**
     * Assigne une valeur a une variable
     * @param assignment Tableau de valeur correspondant à l'assignement des variables
     * @param variable Variable à assigner
     * @param value Valeur à assigner à la variable
     */
    private void assign(Integer[][] assignment, Variable variable, int value){
        int xPos = variable.getxPos();
        int yPos = variable.getyPos();

        assignment[xPos][yPos] = value;

    }

    /**
     * Désasigne un variable
     * @param assignment Tableau de valeur correspondant à l'assignement des variables
     * @param variable Variable à désasigne
     */
    private void unassign(Integer[][] assignment, Variable variable){
        int xPos = variable.getxPos();
        int yPos = variable.getyPos();

        assignment[xPos][yPos] = null;
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
                    // si l'assignement est consistant cela veut dire que le domaine de la variable voisine sera réduit si on attribu la valeur à la variable de départ.
                    // on ajoute alors 1 au score de cette valeur
                    if(isConsistent(assignment, csp)){
                        score++;
                    }
                    assignment[neighbor.getxPos()][neighbor.getyPos()] = null;
                }
            }

            // on trie les valeurs dans une liste en fonction du score calculé
            int i = 0;
            while (i < orderedScore.size() && score > orderedScore.get(i)){
                i++;
            }
            orderedScore.add(i, score);
            orderedDomain.add(i, value);
        }
        return orderedDomain;
    }

    /**
     * Fonction de propagation des contraintes sur les arcs.
     * @param csp Ensemble de variable du problème dont les domains vont etre réduits
     */
    private void AC_3(Variable[][] csp){
        LinkedList<Pair<Variable,Variable>> queue = new LinkedList<>();
        int i = 0;
        int j = 0;
        while (i < 9){
            while (j< 9){
                Variable var = csp[i][j];
                for (Variable neighbor: var.getNeighbors()){
                    queue.add(new ImmutablePair<>(var, neighbor));
                }
                j++;
            }
            j =0;
            i++;
        }
        while (!queue.isEmpty()){
            Pair<Variable,Variable> arc = queue.remove();
            if(removeInconsistentValue(arc)){
                for (Variable neighbor: arc.getLeft().getNeighbors()){
                    queue.add(new ImmutablePair<>(arc.getLeft(), neighbor));
                }
            }
        }
    }

    /**
     * fonction testant pour un arc donnée si des valeurs du domaine du membre de gauche de l'arc sont inconsitant avec le domaine du membre de droite.
     * Si c'est le cas la valeur est retirée du domaine de la variable.
     * @param arc Arc dont on étudit la consistance.
     * @return True si le domaine de la variable à été modifié, false sinon.
     */
    private boolean removeInconsistentValue(Pair<Variable, Variable> arc) {
        boolean removed = false;
        boolean inconsitent;
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (Integer valueX:arc.getLeft().getDomains()) {
            inconsitent = true;
            for (Integer valueY: arc.getRight().getDomains()) {
                if (!valueX.equals(valueY)) {
                    inconsitent = false;
                    break;
                }
            }
            if (inconsitent){
                toRemove.add(valueX);
                removed = true;
            }
        }
        for (Integer value: toRemove) {
            arc.getLeft().removeFromDomains(value);
        }
        return removed;
    }
}

