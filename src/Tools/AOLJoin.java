package Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AOLJoin {
	public static void main(String[]args) throws IOException{
		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/join1.txt";
		File file=new File(inPath);
		String[]FileList=file.list();
		FileWriter output = new FileWriter(outPath,true);
		for(String filename : FileList){
			FileReader fr=new FileReader(inPath+"/"+filename);
			BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
			String line="";
			int count=0;
			while((line=input.readLine())!=null){
				count++;
				if(count>10)
					return;
				String[]lineArray=line.split("\t");
				if(lineArray.length!=5)
					continue;
				else
					output.append(line+"\n");
				System.out.println(line);
			}
		}
		output.flush();
		output.close();
	}
}
