import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main{
	
	public static void main(String[] args) {
		
		/* 앨범으로 사용할 폴더를 생성 */
		Folder folder = new Folder("album");
		String albumPath = folder.makedir();
		
		/* 이미지가 저장된 폴더의 경로를 입력받는다 */
		Scanner scan = new Scanner(System.in);
		String userPath = scan.next();
		
		/* 사진 리스트 생성(파일명, 년, 월, 일 포함) */
		ArrayList<HashMap<String, Object>> photoList = new ArrayList<HashMap<String, Object>>();
		photoList = GetPhoto.getPhotoList(userPath);
		
		/* test용도로 File[]만듦 - 수정할 것 */
		File[] tests = new File[1];
		int temp = 0;
		for(HashMap<String, Object> i : photoList) {
			File test = new File(userPath + File.separator + i.get("filefullname"));
			tests[temp] = test;
			temp++;
		}

		/* 선택한 파일을 앨범 폴더에 추가 */
		Album.addToAlbum(tests, albumPath);
		
		/* 선택한 파일을 앨범 폴더에서 삭제 */
//		Album.deleteFromAlbum(tests[0], albumPath);
	}

}
