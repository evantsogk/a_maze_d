import java.io.*;
import java.util.*;

/* Class for writing at Sound.txt if the sound is on or off. */
public class StoreSound {
	public void createFile (String fn, boolean sound) {
		File f=null;
		BufferedWriter writer = null;
		try	{
			f = new File(fn);
		}
		catch (NullPointerException e) {
			System.err.println ("File not found.");
		}
        try	{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
		}
		catch (FileNotFoundException e) {
			System.err.println("Error opening file for writing!");
		}
        
		try	{
			if (sound) 
			    writer.write("SOUND on");
			else
				writer.write("SOUND off");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}
        try {
			writer.close();
		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
}

