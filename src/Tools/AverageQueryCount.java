package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AverageQueryCount {
	public double CalculateAverageQueryCount(String inFile,int pos) throws IOException{
		double average=0;
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line = null;
		int count=0;
		while((line=input.readLine())!=null){
			count++;
			if(count==1)
				continue;
			String []linearray=line.split("\t");
			String Query=linearray[pos];
			average+=Query.split(" ").length;
		}
		average=average/count;
		return average;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		AverageQueryCount aqc=new AverageQueryCount();
		String inFile="F:\\research\\111\\time-sensitive query\\MS Data Set\\srfp20060501-20060531.queries.txt";
		int pos=1;
		double avg=aqc.CalculateAverageQueryCount(inFile, pos);
		System.out.println(avg);
	}

}
