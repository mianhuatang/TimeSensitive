package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CalculateDF {
	Map<String,Integer>DF=new HashMap<String,Integer>();
	public void CalculateDFFunction(String inFile,int pos,String outFile) throws IOException{
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		while((line=input.readLine())!=null){
			String []linearray=line.split("\t");
			String TopicPart=linearray[pos];
			String[] TopicPartArray=TopicPart.split(" ");
			for(String word:TopicPartArray){
				if(DF.containsKey(word)){
					int count=DF.get(word)+1;
					DF.put(word, count);
				}
				else
					DF.put(word, 1);
			}
		}
		for(String key:DF.keySet()){
			String outline="";
			outline+=key+"\t";
			outline+=DF.get(key);
			output.append(outline+"\n");
		}
		output.flush();
		output.close();
	}
}
