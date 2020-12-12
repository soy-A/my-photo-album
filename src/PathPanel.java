import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PathPanel extends JPanel {

	private static ArrayList<HashMap<String, Object>> photoList = new ArrayList<HashMap<String, Object>>();

	/* <사진 가져오기> 패널 */
	public PathPanel() {

		setLayout(new BorderLayout(0, 0));

		/* <사진 가져오기 - 경로 입력> 패널 */
		JPanel pathget_panel = new JPanel();
		pathget_panel.setBackground(Color.LIGHT_GRAY);
		add(pathget_panel, BorderLayout.NORTH);

		JLabel path_label = new JLabel("사진을 불러올 폴더의 경로를 입력해주세요");
		pathget_panel.add(path_label);

		JTextField path_text = new JTextField();
		pathget_panel.add(path_text);
		path_text.setColumns(30);

		JButton pathget_button = new JButton("불러오기");
		pathget_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String userPath = path_text.getText(); // 입력받은 경로

				/* 사진 리스트 생성(파일명, 년, 월, 일 포함) */
				photoList = GetPhoto.getPhotoList(userPath);

				/* <사진 가져오기 - 메인> 패널 */
				JPanel image_panel = new JPanel();
				Dimension panel_size = new Dimension(400, 2000);
				image_panel.setPreferredSize(panel_size);
				image_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				JScrollPane scrollPane = new JScrollPane(image_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				add(scrollPane, BorderLayout.CENTER);
				scrollPane.setViewportView(image_panel);

				/* 체크박스(사진 선택) 생성, 선택한 사진들을 앨범 폴더에 추가 */
				JCheckBox photo_check[] = new JCheckBox[photoList.size()];
				ImageIcon photo_icon[] = new ImageIcon[photoList.size()];
				Dimension icon_size = new Dimension(145, 145);
				ArrayList<Integer> selected_index = new ArrayList<Integer>();

				class PhotoItem implements ItemListener {
					/* 선택된 사진을 표시하며, 선택된 인덱스를 저장 */
					public void itemStateChanged(ItemEvent e) {
						for (int i = 0; i < photoList.size(); i++) {
							if (e.getItem() == photo_check[i]) {
								if (e.getStateChange() == ItemEvent.SELECTED) {
									photo_check[i].setOpaque(true); // 선택 표시
									selected_index.add(i);
								} else if (e.getStateChange() == ItemEvent.DESELECTED) {
									photo_check[i].setOpaque(false);
									for (int j = 0; j < selected_index.size(); j++) {
										if (selected_index.get(j) == i) {
											selected_index.remove(j);
										}
									}
								}
							}
						}
					}
				}
				for (int i = 0; i < photoList.size(); i++) {
					photo_check[i] = new JCheckBox();
					photo_check[i].addItemListener(new PhotoItem());
					photo_check[i].setBackground(Color.DARK_GRAY);
					photo_icon[i] = new ImageIcon(userPath + File.separator + photoList.get(i).get("filefullname"));
					Image img = photo_icon[i].getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
					photo_icon[i] = new ImageIcon(img);
					photo_check[i].setIcon(photo_icon[i]);
					photo_check[i].setPreferredSize(icon_size);
					image_panel.add(photo_check[i]);
				}

				/* 경로 입력 패널 숨기기 */
				pathget_panel.setVisible(false);

				/* <사진 가져오기 - 경로 입력 - 추가> 패널 */
				JPanel add_panel = new JPanel();
				add(add_panel, BorderLayout.NORTH);

				JLabel add_label = new JLabel("앨범에 추가할 사진을 선택한 후 \"추가\" 버튼을 눌러주세요");
				add_panel.add(add_label);

				JButton add_button = new JButton("추가");
				add_button.addActionListener(new ActionListener() {	// 선택된 파일들을 앨범에 추가한다
					public void actionPerformed(ActionEvent e) {
						File selected_photo[] = new File[selected_index.size()];
						ArrayList<HashMap<String, Object>> selected_list = new ArrayList<HashMap<String, Object>>();

						for (int i = 0; i < selected_index.size(); i++) {
							selected_photo[i] = new File(userPath + File.separator
									+ photoList.get(selected_index.get(i)).get("filefullname"));
							selected_list.add(i, photoList.get(selected_index.get(i)));
						}
						
						Album.addToAlbum(selected_photo, Main.getAlbumPath());
						Key.addToKey(selected_list, "Key");
						JOptionPane.showMessageDialog(null, "사진이 추가되었습니다.");
					}
				});
				add_panel.add(add_button);
				add_panel.setVisible(true);

			}
		});
		pathget_panel.add(pathget_button);
	}
}
