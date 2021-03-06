import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

	/* 코드 어디서든지 화면전환 할 수 있도록 public static으로 선언 */
	public static JPanel main_panel = new JPanel();
	public static AllPanel all_panel = new AllPanel(Key.bringKeys("Key"));
	public static PathPanel path_panel = new PathPanel();
	public static MyPanel my_panel = new MyPanel();

	public GUI() {

		/* 기본 프레임 설정 */
		setSize(900, 600);
		setTitle("My Photo Album");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));	// 약간의 가장자리 여백 줌
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/* 사이드바 패널 */
		JPanel sidebar_panel = new JPanel();
		sidebar_panel.setBackground(Color.DARK_GRAY);
		contentPane.add(sidebar_panel, BorderLayout.WEST);
		sidebar_panel.setLayout(new BoxLayout(sidebar_panel, BoxLayout.Y_AXIS)); // 버튼을 정렬하기 위한 레이아웃

		/* 메인 패널 설정 */
		contentPane.add(main_panel, BorderLayout.CENTER);
		main_panel.setLayout(new BorderLayout(0, 0));

		/* 사이드바 버튼 액션 설정 */
		JButton path_button = new JButton("사진 가져오기");
		path_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_panel.removeAll();
				path_panel = new PathPanel();
				main_panel.add(path_panel);
				main_panel.updateUI();
			}
		});
		JButton all_button = new JButton("모든 사진");
		all_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_panel.removeAll();
				all_panel = new AllPanel(Key.bringKeys("Key"));
				main_panel.add(all_panel);
				main_panel.updateUI();
			}
		});
		JButton favourite_button = new JButton("좋아하는 사진");
		favourite_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_panel.removeAll();
				all_panel = new AllPanel(Key.bringKeys("Like"));
				main_panel.add(all_panel);
				main_panel.updateUI();
			}
		});
		JButton album_button = new JButton("나의 앨범");
		album_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_panel.removeAll();
				my_panel = new MyPanel();
				main_panel.add(my_panel);
				main_panel.updateUI();
			}
		});
		JButton insta_button = new JButton("인스타그램");
		insta_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.instagram.com"));	// 클릭시 브라우저에서 인스타그램 페이지를 띄운다.
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e2) {
					e2.printStackTrace();
				}
			}
		});

		/* 사이드바에 버튼 추가 */
		sidebar_panel.add(path_button);
		sidebar_panel.add(all_button);
		sidebar_panel.add(favourite_button);
		sidebar_panel.add(album_button);
		sidebar_panel.add(insta_button);

		/* 상단 검색 영역 패널 */
		JPanel searchbar_panel = new JPanel();
		searchbar_panel.setBackground(Color.DARK_GRAY);
		contentPane.add(searchbar_panel, BorderLayout.NORTH);

		/* 검색 유형 선택 */
		String serchType[] = { "파일명", "날짜", "메모" };
		JComboBox search_combobox = new JComboBox(serchType);

		/* 상단 검색 영역 */
		JTextField searchField = new JTextField();
		searchField.setHorizontalAlignment(SwingConstants.CENTER);
		searchField.setColumns(30);

		/* 상단 검색 버튼 */
		JButton search_button = new JButton("검색");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String type = search_combobox.getSelectedItem().toString();
				String searchTerm = searchField.getText();
				
				main_panel.removeAll();
				AllPanel search_panel;
				ArrayList<HashMap<String, Object>> foundPhotoList = Search.getSearchedList(searchTerm, type);
				search_panel = new AllPanel(foundPhotoList);
				main_panel.add(search_panel);
				main_panel.updateUI();

			}
		});
		
		searchbar_panel.add(search_combobox);
		searchbar_panel.add(searchField);
		searchbar_panel.add(search_button);

	}
}