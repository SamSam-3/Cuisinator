package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {

    static private String path = "data/";  


    static public boolean save(String key, Object value)  {
        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = new FileOutputStream(path + key + ".save");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(value);
            oos.close();
            fos.close();
            return true; 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; 
        

    }

    static public Object load(String key) {
        Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(path + key + ".save");
			ObjectInputStream ois = new ObjectInputStream(fis);
            
			obj = ois.readObject();

			ois.close();
			fis.close();

		} catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error while loading the binary file: " + key + ".save");
		}
        return obj;
    }
}
