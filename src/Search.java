import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Search {

	public static ArrayList<HashMap<String, Object>> getSearchedList (String searchTerm, String type){
		
		ArrayList<HashMap<String, Object>> foundPhotoList = new ArrayList<HashMap<String, Object>>();
		
		if (type.equals("파일명")) {
			for (HashMap<String, Object> photo : Key.bringKeys("Key")) {
				if (((String) photo.get("filefullname")).contains(searchTerm)) {
					foundPhotoList.add(photo);
				}
			}
		} else if (type.equals("날짜")) {
			for (HashMap<String, Object> photo : Key.bringKeys("Key")) {
				if (((String) photo.get("fileyear") + photo.get("filemonth") + photo.get("fileday"))
						.contains(searchTerm)) {
					foundPhotoList.add(photo);
				}
			}
		} else if (type.equals("메모")) {
			for (HashMap<String, Object> photo : Key.bringKeys("Key")) {
				String memo = Album.getSavedMemo_str((String) photo.get("filename"));
				if (memo != null && memo.contains(searchTerm)) {
					foundPhotoList.add(photo);
				}
			}
		}
		
		if (foundPhotoList.size() == 0) {
			JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.");
		}
		
		return foundPhotoList;
	}
}
