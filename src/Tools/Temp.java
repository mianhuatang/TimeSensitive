package Tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Temp {
	public static void main(String[]args) throws IOException{
		String inFile="F:/research/111/201307/MS/Cluster/Time sensitive queries(new).txt";
		String outFile="F:/research/111/201307/MS/Cluster/Time-sensitive queries.txt";
		FileReader fr=new FileReader(inFile);//0:queryfile,1:clickthroughfile
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter fw=new FileWriter(outFile);
		String line="";
		Set<String> querySet=new HashSet<String>();
		while((line=input.readLine())!=null){
			if(line.trim().length()!=0)
				querySet.add(line);
		}
		Iterator<String> it=querySet.iterator();
		while(it.hasNext()){
			fw.append(it.next()+"\n");
		}
		fw.flush();
		fw.close();
		fr.close();
	}
}



