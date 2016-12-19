package cc.holstr.imLoad2.gui.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import cc.holstr.imLoad2.gif.GifSequenceWriter;
import cc.holstr.util.ZFileUtils;

public class MakeGifWindow {
	private JFileChooser fc; 
	
	private JFrame parent; 
	
	private BufferedImage[] imgs;
	
	public MakeGifWindow(JFrame parent, BufferedImage[] imgs) {
		this.parent = parent; 
		this.imgs = imgs;
		build();
	}
	
	public void	build() {
		saveGif(makeFileChooser(null));
	}
	
	public void saveGif(File gifDirectory) {
		try {
			ImageOutputStream ios = new FileImageOutputStream(gifDirectory);
			GifSequenceWriter gsw = new GifSequenceWriter(ios,imgs[0].getType(),1,false);
			for(BufferedImage img : imgs) {
				gsw.writeToSequence(img);
			}
			gsw.close();
			ios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File makeFileChooser(String startingDir) {
		File startFile = null;
		if(!(startingDir==null)) {
			startFile = new File(startingDir);
		}
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				String ext = ZFileUtils.getExt(f);
				if(ext!=null) {
					if(ext.equalsIgnoreCase("gif")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "GIF Image";
			}
			
		});
		if(!(startingDir==null)) {
		fc.setSelectedFile(startFile);
		}
		File file = null;
		int returnVal = fc.showSaveDialog(parent);
		if(returnVal==JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else if(returnVal==JFileChooser.CANCEL_OPTION) {
//			file = startFile;
		}
		return file;
	}
}
