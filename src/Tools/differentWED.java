package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class differentWED {
	public static void main(String[]args) throws Exception{
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/EditDistance.txt";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/DiffEditDistance.txt";
		FileReader fr = new FileReader(inPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outPath);
		String line="";
		
		int linecount=0;
		while((line=input.readLine())!=null){
			//line="8706	 plymouth roadrunner 	1	1	(1,4)	(1970,2)(1971,7)(1969,19)	(1,2)(1,2)	";
			linecount++;
			String[]lineArray=line.split("\t");
			if(lineArray.length<=7)
				continue;
//			if(!lineArray[0].equals(lineArray[4])&&!lineArray[0].equals(lineArray[5]))//min,fre
			if(!lineArray[0].equals(lineArray[5]))
				output.append(line+"\n");
			System.out.println(linecount);
		}
		output.flush();
		output.close();
	}
}
