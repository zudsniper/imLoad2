package cc.holstr.imLoad2;

import cc.holstr.imLoad2.gui.Window;
import cc.holstr.imLoad2.properties.Unpacker;

public class App 
{
	public static Window w; 
	
    public static void main( String[] args )
    {
    	Unpacker.os = System.getProperty("os.name");
       w = new Window();
    }
}
