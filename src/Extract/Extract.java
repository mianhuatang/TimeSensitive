package Extract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Extract {
	private String inputPath = null;
	private String outputPath = null;
	public String getInputPath() {
		return inputPath;
	}
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public void ExtractTopicpart(String inputPath,int pos,String outputPath,int delposq,int delpost) throws Exception {
		FileReader fr = new FileReader(inputPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outputPath);
		FileWriter removeout=new FileWriter("F:\\research\\111\\201307\\MS\\Extract\\Remove\\remove-srfp20060501-20060531.queries.txt");
		String line = null;
		YearSensitiveRules ysr=new YearSensitiveRules();
		int count=0;
		while((line=input.readLine())!=null)
		{
			//line="walk protest illig immigr	2006-05-01 14:07:49	5/01/06 walk for the protest against illigal immigrants where at	198c270af9f94058	10555291151f44c3	 walk for the protest against illigal immigrants where at 	5/01/06z 	0";
			//line="2006-05-01 00:57:42	may 13rd 2006 olympics	1971c2ca889640b2	0a1a4aa19dfb41e6 	11";
//			line="2006-05-01 00:57:42	what is may day	1971c2ca889640b2	0a1a4aa19dfb41e6 	11";
			List<String>RemoveList=new ArrayList<String>();
			System.out.println("count:"+count);
			count++;
			String []linearray=line.split("\t");
			String query=linearray[pos].toLowerCase();
			
			linearray[pos]=query;
			query=" "+query+" ";
			
			RemoveList.addAll(ysr.Filter8figure(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterMonthDay4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterMonthDay2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonthDay4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonthDay2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonth4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonth2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonthDay(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.Filter4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.Filter2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterDictionary(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			if(RemoveList.size()==0){
				removeout.append(line+"\n");
				continue;
				}
			String removequery="";
			for(String remove:RemoveList){
				removequery+=remove+" ";
			}
			removequery.trim();
			line="";
			for(int j=0;j<linearray.length-1;j++){
				if(j==delposq||j==delpost)
					continue;
				line+=linearray[j]+"\t";
			}
			line+=query+"\t";
			line+=removequery+"\t";
			line+=linearray[linearray.length-1];
			output.append(line + "\n");
		}
		output.flush();
		output.close();
		removeout.flush();
		removeout.close();
	}
	public void ExtractTopicpart(String inputPath,String outputPath) throws Exception {
		// TODO Auto-generated constructor stub
		FileReader fr = new FileReader(inputPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outputPath);
		String line = null;
		YearSensitiveRules ysr=new YearSensitiveRules();
		int count=0;
		while((line=input.readLine())!=null)
		{
			
//			line="2006-05-01 00:57:42	may 13rd 2006 olympics	1971c2ca889640b2	0a1a4aa19dfb41e6 	11";
			line="2006-05-01 00:57:42	what is may day	1971c2ca889640b2	0a1a4aa19dfb41e6 	11";
			List<String>RemoveList=new ArrayList<String>();
			if(count==0){
				count++;
				continue;
			}
			System.out.println("count:"+count);
			count++;
			String []linearray=line.split("\t");
			String query=linearray[1].toLowerCase();
			
			linearray[1]=query;
			query=" "+query+" ";
			
			RemoveList.addAll(ysr.Filter8figure(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterMonthDay4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterMonthDay2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonthDay4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonthDay2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonth4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonth2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterLetterMonthDay(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.Filter4Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.Filter2Year(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			RemoveList.addAll(ysr.FilterDictionary(query));
			query=RemoveList.get(RemoveList.size()-1);
			RemoveList.remove(RemoveList.size()-1);
			
			if(RemoveList.size()==0)
				continue;
			String removequery="";
			for(String remove:RemoveList){
				removequery+=remove+" ";
			}
			removequery.trim();
			line="";
			for(int j=0;j<linearray.length-1;j++){
				line+=linearray[j]+"\t";
			}
			line+=query+"\t";
			line+=removequery+"\t";
			line+=linearray[linearray.length-1];
			output.append(line + "\n");
		}
		
		
	}
	public static void main(String[]args) throws Exception{
		long timer = System.currentTimeMillis();
		String inputPath="F:\\research\\111\\time-sensitive query\\MS Data Set\\srfp20060501-20060531.queries.txt";
		String outputPath="F:\\research\\111\\201307\\MS\\Extract\\extract-srfp20060501-20060531.queries.txt";
//		String inputPath="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\PreProcessing\\PreProcessing-JoinUrls-YearSensitive.txt";
//		String outputPath="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\YearSensitive\\PreProcessing\\new-PreProcessing-JoinUrls-YearSensitive.txt";
		Extract ex=new Extract();
		//ex.ExtractTopicpart(inputPath, outputPath);
		ex.ExtractTopicpart(inputPath,  1, outputPath, -1,-1);
		
		timer = System.currentTimeMillis() - timer;
		System.out.println("完成时间" + timer);
	}
}
