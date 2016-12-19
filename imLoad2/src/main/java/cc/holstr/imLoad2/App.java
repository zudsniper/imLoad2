package cc.holstr.imLoad2;

import cc.holstr.imLoad2.gui.Window;
import cc.holstr.imLoad2.properties.Unpacker;

public class App 
{
	public static Window w; 
	
    public static void main( String[] args )
    {
    	if(args.length!=0) {
    		for(int i =0 ; i<args.length;i++) {
    			if(args[i].contains("-d")) {
    				Window.debug = true;
    			}
    		}
    	}
    	
    	Unpacker.os = System.getProperty("os.name");
       w = new Window();
    }
}
