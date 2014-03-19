package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractFrequentTopic {
	public static Map<String,List<String>>topic=new HashMap<String,List<String>>(); 
	public final static int topicPos=5;
	public final static int idPos=0;
	public final static int minSize=2;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inFile="F:\\research\\111\\201307\\MS\\Extract\\filterwithTopicID-extract-smalldictionary-srfp20060501-20060531.queries.txt";
		String outFile="F:\\research\\111\\201307\\MS\\Extract\\frequent-topicwithID-filter-smalldictionary-srfp20060501-20060531.queries.txt";
		FileWriter output = new FileWriter(outFile);
		String outFile2="F:\\research\\111\\201307\\MS\\Extract\\frequent-filterwithTopicID-extract-smalldictionary-srfp20060501-20060531.queries.txt";
		FileWriter output2 = new FileWriter(outFile2);
		FileReader fr;
		fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			String key=lineArray[topicPos].replaceAll("①", " ");
			if(key.trim().length()==0)
				continue;
			else
			key=lineArray[idPos]+"\t"+lineArray[topicPos].replaceAll("①", " ");
			if(topic.containsKey(key)){
				List<String>lineList=topic.get(key);
				lineList.add(line);
				topic.put(key, lineList);
			}
			else{
				List<String>lineList=new ArrayList<String>();
				topic.put(key, lineList);
			}
		}
		for(String key:topic.keySet()){
			List<String>lineList=topic.get(key);
			if(lineList.size()>=minSize){
				for(String outLine:lineList){
					output2.append(outLine+"\n");
				}
				output.append(key+"\n");
			}
		}
		fr.close();
		output.close();
		output2.close();
	}
}
