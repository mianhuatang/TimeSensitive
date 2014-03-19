package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AverageURLs {
	public double CalculateAverageURLs(String inFile,int pos) throws IOException{
		List<String>querylist=new ArrayList<String>();
		double average=0;
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line = null;
		int count=0;
		while((line=input.readLine())!=null){
			count++;
			System.out.println("count:"+count);
			if(count==1)
				continue;
			String []linearray=line.split("\t");
			String QueryID=linearray[pos];
			if(!querylist.contains(QueryID)){
				querylist.add(QueryID);
			}
		}
		average=(count-1)/querylist.size();
		System.out.println(querylist.size());
		return average;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		AverageURLs au=new AverageURLs();
		String inFile="F:\\research\\111\\time-sensitive query\\MS Data Set\\srfp20060501-20060531.clicks.txt";
		int pos=0;
		double avg=au.CalculateAverageURLs(inFile, pos);
		System.out.println(avg);
	}
}
