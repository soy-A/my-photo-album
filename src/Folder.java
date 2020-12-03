import java.io.File;

public class Folder {

	private String dirname;

	public Folder(String dirname) {
		this.dirname = dirname;
	}

	/* 파일을 관리할 폴더 만듦 */
	public String makedir() {

		final String path = System.getProperty("user.dir") + File.separator + dirname;
		File folder = new File(path);

		if (!folder.exists()) { // 폴더가 존재하지 않는 경우에만 새로 생성한
			try {
				folder.mkdir();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		return path;

	}

}
