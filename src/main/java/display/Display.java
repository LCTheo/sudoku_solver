package display;

import resolver.Resolver;
import resolver.Variable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe Display qui permet de gérer l'affcihage graphique du sudoku et également de charger une grille de sudoku
 *
 */
public class Display {

    //Fenêtre principale
    private JFrame frame;

    //Panel pour afficher les cases
    private JPanel panel;

    //Affichage graphique de l'environnement : tableau 2D de 9 par 9. Chaque Label représente une case
    private JLabel[][] tab_labels_ = new JLabel[9][9];

    //Barre de menu pour la fenêtre
    private JMenuBar menubar = new JMenuBar();

    //instance du resolver
    private Resolver resolver;

    /**
     * Constructeur (permet de créer la fenêtre, les cases, et le menu)
     */
    public Display() {
        //Création de la fenetre principale
        frame = new JFrame();
        this.panel = new JPanel();
        //Création des bordures pour délimiter les cases.
        Border border_side = new MatteBorder(1,5,1,1,Color.black);
        Border border_top = new MatteBorder(5,1,1,1,Color.black);
        Border border = new MatteBorder(1,1,1,1,Color.black);
        Border border_side_top = new MatteBorder(5,5,1,1,Color.black);

        //Création du tableau de cases.
        int i = 0;
        int j = 0;
        while(i<9){
            while(j<9){
                //Case classiques
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.PLAIN, 30));
                //this.tab_labels[i][j] = label;
                this.tab_labels_[i][j] = label;
                label.setPreferredSize(new Dimension(50,50));
                //Gestion des bordures pour découper le jeu en 9 cases de 9 cases
                if(j==3 || j==6){
                    label.setBorder(border_side);
                } else {
                    label.setBorder(border);
                }
                if(i==3 || i==6) {
                    if (j == 3 || j == 6) {
                        label.setBorder(border_side_top);
                    } else {
                        label.setBorder(border_top);
                    }
                }
                j++;
            }
            j=0;
            i++;
        }

        //Ajout des cases créées à la fenêtre graphique
        i = 0;
        j = 0;
        while(i<9){
            while (j < 9) {
                this.panel.add(this.tab_labels_[i][j]);
                j++;
            }
            j=0;
            i++;
        }

        //Affichage des cases selon une grille de 9 par 9
        this.panel.setLayout(new GridLayout(9,9));
        frame.setLayout(new BorderLayout());
        frame.add(this.panel, BorderLayout.CENTER);

        //création du bouton pour lancer le resolver
        JPanel buttonPanel = new JPanel();
        JButton runButton = new JButton();
        runButton.setPreferredSize(new Dimension(75, 30));
        runButton.setText("Run");
        runButton.setVisible(true);
        runButton.addActionListener(new RunListener(this));
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(runButton, BorderLayout.LINE_START);
        frame.add(buttonPanel, BorderLayout.PAGE_END);

        buttonPanel.setVisible(true);

        //Ajout d'un menu pour charger les fichiers de sudoku
        JMenu menu = new JMenu("Fichier");
        JMenuItem load = new JMenuItem("Charger un fichier");
        //Action pour aller ouvrir le selecteur de fichier
        load.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            File file;
            if (chooser.showOpenDialog(frame) == JFileChooser.OPEN_DIALOG) {
                file = chooser.getSelectedFile();
                try {
                    //Chargement du fichier
                    load(file);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        menu.add(load);
        this.menubar.add(menu);
        frame.setJMenuBar(this.menubar);

        frame.setPreferredSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sudoku Solver");
        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);

        resolver = new Resolver(this);
    }

    /**
     * Permet de charger le fichier .ss du sudoku sur l'affichage graphique
     * @param file le fichier à charger
     * @throws FileNotFoundException
     */
    private void load(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(file);
        int compteurcar = 0;
        int compteurline = 0;
        resetSudoku();
        //on itère sur chaque ligne et sur chaque caracètre de chaque ligne
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            for (int i =0; i<line.length(); i++) {
                //si le caractère courant est un '!', alors on décrémente le compteur de caractères, pour ne pas fausser le nombre de caractères sur une ligne (Pour en garder 9 au lieu de 11)
                if(line.charAt(i)=='!') {
                    compteurcar--;
                //si le caractère courant est un '-', alors on décrémente le compteur de lignes et on break la boucle pour ne pas fausser le nombre total de lignes (Pour en garder 9 au lieu de 11)
                } else if(line.charAt(i)=='-'){
                    compteurline--;
                    break;
                    //si le caracètre est un integer, alors on l'affiche sur la fenêtre graphique
                } else if(isInteger(String.valueOf(line.charAt(i)))){
                    this.tab_labels_[compteurline][compteurcar].setText(String.valueOf(line.charAt(i)));
                }
                compteurcar++;
            }
            compteurcar=0;
            compteurline++;
        }
    }

    private void resetSudoku() {
        int i = 0;
        int j = 0;
        while (i < 9){
            while (j < 9){
                tab_labels_[i][j].setText("");
                j++;
            }
            j = 0;
            i++;
        }
    }

    /**
     * Permet de déterminer si un caractère est un integer ou non
     * @param valueOf le caractère à évaluer
     * @return true si le caractère est un integer, false sinon
     */
    private boolean isInteger(String valueOf) {
        boolean valid = false;
        try{
            Integer.parseInt(valueOf);
            valid = true;
        } catch (NumberFormatException ex){
            valid = false;
        }
        return valid;
    }

    /**
     * permet de placer une valeur sur une case donnée
     * @param x coordonnée x de la case
     * @param y coordonnée y de la case
     * @param value valeur à afficher
     */
    public void placeVariable(int x, int y, int value){
        this.tab_labels_[x][y].setText(Integer.toString(value));
    }

    /**
     * retire la valeur affichée dans la case aux coordonnées indiquées
     * @param x coordonnée x de la case
     * @param y coordonnée y de la case
     */
    public void deleteVariable(int x, int y){
        this.tab_labels_[x][y].setText("");
    }

    public void runResolver(){
        Integer[][] sudoku = new Integer[9][9];
        int x = 0;
        int y = 0;

        while (x< 9){
            while (y< 9){
                if(!tab_labels_[x][y].getText().equals("")){
                    sudoku[x][y] = Integer.valueOf(tab_labels_[x][y].getText());
                }
                y++;
            }
            y = 0;
            x++;
        }

        sudoku = resolver.resolve(sudoku);
        if(sudoku == null){
            System.out.println("fail");
        }else {
            x = 0;
            y = 0;

            while (x< 9){
                while (y< 9){
                    placeVariable(x, y, sudoku[x][y]);
                    y++;
                }
                y = 0;
                x++;
            }
        }
    }
}
