package cc.holstr.imLoad2.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressMonitorWindow extends JFrame{

	private JPanel layout; 
	
	private JProgressBar jpb; 

	private JLabel text;
	
	public ProgressMonitorWindow(String text, int value) {
		super();
		build(text, value);
	}
	
	public void build(String shownText, int value) {
		layout = new JPanel();
		jpb = new JProgressBar(value);
		text = new JLabel(shownText);
		
		layout.setLayout(new GridLayout(2,1));
		
		add(layout);
		layout.add(jpb);
		layout.add(text);
		
		jpb.setValue(0);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(250,80));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void setProgress(int n) {
		jpb.setValue(n);
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public JLabel getTextLabel() {
		return text;
	}

	public JProgressBar getProgressBar() {
		return jpb;
	}
	
}
