import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import junit.framework.Test;


public class FileLoader {

	private String filename = "src/Files/inst/knap_10.inst.dat";
	
	
//	public String getURL() {
//		URL url = Test.class.getClassLoader().getResource(filename);
//	    System.out.println(url.toString());
//	    
//	    return(url.getPath());
//	}
	
	private void parseLine(String strLine) {
		
		String[] lineData = strLine.split(" ");
		int instId = Integer.parseInt(lineData[0]);
		int instSize = Integer.parseInt(lineData[1]);
		Knapsack instKnapsack = new Knapsack();
		instKnapsack.setLimit(Integer.parseInt(lineData[2]));
		ItemPool instItemPool = new ItemPool(instSize);
		int itemId = 0;
		
		for (int i = 3; i < lineData.length; i+=2) {
			Item instItem = new Item(itemId, 
					Integer.parseInt(lineData[i]), 
					Integer.parseInt(lineData[i+1]));
			instItemPool.addToPool(instItem, itemId);
			itemId++;
		}
		
		Instance instance = new Instance(instId, instSize, instItemPool);
		System.out.println(instance.toString());
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
