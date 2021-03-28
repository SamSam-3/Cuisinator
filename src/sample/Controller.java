package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class Controller {

    @FXML private Button btnTest = new Button();

        @FXML
        public void handleButtonClick(ActionEvent evt){
            Button test = (Button) evt.getSource();

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


    }

}
