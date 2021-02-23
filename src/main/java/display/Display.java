package display;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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

    //Affichage graphique de l'environnement : tableau 2D de 11 par 11. Chaque Label représente une case (sauf certains labels qui représentent des lignes)
    private JLabel[][] tab_labels = new JLabel[11][11];

    //Barre de menu pour la fenêtre
    private JMenuBar menubar = new JMenuBar();

    /**
     * Constructeur (permet de créer la fenêtre, les cases, et le menu)
     */
    public Display() {
        //Création de la fenetre principale
        frame = new JFrame();
        this.panel = new JPanel();
        //Création d'une bordure pour délimiter les cases.
        Border border = BorderFactory.createLineBorder(Color.black,2);

        //Création du tableau de cases.
        int i = 0;
        int j = 0;
        while(i<11){
            while(j<11){
                //Case classiques
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.PLAIN, 30));
                this.tab_labels[i][j] = label;
                label.setPreferredSize(new Dimension(50,50));
                label.setBackground(Color.gray);
                label.setBorder(border);
                //Ajout de case noires pour faire les lignes
                if(i==3 || i==7 || j==3 || j==7){
                    this.tab_labels[i][j] = label;
                    label.setPreferredSize(new Dimension(60,60));
                    label.setOpaque(true);
                    label.setBackground(Color.black);
                    label.setBorder(border);
                }
                j++;
            }
            j=0;
            i++;
        }

        //Ajout des cases créées à la fenêtre graphique
        i = 0;
        j = 0;
        while(i<11){
            while(j<11){
                this.panel.add(this.tab_labels[j][i]);
                j++;
            }
            j=0;
            i++;
        }
        //Affichage des cases selon une grille de 11 par 11
        this.panel.setLayout(new GridLayout(11,11));
        frame.add(this.panel);

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
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Permet de charger le fichier .ss du sudoku sur l'affichage graphique
     * @param file le fichier à charger
     * @throws FileNotFoundException
     */
    private void load(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(file);
        int linenum = 0;
        //on itère sur chaque ligne et sur chaque caracètre de chaque ligne
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            for (int i =0; i<line.length(); i++) {
                //si le caracètre est un integer, alors on l'affiche sur la fenêtre graphique
                if(isInteger(String.valueOf(line.charAt(i)))){
                    this.tab_labels[i][linenum].setText(String.valueOf(line.charAt(i)));
                }
            }
            linenum++;
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
     * @param value valeure à afficher
     */
    public void placeVariable(int x, int y, int value){
        this.tab_labels[x][y].setText(Integer.toString(value));
    }

    /**
     * retire la valeur affichée dans la case aux coordonnées indiquées
     * @param x coordonnée x de la case
     * @param y coordonnée y de la case
     */
    public void deleteVariable(int x, int y){
        this.tab_labels[x][y].setText(null);
    }

}
