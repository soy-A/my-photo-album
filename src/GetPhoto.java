import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GetPhoto {
	
	private static String image_extension = "jpg,jpeg,png,gif,swf,bmp";	// 이미지 파일 확장자
	
	public static ArrayList<HashMap<String, Object>> getPhotoList(String userPath){
		
		ArrayList<HashMap<String, Object>> photoList = new ArrayList<HashMap<String, Object>>();
		File userFile = new File(userPath);
		
		if(userFile.exists() && userFile.isDirectory()) {
			
			File[] fileList = userFile.listFiles();
			
			for (File tempFile : fileList) {
				
				if(tempFile.isFile() && tempFile.length() > 0) {
					
					/* 파일 이름, 년, 월, 일을 리스트에 저장 */
					String fileFullName = tempFile.getName();
					String onlyFileExt = (fileFullName.substring(fileFullName.lastIndexOf(".")+1)).toLowerCase();
					String onlyFileName = fileFullName.toLowerCase().substring(0, fileFullName.lastIndexOf("."));
					
					SimpleDateFormat year = new SimpleDateFormat("yyyy");
					SimpleDateFormat month = new SimpleDateFormat("MM");
					SimpleDateFormat day = new SimpleDateFormat("dd");
					Date fileDate = new Date(tempFile.lastModified());
					
					if(image_extension.contains(onlyFileExt)) {	// 확장자 확인
						
						HashMap<String, Object> photo = new HashMap<String, Object>();
						photo.put("filename", onlyFileName);
						photo.put("filefullname", fileFullName);
						photo.put("fileyear", year.format(fileDate));
						photo.put("filemonth", month.format(fileDate));
						photo.put("fileday", day.format(fileDate));
						
						photoList.add(photo);
					}
				}
			}
		}
		return photoList;
	}
}
