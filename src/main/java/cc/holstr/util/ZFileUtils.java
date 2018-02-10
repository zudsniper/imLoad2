package cc.holstr.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZFileUtils {

	public static boolean isDirectoryValid(String path) {
		return (path.contains(System.getProperty("file.separator")));
	}
	
	public static File getFirstExistingParent(File f) {
		Path p = Paths.get(f.getAbsolutePath());
		if(p.toFile().exists()) {
			return p.toFile();
		} else {
			return getFirstExistingParent(p.getParent().toFile());
		}
	}
	
	public static String getExt(File f) {
		String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
	}
	
}
