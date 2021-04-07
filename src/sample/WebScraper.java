package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {


    static public Recipe makeRecipe(String url) throws Exception {
        ////////// Collect des elements //////////
        Document doc = Jsoup.connect(url).get();
        Elements psbIngredients = doc.select("ul a.autobesttag");
        Elements tableOfContent = doc.select(".entry-content > ul li");
        Elements steps = doc.select(".entry-content > ul li");

        // Verifie si on a pu obtenir tout les ingredients
        if (psbIngredients == null || psbIngredients.size() != tableOfContent.size()) {
            return null;
        }

        ////////// Obtention des attribut pour construire la recette //////////
        String title = doc.selectFirst(".entry-title").text();
        String category = "dinner";
        String[] ingredients = new String[tableOfContent.size()];
        String[] requirements = new String[tableOfContent.size()];

        int i = 0;
        for(Element el : tableOfContent){
            requirements[i] = el.text();
            Element a = el.selectFirst("a.autobesttag");
            if (a == null)
                return null;
            ingredients[i] = a.text().toLowerCase();
            i++;
        }
        ////////// Creation de la recette //////////
        Recipe rcp = new Recipe(title, category, ingredients, requirements);
        return rcp;
    }

    static public ArrayList<Recipe> collectRecipes() {
        String url = "http://www.lesrecettesdecuisine.com/liste-recettes-cuisine-html";
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements linkGroup = doc.select("h3 a");
            int size = linkGroup.size();
            int acc = 0;

            for (Element e : linkGroup) {
                ////////// Affiche le progrès //////////
                System.out.println("( " + acc + "/" + size + " )");
                acc ++;
                ////////// Echantillon de test //////////
                if (acc > 20)
                    break;

                ////////// Création les recettes a partir de l'url //////////
                Recipe rcp = makeRecipe(e.attr("href"));
                if (rcp != null)  // Si la creation est un succés
                    recipes.add(rcp);

            }

        } catch (Exception e) {
            System.out.println(e);
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
        // recipes.add(new Recipe("Ratatouille", "dinner", new String[]{"oignon", "carotte"}));
        // recipes.add(new Recipe("Mousse au chocolat", "dessert", new String[]{"crème", "chocolat"}));
        // recipes.add(new Recipe("Crème brulée", "dessert", new String[]{"crème"}));


        //////////////////// Test de collecte de donnée ////////////////////
        //ArrayList<Recipe> r = collectRecipes();

        //////////////////// Test de sauvegarde ////////////////////
        // saveRecipes(recipes);

        //////////////////// Test de charge en memoire ////////////////////
        //ArrayList<Recipe> r = load();
        //System.out.println(r);

        //////////////////// Test mapping ////////////////////
        //RecipeMap mappedRecipe = new RecipeMap(r);
        //System.out.println(mappedRecipe);
    }

}