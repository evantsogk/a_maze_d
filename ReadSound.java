import java.io.*; 
import java.util.*;
/* Class for reading Sound.txt, and determining if the sound is on or off. */
public class ReadSound {
	private boolean sound=true;
	public void loadFile(String data) {
		File f = null;
        BufferedReader reader = null;
		String line;
        try {
            f = new File(data);
        } 
	    catch (NullPointerException e) { 
	        System.err.println("File not found.");
        }    

        try {
            reader = new BufferedReader(new FileReader(f));
        } 
	    catch (FileNotFoundException e) { 
	        System.err.println("Error opening file!");
        }
		try {
			line = reader.readLine();
            if (line != null) {
				if (line.trim().toUpperCase().startsWith("SOUND")) 
				    sound=(line.trim().substring(6).trim().equalsIgnoreCase("off")) ? false : true;
			}
		}
		catch (IOException e) {
            System.out.println("Error reading line.");
		}		
	    try {
			reader.close();
		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
	public boolean getSound() {
		return sound;
	}
}

