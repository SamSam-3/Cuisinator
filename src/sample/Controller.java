package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private Button btnAvancee;

    @FXML
    private Button btnCategorie;

    @FXML
    private Button btnCourse;

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
    public void recipeFinder(){

        barreRecherche.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
            @Override
            public void handle(InputMethodEvent inputMethodEvent) {
                System.out.println("oui");
            }
        });
    }

    public Controller() {

        }
}
