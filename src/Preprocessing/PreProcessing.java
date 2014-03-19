package Preprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class PreProcessing {//stemmer->remove stopwordss
	public void PreProcessingFunction(String inFile,int pos,String outFile,String stopwordFile) throws IOException{
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		
		while((line=input.readLine())!=null){
			String []linearray=line.split("\t");
			Stemmer s = new Stemmer();
			String TopicPart=linearray[pos];
			String[]queryArray=TopicPart.split(" ");
			TopicPart="";
			for(int i=0;i<queryArray.length;i++){
				s.add(queryArray[i].toLowerCase().toCharArray(), queryArray[i].length());
				s.stem();
				TopicPart+=s.toString()+" ";
			}
			TopicPart.trim();
			RemoveStopWords rsw=new RemoveStopWords();
			rsw.IniStopWordList(stopwordFile);
			TopicPart=rsw.RemoveStopWordsFunction(TopicPart);
			line=TopicPart+"\t"+line;
			output.append(line + "\n");
		
		}
		output.flush();
		output.close();
		output = null;
		input.close();
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String infile="F:\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\Filter\\YearSensitive.txt";
//		String outfile="F:\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\PreProcessing\\PreProcessing-YearSensitive.txt";
//		String stopwordfile="F:\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\PreProcessing\\stopwordnew.txt";
//		int pos=4;
		String infile="F:\\research\\111\\201307\\MS\\Processing\\url-frequentquery.txt";
		String outfile="F:\\research\\111\\201307\\MS\\Processing\\processing-url-frequentquery.txt";
		String stopwordfile="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\PreProcessing\\stopwordnew.txt";
		int pos=5;
		PreProcessing pp=new PreProcessing();
		pp.PreProcessingFunction(infile, pos, outfile, stopwordfile);
	}

}
