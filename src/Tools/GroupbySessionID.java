package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupbySessionID {
	public final static int sessionPos=5;
	public final static int queryPos=0;
	public static Map<String,ArrayList<String>>sessionMap=new HashMap<String,ArrayList<String>>();
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inFile="F:\\research\\111\\201307\\MS\\Processing\\fenci-url-frequentquery.txt";
//		String inFile="./fenci-url-frequentquery.txt";
//		String outFile="./sessionInfo.txt";
		String outFile="F:\\research\\111\\201307\\MS\\sessionInfo\\sessionInfo.txt";
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		while((line=input.readLine())!=null){
			String []lineArray=line.split("\t");
			String session=lineArray[sessionPos];
			if(sessionMap.containsKey(session)){
				ArrayList <String> sessionList=sessionMap.get(session);
				sessionList.add(lineArray[queryPos]);
				sessionMap.put(session, sessionList);
			}
			else
			{
				ArrayList <String> sessionList=new ArrayList<String>();
				sessionList.add(lineArray[queryPos]);
				sessionMap.put(session, sessionList);
			}
		}
		for(String key:sessionMap.keySet()){
			StringBuffer outputLine=new StringBuffer();
			outputLine.append(key+"\t");
			ArrayList<String>lineList=sessionMap.get(key);
			for(String outLine:lineList){
				String temp=outLine.trim();
				if(temp.length()==0)
					continue;
				outputLine.append(outLine+" ");
				
			}
			if(!outputLine.toString().equals(key+"\t"))
				output.append(outputLine+"\n");
		}
		output.flush();
	}
}
