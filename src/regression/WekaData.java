package regression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WekaData {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/NNdataPurity&Length.txt";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/NNdataPurity&LengthWeka.arff";
		FileReader fr = new FileReader(inPath);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter output=new FileWriter(outPath);
		String line="";
		String headline="sensitivity,min,mostfrequency,Nlength,purity,Nsupport";
		output.append(headline+"\n");
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			String outline="";
			outline+=lineArray[7];
//			outline+=lineArray[4]+","+lineArray[7];
//			String[]lineArray7=lineArray[7].split(",");
//			for(int i=0;i<lineArray7.length;i++){
//				if(i!=2)
//					outline+=lineArray7[i]+",";
//			}
//			outline=outline.substring(0,outline.length()-1);
			output.append(outline+"\n");
		}
		output.flush();
		output.close();
	}

}
