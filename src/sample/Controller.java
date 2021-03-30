package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private Button btnAvancee;
    private Button btnCategorie;
    private Button btnCourse;
    private Pane parent;
    private Pane toolbar;
    private Pane layerCategorie;
    private ScrollPane layerCourse;

    @FXML
    public void handleButtonClick(ActionEvent evt){
            Button test = (Button) evt.getSource();
            System.out.println(test.getId());
            switch(test.getId()){
                case "categorie":

                    //Slide layer categorie vers la droite


                case "course":
                    //Slide layer liste de course vers la gauche


                case "avancée":
                    //Apparition layer recherche avancée

            }


            System.out.println(test.getId());
        }

    public Controller() {
        this.btnAvancee = new Button();
        this.btnCategorie = new Button();
        this.btnCourse = new Button();
        this.parent = new Pane();
        this.toolbar = new Pane();
        this.layerCategorie = new Pane();
        this.layerCourse = new ScrollPane();

        //Ajout de la toolbar à la pane principal
        parent.getChildren().add(toolbar);

        //Ajout des éléments à la toolbar
        toolbar.getChildren().add(btnCategorie);
        btnCategorie.setId("btnBurger");
        toolbar.getChildren().add(btnCourse);
        toolbar.getChildren().add(btnAvancee);

        //Ajout des panes Categorie et liste de course
        parent.getChildren().add(layerCategorie);
        parent.getChildren().add(layerCourse);

        btnCategorie.setOnAction(this::handleButtonClick);
        }
}
