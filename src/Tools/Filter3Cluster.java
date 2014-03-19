package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Filter3Cluster {
	public static Map<String,ArrayList<String>>cluster=new HashMap<String,ArrayList<String>>();
	public final static int minPoints=3;
	public static void main(String[] args) throws Exception {
		String filename = "./urlCluster.txt";
		FileReader fr;
		fr = new FileReader(filename);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String outfile = "./3urlCluster.txt";
		FileWriter output = new FileWriter(outfile);
		
		String line="";
		while(( line=input.readLine())!=null){
			String []linearray=line.split("\t");
			String key=linearray[0];
			if(cluster.containsKey(key)){
				ArrayList<String>lineList=cluster.get(key);
				lineList.add(line);
				cluster.put(key, lineList);
			}
			else{
				ArrayList<String>lineList=new ArrayList<String>();
				lineList.add(line);
				cluster.put(key, lineList);
			}
		}
		for(String key:cluster.keySet()){
			if(key.equals("-1"))
				continue;
			ArrayList<String>lineList=cluster.get(key);
			if(lineList.size()>=3){
				for(String outLine:lineList){
					output.append(outLine+"\n");
				}
			}
		}
		output.flush();
		input.close();
		output.close();
	}
}
