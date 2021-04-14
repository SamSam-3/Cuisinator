package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Stack;

public class Controller {

    ///////// VARIABLES \\\\\\\\\\
    private Model model;

    private int ca=0;
    private int co=0;
    private int av=0;
    ArrayList<String> I; 
    Stack<String> frigo = new Stack<String>();

    ArrayList<Recipe> recetteclickable = new ArrayList<Recipe>();
    ArrayList<String> ingredientsManquant = new ArrayList<String>();

    ///////// ELEMENTS INTERACTIFS \\\\\\\\\\

    @FXML private TextField barreRecherche;
    @FXML ImageView imageRecette = new ImageView();
    //@FXML Label titreRecette = new Label();

    ///////// AFFICHAGE PANE \\\\\\\\\\
    @FXML private ScrollPane recettePossible; //Affichage des recettes contenant le terme recherché
    @FXML private ScrollPane ingredientsPossible;
    @FXML private Pane layerCategorie; 
    @FXML private Pane accueil;
    @FXML private ScrollPane layerCourse;
    @FXML private ScrollPane recipeContainer;
    @FXML private VBox diffCat; //Affichage des éléments "catégorie" (vertical et + propre)
    @FXML private VBox vb;
    @FXML private VBox vbI; 
    @FXML private VBox listing;
    @FXML private VBox ingredientsRequis;

    ///////// FONCTIONS \\\\\\\\\\
    public Controller(Model model) {
        this.model = model;
    }


    @FXML
    public void handleButtonClick(ActionEvent evt){
            Button test = (Button) evt.getSource();

            //Affiche IDs boutons
            System.out.println(test.getId());

            switch(test.getId()){
                case "categorie":

                    //Slide layer categorie vers la droite
                    if(ca == 0){
                        for (String name : this.model.recipes.getCategories()) {
                            Label lbl = new Label(name);
                            lbl.setFont(new Font("Arial",20));
                            diffCat.setSpacing(10);
                            diffCat.setAlignment(Pos.TOP_CENTER);
                            diffCat.getChildren().add(lbl);
                        }
                        layerCategorie.setTranslateX(Math.abs(layerCategorie.getLayoutX()));
                        layerCategorie.toFront();
                        ca=1;

                    } else {
                        layerCategorie.setTranslateX(0);
                        diffCat.getChildren().clear();
                        ca=0;
                    }
                    break;

                case "course":
                    //Slide layer liste de course vers la gauche

                    VBox liste = new VBox();
                    for (String s : ingredientsManquant) {
                        Label ig = new Label(s);
                        ig.setFont(new Font("Arial", 15));
                        liste.getChildren().add(ig);
                    }

                    layerCourse.setContent(liste);
                    layerCourse.toFront();

                    if(co == 0){
                        layerCourse.setTranslateX(-layerCourse.getWidth());
                        co=1;
                    } else {
                        layerCourse.setTranslateX(0);
                        co=0;
                    }
                    break;

                case "avancee":
                    //Apparition layer recherche avancée
                    if(av == 0 && recettePossible.isVisible()) {
                        ingredientsPossible.setVisible(true);
                        av=1;
                    } else {
                        ingredientsPossible.setVisible(false);

                        av=0;
                    }
                    break;
            }
        }

    @FXML
    public void findRecipe() {
        String input = barreRecherche.getCharacters().toString().toLowerCase();
        
        if (input.length() > 0) {
            vb.getChildren().clear();
            vbI.getChildren().clear();
            recetteclickable.clear();

            listing.toFront();
            listing.setVisible(true);
            recettePossible.setVisible(true);

            System.out.println("############################################# Recettes #####################################################"); // Wtf?
            for (Recipe recipe : r) {
                if (recipe.getName().toLowerCase().contains(input)) { //A modifier pour faire des recherches sans accents et autres caracteres spéciaux

                    Label lb = new Label(recipe.getName());
                    vb.getChildren().add(lb);
                    recetteclickable.add(recipe);

                    System.out.println(recipe.getName()); //Affiche les recettes correspondantes
                }
            }
            recettePossible.setContent(vb); // TODO: deplacer tout ca dans la vue
            System.out.println("############################################# Ingrédients #################################################");

            for(String ing : I){
                if(ing.toLowerCase().contains(input)){
                    Label lb = new Label(ing);
                    lb.setFont(new Font("Arial",15));
                    Button cancel = new Button("X");
                    cancel.setPrefSize(5,5);
                    HBox hb = new HBox();
                    hb.getChildren().add(lb);
                    hb.getChildren().add(cancel);
                    hb.setAlignment(Pos.CENTER);
                    vbI.getChildren().add(hb);

                    System.out.println(ing);
                }
            }
            ingredientsPossible.setContent(vbI);
            System.out.println("############################################################################################################");

        }

        if (barreRecherche.getCharacters().length() == 0) {
            listing.setVisible(false);

            vb.getChildren().clear();
            recetteclickable.clear();
            vbI.getChildren().clear();
        }
    }

    @FXML
    public void watchRecipe(MouseEvent mouseEvent){

        Text recette = (Text) mouseEvent.getTarget();

        for(Recipe recipe : recetteclickable){
            if(recipe.getName().equals(recette.getText())){
                ingredientsRequis.getChildren().clear();
                recettePossible.setVisible(false);
                recipeContainer.setVisible(true);
                Pane rectPane = (Pane) recipeContainer.getContent();

                Label titreRecette = new Label(recipe.getName());
                titreRecette.setFont(new Font("Arial Black",25));
                titreRecette.getStyleClass().add("h1");


                ImageView img = new ImageView(new Image(recipe.getImage()));
                img.getStyleClass().add("img");

                VBox ingre = new VBox();
                for(String ing : recipe.getRequirements()){
                    CheckBox cb = new CheckBox(ing);

                    cb.setOnAction(actionEvent -> {

                        CheckBox ch = (CheckBox) actionEvent.getTarget();
                        if(ingredientsManquant.contains(ch.getText())){
                           ingredientsManquant.remove(ch.getText());
                        } else {
                            ingredientsManquant.add(ch.getText());
                        }
                        System.out.println(ingredientsManquant);
                    });

                    ingredientsManquant.add(ing);
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

    @FXML
    public void stackIngredients(MouseEvent mouseEvent){
        Text element = (Text) mouseEvent.getTarget();
        frigo.add(element.getText());

        System.out.println("Elements dans le frigo :"+frigo.toString());

        System.out.println("Recette correspondantes : ");

        for(String s : frigo){
            for(Recipe recipe : r){
                //Faire page d'accueil qui montre les recettes dispo
                for(String ing : recipe.getIngredients()){
                    if(ing.equals(s)){
                        System.out.println(recipe.getName());
                    }
                }
            }
        }
    }


}
