import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

public class Album {
	
	/* 파일을 관리할 폴더 만듦 */
	public static String makedir(String dirName) {
		
		final String path = System.getProperty("user.dir") + File.separator + dirName;
		File folder = new File(path);

		if (!folder.exists()) { // 폴더가 존재하지 않는 경우에만 새로 생성함
			try {
				folder.mkdir();
				
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return path;
	}

	/* 앨범에 추가 */
	public static void addToAlbum(File[] targetFiles, String albumPath) { // 선택한 파일들은 파일리스트에 넣어둘 것

		for (File file : targetFiles) {
			File temp = new File(albumPath + File.separator + file.getName());
			FileInputStream fis = null;
			FileOutputStream fos = null;

			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(temp);
				byte[] buf = new byte[4096];
				int cnt = 0;

				while ((cnt = fis.read(buf)) != -1) {
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
	public static void deleteFromAlbum(String fileName) {

		File temp = new File(Main.albumPath + File.separator + fileName);

		try {
			temp.delete();
			
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/* 메모를 파일로 내보냄 */
	public static void addToMemo(String fileName, String memo) {

		String memoPath = makedir("album" + File.separator + "memo");

		try {
			FileWriter fw = new FileWriter(memoPath + File.separator + fileName + ".txt");
			BufferedWriter bf = new BufferedWriter(fw);

			bf.write(memo);
			bf.close();

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/* 파일을 삭제할 때 메모도 같이 삭제함 */
	public static void deleteFromMemo(String fileName) {

		File temp = new File(Main.albumPath + File.separator + "memo" + File.separator + fileName + ".txt");

		try {
			if (temp.exists()) {
				temp.delete();
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/* 저장된 메모를 읽어옴 */
	public static void getSavedMemo(String fileName, JTextArea textArea) {
		
		String memoPath = Main.albumPath + File.separator + "memo";

		try {
			FileReader fr = new FileReader(memoPath + File.separator + fileName + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String str = null;

			while ((str = br.readLine()) != null) {
				textArea.append(str + "\n");
			}
			br.close();

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/* 저장된 메모를 읽어옴(String) */
	public static String getSavedMemo_str(String fileName) {

		String string = "";
		String memoPath = Main.albumPath + File.separator + "memo";

		try {
			FileReader fr = new FileReader(memoPath + File.separator + fileName + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String str;

			while ((str = br.readLine()) != null) {
				string = string + "\n" + str;
			}
			br.close();

		} catch (Exception e) {
			e.getStackTrace();
		}

		return string;
	}
}
