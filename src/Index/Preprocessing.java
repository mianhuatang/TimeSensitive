package Index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import Tools.UrlFenCi;

public class Preprocessing {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Filter/FilterBlank-PreProcessing-JoinUrls-YearSensitive.txt";
		String outputPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Index/beforindex.txt";
		FileReader fr = new FileReader(inputPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outputPath);
		String line=null;
		UrlFenCi fc=new UrlFenCi();
		int count=0;
		while((line=input.readLine())!=null){
			String []lineArray=line.split("\t");
			String outline="";
			outline+=lineArray[0]+"\t";
			outline+=lineArray[4]+"\t";
			outline+=lineArray[5]+"\t";
			outline+=lineArray[6]+"\t";
			outline+=lineArray[10]+"\t";//url
			outline+=fc.FenCi(lineArray[10]);
			output.append(outline + "\n");
			System.out.println(++count);
		}
		output.flush();
		output.close();
		output = null;
		input.close();
	}

}
