package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class TimeSensitiveTopic {
	public Map<String,String> TopicInfo=new HashMap<String,String>();
	public static void main(String []args) throws Exception{
		String inPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Index/beforindex.txt";
		String outPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Sensitivity/Topic.txt";
		FileWriter output = new FileWriter(outPath);
		FileReader fr = new FileReader(inPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		TimeSensitiveTopic tst=new TimeSensitiveTopic();
		int count=0;
		while((line=input.readLine())!=null){
			count++;
			String[]lineArray=line.split("\t");
			String topic=lineArray[2];
			if(tst.TopicInfo.containsKey(topic))
				continue;
			else{
				tst.TopicInfo.put(topic, null);
				output.append(topic+"\n");
			}
			System.out.println("count:"+count);
		}
		output.flush();
		output.close();
		input=null;
	}
}
