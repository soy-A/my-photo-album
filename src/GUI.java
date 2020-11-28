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
	public static JPanel main_panel = new JPanel();
	public static AllPanel all_panel = new AllPanel();
	public static PathPanel path_panel = new PathPanel();

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
				all_panel = new AllPanel();
				main_panel.add(all_panel);
				main_panel.updateUI();
			}
		});
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

	}
}