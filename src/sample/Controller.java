package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Controller {

    @FXML
    private Button btnAvancee;

    @FXML
    private Button btnCategorie;

    @FXML
    private Button btnCourse;

    @FXML
    private AnchorPane liste;

    @FXML
    private ScrollPane recettePossible;

    @FXML
    private TextField barreRecherche;

    @FXML
    private Pane parent;

    @FXML
    private Pane toolbar;

    @FXML
    private Pane layerCategorie;

    @FXML
    private ScrollPane layerCourse;

    @FXML
    public void handleButtonClick(ActionEvent evt){
            Button test = (Button) evt.getSource();

            //Affiche IDs boutons
            System.out.println(test.getId());

            switch(test.getId()){
                case "categorie":
                    //Slide layer categorie vers la droite
                    //layerCategorie.setTranslateX(layerCategorie.layoutXProperty().getValue()+167);

                case "course":
                    //Slide layer liste de course vers la gauche


                case "avancee":
                    //Apparition layer recherche avancée

            }
        }

    @FXML
    public void recipeFinder() {
        FlowPane fp = new FlowPane();

        System.out.println(barreRecherche.getCharacters() + " | " + barreRecherche.getCharacters().length());

        if (barreRecherche.getCharacters().length() > 0) {
            ArrayList<Recipe> r = null;

            recettePossible.setVisible(true);
            recettePossible.setPannable(true);

            try {
                FileInputStream fis = new FileInputStream("recipes.data");
                ObjectInputStream ois = new ObjectInputStream(fis);

                r = (ArrayList<Recipe>) ois.readObject();

                ois.close();
                fis.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            //ArrayList<Recipe> recette = new ArrayList<Recipe>();

            System.out.println("############################################################################################################");
            for (int i = 0; i < r.size(); i++) {
                if (r.get(i).getName().toLowerCase().contains(barreRecherche.getCharacters().toString().toLowerCase())) {
                    fp.getChildren().add(new Label(r.get(i).getName())); //Modifier label pour qu'ils soient Clickable
                    System.out.println(r.get(i).getName()); //Affiche les recettes correspondantes
                }
            }
            fp.setOrientation(Orientation.VERTICAL);
            recettePossible.setContent(fp);
            System.out.println("############################################################################################################");
        }
    }
    public Controller() {

        }
}
