package sample;

import java.util.ArrayList;

//  import javafx.event.ActionEvent;
//  import javafx.event.EventHandler;

//  import javafx.fxml.FXML;
 import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
 import javafx.scene.control.Label;
//  import javafx.scene.control.ScrollPane;
 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
 import javafx.scene.layout.VBox;
 import javafx.scene.text.Font;

public class View {

    private Controller ctrl;
    private int etatCA = 0;
    private int etatCO = 0;
    private int etatAV = 0;

    VBox liste = new VBox();

    public void newCategories(String catName){
        Label lbl = new Label(catName);
        lbl.setFont(new Font("Arial",20)); // A modifier avec css

        this.ctrl.diffCat.setSpacing(10); // A modifier avec css
        this.ctrl.diffCat.setAlignment(Pos.TOP_CENTER); // A modifier avec css

        this.ctrl.diffCat.getChildren().add(lbl);
    }

    public void newIngredient(String ingName){

        Label ig = new Label(ingName);
        ig.setFont(new Font("Arial", 15)); // A modifier avec css
        this.liste.getChildren().add(ig);

        this.ctrl.layerCourse.setContent(liste);
        this.ctrl.layerCourse.toFront();
    }

    public void showCategories(){
        if(etatCA == 0){
            this.ctrl.layerCategorie.setTranslateX(Math.abs(this.ctrl.layerCategorie.getLayoutX()));
            this.ctrl.layerCategorie.toFront();

            System.out.println("Cat showed");
            etatCA = 1;

        } else {
            this.ctrl.layerCategorie.setTranslateX(0);
            this.ctrl.diffCat.getChildren().clear();

            System.out.println("Cat unshowed");
            etatCA = 0;

        }
    }

    public void showIngredients(){
        if(etatCO == 0){
            this.ctrl.layerCourse.setTranslateX(-this.ctrl.layerCourse.getWidth());
            etatCO=1;
        } else {
            this.ctrl.layerCourse.setTranslateX(0);
            etatCO=0;
        }
    }

    public void showAdvanced(){
        if(etatAV == 0 && this.ctrl.recettePossible.isVisible()) {
            this.ctrl.ingredientsPossible.setVisible(true);
            etatAV=1;
        } else {
            this.ctrl.ingredientsPossible.setVisible(false);
            etatAV=0;
        }
    }

    public void showRecipe(String title, String img, String[] req, String steps, ArrayList<String> ingredientsManquant){

        /// View
        this.ctrl.ingredientsRequis.getChildren().clear();

        this.ctrl.recettePossible.setVisible(false);
        this.ctrl.recipeContainer.setVisible(true);

        Pane rectPane = (Pane) this.ctrl.recipeContainer.getContent();
        Label titreRecette = new Label(title);
        titreRecette.setFont(new Font("Arial Black",25));
        titreRecette.getStyleClass().add("h1");

        ImageView image = new ImageView(new Image(img));
        image.getStyleClass().add("img");
        VBox ingre = new VBox();

        for (String ing : req){
            CheckBox cb = new CheckBox(ing);

            cb.setOnAction(actionEvent -> {
                String txt = ((CheckBox) actionEvent.getTarget()).getText();
                if (ingredientsManquant.contains(txt)){
                    ingredientsManquant.remove(txt);
                } else {
                    ingredientsManquant.add(txt);
                }
                System.out.println(ingredientsManquant);
            });

            ingredientsManquant.add(ing);
            ingre.getChildren().add(cb);
        }

        rectPane.getChildren().add(titreRecette);
        rectPane.getChildren().add(image);
        rectPane.getChildren().add(ingre);

        rectPane.getChildren().add(new Label("Les étapes :"));
        rectPane.getChildren().add(new Label(steps));
    }

    public void wipe(String input){

        if(input.length()>0) {

            this.ctrl.vb.getChildren().clear();
            this.ctrl.vbI.getChildren().clear();

            this.ctrl.listing.toFront();
            this.ctrl.listing.setVisible(true);
            this.ctrl.recettePossible.setVisible(true);

        }

        if (input.length() == 0) {
            this.ctrl.listing.setVisible(false);
            this.ctrl.ingredientsPossible.setVisible(false);

            this.ctrl.vb.getChildren().clear();
            this.ctrl.vbI.getChildren().clear();
        }
    }

    public void addRecipe(String name){

        /// A refaire par css
        Label lbl = new Label(name);
        this.ctrl.vb.getChildren().add(lbl);
    }

    public void addIngredients(String ing){

        /// A refaire par css
        Label lb = new Label(ing);
        lb.setFont(new Font("Arial",15));
        Button cancel = new Button("X");
        cancel.setPrefSize(5,5);
        HBox hb = new HBox();
        hb.getChildren().add(lb);
        hb.getChildren().add(cancel);
        hb.setAlignment(Pos.CENTER);
        this.ctrl.vbI.getChildren().add(hb);

    }

    public void saveState(){
        this.ctrl.recettePossible.setContent(this.ctrl.vb);
        this.ctrl.ingredientsPossible.setContent(this.ctrl.vbI);
    }

    public View(Controller ctrl) {
        this.ctrl = ctrl;

    }
    
}
