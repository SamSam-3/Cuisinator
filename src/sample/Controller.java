package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;

public class Controller {
    // fxml
    @FXML public Pane layerCategorie;
    @FXML public Pane accueil;
    @FXML public Pane toolbar;
    @FXML public Pane findByIngredients;
    @FXML public VBox vb; //VBox recettes
    @FXML public VBox vbI; //VBox ingrédients
    @FXML public VBox vbC; //VBox catégories
    @FXML public VBox diffCat; // Affichage des éléments "catégorie" (vertical et + propre)
    @FXML public HBox tags;
    @FXML public ScrollPane ingredientsPossible;
    @FXML public ScrollPane categoriesPossible;
    @FXML public ScrollPane layerCourse;
    @FXML public ScrollPane recipeContainer;
    @FXML public ScrollPane recipePossible; // Affichage des recettes contenant le terme recherché
    @FXML public TextField barreRecherche;
    @FXML public TextField barreTags;
    @FXML public Button course;
    @FXML public Button categorie;

    private Model model;
    private ArrayList<String> ingredsLeft = new ArrayList<String>();
    private Set<Recipe> recipeDisplay = new HashSet<Recipe>(); 
    private Stack<String> frigo  = new Stack<String>();
    ArrayList<VBox> listCard = new ArrayList<VBox>();
    private VBox liste = new VBox();
    private Button btnFile = new Button();
    private TextField ing = new TextField();
    private int etatCard=0;
    public int nbCard=3;


    // Etat
    private boolean doIngredSearch = false;
    public int etatCA = 0;
    public int etatCO = 0;


    public void initModel(Model model) {
        this.model = model;
    }

