import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JToolBar;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.JMenuItem;
import javax.swing.JLabel;


public class MainWindow extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/lorrygreen.png")));
		setTitle("ITS Test App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmQuit);
		
		JMenu mnNewMenu_1 = new JMenu("Tests");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmPhotocell = new JMenuItem("System Error");
		mnNewMenu_1.add(mntmPhotocell);
		
		JMenuItem mntmMessageActivation = new JMenuItem("Message Activation");
		mnNewMenu_1.add(mntmMessageActivation);
		
		JMenuItem mntmPhotocells = new JMenuItem("Photocells");
		mnNewMenu_1.add(mntmPhotocells);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmPowerMonitoring = new JMenuItem("Power Monitoring");
		mnTools.add(mntmPowerMonitoring);
		
		JMenuItem mntmFanMonitoring = new JMenuItem("Fan Monitoring");
		mnTools.add(mntmFanMonitoring);
		
		JMenuItem mntmBrightnessMonitoring = new JMenuItem("Brightness Monitoring");
		mnTools.add(mntmBrightnessMonitoring);
		
		JMenuItem mntmBitmapCreater = new JMenuItem("Bitmap Creator");
		mnTools.add(mntmBitmapCreater);
		
		JMenu mnNewMenu_2 = new JMenu("Connection");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmIpAddressport = new JMenuItem("IP Address/Port");
		mntmIpAddressport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							    
				contentPane.setVisible(false);
				
				
			}
		});
		mnNewMenu_2.add(mntmIpAddressport);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		contentPane = new JPanel() {  
            public void paintComponent(Graphics g) {  
                 Image img = Toolkit.getDefaultToolkit().getImage(  
                           MainWindow.class.getResource("/images/background1.jpg"));  
                 g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
            }  
       };  
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
	
	}
	
}