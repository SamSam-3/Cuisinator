package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale.Category;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {

    static public Recipe makeRecipe(String url, String category) throws Exception {
        ////////// Collect des elements //////////
        Document doc = Jsoup.connect(url).get();
        Elements psbIngredients = doc.select("ul a.autobesttag");
        Elements tableOfContent = doc.select(".entry-content > ul li");
        Elements stepsP = doc.select("p");

        // Verifie si on a pu obtenir tout les ingredients
        if (psbIngredients == null || tableOfContent == null || psbIngredients.size() != tableOfContent.size() || psbIngredients.size() == 0) {
            return null;
        }

        ////////// Obtention des attribut pour construire la recette //////////
        String title = doc.selectFirst(".entry-title").text();
        String[] ingredients = new String[tableOfContent.size()];
        String[] requirements = new String[tableOfContent.size()];
        String img = doc.selectFirst(".post-thumbnail img").attr("data-src");
        int i = 0;
        for(Element el : tableOfContent){
            requirements[i] = el.text();
            Element a = el.selectFirst("a.autobesttag");
            if (a == null)
                return null;
            ingredients[i] = a.text().toLowerCase();
            i++;
        }
        String steps = "";
        for (Element el : stepsP) {
            steps += el.text();
        }
        ////////// Creation de la recette //////////
        return new Recipe(title, category, ingredients, requirements, img, steps);
    }

    static public ArrayList<Recipe> collectRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        String[][] urlDir = {
            {"salades-economiques", "avocat-en-salade"},

            {"cuisine-indienne", "les-paellas", "recette-brochette-grillade", "les-oeufs", 
            "les-poissons", "gibiers", "recette-brochette-grillade", "viande-dinde", "viande-de-porc", 
            "viande-de-boeuf", "volaille-poulet", "viande-de-veau", "viande-mouton", "viande-oie", 
            "cuisine-exotique", "les-legumes",  "potages-et-soupes", "terrines-de-legumes", "les-spaghettis", "tomato-ketchup", "terrines-de-legumes"},
            
            {"les-desserts" , "les-pates-levees"},
            
            {"les-sauces"}
        };
        String[] tags = {"salade", "plat", "dessert", "sauce"};
 
        for (int i = 0; i < tags.length; i++) {
            for (String url : urlDir[i]) {

                try {
                    Document doc = Jsoup.connect("http://www.lesrecettesdecuisine.com/recette-de-cuisine/" + url).get();
                    Elements linkGroup = doc.select("h3 a");
                    int size = linkGroup.size();
                    int acc = 0;
        
                    for (Element e : linkGroup) {
                        ////////// Affiche le progrès //////////
                        System.out.println(i + " - ( " + acc + "/" + size + " )");
                        acc ++;
                        ////////// Echantillon de test //////////
                        if (acc > 10000)
                            break;
        
                        ////////// Création les recettes a partir de l'url //////////
                        Recipe rcp = makeRecipe(e.attr("href"), tags[i]);
                        if (rcp != null)  // Si la creation est un succés
                            recipes.add(rcp);
        
                    }
        
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            
        }

        

        return recipes;
    }

    static public void saveRecipes(ArrayList<Recipe> r) {
        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = new FileOutputStream("recipes.data");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(r);
            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }
    static public ArrayList<Recipe> load() {

        ArrayList<Recipe> r = null;
		

		try {
			FileInputStream fis = new FileInputStream("recipes.data");
			ObjectInputStream ois = new ObjectInputStream(fis);
            
			r = (ArrayList<Recipe>)ois.readObject();

			ois.close();
			fis.close();

		} catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
		}
        return r;
    }

    public static void main(String[] args) {
        //////////////////// Test ////////////////////
        //////////////////// Test de donnée ////////////////////
        // ArrayList<Recipe> recipes = new ArrayList<Recipe>();


        //////////////////// Test de collecte de donnée ////////////////////
        ArrayList<Recipe> r = collectRecipes();

        //////////////////// Test mapping ////////////////////
        RecipeMap mappedRecipe = new RecipeMap(r);
        System.out.println(mappedRecipe);

        //////////////////// Test de sauvegarde ////////////////////
        saveRecipes(r);

        //////////////////// Test de charge en memoire ////////////////////
        //ArrayList<Recipe> r = load();
        //System.out.println(r);
    }

}