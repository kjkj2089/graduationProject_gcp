package what;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Myproject {

	private JFrame frame;
	private JTextField textField_2;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Myproject window = new Myproject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Myproject() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(UIManager.getColor("ColorChooser.background"));
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblMyvideo = new JLabel("My Video");
		lblMyvideo.setForeground(Color.BLACK);
		lblMyvideo.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblMyvideo.setBounds(230, 42, 106, 41);
		frame.getContentPane().add(lblMyvideo);
		
		JLabel lblText = new JLabel("Text");
		lblText.setForeground(Color.BLACK);
		lblText.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblText.setBounds(946, 15, 106, 41);
		frame.getContentPane().add(lblText);
		
		JLabel lblLanguage = new JLabel("Language :");
		lblLanguage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLanguage.setBounds(1076, 31, 70, 15);
		frame.getContentPane().add(lblLanguage);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"KOR", "ENG", "JPN", "CHN"}));
		comboBox.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
			}
		});
		comboBox.setToolTipText("");
		comboBox.setBounds(1144, 27, 58, 23);
		frame.getContentPane().add(comboBox);
		
		JButton btnVideoLoad = new JButton("Video load");
		btnVideoLoad.setBounds(375, 54, 97, 23);
		frame.getContentPane().add(btnVideoLoad);
		
		JLabel lblTranslatedText = new JLabel("Translated Text");
		lblTranslatedText.setForeground(Color.BLACK);
		lblTranslatedText.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblTranslatedText.setBounds(878, 257, 190, 15);
		frame.getContentPane().add(lblTranslatedText);
		
		JLabel lblLanguage_1 = new JLabel("Language :");
		lblLanguage_1.setBounds(1076, 260, 70, 15);
		frame.getContentPane().add(lblLanguage_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"KOR", "ENG", "JPN", "CHN"}));
		comboBox_1.setBounds(1144, 256, 58, 23);
		frame.getContentPane().add(comboBox_1);
		
		JButton button = new JButton("\uB2E8\uC5B4\uCD94\uAC00");
		button.setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		button.setFont(new Font("경기천년제목 Medium", Font.PLAIN, 15));
		button.setForeground(Color.BLACK);
		button.setBounds(741, 511, 97, 23);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("\uAC80 \uC0C9");
		button_1.setForeground(Color.BLACK);
		button_1.setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		button_1.setFont(new Font("경기천년제목 Medium", Font.PLAIN, 15));
		button_1.setBounds(741, 555, 97, 23);
		frame.getContentPane().add(button_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(855, 511, 336, 143);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton button_2 = new JButton("\uC774 \uB3D9");
		button_2.setForeground(Color.BLACK);
		button_2.setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		button_2.setFont(new Font("경기천년제목 Medium", Font.PLAIN, 15));
		button_2.setBounds(741, 600, 97, 23);
		frame.getContentPane().add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(741, 86, 491, 126);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.WHITE);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(741, 313, 491, 135);
		frame.getContentPane().add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JButton btnTextLoad = new JButton("Text Load");
		btnTextLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File aFile = new File("C:\\Users\\kjkj2\\OneDrive\\바탕 화면\\java-docs-samples-master\\java-docs-samples-master\\speech\\cloud-client\\Google_Gnome.txt"); // 여기에 원하는 텍스트파일 위치 넣으면 돼!
					FileReader fileReader = new FileReader(aFile);
					BufferedReader reader = new BufferedReader(fileReader);
					
					String line = null;
					while((line = reader.readLine()) != null) {
						System.out.println(line);
						textArea.append(line + "\n");
					}
					reader.close();
					
					}catch(Exception ex) {
					}
				}
				
		});
		btnTextLoad.setBackground(UIManager.getColor("ComboBox.selectionBackground"));
		btnTextLoad.setForeground(Color.BLACK);
		btnTextLoad.setFont(new Font("경기천년제목 Medium", Font.PLAIN, 15));
		btnTextLoad.setBounds(741, 27, 113, 23);
		frame.getContentPane().add(btnTextLoad);
		
	
		
	}
}