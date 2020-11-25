
public class Main{
	
	static String albumPath;
	
	public static void main(String[] args) {
		
		/* 앨범으로 사용할 폴더를 생성 */
		Folder folder = new Folder("album");
		albumPath = folder.makedir();
		
		/* 선택한 파일을 앨범 폴더에서 삭제 */
//		Album.deleteFromAlbum(tests[0], albumPath);
		
		/* GUI */
		GUI gui = new GUI();
		gui.setVisible(true);
	}

}
