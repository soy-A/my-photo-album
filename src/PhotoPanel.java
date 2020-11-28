import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

public class PhotoPanel extends JPanel {
	
	/* 선택한 이미지의 크기를 패널의 크기에 맞게 조정 */
	private BufferedImage resizing(Image selected_image) {
		
		BufferedImage resized_bufimage;
		Image resized_image;
			
		double panel_width = 760, panel_height = 500;
		double panel_ratio = panel_width / panel_height;
		double image_width = (double)selected_image.getWidth(null);
		double image_height = (double)selected_image.getHeight(null);
		double image_ratio = image_width / image_height;
		
		if (image_ratio <= panel_ratio) {
			image_height = panel_height;
			image_width = panel_height * image_ratio;
			resized_image = selected_image.getScaledInstance((int)image_width, (int)image_height, selected_image.SCALE_SMOOTH);
			resized_bufimage = new BufferedImage((int)image_width, (int)image_height, BufferedImage.TYPE_INT_RGB);
			Graphics g = resized_bufimage.getGraphics();
			g.drawImage(resized_image, 0, 0, null);
			g.dispose();
			
		} else {
			image_width = (int)panel_width;
			image_height = (int)(panel_width / image_ratio);
			resized_image = selected_image.getScaledInstance((int)image_width, (int)image_height, selected_image.SCALE_SMOOTH);
			resized_bufimage = new BufferedImage((int)image_width, (int)image_height, BufferedImage.TYPE_INT_RGB);
			Graphics g = resized_bufimage.getGraphics();
			g.drawImage(resized_image, 0, 0, null);
			g.dispose();
		}
		
		return resized_bufimage;
		
	}
	
	public PhotoPanel(Image selected_image, ArrayList<HashMap<String, Object>> photoList, int i) {
		
		setLayout(new BorderLayout(0, 0));
		
		/* 사진 위에 띄울 정보 패널 */
		JPanel info_panel = new JPanel();
		info_panel.setBackground(Color.GRAY);
		add(info_panel, BorderLayout.NORTH);
		info_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel east_panel = new JPanel();
		east_panel.setBackground(Color.GRAY);
		info_panel.add(east_panel, BorderLayout.EAST);
		
		JPanel west_panel = new JPanel();
		west_panel.setBackground(Color.GRAY);
		info_panel.add(west_panel, BorderLayout.WEST);
		
		/* 사진의 날짜 */
		JLabel date_label = new JLabel((String)photoList.get(i).get("filedatetime"));
		date_label.setHorizontalAlignment(SwingConstants.CENTER);
		info_panel.add(date_label, BorderLayout.CENTER);
		
		/* 메모 버튼을 누르면 저장된 메모를 보여주며, 메모를 할 수 있는 창을 띄운다 */
		JButton memo_button = new JButton("메모");
		memo_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		east_panel.add(memo_button);
		
		/* 현재 보고있는 사진을 삭제함 */
		JButton delete_button = new JButton("삭제");
		delete_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int delete = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(delete == JOptionPane.YES_OPTION) {
					Album.deleteFromAlbum((String)photoList.get(i).get("filefullname"), Main.albumPath);
					Key.deleteKey(photoList, i);
					JOptionPane.showMessageDialog(null, "사진이 삭제되었습니다.");
					GUI.main_panel.removeAll();
					GUI.all_panel = new AllPanel();
					GUI.main_panel.add(GUI.all_panel);
					GUI.main_panel.updateUI();
				}
			}
		});
		east_panel.add(delete_button);
		
		/* 이전 화면(모든 사진)으로 돌아감 */
		JButton back_button = new JButton("이전으로");
		back_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.main_panel.removeAll();
				GUI.all_panel = new AllPanel();
				GUI.main_panel.add(GUI.all_panel);
				GUI.main_panel.updateUI();
			}
		});
		west_panel.add(back_button);

		/* 큰 사진을 띄우는 패널 */
		JPanel big_image_panel = new JPanel();
		
		JLabel resized_label = new JLabel(new ImageIcon(resizing(selected_image)));

		big_image_panel.add(resized_label);
		
		add(big_image_panel, BorderLayout.CENTER);
	}
}
