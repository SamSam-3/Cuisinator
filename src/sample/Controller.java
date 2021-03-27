package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML private Button btnTest = new Button();

        @FXML
        public void handleButtonClick(ActionEvent evt){
            Button test = (Button) evt.getSource();

            switch(test.getId()){
                case "categorie":
                    //Faire le layer à gauche


                case "course":
                    //Faire le layer liste de course


                case "avancée":
                    //Faire le layer avancée

            }


            System.out.println(test.getId());
        }

    public Controller() {


    }

}
