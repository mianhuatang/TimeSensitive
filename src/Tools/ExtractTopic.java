package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;


public class ExtractTopic {
	public static Map<String,Integer>topic=new HashMap<String,Integer>(); 
	public final static int topicPos=4;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inFile="F:\\research\\111\\201307\\MS\\Extract\\filter-extract-smalldictionary-srfp20060501-20060531.queries.txt";
		String outFile="F:\\research\\111\\201307\\MS\\Extract\\topicwithID-filter-smalldictionary-srfp20060501-20060531.queries.txt";
		FileWriter output = new FileWriter(outFile);
		String outFile2="F:\\research\\111\\201307\\MS\\Extract\\filterwithTopicID-extract-smalldictionary-srfp20060501-20060531.queries.txt";
		FileWriter output2 = new FileWriter(outFile2);
		FileReader fr;
		fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		int topicID=1;
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			String topicPart=lineArray[topicPos].replaceAll("①", " ");
			if(topic.containsKey(topicPart)){
				 int ID=topic.get(topicPart);
				 line=ID+"\t"+line;
			}
			else{
				topic.put(topicPart, topicID);
				line=topicID+"\t"+line;
				topicID++;
			}
			output2.append(line+"\n");
		}
		for(String key:topic.keySet()){
			topicID=topic.get(key);
			output.append(topicID+"\t"+key+"\n");
		}
		fr.close();
		output.close();
		output2.close();
	}
}
