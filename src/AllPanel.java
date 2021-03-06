import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AllPanel extends JPanel {

	public AllPanel(ArrayList<HashMap<String, Object>> photoList) {

		setLayout(new BorderLayout(0, 0));

		/* "모든 사진"의 패널, scrollPane */
		JPanel image_panel = new JPanel();
		Dimension panel_size = new Dimension(400, 2000);
		image_panel.setPreferredSize(panel_size);
		image_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JScrollPane all_scrollPane = new JScrollPane(image_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(all_scrollPane, BorderLayout.CENTER);
		all_scrollPane.setViewportView(image_panel);

		/* 아이콘으로 사용될 것 */
		JLabel photo_label[] = new JLabel[photoList.size()];
		ImageIcon photo_icon[] = new ImageIcon[photoList.size()];
		Dimension icon_size = new Dimension(145, 145);

		/* 이미지를 더블클릭했을 때 작동 - photo_panel로 화면 전환 */
		class Mouse implements MouseListener {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					removeAll();
					for (int j = 0; j < photoList.size(); j++) {
						if (e.getSource() == photo_label[j]) {
							try {
								Image image = ImageIO.read(new File(
										Main.getAlbumPath() + File.separator + photoList.get(j).get("filefullname")));
								PhotoPanel photo_panel = new PhotoPanel(image, photoList, j);
								add(photo_panel);
							} catch (IOException e1) {
								e1.getStackTrace();
							}
						}
					}
					updateUI();
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

		}

		for (int i = 0; i < photoList.size(); i++) { // photoList의 사진들의 아이콘을 띄운다

			photo_icon[i] = new ImageIcon(Main.getAlbumPath() + File.separator + photoList.get(i).get("filefullname"));
			Image img = photo_icon[i].getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
			photo_icon[i] = new ImageIcon(img);
			photo_label[i] = new JLabel(photo_icon[i]);
			photo_label[i].setPreferredSize(icon_size);
			photo_label[i].addMouseListener(new Mouse());
			image_panel.add(photo_label[i]);

		}
	}
}
