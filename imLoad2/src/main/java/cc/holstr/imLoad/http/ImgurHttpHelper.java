package cc.holstr.imLoad.http;

import java.io.InputStream;

import cc.holstr.http.HttpHelper;

public class ImgurHttpHelper{
	
	private String clientID;
	
	public ImgurHttpHelper(String clientID) {
		super();
		this.clientID = clientID;
	}

	public InputStream get(String url) {
		try {
			return HttpHelper.get(url, "Client-ID "+clientID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InputStream post(String url, String jsonString) {
		try {
			return HttpHelper.post(url,jsonString,"Client-ID "+clientID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InputStream put(String url, String jsonString) {
		try {
			return HttpHelper.put(url,jsonString, "Client-ID "+clientID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
}
