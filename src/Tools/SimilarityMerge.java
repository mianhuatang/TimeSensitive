package Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SimilarityMerge {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String inputPath="F:\\research\\111\\201307\\MS\\Similarity";
//		String outputPath="F:\\research\\111\\201307\\MS\\SimilarityMerge\\similarityMerge.txt";
//		FileWriter output = new FileWriter(outputPath);
//		File file=new File(inputPath);
//		String[]fileArray=file.list();
//		int count=0;
//		for(String aFile:fileArray){
//			FileReader fr = new FileReader("F:\\research\\111\\201307\\MS\\Similarity\\"+aFile);
//			BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
//			String line=null;
//			while((line=input.readLine())!=null){
//				output.append(line + "\n");
//				System.out.println(++count);
//			}
//			input.close();
//		}
//		
//		output.flush();
//		output.close();
//		output = null;
		String inputPath="F:\\research\\111\\201307\\MS\\SimilarityMerge\\similarityMerge.txt";
		String outputPath="F:\\research\\111\\201307\\MS\\SimilarityMerge\\similarityMerge2.txt";
		FileReader fr = new FileReader(inputPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outputPath);
		String line="";
		int count=0;
		while((line=input.readLine())!=null){
			String[] lineArray=line.split("\t");
			int index=lineArray[2].lastIndexOf(" ");
			String[] lineArray2Array=lineArray[2].split(" ");
			String id1=lineArray2Array[lineArray2Array.length-1];
			String query2=lineArray[2].substring(0,index);
			String outLine=lineArray[0]+"\t"+lineArray[1]+"\t"+query2+"\t"+id1+"\t"+lineArray[3];
			output.append(outLine+"\n");
			System.out.println(++count);
		}
		input.close();
		output.close();
	}

}
