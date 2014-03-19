package Sensitivity;

import java.io.BufferedReader;
import java.io.FileReader;

public class TongjiResult {
	public static void main(String[]arts) throws Exception{
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/SensitivityRemoved.txt";
		FileReader fr = new FileReader(inPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		int true1=0;//min true
		int true2=0;//frequent true
		int T1=0;//product
		int T2=0;//product
		int f1=0;//min false
		int f2=0;//frequent false
		int count=0;
		while((line=input.readLine())!=null){
			count++;
			if(count==252)
				break;
			String []lineArray=line.split("\t");
			if(lineArray[0].equals("y"))
				true1++;
			else if(lineArray[0].equals("t"))
				T1++;
			else if(lineArray[0].equals("n"))
				f1++;
			if(lineArray[1].equals("y"))
				true2++;
			else if(lineArray[1].equals("t"))
				T2++;
			else if(lineArray[1].equals("n"))
				f2++;
		}
		System.out.println("true1:"+true1);
		System.out.println("f1:"+f1);
		System.out.println("t1:"+T1);
		System.out.println("true2:"+true2);
		System.out.println("f2:"+f2);
		System.out.println("t2:"+T2);
	}
}
