package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class Controller {

    @FXML public Pane layerCategorie;
    @FXML public Pane accueil;
    @FXML public VBox vb;
    @FXML public VBox vbI;
    @FXML public VBox listing;
    @FXML public VBox diffCat; // Affichage des éléments "catégorie" (vertical et + propre)
    @FXML public ScrollPane ingredientsPossible;
    @FXML public ScrollPane layerCourse;
    @FXML public ScrollPane recipeContainer;
    @FXML public ScrollPane recipePossible; // Affichage des recettes contenant le terme recherché
    @FXML public TextField barreRecherche;

    private Model model;
    private boolean doIngredSearch = false;
    Stack<String> frigo  = new Stack<String>();
    private ArrayList<String> ingredsLeft = new ArrayList<String>();
    private Set<Recipe> recipeDisplay = new HashSet<Recipe>(); 

    private int etatCA = 0;
    private int etatCO = 0;

    VBox liste = new VBox();


    public void initModel(Model model) {
        this.model = model;
    }

    public void newCategories(String catName){
        Label lbl = new Label(catName);
        lbl.setFont(new Font("Arial", 20)); // A modifier avec css

        this.diffCat.setSpacing(10); // A modifier avec css
        this.diffCat.setAlignment(Pos.TOP_CENTER); // A modifier avec css
        this.diffCat.getChildren().add(lbl);
    }

    public void newIngredient(String ingName){
        Label ig = new Label(ingName);
        ig.setFont(new Font("Arial", 15)); // A modifier avec css
        this.liste.getChildren().add(ig);

        this.layerCourse.setContent(liste);
        this.layerCourse.toFront();
    }

    public void showCategories(){
        if (etatCA == 0){
            this.layerCategorie.setTranslateX(Math.abs(this.layerCategorie.getLayoutX()));
            this.layerCategorie.toFront();
            etatCA = 1;

        } else {
            this.layerCategorie.setTranslateX(0);
            this.diffCat.getChildren().clear();
            etatCA = 0;
        }
    }

    public void showIngredients(){
        if (etatCO == 0){
            this.layerCourse.setTranslateX(-this.layerCourse.getWidth());
            etatCO=1;
        } else {
            this.layerCourse.setTranslateX(0);
            etatCO=0;
        }
    }

    public void showRecipe(Recipe recipe){
        this.recipePossible.setVisible(false);
        this.recipeContainer.setVisible(true);

        VBox rectPane = (VBox) this.recipeContainer.getContent();
        rectPane.getStyleClass().add("recipePage");
        rectPane.getChildren().clear();

        Rectangle rect = new Rectangle(0,0, 200, 250);
        ImagePattern image = new ImagePattern(new Image(recipe.getImage()));
        rect.setArcHeight(90.0);
        rect.setArcWidth(90.0);

        rect.setFill(image);
        rect.getStyleClass().add("img");

        VBox ingre = new VBox();
        for (String ing : recipe.getRequirements()){
            CheckBox cb = new CheckBox(ing);

            cb.setOnAction(actionEvent -> {
                String txt = ((CheckBox) actionEvent.getTarget()).getText();
                if (ingredsLeft.contains(txt)){
                    ingredsLeft.remove(txt);
                } else {
                    ingredsLeft.add(txt);
                }
                System.out.println(ingredsLeft);
            });

            ingredsLeft.add(ing);
            ingre.getChildren().add(cb);
        }

        Label titreRecette = new Label(recipe.getName());
        titreRecette.getStyleClass().add("h1");

        Label titreIngredient = new Label("Les ingrédients");
        titreIngredient.getStyleClass().add("h1");

        Label titreEtape = new Label("Les étapes");
        titreEtape.getStyleClass().add("h1");

        rectPane.getChildren().add(titreRecette);
        rectPane.getChildren().add(rect);
        rectPane.getChildren().add(titreIngredient);
        rectPane.getChildren().add(ingre);
        rectPane.getChildren().add(titreEtape);

        rectPane.getChildren().add(new Label(recipe.getSteps()));
        System.out.println(recipe.getSteps());
    }

    private void showDropdown() {
        this.recipePossible.setContent(this.vb);
        this.ingredientsPossible.setContent(this.vbI);
        this.listing.toFront();
        this.listing.setVisible(true);
        this.recipePossible.setVisible(true);
    }
    private void hideDropdown() {
        this.listing.setVisible(false);
        this.ingredientsPossible.setVisible(false);
    }

    public void addRecipe(String name){
        /// A refaire par css
        Label lbl = new Label(name);
        lbl.getStyleClass().add("listRecipe");
        this.vb.getChildren().add(lbl);
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
        hb.getStyleClass().add("listIngredients");
        this.vbI.getChildren().add(hb);
    }
    
    @FXML
    public void categorieBtn() { 
        for (String name : this.model.getCategories()) {
            this.newCategories(name); 
        }
        this.showCategories();
    }

    @FXML
    public void courseBtn() { 
        this.liste.getChildren().clear();
        for (String s : this.ingredsLeft) {
            this.newIngredient(s);
        }
        this.showIngredients();
    }

    @FXML
    public void showAdvanced() {
        this.doIngredSearch = (! this.doIngredSearch) && this.recipePossible.isVisible();
        this.ingredientsPossible.setVisible(this.doIngredSearch);
    }

    @FXML
    public void findRecipe() {
        String input = barreRecherche.getCharacters().toString().toLowerCase();
        this.vb.getChildren().clear();
        this.vbI.getChildren().clear();

        if (input.length() > 0) {
            this.recipeDisplay = this.model.search(input, null, null); // TODO: categsFilter, ingredsFilter
            for (Recipe rcp : this.recipeDisplay) {
                this.addRecipe(rcp.getName());
            } 
            if (this.doIngredSearch) {
                for (String ing : this.model.searchIngredients(input)) {
                    this.addIngredients(ing);
                }
            }   
            this.showDropdown();
        } else {
            this.hideDropdown();
        }
    }

    @FXML
    public void watchRecipe(MouseEvent mouseEvent){
        String recipeName = ((Text) mouseEvent.getTarget()).getText();
        for (Recipe recipe : this.recipeDisplay){
            if (recipe.getName().equals(recipeName)) {
                this.showRecipe(recipe);
            }
        }
    }

    @FXML
    public void stackIngredients(MouseEvent mouseEvent){
        Text element = (Text) mouseEvent.getTarget();
        frigo.add(element.getText());

        System.out.println("Elements dans le frigo :" + frigo);
        System.out.println("Recette correspondantes : ");

        // for (String s : frigo){
        //     for (Recipe recipe : this.model.recipeList){
        //         // Faire page d'accueil qui montre les recettes dispo 
        //         for (String ing : recipe.getIngredients()){
        //             if (ing.equals(s)){
        //                 System.out.println(recipe.getName());
        //             }
        //         }
        //     }
        // }
    }


    public void mainPage(ArrayList<Recipe> recipeList){
        VBox main = (VBox) recipeContainer.getContent();

        HBox line = new HBox();
        VBox card = new VBox();

        card.getStyleClass().add("card"); // Faire css arrondi / etc...
        line.getStyleClass().add("line"); // Faire css margin de chaque coté

        card.setAlignment(Pos.CENTER);
        /// POUR TEST je prend un recette au hasard
        /// Plus tard on mettra les 20 premiers meileurs recettes (par likes) boucle for pour 20
        /// En scrollant s'il arrive a la fin des 20 premiers, on aggrandi la liste et reset mainPage()
        Recipe test = recipeList.get(0);

        Rectangle rect = new Rectangle(0,0, 200, 250);
        ImagePattern image = new ImagePattern(new Image(test.getImage()));
        rect.setArcHeight(90.0);
        rect.setArcWidth(90.0);

        rect.setFill(image);
        rect.getStyleClass().add("img");

        Label titre = new Label(test.getName());
        titre.getStyleClass().add("cardTitle");

        card.getChildren().add(rect);
        card.getChildren().add(titre);

        line.getChildren().add(card);
        main.getChildren().add(line);
    }
}
