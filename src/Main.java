import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame{
	static JPanel page1 = new JPanel() {
		Image background = new ImageIcon(Main.class.getResource("/Users/soy-a/Downloads/baekgu.jpeg")).getImage();
		public void paint(Graphics g) {
			g.drawImage(background, 0, 0, null);
		}
	};
	public Main() {
		homeframe();
	}
	public void homeframe() {
		setTitle("testing");
		setSize(400,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
		page1.setLayout(null);
		page1.setBounds(0,0,400,600);
		add(page1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Main();

	}

}
