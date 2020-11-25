import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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
		JPanel sideBar = new JPanel();
		sideBar.setBackground(Color.DARK_GRAY);
		contentPane.add(sideBar, BorderLayout.WEST);
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));	// 버튼을 정렬하기 위한 레이아웃
		
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
		sideBar.add(path_button);
		sideBar.add(all_button);
		sideBar.add(favourite_button);
		sideBar.add(tag_button);
		sideBar.add(album_button);
		
		/* 상단 검색 영역 패널 */
		JPanel searchBar = new JPanel();
		searchBar.setBackground(Color.DARK_GRAY);
		contentPane.add(searchBar, BorderLayout.NORTH);
		
		/* 검색 유형 선택 */
		JComboBox searchType = new JComboBox();
		searchBar.add(searchType);
		
		searchField = new JTextField();
		searchField.setHorizontalAlignment(SwingConstants.CENTER);
		searchField.setColumns(30);
		searchBar.add(searchField);
		
		JButton search_button = new JButton("검색");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchBar.add(search_button);
	}
}
