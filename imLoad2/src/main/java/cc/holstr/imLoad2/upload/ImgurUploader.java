package cc.holstr.imLoad2.upload;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import cc.holstr.imLoad.http.ImgurHttpHelper;
import cc.holstr.imLoad.json.JsonGenerator;

public class ImgurUploader {
	
	private final static String UPLOAD_URL = "https://api.imgur.com/3/upload.json"; 
	
	public String API_KEY;
	
	private ImgurHttpHelper helper;
	
	public ImgurUploader(String api_key) {
			API_KEY = api_key;
			helper = new ImgurHttpHelper(API_KEY);
	}
	
	public String upload(BufferedImage image, boolean anonymous) throws IOException{
		String photoUrl = null;
		if(anonymous) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image,"png", baos);
		Base64.encodeBase64String(baos.toByteArray()).toString();
		String json = JsonGenerator.getUploadImageJson(Base64.encodeBase64String(baos.toByteArray()).toString()).toString();
		InputStream stream = helper.post(UPLOAD_URL, json);
		if(stream==null) {
			return "FAILED TO UPLOAD";
		}
		String temp = IOUtils.toString(stream, "UTF-8");
		System.out.println(temp);
		temp = temp.substring(temp.indexOf("\"id\"")+6);
		String id = temp.substring(0, temp.indexOf("\",\""));
		photoUrl = "http://i.imgur.com/"+id;
		}
		//clipboard.setContents(new StringSelection(photoUrl),null);
		
		return photoUrl;
	}
	
}
