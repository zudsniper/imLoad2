package cc.holstr.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpHelper {
	public static InputStream get(String url, String auth) throws Exception{
		//http GET 
		System.out.println("HTTPHELPER : GET to " + url);
		boolean failed = false;
		  String charset = "UTF-8"; 
		  URLConnection connection = new URL(url).openConnection();
		  connection.setRequestProperty("accept-charset", charset);
		  connection.setRequestProperty("content-length", "0");
		  connection.setRequestProperty("authorization",auth);
		  InputStream response = connection.getInputStream();
		  return response;
	}
	
	public static InputStream post(String url, String jsonString, String auth) throws Exception{
		//http POST
		System.out.println("HTTPHELPER : POST to " + url);
		  boolean failed = false;
		  String charset = "UTF-8"; 
		  URLConnection connection = new URL(url).openConnection();
		  connection.setDoOutput(true); // Triggers POST.
		  connection.setRequestProperty("accept-charset", charset);
		  connection.setRequestProperty("content-type", "application/json;charset=" + charset);
		  connection.setRequestProperty("content-length", ""+jsonString.length());
		  connection.setRequestProperty("authorization",auth);
		  try (OutputStream output = connection.getOutputStream()) {
		    output.write(jsonString.getBytes(charset));
		  } catch(Exception e) {
			  failed = true;
			  e.printStackTrace();
		  }

		  InputStream response = connection.getInputStream();
		  return response;
		}
	
	public static InputStream put(String url, String jsonString, String auth) throws Exception{
		//http PUT
		System.out.println("GOOGLE HTTP HELPER : PUT to " + url);
		  boolean failed = false;
		  String charset = "UTF-8"; 
		  HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
		  connection.setDoOutput(true);
		  connection.setRequestMethod("PUT");
		  connection.setRequestProperty("accept-charset", charset);
		  connection.setRequestProperty("content-type", "application/json;charset=" + charset);
		  connection.setRequestProperty("content-length", ""+jsonString.length());
		  connection.setRequestProperty("authorization",auth);
		  
		  try (OutputStream output = connection.getOutputStream()) {
		    output.write(jsonString.getBytes(charset));
		  } catch(Exception e) {
			  failed = true;
			  e.printStackTrace();
		  }

		  InputStream response = connection.getInputStream();
		  return response;
		}
}
