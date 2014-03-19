package Filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FilterProducts {
	List<String>ProductList=new ArrayList<String>();
	FilterProducts(String ProductFile) throws Exception{
		FileReader fr = new FileReader(ProductFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		while((line=input.readLine())!=null){
			ProductList.add(line);
		}
	}
	public void Filter(String inFile, String outFile) throws Exception{
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line="";
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			String[]queryArray=lineArray[1].split(" ");
			Boolean flag=false;
			for(String word : queryArray){
				for(String Pword: ProductList){
					if(word.equals(Pword))
						flag=true;
				}
			}
			if(flag==false)
				output.append(line+"\n");
		}
	}
	public static void main(String[]args) throws Exception{
		String ProductFile="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/removedwords.txt";
		FilterProducts fp=new FilterProducts(ProductFile);
		String inFile="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/Sensitivity.txt";
		String outFile="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/SensitivityRemoved1.txt";
		fp.Filter(inFile, outFile);
	}
}
