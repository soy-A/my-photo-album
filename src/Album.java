import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Album {
	
	/* 앨범에 추가 */
	public static void addToAlbum(File[] targetFiles, String albumPath) {	// 선택한 파일들은 파일리스트에 넣어둘 것
		
		for(File file : targetFiles) {
			
			File temp = new File(albumPath + File.separator + file.getName());
			FileInputStream fis = null;
			FileOutputStream fos = null;
			
			try {
				
				fis = new FileInputStream(file);
				fos = new FileOutputStream(temp);
				byte[] buf = new byte[4096];
				int cnt = 0;
				
				while((cnt = fis.read(buf)) != -1) {
					
					fos.write(buf, 0, cnt);
					
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			} finally {
				
				try {
					
					fis.close();
					fos.close();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
			}
		}
	}
	
	
	/* 앨범에서 삭제 */
	public static void deleteFromAlbum(String fileName, String albumPath) {
		
		File temp = new File(albumPath + File.separator + fileName);
		
		try {
			
			temp.delete();
			
		} catch (Exception e) {
			
			e.getStackTrace();
			
		}
	}
}
