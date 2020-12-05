import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewAlbumWindow extends JFrame {

	/* 새 창을 띄워 앨범 추가/삭제 */
	public NewAlbumWindow() {

		setTitle("새로운 앨범 만들기");

		JPanel newAlbum_panel = new JPanel();
		setContentPane(newAlbum_panel);

		JLabel newAlbum_label = new JLabel("새로 만들 앨범의 이름을 입력하세요");
		newAlbum_panel.add(newAlbum_label);

		JTextField newAlbumName_field = new JTextField();
		newAlbumName_field.setColumns(10);
		add(newAlbumName_field);

		JButton newAlbumMake_button = new JButton("추가");
		newAlbumMake_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newAlbumName = newAlbumName_field.getText();
				MyPanel.addMyAlbum(newAlbumName);
				setVisible(false);
				GUI.main_panel.removeAll();
				GUI.my_panel = new MyPanel();
				GUI.main_panel.add(GUI.my_panel);
				GUI.main_panel.updateUI();
			}
		});
		add(newAlbumMake_button);

		JLabel deleteAlbum_label = new JLabel("삭제할 앨범의 이름을 선택하세요");
		newAlbum_panel.add(deleteAlbum_label);

		Vector<String> myAlbumName = new Vector<String>();
		myAlbumName = MyPanel.bringAlbumNames();

		JComboBox album_combobox = new JComboBox(myAlbumName);
		add(album_combobox);

		JButton deleteAlbumMake_button = new JButton("삭제");
		deleteAlbumMake_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedAlbum = album_combobox.getSelectedItem().toString();
				MyPanel.deleteMyAlbum(selectedAlbum);
				setVisible(false);
			}
		});
		add(deleteAlbumMake_button);

		setSize(250, 150);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
}