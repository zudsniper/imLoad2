package cc.holstr.imLoad2.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cc.holstr.imLoad2.gui.model.ScaleImageWindow;
import cc.holstr.imLoad2.gui.model.ScreenRegionDisplayPane;
import cc.holstr.imLoad2.gui.work.UploadTask;

public class Window extends JFrame implements ComponentListener, FocusListener{

	public static boolean debug = false;
	
	private final static String API_KEY = "NOT_FOR_GITHUB";
	
	private String loadedKey;
	
	private ScreenRegionDisplayPane region; 
	private JPanel buttonBar; 
	private JPanel bottomBar;
	
	private JButton capture; 
	private JButton upload;
	private JButton reload;
	
	private JTextField linkField; 
	
	private JMenuBar bar; 
	
	private JMenu fileMenu;
	private JMenu uploadMenu;
	private JMenu saveMenu;
	
	private JMenuItem showHistoryMenuItem;
	private JMenuItem customAPIKeyMenuItem;
	private JMenuItem quitMenuItem; 
	
	private JMenuItem uploadToImgurMenuItem;
	
	private JMenuItem saveToFileMenuItem;
	
	public Window() {
		super();
		build();
	}
	
	public Window build() {
		setLayout(new BorderLayout());
		
		bar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		uploadMenu = new JMenu("Upload");
		saveMenu = new JMenu("Save");
		
		showHistoryMenuItem = new JMenuItem("Show Upload History...");
		customAPIKeyMenuItem = new JMenuItem("Use Custom API Key...");
		quitMenuItem = new JMenuItem("Quit");
		
		uploadToImgurMenuItem = new JMenuItem("Upload to Imgur...");
		
		saveToFileMenuItem = new JMenuItem("Save to File...");
		
		capture = new JButton("Capture");
		upload = new JButton("Upload");
		reload = new JButton("Reload");
		
		linkField = new JTextField();
		
		upload.setEnabled(false);
		uploadToImgurMenuItem.setEnabled(false);
		saveToFileMenuItem.setEnabled(false);
		linkField.setEditable(false);
		
		//menu layout
		bar.add(fileMenu);
		bar.add(uploadMenu);
		bar.add(saveMenu);
		
		fileMenu.add(showHistoryMenuItem);
		fileMenu.add(customAPIKeyMenuItem);
		fileMenu.add(quitMenuItem);
		
		uploadMenu.add(uploadToImgurMenuItem);
		
		saveMenu.add(saveToFileMenuItem);
		
		//main layout
		region = new ScreenRegionDisplayPane(this);
		
		if(Window.debug) {
			region.setSleepDuration(Long.parseLong(JOptionPane.showInputDialog(this,"Enter sleep long (millis)")));
		}
		
		bottomBar = new JPanel(new BorderLayout());
		buttonBar = new JPanel();
		buttonBar.setLayout(new GridLayout(1,3));
		
		//bottom bar layout
		bottomBar.add(buttonBar,BorderLayout.CENTER);
		bottomBar.add(linkField, BorderLayout.SOUTH);
		
		//button bar layout
		buttonBar.add(capture);
		buttonBar.add(reload);
		buttonBar.add(upload);
		
		//window layout
		add(region,BorderLayout.CENTER);
		add(bottomBar, BorderLayout.SOUTH);
		
		//menu action handling
		
		customAPIKeyMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadedKey = JOptionPane.showInputDialog(this, "Enter API key. (Enter \"reset\" to set to default.)");
				
			}
		});
		
		quitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		});
		
		uploadToImgurMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				upload();
				
			}
			
		});
		
		saveToFileMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
				
			}
		});
		
		//action handling
		capture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				region.capture();
				capture.setEnabled(false);
				upload.setEnabled(true);
				uploadToImgurMenuItem.setEnabled(true);
				saveToFileMenuItem.setEnabled(true);
				
			}
		});
		
		reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				region.reset();
				capture.setEnabled(true);
				upload.setEnabled(false);
				uploadToImgurMenuItem.setEnabled(false);
				saveToFileMenuItem.setEnabled(false);
			}
		});
		
		upload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				upload();
				
			}
		});
		
		//housekeeping
		addComponentListener(this);
		addFocusListener(this);
		
		setTitle("imLoad2");
		setJMenuBar(bar);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(new Dimension(400,250));
		setMinimumSize(new Dimension(200,125));
		
		return this;
	}

	public void upload() {
		String key; 
		if(loadedKey==null) {
			key = API_KEY;
		} else if(loadedKey.equals("reset")) { 
			key = API_KEY;
		} else {
			key = loadedKey;
		}
		new UploadTask(key, region.getCapturedImage(),reload,linkField,region).execute();
		region.updateFullScreen();
		
	}
	
	public void save() {
		new ScaleImageWindow(region.getCapturedImage());
	}
	
	public String getLinkText() {
		return linkField.getText();
	}
	
	public void setLinkText(String text) {
		linkField.setText(text);
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		region.repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		region.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		region.repaint();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		region.repaint();
	}

	@Override
	public void focusGained(FocusEvent e) {
//		Point p = getLocation();
//		setLocation(Integer.MAX_VALUE,Integer.MAX_VALUE);
//		region.updateFullScreenSoftly();
//		setLocation(p);
		
	}

	@Override
	public void focusLost(FocusEvent e) {
//		region.updateFullScreenSoftly();
	}
	
}
