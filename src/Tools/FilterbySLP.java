package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FilterbySLP {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String infile="F:\\research\\111\\time-sensitive query\\AOL Data Set\\All\\Regression\\MS+AOLregression.txt";
		String infile="F:\\research\\111\\time-sensitive query\\AOL Data Set\\All\\Regression\\MSDATA+AOLwekadata.arff";
		String outfile="F:\\research\\111\\time-sensitive query\\AOL Data Set\\All\\Regression\\FilterbySLP1.txt";
		int count=0;
		FileReader fr = new FileReader(infile);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter output=new FileWriter(outfile);
		String line="";
		while((line=input.readLine())!=null){
			count++;
			if(count<=10)
				continue;
//			String[]lineArray1=line.split("\t");
//			String []lineArray=lineArray1[7].split(",");
			String []lineArray=line.split(",");
			//length,purity,support
//			if(Double.parseDouble(lineArray[3])<=0.067402&&
//					(Double.parseDouble(lineArray[4])>=-0.388548&&
//					Double.parseDouble(lineArray[4])<=2.019321)&&
//					Double.parseDouble(lineArray[5])<=0.050735)
			if(
					Double.parseDouble(lineArray[5])<=0.050735)
				output.append(line+"\n");
		}
	}

}
