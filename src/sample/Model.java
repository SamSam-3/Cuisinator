package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Model {

    public RecipeMap recipeMap;
    public ArrayList<Recipe> recipeList;
    private View view;
    private Controller ctrl;

    public Model(View view, Controller ctrl) {
        this.view = view;
        this.ctrl = ctrl;
        this.loadData();
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        recipeMap = (RecipeMap)DataManager.load("recipe-map");
        recipeList = (ArrayList<Recipe>)DataManager.load("recipe-list");
        // TODO: fav.save

        if (recipeMap == null || recipeList == null) {
            throw new RuntimeException("Data base not found");
        }
    }

    public void showLayer(String btn){

        switch(btn){
            case "categorie":

                // Slide layer categorie vers la droite
                if(this.ctrl.ca == 0){
                    for (String name : recipeMap.getCategories()) {

                        Label lbl = new Label(name);
                        lbl.setFont(new Font("Arial",20)); // A modifier avec css
                        this.view.diffCat.setSpacing(10); // A modifier avec css
                        this.view.diffCat.setAlignment(Pos.TOP_CENTER); // A modifier avec css

                        this.view.diffCat.getChildren().add(lbl);
                    }
                    this.view.layerCategorie.setTranslateX(Math.abs(this.view.layerCategorie.getLayoutX()));
                    this.view.layerCategorie.toFront();
                    this.ctrl.ca=1;

                } else {
                    this.view.layerCategorie.setTranslateX(0);
                    this.view.diffCat.getChildren().clear();

                    this.ctrl.ca=0;
                }
                break;

            case "course":
                // Slide layer liste de course vers la gauche

                VBox liste = new VBox();
                for (String s : this.ctrl.ingredientsManquant) {
                    Label ig = new Label(s);
                    ig.setFont(new Font("Arial", 15)); // A modifier avec css
                    liste.getChildren().add(ig);
                }

                this.view.layerCourse.setContent(liste);
                this.view.layerCourse.toFront();

                if(this.ctrl.co == 0){
                    this.view.layerCourse.setTranslateX(-this.view.layerCourse.getWidth());
                    this.ctrl.co=1;
                } else {
                    this.view.layerCourse.setTranslateX(0);
                    this.ctrl.co=0;
                }
                break;

            case "avancee":
                // Apparition layer recherche avancée
                if(this.ctrl.av == 0 && this.view.recettePossible.isVisible()) {
                    this.view.ingredientsPossible.setVisible(true);
                    this.ctrl.av=1;
                } else {
                    this.view.ingredientsPossible.setVisible(false);

                    this.ctrl.av=0;
                }
                break;
        }

    }

    public void showRecipe(Text recipeName){

        for(Recipe recipe : this.ctrl.recetteclickable){
            if(recipe.getName().equals(recipeName.getText())){

                /// View
                this.view.ingredientsRequis.getChildren().clear();

                this.view.recettePossible.setVisible(false);
                this.view.recipeContainer.setVisible(true);

                Pane rectPane = (Pane) this.view.recipeContainer.getContent();
                Label titreRecette = new Label(recipe.getName());
                titreRecette.setFont(new Font("Arial Black",25));
                titreRecette.getStyleClass().add("h1");


                ImageView img = new ImageView(new Image(recipe.getImage()));
                img.getStyleClass().add("img");
                VBox ingre = new VBox();

                for(String ing : recipe.getRequirements()){
                    CheckBox cb = new CheckBox(ing);

                    cb.setOnAction(actionEvent -> {
                        String txt = ((CheckBox)actionEvent.getTarget()).getText();
                        if(this.ctrl.ingredientsManquant.contains(txt)){
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
                rectPane.getChildren().add(img);
                rectPane.getChildren().add(ingre);

                rectPane.getChildren().add(new Label("Les étapes :"));
                rectPane.getChildren().add(new Label(recipe.getSteps()));
            }
        }

    }

    public void search(String input) {
        // ArrayList<String> names = new ArrayList<String>();
        //ArrayList<String> ingredients = new ArrayList<String>();

        if(input.length()>0){

            this.view.vb.getChildren().clear();
            this.view.vbI.getChildren().clear();
            this.ctrl.recetteclickable.clear();

            //// View
            this.view.listing.toFront();
            this.view.listing.setVisible(true);
            this.view.recettePossible.setVisible(true);

            //////// Recettes \\\\\\\\
            System.out.println("############################################# Recettes #####################################################");
            for (Recipe recipe : recipeList) {
                String name = recipe.getName();
                if (name.toLowerCase().contains(input)) { // A modifier pour faire des recherches sans accents et autres caracteres spéciaux

                    /// View
                    Label lbl = new Label(name);
                    this.view.vb.getChildren().add(lbl);
                    this.ctrl.recetteclickable.add(recipe);

                    System.out.println(name); // Affiche les recettes correspondantes
                }
            }
            /// View
            this.view.recettePossible.setContent(this.view.vb); // TODO: deplacer tout ca dans la vue

            //////// Ingrédients \\\\\\\\
            System.out.println("############################################# Ingrédients #################################################");

            for(String ing : recipeMap.getIngredients()){
                if(ing.toLowerCase().contains(input)){

                    /// View
                    Label lb = new Label(ing);
                    lb.setFont(new Font("Arial",15));
                    Button cancel = new Button("X");
                    cancel.setPrefSize(5,5);
                    HBox hb = new HBox();
                    hb.getChildren().add(lb);
                    hb.getChildren().add(cancel);
                    hb.setAlignment(Pos.CENTER);
                    this.view.vbI.getChildren().add(hb);

                    System.out.println(ing);
                }
            }
            this.view.ingredientsPossible.setContent(this.view.vbI);
            System.out.println("############################################################################################################");
        }

        if (input.length() == 0) {
            this.view.listing.setVisible(false);

            this.view.vb.getChildren().clear();
            this.view.vbI.getChildren().clear();
            this.ctrl.recetteclickable.clear();
        }
    }
        // view.showIngredients()
    
}
