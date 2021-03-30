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

    static public String[] collectIngredient(String url) throws Exception {

        Document doc = Jsoup.connect(url).get();
        Elements possibleIngredients = doc.select("ul a.autobesttag");

        // TODO: Test si on a trouvé tout les ingredients de la liste 
        if (possibleIngredients == null) {
            // System.out.println(possibleIngredients.size());
            // System.out.println(doc.select(".entry-content > ul li").size());
            return null;
        }

        // Stockage des ingredients dans un tableau
        String[] ingredients = new String[possibleIngredients.size()];
        int i = 0;
        for(Element el : possibleIngredients){
            System.out.println(el.text());
            ingredients[i++] = el.text().toLowerCase();
        }

        return ingredients;
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
                // if (acc > 50) 
                //     break; 

                ////////// Trouve les ingredients dans l'url //////////
                String[] ingredients = collectIngredient(e.attr("href"));

                if (ingredients == null) 
                    continue;

                recipes.add(new Recipe(e.text(), "dinner", ingredients));
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


        //////////////////// Test collecte de donnée ////////////////////
        // ArrayList<Recipe> recipes = collectRecipes();
        
        //////////////////// Sauvegarde test ////////////////////
        // saveRecipes(recipes);

        //////////////////// Load test ////////////////////
        ArrayList<Recipe> r = load();
        System.out.println(r);
        // RecipeMap mappedRecipe = new RecipeMap(r);
        // System.out.println(mappedRecipe);
    }

}