package resolver;

import display.Display;
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

    private Variable selectUnassignedVariable(Variable[][] csp, Integer[][] assignment) {
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
        return null;
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

