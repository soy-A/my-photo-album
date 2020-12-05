import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GetPhoto {

	private static String image_extension = "jpg,jpeg,png,gif,swf,bmp"; // 이미지 파일 확장자

	/* 사용자가 입력한 경로에서 이미지파일의 key-value들을 가져옴 */
	public static ArrayList<HashMap<String, Object>> getPhotoList(String userPath) {

		ArrayList<HashMap<String, Object>> photoList = new ArrayList<HashMap<String, Object>>();
		File userFile = new File(userPath);
		File[] fileList;

		if (userFile.exists() && userFile.isDirectory()) { // 존재하는 폴더인지 확인
			fileList = userFile.listFiles(); // 폴더의 파일들을 리스트에 가져온다

			for (File tempFile : fileList) {
				
				if (tempFile.isFile() && tempFile.length() > 0) {

					/* 파일 이름, 년, 월, 일을 리스트에 저장 */
					String fileFullName = tempFile.getName();
					String onlyFileExt = (fileFullName.substring(fileFullName.lastIndexOf(".") + 1)).toLowerCase();
					String onlyFileName = fileFullName.toLowerCase().substring(0, fileFullName.lastIndexOf("."));

					String year_format = "yyyy";
					String month_format = "MM";
					String day_format = "dd";
					String dateTime_format = "yyyy년 MM월 dd일 a hh:mm:ss";
					BasicFileAttributes attrs;

					try {
						attrs = Files.readAttributes(tempFile.toPath(), BasicFileAttributes.class);
						FileTime time = attrs.creationTime();

						SimpleDateFormat year = new SimpleDateFormat(year_format);
						SimpleDateFormat month = new SimpleDateFormat(month_format);
						SimpleDateFormat day = new SimpleDateFormat(day_format);
						SimpleDateFormat dateTime = new SimpleDateFormat(dateTime_format);

						if (image_extension.contains(onlyFileExt)) { // 확장자 확인, 이미지 파일만 리스트에 삽입

							HashMap<String, Object> photo = new HashMap<String, Object>();
							photo.put("filename", onlyFileName);
							photo.put("filefullname", fileFullName);
							photo.put("fileyear", year.format(new Date(time.toMillis())));
							photo.put("filemonth", month.format(new Date(time.toMillis())));
							photo.put("fileday", day.format(new Date(time.toMillis())));
							photo.put("filedatetime", dateTime.format(new Date(time.toMillis())));

							photoList.add(photo);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return photoList;
	}
}
