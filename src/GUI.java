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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class PathPanel extends JPanel{

	private int flag = 0;
	private JTextField path_text;
	
	/* 아래 두개는 스크롤 문제를 해결하지 못할 시 다시 코드 안쪽 (final)로 되돌려놓을 것 */
	private int height;
	private JScrollPane scrollPane;
	
	/* <사진 가져오기> 패널 */
	public PathPanel() {

		setLayout(new BorderLayout(0, 0));
		
		/* <사진 가져오기 - 경로 입력> 패널 */
		JPanel pathget_panel = new JPanel();
		add(pathget_panel, BorderLayout.NORTH);
		
		JLabel path_label = new JLabel("사진을 불러올 폴더의 경로를 입력해주세요");
		pathget_panel.add(path_label);
		
		path_text = new JTextField();
		pathget_panel.add(path_text);
		path_text.setColumns(30);
		
		JButton pathget_button = new JButton("불러오기");
		pathget_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String userPath = path_text.getText();	// 입력받은 경로
				
				/* 사진 리스트 생성(파일명, 년, 월, 일 포함) */
				ArrayList<HashMap<String, Object>> photoList = new ArrayList<HashMap<String, Object>>();
				photoList = GetPhoto.getPhotoList(userPath);
				int photoListSize = photoList.size();
				
				/* <사진 가져오기 - 메인> 패널 */
				JPanel image_panel = new JPanel();
				height = image_panel.getHeight();
//				Dimension panel_size = new Dimension(400, height);
				Dimension panel_size = new Dimension(400, 2000);	// 임시로 2000의 값을 주었다(스크롤이 생성되지 않는 문제)
				image_panel.setPreferredSize(panel_size);
				image_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				scrollPane = new JScrollPane(image_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				add(scrollPane, BorderLayout.CENTER);
				scrollPane.setViewportView(image_panel);
				
				/* 체크박스(사진 선택) 생성, 선택한 사진들을 앨범 폴더에 추가 */
				JCheckBox photo_check[] = new JCheckBox[photoList.size()];
				ImageIcon photo_icon[] = new ImageIcon[photoList.size()];
				Dimension icon_size = new Dimension(145, 145);
				int selected_index[] = new int[photoListSize];
				
				class PhotoItem implements ItemListener{

					@Override
					public void itemStateChanged(ItemEvent e) {
						for(int i = 0; i < photoListSize; i++) {
							if(e.getItem() == photo_check[i]) {
								photo_check[i].setOpaque(true);		// 선택 표시
								selected_index[flag] = i;
								flag++;
							}
						}
					}
				}
				
				for(int i = 0; i < photoList.size(); i++) {
					photo_check[i] = new JCheckBox();
					photo_check[i].addItemListener(new PhotoItem());
					photo_check[i].setBackground(Color.DARK_GRAY);
					photo_icon[i] = new ImageIcon(userPath + File.separator +photoList.get(i).get("filefullname"));
					Image img = photo_icon[i].getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
					photo_icon[i] = new ImageIcon(img);
					photo_check[i].setIcon(photo_icon[i]);
					photo_check[i].setPreferredSize(icon_size);
					image_panel.add(photo_check[i]);
				}
				/* 이 위까지는 패널의 height가 0임, 그러나 한번 렌더링되면 height가 제대로 들어옴. 따라서 아래의 componentlistener를 잘 활용해 이 값을 가질 수 있도록 시도해볼 것 
				 * https://stackoverflow.com/questions/8336262/why-cant-i-access-my-panels-getwidth-and-getheight-functions 를 참고 */
//				image_panel.addComponentListener(new ComponentAdapter() {
//					public void componentResized(ComponentEvent e) {
//						height = image_panel.getHeight();
//						Dimension panel_size = new Dimension(400, height);
//						image_panel.setPreferredSize(panel_size);
//						scrollPane = new JScrollPane(image_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//						add(scrollPane, BorderLayout.CENTER);
//						scrollPane.setViewportView(image_panel);
//						image_panel.requestFocusInWindow();
//					}
//				});
				
				/* 경로 입력 패널 숨기기 */
				pathget_panel.setVisible(false);
				
				/* <사진 가져오기 - 경로 입력 - 추가> 패널 */
				JPanel add_panel = new JPanel();
				add(add_panel, BorderLayout.NORTH);
				
				JLabel add_label = new JLabel("앨범에 추가할 사진을 선택한 후 \"추가\" 버튼을 눌러주세요");
				add_panel.add(add_label);
				
				JButton add_button = new JButton("추가");
				add_button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						File selected_photo[] = new File[selected_index.length];
						for(int i = 0; i < selected_index.length; i++) {
							selected_photo[i] = GetPhoto.fileList[selected_index[i]];
						}
						Album.addToAlbum(selected_photo, Main.albumPath);
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

public class GUI extends JFrame {
	
	private JPanel contentPane;
	private JTextField searchField;

	public GUI() {
		setSize(900, 600);
		setTitle("My Photo Album");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/* 사이드바 패널 */
		JPanel sidebar_panel = new JPanel();
		sidebar_panel.setBackground(Color.DARK_GRAY);
		contentPane.add(sidebar_panel, BorderLayout.WEST);
		sidebar_panel.setLayout(new BoxLayout(sidebar_panel, BoxLayout.Y_AXIS));	// 버튼을 정렬하기 위한 레이아웃
		
		/* 사이드바 버튼 설정 */
		JButton path_button = new JButton("사진 가져오기");
		path_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		JButton all_button = new JButton("모든 사진");
		JButton favourite_button = new JButton("좋아하는 사진");
		JButton tag_button = new JButton("태그");
		JButton album_button = new JButton("나의 앨범");
		
		/* 사이드바에 버튼 추가 */
		sidebar_panel.add(path_button);
		sidebar_panel.add(all_button);
		sidebar_panel.add(favourite_button);
		sidebar_panel.add(tag_button);
		sidebar_panel.add(album_button);
		
		/* 상단 검색 영역 패널 */
		JPanel searchbar_panel = new JPanel();
		searchbar_panel.setBackground(Color.DARK_GRAY);
		contentPane.add(searchbar_panel, BorderLayout.NORTH);
		
		/* 검색 유형 선택 */
		String serchType[] = {"파일명", "확장자", "날짜"};
		JComboBox search_combobox = new JComboBox(serchType);
		searchbar_panel.add(search_combobox);
		
		/* 상단 검색 영역 */
		searchField = new JTextField();
		searchField.setHorizontalAlignment(SwingConstants.CENTER);
		searchField.setColumns(30);
		searchbar_panel.add(searchField);
		
		/* 상단 검색 버튼 */
		JButton search_button = new JButton("검색");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchbar_panel.add(search_button);
		
		add(new PathPanel());
		
	}
}