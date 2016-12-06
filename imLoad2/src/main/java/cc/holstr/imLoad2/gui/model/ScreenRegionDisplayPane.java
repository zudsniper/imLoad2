package cc.holstr.imLoad2.gui.model;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenRegionDisplayPane extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7774204225765298821L;

	private JFrame parentFrame;
	
	private JLabel capturedLabel;
	
	private BufferedImage fullscreenDuringSave;
	private BufferedImage fullscreen;
	private BufferedImage capturedImage;
	
	private Robot rob; 
	
    final Dimension screenSize;
    
    private boolean captured;
    
	public ScreenRegionDisplayPane(JFrame parentFrame) {
		super();
		capturedLabel = new JLabel("");
		capturedLabel.setForeground(Color.white);
		add(capturedLabel);
		try {
			rob = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setParentFrame(parentFrame);
		updateFullScreen();
	}
	
	public BufferedImage capture() {
		capturedImage = getRegionOver();
		setCaptured(true);
		capturedLabel.setText("<html><span bgcolor=\"green\">CAPTURED</span></html>");
		return capturedImage;
	}
	
	public void reset() {
		updateFullScreen();
		setCaptured(false);
		capturedLabel.setText("");
	}
	
	public BufferedImage getRegionOver() {
		Point p = this.getLocationOnScreen();
		int width = this.getWidth();
		int height = this.getHeight();
		
		BufferedImage newImg = fullscreen.getSubimage((int)p.getX(), (int)p.getY(), width, height);
		return newImg;
	}
	
	public void updateFullScreenSoftly() {
		setFullscreen(rob.createScreenCapture(new Rectangle(screenSize)));
	}
	
	public void updateFullScreen() {
		parentFrame.setVisible(false);
		setFullscreen(rob.createScreenCapture(new Rectangle(screenSize)));
		parentFrame.setVisible(true);
	}
	
	public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public BufferedImage getFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(BufferedImage fullscreen) {
		this.fullscreen = fullscreen;
	}

	public BufferedImage getCapturedImage() {
		return capturedImage;
	}

	public boolean isCaptured() {
		return captured;
	}

	public void setCaptured(boolean captured) {
		this.captured = captured;
	}

	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    	if(!isCaptured()) {
	    		g.drawImage(getRegionOver(), 0, 0, null);
	    	} else {
	    		g.drawImage(capturedImage, 0, 0, null);
	    	}
	    	
	}
	
}
