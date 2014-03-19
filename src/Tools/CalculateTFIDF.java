package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CalculateTFIDF {
	public static int DocumentsCount=197691;
	public void CalIFIDF(String inFile,int pos,String outFile,Map<String,Integer>DF) throws IOException{
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		while((line=input.readLine())!=null){
			Map<String,Integer>TF=new HashMap<String,Integer>();
			String []linearray=line.split("\t");
			String TopicPart=linearray[pos];
			String[] TopicPartArray=TopicPart.split(" ");
			for(String word:TopicPartArray){
				if(TF.containsKey(word)){
					int count=TF.get(word)+1;
					TF.put(word, count);
				}
				else
					TF.put(word, 1);
			}
			String querytfidf="";
			for(String key:TF.keySet()){
				int tf=TF.get(key);
				int df=DF.get(key);
				double tfidf=(tf*1.0/TopicPartArray.length)*Math.log10(DocumentsCount*1.0/df);
				querytfidf+=key+"#"+tfidf+"@";
			}
			output.append(querytfidf+"\t"+line+"\n");
		}
		output.flush();
		output.close();
	}
	public static void main(String[]args) throws IOException{
		String inFile="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\" +
		"YearSensitive\\Filter\\FilterBlank-PreProcessing-YearSensitive.txt";
		String outFile1="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\" +
				"YearSensitive\\TFIDF\\DF-FilterBlank-PreProcessing-YearSensitive.txt";
		String outFile2="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\" +
		"YearSensitive\\TFIDF\\TFIDF-FilterBlank-PreProcessing-YearSensitive.txt";
		int pos=0;
		CalculateDF cdf=new CalculateDF();
		cdf.CalculateDFFunction(inFile, pos, outFile1);
		CalculateTFIDF cti=new CalculateTFIDF();
		cti.CalIFIDF(inFile, pos, outFile2, cdf.DF);
	}
}
