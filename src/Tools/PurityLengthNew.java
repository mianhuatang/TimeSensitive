package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurityLengthNew {
	public static double minLength=0.3;
	public static  Map<String,List<String>> sequence=new HashMap<String,List<String>>();
	public static Map<String,List<String>> outSequence=new HashMap<String,List<String>>();
	public void FindMostPurityandLength(String outPath,String inPath) throws IOException{
		CreateSequenceMap(inPath);
		for(String key:sequence.keySet()){
			List<String> lineList=sequence.get(key);
			double mostPurity=1000;//100% pure
			double mostLength=-1000;//100% pure
			
			double mostPurityWithLength=1000;
			double mostLengthWithLength=-1000;
			for(String line:lineList){//find the max purity and max length, and length is not smaller than minlength
				String[]lineArray=line.split("\t");
				String lineArray7=lineArray[7];
				String []lineArray7Array=lineArray7.split(",");
				double purity=Double.parseDouble(lineArray7Array[4]);
				double length=Double.parseDouble(lineArray7Array[3]);
				
				if(purity<mostPurity){//the smaller the entropy, the more pure the subsequence is.
					mostPurity=purity;
					mostLength=length;
				}
				else if(purity==mostPurity){
					if(length>mostLength){
						mostPurity=purity;
						mostLength=length;
					}
				}
				
				if(purity<mostPurityWithLength&&length>=minLength){
					mostPurityWithLength=purity;
					mostLengthWithLength=length;
				}
				else if(purity==mostPurityWithLength&&length>=minLength){
					if(length>mostLengthWithLength){
						mostPurityWithLength=purity;
						mostLengthWithLength=length;
					}
				}
				//&&length>=minlength
			}
			
			if(mostPurity==mostPurityWithLength){//100% pure
				for(String line:lineList){
					String[]lineArray=line.split("\t");
					String lineArray7=lineArray[7];
					String []lineArray7Array=lineArray7.split(",");
					double purity=Double.parseDouble(lineArray7Array[4]);
					double length=Double.parseDouble(lineArray7Array[3]);
					if(purity==mostPurity&&length==mostLength){
						if(outSequence.containsKey(key)){
							List<String> outLineList=outSequence.get(key);
							outLineList.add(line+"\t"+"100%");
							outSequence.put(key, outLineList);
						}
						else{
							List<String> outLineList=new ArrayList<String>();
							outLineList.add(line+"\t"+"100%");
							outSequence.put(key, outLineList);
						}
					}
				}
			}
			else{
				for(String line:lineList){
					String[]lineArray=line.split("\t");
					String lineArray7=lineArray[7];
					String []lineArray7Array=lineArray7.split(",");
					double purity=Double.parseDouble(lineArray7Array[4]);
					double length=Double.parseDouble(lineArray7Array[3]);
					if(purity==mostPurityWithLength&&length==mostLengthWithLength){
						if(outSequence.containsKey(key)){
							List<String> outLineList=outSequence.get(key);
							outLineList.add(line+"\t"+"50%");
							outSequence.put(key, outLineList);
						}
						else{
							List<String> outLineList=new ArrayList<String>();
							outLineList.add(line+"\t"+"50%");
							outSequence.put(key, outLineList);
						}
					}
				}
			}
		}
		outPut(outSequence,outPath);
	}
	public void outPut(Map<String,List<String>> outSequence,String outPath) throws IOException{
		FileWriter output=new FileWriter(outPath);
		for(String key:outSequence.keySet()){
			List<String> lineList=outSequence.get(key);
			for(String line:lineList)
				output.append(line+"\n");
		}
		output.flush();
	}
	public void CreateSequenceMap(String inPath) throws IOException{//key: topic ,value:a list of subsequence
		FileReader fr = new FileReader(inPath);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		String line="";
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			if(sequence.containsKey(lineArray[2])){
				List<String> lineList=sequence.get(lineArray[2]);
				lineList.add(line);
				sequence.put(lineArray[2], lineList);
			}
			else{
				List<String> lineList=new ArrayList<String>();
				lineList.add(line);
				sequence.put(lineArray[2], lineList);
			}
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/msdata.txt";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/msdataPurity&Length.txt";
		PurityLengthNew pl=new PurityLengthNew();
		pl.FindMostPurityandLength(outPath, inPath);
	}

}
