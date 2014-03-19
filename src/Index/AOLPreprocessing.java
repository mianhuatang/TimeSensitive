/**
 * 
 */
package Index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Preprocessing.RemoveStopWords;
import Preprocessing.Stemmer;
import Tools.UrlFenCi;

/**
 * @author rebecca
 *
 */
public class AOLPreprocessing {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public void PreProcessingFunction(String inFile,int pos,String outFile,String stopwordFile) throws IOException{
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		int count=0;
		//2421	wow lvl 30 guide	2006-03-22 15:03:10	2	 wow lvl guide 	30y 	http://wow.allakhazam.com
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
			System.out.println(count++);
		
		}
		output.flush();
		output.close();
		output = null;
		input.close();
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputPath="F:/research/111/time-sensitive query/AOL Data Set/All/Extractjoin.txt";
		String outputPath="F:/research/111/time-sensitive query/AOL Data Set/All/PreprocessingExtractjoin.txt";
		String stopFile="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/PreProcessing/stopwordnew.txt";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/URLFenciExtractjoin.txt";
		FileReader fr = new FileReader(inputPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outputPath);
		
		int pos=4;
		AOLPreprocessing pp=new AOLPreprocessing();
		pp.PreProcessingFunction(inputPath, pos, outputPath, stopFile);
		
		fr=new FileReader(outputPath);
		input=new BufferedReader(fr, 20 * 1024 * 1024);
		output=new FileWriter(outPath);
		
		String line=null;
		UrlFenCi fc=new UrlFenCi();
		int count=0;
		while((line=input.readLine())!=null){
			String []lineArray=line.split("\t");
			String outline="";
			outline+=lineArray[0]+"\t";
			outline+=lineArray[1]+"\t";
			outline+=lineArray[2]+"\t";
			outline+=lineArray[3]+"\t";
			outline+=lineArray[4]+"\t";
			outline+=lineArray[5]+"\t";
			outline+=lineArray[6]+"\t";//url
			outline+=lineArray[7]+"\t";//url
			outline+=fc.FenCi(lineArray[7]);
			output.append(outline + "\n");
			System.out.println(++count);
		}
		output.flush();
		output.close();
		output = null;
		input.close();
	}

}
