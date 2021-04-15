package sample;

//  import javafx.event.ActionEvent;
//  import javafx.event.EventHandler;
 import javafx.fxml.FXML;
 import javafx.scene.control.ScrollPane;
 import javafx.scene.layout.Pane;
 import javafx.scene.layout.VBox;

public class View {

    private Controller ctrl;

    ///////// AFFICHAGE PANE \\\\\\\\\\

    @FXML public ScrollPane recettePossible = new ScrollPane(); // Affichage des recettes contenant le terme recherché
    @FXML public ScrollPane ingredientsPossible = new ScrollPane();
    @FXML public Pane layerCategorie = new Pane();
    @FXML public Pane accueil = new Pane();
    @FXML public ScrollPane layerCourse = new ScrollPane();
    @FXML public ScrollPane recipeContainer = new ScrollPane();
    @FXML public VBox diffCat = new VBox(); // Affichage des éléments "catégorie" (vertical et + propre)
    @FXML public VBox vb = new VBox();
    @FXML public VBox vbI = new VBox();
    @FXML public VBox listing = new VBox();
    @FXML public VBox ingredientsRequis = new VBox();


    public View(Controller ctrl) {
        this.ctrl = ctrl;
    }
    
}