    public void addRecipe(){
        ArrayList<String> ingredients = new ArrayList<String>();
        final String[] lien = {""}; //Va comprendre, java me saoule pour que ca soit une array String x)

        //Initialisation mainPage
        VBox addRecipePage = (VBox) recipeContainer.getContent();
        addRecipePage.getChildren().clear();
        FileChooser fc = new FileChooser();

        //Juste une entete de titre pour le style de page
        Label entete = new Label(" Ajout d'une recette :");
        entete.getStyleClass().add("h1");

        //Titre de la recette
        TextField title = new TextField();
        title.setPromptText("Titre de la recette ... ");
        title.setMaxWidth(300);

        //Menu déroulant des categories
        HBox getCat = new HBox();
        getCat.getChildren().add(new Label("Sélection de la catégorie : "));
        ComboBox<String> menu = new ComboBox<String>();
        for(String cat: model.getCategories()){
            menu.getItems().add(cat);
        }
        getCat.getChildren().add(menu); // Ajout du menu dans une liste horizontal



        // Récupération de l'image (Récupère le lien vers l'image depuis le pc)
        HBox fichier = new HBox();
        fichier.getChildren().add(new Label("Ajouter une image : "));
        btnFile = new Button("Parcourir... ");
        fichier.getChildren().add(btnFile);

        btnFile.setOnMousePressed(mouseEvent -> {
            fc.setTitle("Open image ...");
            File file = fc.showOpenDialog(Window.getWindows().get(0));
            lien[0] = file.getAbsolutePath();

            Rectangle rect = new Rectangle(0,0, 120, 150);
            ImagePattern image = null;
            try {
                image = new ImagePattern(new Image(file.toURI().toURL().toExternalForm()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            rect.setArcHeight(90.0);
            rect.setArcWidth(90.0);

            rect.setFill(image);
            rect.getStyleClass().add("img");

            fichier.getChildren().add(rect);
        });

        // Ajout des ingrédient un par un
        HBox listeIng = new HBox();
        Button ingPlus = new Button("Ajouter l'ingrédient");
        ing = new TextField();
        ing.setPromptText("Donner un ingrédients ... ");
        listeIng.getChildren().add(ing);
        listeIng.getChildren().add(ingPlus);


        // Affichage des ingrédients ajoutés
        VBox vb = new VBox();
        ingPlus.setOnMousePressed(mouseEvent -> {
            if(ing.getText().length()>0) {
                HBox hb = new HBox();
                Button annule = new Button("x");
                annule.getStyleClass().add("btnAnnuler");
                ingredients.add(ing.getCharacters().toString().toLowerCase());
                hb.getChildren().add(new Label(ing.getCharacters().toString().toLowerCase()));
                hb.getChildren().add(annule);

                //Evenement pour supprimer l'élément
                annule.setOnMousePressed(mouseEvent1 -> {
                    vb.getChildren().remove(hb);
                    Label toRemove = (Label) hb.getChildren().get(0);
                    ingredients.remove(toRemove.getText());

                });

                vb.getChildren().add(hb);
                ing.clear();
            }
        });


        TextArea steps = new TextArea();
        steps.setPromptText("Décrivez chaque étapes de votre recette\n N'hésitez pas à sauter des lignes quand vous finissez une étape.");

        Button confirmer = new Button("Confirmer !");
        confirmer.setOnMousePressed(mouseEvent -> {

            //J'ai trouvé que ca comme verif pour l'instant
            if(this.condition(title,menu,lien[0],ingredients,steps)) {
                Recipe newRecipe = new Recipe(
                        title.getText(),
                        menu.getValue(),
                        ingredients.toArray(new String[ingredients.size()]),
                        ingredients.toArray(new String[ingredients.size()]),
                        lien[0], steps.getText());

                //Ajouter la nouvelle recette à la base de données
                model.recipeList.add(newRecipe);

            } else {
                //Montrer qu'un champ n'est pas remplie a tel ou tel endroit
                Stage popup = new Stage(); //J'ai fais une PopUp, j'apprend des trucs c'est cool x)
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.setTitle("This is a pop up window");

                Label error = new Label("Champs requis manquants.");
                Button ok = new Button("Ok");
                ok.setOnAction(e -> popup.close());

                HBox layout = new HBox(5);

                layout.getChildren().addAll(error,ok);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout, 300, 100);
                popup.setScene(scene);
                popup.showAndWait();
            }
        });


        // Ajout des éléments à la page
        addRecipePage.getChildren().add(entete);
        addRecipePage.getChildren().add(title);
        addRecipePage.getChildren().add(getCat);
        addRecipePage.getChildren().add(fichier);
        addRecipePage.getChildren().add(listeIng);
        addRecipePage.getChildren().add(vb);
        addRecipePage.getChildren().add(steps);
        addRecipePage.getChildren().add(confirmer);
    }

    public boolean condition(TextField title,ComboBox<String> menu, String lien, ArrayList<String> ingredients, TextArea etapes){
        boolean verif = true;
        String errStyle = "-fx-border-color: red;";
        String goodStyle = "-fx-border-color: black;";

        if(title.getText().length()==0){
            title.setStyle(errStyle);
            verif = false;
        } else {
            title.setStyle(goodStyle);
        }

        if(menu.getValue() ==null || menu.getValue().length()==0){
            menu.setStyle(errStyle);
            verif = false;
        } else {
            menu.setStyle(goodStyle);
        }

        if(lien.length() == 0){
            btnFile.setStyle(errStyle);
            verif = false;
        } else {
            btnFile.setStyle(goodStyle);
        }

        if(ingredients.size()==0){
            ing.setStyle(errStyle);
            verif = false;
        } else {
            ing.setStyle(goodStyle);
        }

        if(etapes.getText().length()==0){
            etapes.setStyle(errStyle);
            verif = false;
        } else {
            etapes.setStyle(goodStyle);
        }

        return verif;
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

        this.vbI.getChildren().add(lb);
    }

    public void addCategorie(String cat){
        /// A refaire par css

        Label lb = new Label(cat);
        lb.setFont(new Font("Arial",15));

        this.vbC.getChildren().add(lb);
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
        this.findByIngredients.setVisible(false);

        VBox rectPane = (VBox) this.recipeContainer.getContent();
        rectPane.getStyleClass().add("recipePage");
        rectPane.getChildren().clear();

        Button btnFav = new Button();
        //Mettre le boouton favoris dans l'angle à droite

        btnFav.setOnMousePressed(mouseEvent -> {
            if(recipe.isFavorite()){
                recipe.setFavorite(false);
                btnFav.setStyle("-fx-background-position: center;" +
                        "  -fx-background-image: url('images/unstar.png'');" +
                        "  -fx-background-repeat: no-repeat;" +
                        "  -fx-background-size: cover, auto;");
            } else {
                recipe.setFavorite(true);
                btnFav.setStyle("-fx-background-position: center;" +
                        "  -fx-background-image: url('images/star.png');" +
                        "  -fx-background-repeat: no-repeat;" +
                        "  -fx-background-size: cover, auto;");
            }
        });

        Rectangle rect = new Rectangle(0,0, 200, 250);

        Image img = new Image(recipe.getImage()); //Si l'acces a internet est ok --> affiche l'image
        if(img.isError()){ //Si pas d'internet --> image d'erreur
            img = new Image("images/noInternet.bmp");
        }
        ImagePattern image = new ImagePattern(img);
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

        rectPane.getChildren().add(btnFav);
        rectPane.getChildren().add(titreRecette);
        rectPane.getChildren().add(rect);
        rectPane.getChildren().add(titreIngredient);
        rectPane.getChildren().add(ingre);
        rectPane.getChildren().add(titreEtape);

        rectPane.getChildren().add(new Label(recipe.getSteps()));
        System.out.println(recipe.getSteps());
    }

    private void showDropdown(int etat) {
        if(etat == 0) {
            this.recipePossible.setContent(this.vb);
            this.vb.toFront();
            this.vb.setVisible(true);
            this.recipePossible.toFront();
            this.recipePossible.setVisible(true);
            this.recipePossible.setDisable(false);
        } else {
            this.doIngredSearch = true;
            this.vbI.toFront();
            this.ingredientsPossible.setContent(this.vbI);
            this.findByIngredients.setVisible(true);
        }
    }

    private void hideDropdown(int etat) {
        if(etat == 0) {
            this.recipePossible.setVisible(false);
        } else {
            this.doIngredSearch = false;
            this.findByIngredients.setVisible(false);
        }
    }

    @FXML
    public void categorieBtn() { 
        for (String name : this.model.getCategories()) {
            this.newCategories(name); 
        }

        Button ajoutRecipe = new Button("Ajouter une recette");
        diffCat.getChildren().add(ajoutRecipe);
        ajoutRecipe.setOnMousePressed(mouseEvent -> {
            this.addRecipe();
            layerCategorie.setVisible(false);
        });
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
        if (this.doIngredSearch) {
            for (String ing : this.model.searchIngredients(barreRecherche.getCharacters().toString().toLowerCase())) {
                this.addIngredients(ing);
            }
        }   
    } //Est ce que ca sert réellement ?? Il a pas l'air utilisé et depuis lgtps

    @FXML
    public void findRecipe() {
        String searchRec = barreRecherche.getCharacters().toString().toLowerCase();
        String searchIng = barreTags.getCharacters().toString().toLowerCase();

        this.vb.getChildren().clear();
        this.vbI.getChildren().clear();
        this.vbC.getChildren().clear();

        if (searchRec.length() > 0) {
            this.recipeDisplay = this.model.search(searchRec, null, null); // TODO: categsFilter, ingredsFilter
            for (Recipe rcp : this.recipeDisplay) {
                this.addRecipe(rcp.getName());
            }
            this.showDropdown(0);
        } else {
            this.hideDropdown(0);
        }

        if(searchIng.length() > 0) {
            for (String ing : this.model.searchIngredients(searchIng)) {
                this.addIngredients(ing);
            }
            for(String cat : this.model.getCategories()){
                this.addCategorie(cat);
            }

            this.showDropdown(1);
        } else {
            this.hideDropdown(1);
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
    public void stackTags(MouseEvent mouseEvent){
        Text element = (Text) mouseEvent.getTarget();
        frigo.add(element.getText());

        //Permet d'aligner les éléments proprement
        HBox tag = new HBox();
        Label lb = new Label(element.getText());
        lb.setFont(new Font("Arial",12));
        Button annuler = new Button("x");
        annuler.getStyleClass().add("btnAnnuler");


        //Fonction pour annuler le tag
        annuler.setOnMousePressed(mouseEvent1 -> { //Tout les tags sont dans frigo mtn
            tags.getChildren().remove(tag);
            Label toRemove = (Label) tag.getChildren().get(0);
            frigo.remove(toRemove.getText());
            System.out.println("Elements dans le frigo :" + frigo);
        });

        //Alignement et mise en page des cate
        lb.setPadding(new Insets(5));
        tag.setAlignment(Pos.CENTER);


        //Ajout du label et bouton comme nouveau tag
        tag.getChildren().add(lb);
        tag.getChildren().add(annuler);
        tag.getStyleClass().add("tag");

        this.tags.getChildren().add(tag);

        System.out.println("Elements dans le frigo :" + frigo);

    }

    public void initCard(ArrayList<Recipe> recipeList){
        /// POUR TEST je prend un recette au hasard
        /// Plus tard on mettra les 20 premiers meileurs recettes (par likes) boucle for pour 20
        /// En scrollant s'il arrive a la fin des 20 premiers, on aggrandi la liste et reset mainPage()
        /// Prévoir pour le nombre de carte par la taille adaptative de l'app

        int maxi=6;
        for(int i=0;i<maxi;i++) {
            Recipe recipe = recipeList.get(i);

            VBox card = new VBox(); // Nouvelle carte
            card.getStyleClass().add("card"); // Faire css arrondi / etc...

            card.setAlignment(Pos.CENTER); // Centre les éléments

            Rectangle rect = new Rectangle(0, 0, 200, 200);
            Image img = new Image(recipe.getImage()); //Si l'acces a internet est ok --> affiche l'image
            if (img.isError()) { //Si pas d'internet --> image d'erreur
                img = new Image("images/noInternet.bmp");
            }
            ImagePattern image = new ImagePattern(img);
            rect.setArcHeight(90.0);
            rect.setArcWidth(90.0);
            rect.setFill(image);
            rect.getStyleClass().add("img");

            Label titre = new Label(recipe.getName());
            titre.getStyleClass().add("cardTitle"); // modifier la taille des caractères suivant la longueur du titre

            card.getChildren().add(rect); //ajout element a la carte
            card.getChildren().add(titre); //ajout element a la carte

                // show la recette cliqué au menu
            card.setOnMousePressed(mouseEvent -> {
                showRecipe(recipe);
            });

            listCard.add(card);
        }
    }

    public void mainPage(ArrayList<Recipe> recipeList){
        VBox main = (VBox) recipeContainer.getContent();
        main.getChildren().clear();

        if(etatCard==0){
            initCard(recipeList);
            etatCard=1;
        }
        /// POUR TEST je prend un recette au hasard
        /// Plus tard on mettra les 20 premiers meileurs recettes (par likes) boucle for pour 20
        /// En scrollant s'il arrive a la fin des 20 premiers, on aggrandi la liste et reset mainPage()
        /// Prévoir pour le nombre de carte par la taille adaptative de l'app

        int indice=0;
        int maxi=6;
        int i=0;
        while(i<(maxi/nbCard)+1){

            HBox line = new HBox(); // Nouvelle ligne de cartes
            line.getStyleClass().add("line"); // Faire css margin de chaque coté

            int j=0;
            while(j<nbCard && indice<maxi) {
                line.getChildren().add(listCard.get(indice)); // Ajout des cartes a la ligne
                j++;
                indice++;
            }
            i++;
            main.getChildren().add(line); // Ajout des line à la page d'accueil
        }

    }

}
