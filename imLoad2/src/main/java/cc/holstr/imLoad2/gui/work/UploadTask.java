package cc.holstr.imLoad2.gui.work;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import cc.holstr.imLoad2.gui.ProgressMonitorWindow;
import cc.holstr.imLoad2.gui.model.ScreenRegionDisplayPane;
import cc.holstr.imLoad2.upload.ImgurUploader;

public class UploadTask extends SwingWorker<String, Integer>{

	private String API_KEY; 
	
	private Runnable progressThread;
	
	private ProgressMonitorWindow progressWindow; 
	private BufferedImage uploadImage;
	
	private ImgurUploader uploader; 
	
	private JButton reloadButton; 
	private ScreenRegionDisplayPane srdp; 
	private JTextField linkField;
	
	public UploadTask(String apikey, BufferedImage img,JButton reloadButton, JTextField linkField, ScreenRegionDisplayPane srdp) {
		API_KEY = apikey;
		this.reloadButton = reloadButton;
		this.srdp = srdp;
		uploadImage = img;
		this.linkField = linkField;
		uploader = new ImgurUploader(API_KEY);
	}
	
	@Override
	protected String doInBackground() throws Exception {
		reloadButton.setEnabled(false);
		linkField.setEditable(false);
		progressWindow = new ProgressMonitorWindow("Uploading to imgur...",0);
		progressWindow.setAlwaysOnTop(true);
		String link = uploader.upload(uploadImage, true);
		progressWindow.setProgress(100);
		progressWindow.dispose();
		linkField.setEditable(true);
		linkField.setText(link);
		reloadButton.setEnabled(true);
		srdp.updateFullScreen();
		return null;
	}
	
	protected void process(List<Integer> prog) {
		
	}
	
}
