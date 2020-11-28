import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Key {
	
	private static File album_Key = new File(Main.albumPath + File.separator + "Key");	// 앨범에 key파일을 생성
	
	/* 선택한 사진들의 key-value 정보를 key파일로 내보냄 */
	public static void addToKey(ArrayList<HashMap<String, Object>> selected_list){

		ArrayList<HashMap<String, Object>> brought_list = new ArrayList<HashMap<String, Object>>();
		ObjectOutputStream oos = null;
		
		try {
			
			if(album_Key.exists()) {
				
				brought_list = bringKeys();
				
				for(int i = 0; i < selected_list.size(); i++) {
					
					brought_list.add(selected_list.get(i));
					
				}
				
				oos = new ObjectOutputStream(new FileOutputStream(album_Key));
				oos.writeObject(brought_list);
				
			} else {
				
				oos = new ObjectOutputStream(new FileOutputStream(album_Key));
				oos.writeObject(selected_list);

			}

			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
	
	/* key파일로부터 key-value값을 읽어옴 */
	public static ArrayList<HashMap<String, Object>> bringKeys(){
		
		ArrayList<HashMap<String, Object>> key = new ArrayList<HashMap<String, Object>>();
		ObjectInputStream ois = null;
		
		try {
			
			if(album_Key.exists()) {
				ois = new ObjectInputStream(new FileInputStream(album_Key));
				key = (ArrayList<HashMap<String, Object>>) ois.readObject();
				ois.close();
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
		return key;
	}
	
	/* 지우고자 하는 사진의 key-value를 지우고 저장함 */
	public static void deleteKey(ArrayList<HashMap<String, Object>> photoList, int i) {
		
		ObjectOutputStream oos = null;
		photoList.remove(i);
		
		try {
			
			oos = new ObjectOutputStream(new FileOutputStream(album_Key));
			oos.writeObject(photoList);;
			oos.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
	
}
