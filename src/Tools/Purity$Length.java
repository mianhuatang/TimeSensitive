package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Purity$Length {
	public static Map<String,List<String>> sequence=new HashMap<String,List<String>>();
	public void FindMostPurityandLength(String outPath,String inPath) throws IOException{
		CreateSequenceMap(inPath);
		FileWriter output=new FileWriter(outPath);
		for(String key:sequence.keySet()){
			List<String> lineList=sequence.get(key);
			double mostPurity=1000;
			double mostLength=-1000;
			String mostLine="";
			for(String line:lineList){
				String[]lineArray=line.split("\t");
				String lineArray7=lineArray[7];
				String []lineArray7Array=lineArray7.split(",");
				double purity=Double.parseDouble(lineArray7Array[4]);
				double length=Double.parseDouble(lineArray7Array[3]);
				if(purity<mostPurity){
					mostPurity=purity;
					mostLength=length;
					mostLine=line;
				}
				else if(purity==mostPurity){
					if(length>mostLength){
						mostPurity=purity;
						mostLength=length;
						mostLine=line;
					}
				}
			}
			output.append(mostLine+"\n");
		}
		output.flush();
	}
	public void CreateSequenceMap(String inPath) throws IOException{
		FileReader fr = new FileReader(inPath);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		String line="";
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			if(sequence.containsKey(lineArray[2])){
				List<String> lineList=sequence.get(lineArray[2]);
				lineList.add(line);
				sequence.put(lineArray[2], lineList);
			}
			else{
				List<String> lineList=new ArrayList<String>();
				lineList.add(line);
				sequence.put(lineArray[2], lineList);
			}
		}
	}
	public static void main(String[]args) throws IOException{
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/msdata.txt";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/msdataPurity&Length.txt";
		Purity$Length pl=new Purity$Length();
		pl.FindMostPurityandLength(outPath, inPath);
	}
}
