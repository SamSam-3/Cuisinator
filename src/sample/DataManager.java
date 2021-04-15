package sample;

import java.io.*;

public class DataManager {

    static private String path = "data/";  
    static private String ext = ".save";  

    static private String getPath(String fileName) {
        return path + fileName + ext;
    }

    static public boolean save(String key, Object value)  {
        try {
            FileOutputStream fos = new FileOutputStream(getPath(key));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(value);
            oos.close();
            fos.close();
            return true; 

        } catch (IOException e) {
            e.printStackTrace();
        } 

        return false; 
    }

    static public Object load(String key) {
        Object obj = null;

		try {
			FileInputStream fis = new FileInputStream(getPath(key));
			ObjectInputStream ois = new ObjectInputStream(fis);

			obj = ois.readObject();
			ois.close();
			fis.close();

		} catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error while loading the binary file: " + getPath(key));
		}

        return obj;
    }
}
