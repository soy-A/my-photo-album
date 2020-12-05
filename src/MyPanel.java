import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

	/* 앨범 이름 파일에 새로운 앨범 이름 추가 */
	public static void addMyAlbum(String newAlbumName) {

		File newAlbum_file = new File(Main.albumPath + File.separator + "myAlbum");

		Vector<String> brought_name = new Vector<String>();
		ObjectOutputStream oos = null;

		try {
			if (newAlbum_file.exists()) {
				brought_name = bringAlbumNames();

				if (!brought_name.contains(newAlbumName)) {
					brought_name.add(newAlbumName);
					oos = new ObjectOutputStream(new FileOutputStream(newAlbum_file));
					oos.writeObject(brought_name);
					JOptionPane.showMessageDialog(null, "앨범이 추가되었습니다.");

				} else {
					JOptionPane.showMessageDialog(null, "이미 존재하는 앨범입니다.");	// 이미 존재할 경우 추가하지 않는다
				}

			} else {
				brought_name.add(newAlbumName);
				oos = new ObjectOutputStream(new FileOutputStream(newAlbum_file));
				oos.writeObject(brought_name);
			}

			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* 앨범 이름 파일 가져오기 */
	public static Vector<String> bringAlbumNames() {

		File newAlbum_file = new File(Main.albumPath + File.separator + "myAlbum");
		Vector<String> brought_name = new Vector<String>();
		ObjectInputStream ois = null;

		try {
			if (newAlbum_file.exists()) {
				ois = new ObjectInputStream(new FileInputStream(newAlbum_file));
				brought_name = (Vector<String>) ois.readObject();
				ois.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return brought_name;
	}

	/* 나의 앨범과 앨범에 들어있는 사진에 대한 정보 삭제 */
	public static void deleteMyAlbum(String albumName) {

		File newAlbum_file = new File(Main.albumPath + File.separator + "myAlbum");
		File temp = new File(Main.albumPath + File.separator + albumName);
		ObjectOutputStream oos = null;
		Vector<String> albumNames = bringAlbumNames();
		albumNames.remove(albumName);

		try {
			oos = new ObjectOutputStream(new FileOutputStream(newAlbum_file));
			oos.writeObject(albumNames);
			oos.close();

			if (temp.exists()) {
				temp.delete();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "앨범이 삭제되었습니다.");
	}

	public MyPanel() {

		setLayout(new BorderLayout(0, 0));

		/* <나의 앨범 - 작업창> 패널 */
		JPanel work_panel = new JPanel();
		work_panel.setBackground(Color.LIGHT_GRAY);
		work_panel.setLayout(new BorderLayout(0, 0));
		add(work_panel, BorderLayout.NORTH);

		JPanel east_panel = new JPanel();
		east_panel.setBackground(Color.LIGHT_GRAY);
		work_panel.add(east_panel, BorderLayout.EAST);

		JPanel west_panel = new JPanel();
		west_panel.setBackground(Color.LIGHT_GRAY);
		work_panel.add(west_panel, BorderLayout.WEST);

		/* 새 창을 띄워 앨범을 추가하거나 삭제할 수 있다 */
		JButton newAlbum_button = new JButton("앨범 추가/삭제");
		newAlbum_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewAlbumWindow();
			}
		});
		west_panel.add(newAlbum_button);

		/* 새 창을 띄워 사진을 특정 앨범에 추가할 수 있다 */
		JButton addPhoto_button = new JButton("사진 추가");
		addPhoto_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddPhotoPanel();
			}
		});
		west_panel.add(addPhoto_button);
		
		/* 새 창을 띄워 사진을 특정 앨범에서 삭제할 수 있다 */
		JButton deletePhoto_button = new JButton("사진 삭제");
		deletePhoto_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DeletePhotoPanel();
			}
		});
		west_panel.add(deletePhoto_button);

		JLabel album_label = new JLabel("앨범을 선택하세요");
		east_panel.add(album_label);

		Vector<String> myAlbumName = new Vector<String>();
		myAlbumName = bringAlbumNames();

		JComboBox album_combobox = new JComboBox(myAlbumName);
		east_panel.add(album_combobox);

		JPanel center_panel = new JPanel();
		center_panel.setLayout(new BorderLayout(0, 0));
		add(center_panel, BorderLayout.CENTER);

		JButton albumSelect_button = new JButton("확인");
		albumSelect_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selectedAlbum = album_combobox.getSelectedItem().toString();
				
				if (Key.bringKeys(selectedAlbum).size() > 0) {	// 선택한 앨범에 사진이 있다면 패널을 띄운다
					center_panel.removeAll();
					AllPanel myAlbum_panel = new AllPanel(Key.bringKeys(selectedAlbum));
					center_panel.add(myAlbum_panel, BorderLayout.CENTER);
					updateUI();
					
				} else {
					JOptionPane.showMessageDialog(null, "앨범에 사진이 없습니다.\n\"추가\"버튼을 눌러 사진을 추가해주세요.");
				}
			}
		});
		east_panel.add(albumSelect_button);
	}

}
