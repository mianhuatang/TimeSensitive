package Filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FilterBlank {
	public void FilterBlankFunction(String inFile,int pos,String outFile) throws IOException{
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		
		while((line=input.readLine())!=null)
		{
			String []linearray=line.split("\t");
			if(linearray[pos].equals("")){
				System.out.println(line);
				continue;
			}
			else
				output.append(line+"\n");
		}
		output.flush();
		output.close();
		output = null;
		input.close();
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inFile="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\PreProcessing\\PreProcessing-YearSensitive.txt";
		String outFile="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\" +
				"YearSensitive\\Filter\\FilterBlank-PreProcessing-YearSensitive.txt";
//		String inFile="/date/sunshengyun";
//		String outFile="/date/sunshengyun";
		FilterBlank fb=new FilterBlank();
		fb.FilterBlankFunction(inFile, 0, outFile);
	}

}
