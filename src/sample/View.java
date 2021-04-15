package sample;

//  import javafx.event.ActionEvent;
//  import javafx.event.EventHandler;

 import javafx.fxml.FXML;
 import javafx.geometry.Pos;
 import javafx.scene.control.CheckBox;
 import javafx.scene.control.Label;
 import javafx.scene.control.ScrollPane;
 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
 import javafx.scene.layout.Pane;
 import javafx.scene.layout.VBox;
 import javafx.scene.text.Font;

public class View {

    private Controller ctrl;
    private int etatCA = 1;
    private int etatCO = 1;
    private int etatAV = 1;

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

    public void showRecipe(String title, String img, String[] req, String steps){

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
                if (this.ctrl.ingredientsManquant.contains(txt)){
                    this.ctrl.ingredientsManquant.remove(txt);
                } else {
                    this.ctrl.ingredientsManquant.add(txt);
                }
                System.out.println(this.ctrl.ingredientsManquant);
            });

            this.ctrl.ingredientsManquant.add(ing);
            ingre.getChildren().add(cb);
        }

        rectPane.getChildren().add(titreRecette);
        rectPane.getChildren().add(image);
        rectPane.getChildren().add(ingre);

        rectPane.getChildren().add(new Label("Les étapes :"));
        rectPane.getChildren().add(new Label(steps));
    }

    public View(Controller ctrl) {
        this.ctrl = ctrl;

    }
    
}
