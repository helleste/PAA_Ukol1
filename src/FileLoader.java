import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import junit.framework.Test;


public class FileLoader {

	private String filename = "src/Files/inst/knap_4.inst.dat";
	
	
//	public String getURL() {
//		URL url = Test.class.getClassLoader().getResource(filename);
//	    System.out.println(url.toString());
//	    
//	    return(url.getPath());
//	}
	
	private void parseLine(String strLine) {
		
		String[] lineData = strLine.split(" ");
		int instId = Integer.parseInt(lineData[0]);
		// TODO Finish this
		
		System.out.println(Arrays.toString(lineData));
	}
	
	public void loadFile() {
		
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String strLine;
			
			// Read till you reach EOF
			while ((strLine = br.readLine()) != null) {
				parseLine(strLine);
			}
			
			// Close the input stream
			in.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
