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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cc.holstr.imLoad2.gui.Window;
import cc.holstr.imLoad2.properties.Unpacker;

public class ScreenRegionDisplayPane extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7774204225765298821L;

	private boolean giffing = false;
	
	private Window parentFrame;
	
	private JLabel capturedLabel;
	
	private BufferedImage fullscreen;
	private BufferedImage capturedImage;
	private List<BufferedImage> imgs; 
	
	private Robot rob; 
	
    final Dimension screenSize;
    
    public long sleepDuration = 175;

	private boolean captured;
    
	public ScreenRegionDisplayPane(Window parentFrame) {
		super();
		if(Unpacker.os.contains("Mac")) {
			setSleepDuration(25);
		} else if(Unpacker.os.contains("Windows")) {
			setSleepDuration(175);
		} else {
			setSleepDuration(100);
		}
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
	
	public void startGif() {
		imgs = new ArrayList<BufferedImage>();
		giffing = true;
		
		
	}
	
	public void stopGif() {
		giffing = false;
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
	
	public void updateFullScreenLocationally() {
		Point location = parentFrame.getLocationOnScreen();
		parentFrame.setLocation(Integer.MAX_VALUE, Integer.MAX_VALUE);
		updateFullScreenSoftly();
		parentFrame.setLocation(location);
	}
	
	public void updateFullScreen() {
//		Dimension beforeSize = parentFrame.getSize();
//		String linkText = parentFrame.getLinkText();
//		parentFrame.dispose();
		parentFrame.setVisible(false);
		try {
			Thread.sleep(getSleepDuration());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setFullscreen(rob.createScreenCapture(new Rectangle(screenSize)));
		parentFrame.setVisible(true);
//		parentFrame.pack();
//		parentFrame.setSize(beforeSize);
//		parentFrame.setLinkText(linkText);
	}
	
	public Window getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(Window parentFrame) {
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
	
	public long getSleepDuration() {
		return sleepDuration;
	}

	public void setSleepDuration(long sleepDuration) {
		this.sleepDuration = sleepDuration;
	}
	
	public boolean isGiffing() {
		return giffing;
	}
	
}
