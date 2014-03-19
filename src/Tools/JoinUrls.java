package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinUrls {
	Map<String,List<String>>QueryUrl=new HashMap<String,List<String>>();
	public void QueryJoinUrl(String[]inFiles,String outFile,int firstPosition,int secondPosition) throws Exception{
		FileReader fr=new FileReader(inFiles[0]);//0:queryfile,1:clickthroughfile
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter fw=new FileWriter(outFile);
		String line="";
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			if(!QueryUrl.containsKey(lineArray[firstPosition])){//queryID
				List<String>info=new ArrayList<String>();
				info.add(line);
				QueryUrl.put(lineArray[firstPosition], info);
			}
		}
		fr=new FileReader(inFiles[1]);//click through
		input=new BufferedReader(fr,20*1024*1024);
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			if(QueryUrl.containsKey(lineArray[secondPosition])){//queryID
				line="";
				for(int i=2;i<lineArray.length-1;i++){
					line+=lineArray[i]+"\t";
				}
				line+=lineArray[lineArray.length-1];
				List<String>info=QueryUrl.get(lineArray[secondPosition]);
				info.add(line);
				QueryUrl.put(lineArray[secondPosition], info);
			}
		}
		for(String key:QueryUrl.keySet()){
			List<String>info=QueryUrl.get(key);
			String outline="";
			outline=info.get(0);
			String out=outline+"\t";
			for(int i=1;i<info.size();i++){
				out+=info.get(i);
				fw.append(out+"\n");
				out=outline+"\t";
			}		
		}
		fw.flush();
		fw.close();
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		String[]inFiles={"F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\Filter\\FilterBlank-PreProcessing-YearSensitive.txt",
//		"F:\\research\\111\\time-sensitive query\\MS Data Set\\srfp20060501-20060531.clicks.txt"};
//		String outFile="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\Filter\\FilterBlank-PreProcessing-JoinUrls-YearSensitive.txt";
		String[]inFiles={"F:\\research\\111\\201307\\MS\\Extract\\frequentquery.txt",
		"F:\\research\\111\\time-sensitive query\\MS Data Set\\srfp20060501-20060531.clicks.txt"};
		String outFile="F:\\research\\111\\201307\\MS\\Processing\\url-frequentquery.txt";
		JoinUrls ju=new JoinUrls();
		int firstPosition=3;
		int secondPosition=0;
		ju.QueryJoinUrl(inFiles, outFile,firstPosition,secondPosition);
	}

}
