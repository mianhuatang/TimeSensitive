package Cluster;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class Evaluation {
	public static void getUniqueTimeSensitiveQuery(String inFile,String outFile) throws IOException{
		FileReader fr=new FileReader(inFile);
		BufferedReader br=new BufferedReader(fr,1024*1024*64);
		FileWriter fw=new FileWriter(outFile);
		String line="";
		final int topicPartPos=7;
		final int queryPos=4;
		line=br.readLine();
		int lineCount=0;
		while(line!=null){
			lineCount++;
			System.out.println("lineCount:"+lineCount);
			HashMap<String,String> eachCluster=new HashMap<String,String>();
			while(!isInClass(line)){
				line=br.readLine();
				lineCount++;
				System.out.println("lineCount:"+lineCount);
			}
			while(isInClass(line)){
				lineCount++;
				System.out.println("lineCount:"+lineCount);
				String[]lineArray=line.split("\t");
				eachCluster.put(lineArray[topicPartPos], lineArray[topicPartPos]+"\t"+lineArray[queryPos]);
				line=br.readLine();
				
			}
			Iterator<String> it=eachCluster.keySet().iterator();
			while(it.hasNext()){
				fw.append(eachCluster.get(it.next())+"\n");
			}
			fw.append("\n");
			fw.flush();
		}
		fw.flush();
		fw.close();
	}
	public static boolean isInClass(String line){
		if(null==line||line.trim().length()==0||line.charAt(0)=='+'||line.charAt(0)=='-'||line.charAt(0)=='*')
			return false;
		return true;
	}
	public static void main(String[]args) throws IOException{
		double precision=0;
		double recall=0;
		
		int lineCount=0;
		
		int classCount=0;
		String inFile="F:\\research\\111\\201307\\MS\\Cluster\\3uniqueDBScanCluster08（full）.txt";
		String outFile="F:\\research\\111\\201307\\MS\\Cluster\\clusterResult1.txt";
		String outFile2="F:\\research\\111\\201307\\MS\\Cluster\\uniqueTSQuery.txt";
		
		getUniqueTimeSensitiveQuery( inFile, outFile2);
		
		FileReader fr=new FileReader(inFile);
		BufferedReader br=new BufferedReader(fr,1024*1024*64);
		FileWriter fw=new FileWriter(outFile);
		String line="";
		while((line=br.readLine())!=null){
			lineCount++;
			System.out.println("lineCount:"+lineCount);
			
			double eachPrecision=0;
			double eachRecall=0;
			
			int eachClassCount=0;
			int addCount=0;
			int minusCount=0;
			
			boolean newClusterFlag=false;
			
			while(!isInClass(line)){
				String[]array=line.split("\\+|\\-|\\*");
				for(int i=0;i<array.length-1;i++){
					if(line.charAt(i)=='+')
						addCount+=Integer.valueOf(array[i+1]);
					if(line.charAt(i)=='-')
						minusCount+=Integer.valueOf(array[i+1]);
					if(line.charAt(i)=='*')
						newClusterFlag=true;
				}
				line=br.readLine();
				lineCount++;
				System.out.println("lineCount:"+lineCount);
			}
			
			if(newClusterFlag)
				break;
			
			while(isInClass(line)){
				eachClassCount++;
				line=br.readLine();
				lineCount++;
				System.out.println("lineCount:"+lineCount);
			}
			classCount++;
			
			int truetrue=eachClassCount-addCount;
			int truefalse=minusCount;
			int falsetrue=addCount;
			
			precision+=(double)truetrue/(truetrue+truefalse);
			recall+=(double)truetrue/(truetrue+falsetrue);
			
			eachPrecision=(double)truetrue/(truetrue+truefalse);
			eachRecall=(double)truetrue/(truetrue+falsetrue);
			System.out.println(classCount+"\t"+eachPrecision+"\t"+eachRecall);
			fw.append(classCount+"\t").append(String.valueOf(eachPrecision)).append("\t").append(String.valueOf(eachRecall)).append("\n");
			fw.flush();
		}
		fw.append(String.valueOf(precision/classCount)).append("\t").append(String.valueOf(recall/classCount)).append("\n");
		fw.flush();
		fw.close();
	}
}
