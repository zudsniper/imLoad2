package cc.holstr.imLoad2.gui.model;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.imgscalr.Scalr;

import cc.holstr.util.ZFileUtils;

public class ScaleImageWindow extends JFrame implements ActionListener{
	
	private JFileChooser fc;
	
	private JPanel layout;
	private JPanel scalePanel;
	private JPanel byPanel;
	private JTextField scaleWidthTextField;
	private JTextField scaleHeightTextField;
	private JLabel scaleByLabel;
	private JButton selectFileButton;
	
	private BufferedImage image;
	
	public ScaleImageWindow(BufferedImage image) {
		this.image = image;
		build();
	}
	
	public void build() {
		fc = new JFileChooser();
		layout = new JPanel(new BorderLayout());
		byPanel = new JPanel();
		scalePanel = new JPanel();
		scalePanel.setLayout(new GridLayout(1,3));
		
		scaleWidthTextField = new JTextField();
		scaleHeightTextField = new JTextField();
		
		scaleByLabel = new JLabel("By");
		
		selectFileButton = new JButton("Select Save Directory");
		
		layout.add(scalePanel, BorderLayout.CENTER);
		layout.add(selectFileButton, BorderLayout.SOUTH);
		
		byPanel.add(scaleByLabel);
		
		scalePanel.add(scaleWidthTextField);
		scalePanel.add(byPanel);
		scalePanel.add(scaleHeightTextField);
		
		add(layout);
		
		selectFileButton.addActionListener(this);
		
		scaleWidthTextField.setText(image.getWidth()+"");
		scaleHeightTextField.setText(image.getHeight()+"");
		
		setLocationRelativeTo(null);
		setSize(200,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int width = image.getWidth(); 
		int height = image.getHeight(); 
		if(e.getSource()==selectFileButton) {
			try {
			width = Integer.parseInt(scaleWidthTextField.getText()); 
			height = Integer.parseInt(scaleHeightTextField.getText());
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this,"Please enter valid dimensions.");
			}
			File output = makeFileChooser(System.getProperty("user.home")+System.getProperty("file.separator")+"Desktop");
			try {
				
				ImageIO.write(Scalr.resize(image,width,height,Scalr.OP_ANTIALIAS),"png",output);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dispose();
		}
		
	}
	
	public File makeFileChooser(String startingDir) {
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File startFile = new File(startingDir);
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				String ext = ZFileUtils.getExt(f);
				if(ext!=null) {
					if(ext.equalsIgnoreCase("png")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "PNG Image";
			}
			
		});
		fc.setSelectedFile(startFile);
		File file = null;
		int returnVal = fc.showSaveDialog(this);
		if(returnVal==JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else if(returnVal==JFileChooser.CANCEL_OPTION) {
			file = startFile;
		}
		return file;
	}
}
