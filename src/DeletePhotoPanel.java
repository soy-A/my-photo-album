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
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DeletePhotoPanel extends JFrame {

	public DeletePhotoPanel() {

		setTitle("나의 앨범에서 사진 삭제");

		JPanel newAlbum_panel = new JPanel();
		setContentPane(newAlbum_panel);
		setLayout(new BorderLayout(0, 0));

		JPanel message_panel = new JPanel();
		add(message_panel, BorderLayout.NORTH);

		Vector<String> myAlbumName = new Vector<String>();
		myAlbumName = MyPanel.bringAlbumNames();

		JLabel message_label = new JLabel("사진을 삭제할 앨범을 선택한 후 \"확인\"버튼을 눌러주세요.");
		message_panel.add(message_label);

		JComboBox album_combobox = new JComboBox(myAlbumName);
		message_panel.add(album_combobox);

		ArrayList<Integer> selected_index = new ArrayList<Integer>();

		JButton select_button = new JButton("확인");
		select_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				message_panel.removeAll();
				JLabel message_label = new JLabel("삭제할 사진을 선택한 후 \"삭제\"버튼을 눌러주세요.(선택한 앨범에서만 삭제됩니다)");
				message_panel.add(message_label);
				JButton delete_button = new JButton("삭제");
				delete_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ArrayList<HashMap<String, Object>> photoList = Key.bringKeys(album_combobox.getSelectedItem().toString());
						String selectedAlbum = album_combobox.getSelectedItem().toString();
						ArrayList<HashMap<String, Object>> selected_list = new ArrayList<HashMap<String, Object>>();

						for (int i = 0; i < selected_index.size(); i++) {
							selected_list.add(i, photoList.get(selected_index.get(i)));
						}
						
						Key.deleteKeyFromAlbum(selected_list, selectedAlbum);
						JOptionPane.showMessageDialog(null, "사진이 삭제되었습니다.");
						setVisible(false);
					}
				});
				message_panel.add(delete_button);
				message_panel.updateUI();
				
				ArrayList<HashMap<String, Object>> photoList = Key.bringKeys(album_combobox.getSelectedItem().toString());
				JPanel image_panel = new JPanel();
				Dimension panel_size = new Dimension(400, 2000); // 임시로 2000의 값을 주었다(스크롤이 생성되지 않는 문제)
				image_panel.setPreferredSize(panel_size);
				image_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				JScrollPane scrollPane = new JScrollPane(image_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				add(scrollPane, BorderLayout.CENTER);
				scrollPane.setViewportView(image_panel);
				setVisible(true);

				/* 체크박스(사진 선택) 생성, 선택한 사진들을 해당 앨범에 추가 */
				JCheckBox photo_check[] = new JCheckBox[photoList.size()];
				ImageIcon photo_icon[] = new ImageIcon[photoList.size()];
				Dimension icon_size = new Dimension(145, 145);

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
					photo_icon[i] = new ImageIcon(
							Main.albumPath + File.separator + photoList.get(i).get("filefullname"));
					Image img = photo_icon[i].getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
					photo_icon[i] = new ImageIcon(img);
					photo_check[i].setIcon(photo_icon[i]);
					photo_check[i].setPreferredSize(icon_size);
					image_panel.add(photo_check[i]);
				}
				scrollPane.setVisible(true);
				image_panel.setVisible(true);
			}
		});
		message_panel.add(select_button);

		setSize(630, 500);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);

	}

}
