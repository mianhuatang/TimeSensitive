package regression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurityLengthDataClassification {
	public static Map<String,List<String>> sequence=new HashMap<String,List<String>>();
	public static void main(String []args) throws IOException{
		String outPath100="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/NNdataPurity&Length100.txt";
		String outPath50="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/NNdataPurity&Length50.txt";
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/NNdataPurity&Length1.txt";
		FileReader fr = new FileReader(inPath);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter output100=new FileWriter(outPath100);
		FileWriter output50=new FileWriter(outPath50);
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
		for(String key:sequence.keySet()){
			List <String> lineList=sequence.get(key);
			if(lineList.size()==1){
				String outLine=lineList.get(0);
				String[]lineArray=outLine.split("\t");
				if(lineArray[lineArray.length-1].equals("100%"))
					output100.append(lineList.get(0)+"\n");
				else 
					output50.append(outLine+"\n");
			}
			else{
				for(String outLine:lineList){
					output50.append(outLine+"\n");
				}
			}
		}
		output100.flush();
		output50.flush();
	}
}
