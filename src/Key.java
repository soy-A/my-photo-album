import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Key {
	
	private static File album_Key = new File(Main.albumPath + File.separator + "Key");
	
	public static void addToKey(ArrayList<HashMap<String, Object>> selected_list){
		
		ObjectOutputStream oos = null;
		
		try {
			
			oos = new ObjectOutputStream(new FileOutputStream(album_Key));
			oos.writeObject(selected_list);;
			oos.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
	
	public static ArrayList<HashMap<String, Object>> bringKeys(){
		
		ArrayList<HashMap<String, Object>> key = new ArrayList<HashMap<String, Object>>();
		ObjectInputStream ois = null;
		
		try {
			
			ois = new ObjectInputStream(new FileInputStream(album_Key));
			key = (ArrayList<HashMap<String, Object>>) ois.readObject();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
		return key;
	}
	
}
