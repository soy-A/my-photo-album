public class Main {

	private static String albumPath;
	
	public static String getAlbumPath() {
		return albumPath;
	}

	public static void main(String[] args) {

		/* 앨범으로 사용할 폴더를 생성 */
		albumPath = Album.makedir("album");

		/* GUI */
		GUI gui = new GUI();
		gui.setVisible(true);
	}

}