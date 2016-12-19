package cc.holstr.imLoad2.gui.work;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.SwingWorker;

public class SaveTask extends SwingWorker<Void, Integer>{
	
	private BufferedImage img; 
	private File outputFile; 
	
	public SaveTask(BufferedImage img, File outputFile) {
		this.img = img;
		this.outputFile = outputFile;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		return null;
	}
	
}
