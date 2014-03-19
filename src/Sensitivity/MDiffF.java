/**
 * 
 */
package Sensitivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author rebecca
 *
 */
public class MDiffF {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/SensitivityRemoved.txt";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/MDiffF.txt";
		FileReader fr = new FileReader(inPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output=new FileWriter(outPath);
		String line="";
		int count=0;
		while((line=input.readLine())!=null){
			count++;
			String []lineArray=line.split("\t");
			if(count<=281){
				if(lineArray[4].equals(lineArray[5]))
					continue;
				else
					output.append(line+"\n");
			}
			else{
				if(lineArray.length<4)
					continue;
				if(lineArray[2].equals(lineArray[3]))
					continue;
				else
					output.append(line+"\n");
			}
		}
		output.flush();
		output.close();
	}

}
