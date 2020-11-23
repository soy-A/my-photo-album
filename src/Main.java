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
	}

}
