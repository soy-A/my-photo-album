import java.io.File;
import java.io.IOException;

public class Folder {
	
	private String dirname;
	
	// 생성자
	public Folder(String dirname) { this.dirname = dirname; }
	
	// 파일을 관리할 폴더 만듦
	public String makedir() {
		final String path = System.getProperty("user.dir") + "/" + dirname;
		File folder = new File(path);
		folder.mkdir();
		return path;
	}
}
