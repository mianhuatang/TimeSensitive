package Sensitivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSpan {
	public static int k=2;
	public Map<String,Attribute>ClassList=new HashMap<String,Attribute>();
//	String []MarkDictionary={"F","D","M","N","P","T","Y"};
	String []MarkDictionary={"F","Z","z","W","w","M","m","D","Y","y"};
	public List<String> NormalizeTimeParts(List<String>TimeParts)throws Exception{//ͬһ��class�У�time��ʽ
		//���ܲ�ͬ��timepart�п��ܰ���������ͬ��time
		//2000Y 13y
		List<String> NormalList=new ArrayList<String>();
		
		for(int i=0;i<TimeParts.size();i++){
			String []Times=TimeParts.get(i).split(" ");//2000Y,13y
			for(String time:Times){
				if(time.charAt(time.length()-1)=='F'){
					String t=time.substring(0, 4);
					int IntTime=Integer.parseInt(t);
					if(IntTime>=1500&&IntTime<=2029)
						NormalList.add(t);
				}
				else if(time.charAt(time.length()-1)=='Z'||time.charAt(time.length()-1)=='W'||
						time.charAt(time.length()-1)=='M'||time.charAt(time.length()-1)=='Y'){
					String t=time.substring(time.length()-5, time.length()-1);
					int IntTime=Integer.parseInt(t);
					if(IntTime>=1500&&IntTime<=2029)
						NormalList.add(t);
				}
				else if(time.charAt(time.length()-1)=='z'||time.charAt(time.length()-1)=='w'||
						time.charAt(time.length()-1)=='m'||time.charAt(time.length()-1)=='y'){
					String t=time.substring(time.length()-3, time.length()-1);
					if(t.charAt(0)=='0'||t.charAt(0)=='1')
						t="20"+t;
					else
						t="19"+t;
					try {
						int IntTime=Integer.parseInt(t);
					if(IntTime>=1500&&IntTime<=2029)
						NormalList.add(t);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
			}
		}
		return NormalList;
	}
	public List<String> SortTimeParts(List<String>TimeParts){//TimePart��С��������
		List<String>SortList=new ArrayList<String>();
		for(int i=0;i<TimeParts.size();i++){
			if(TimeParts.get(i).length()==4)
				SortList.add(TimeParts.get(i));
		}
		String[]SortArray=new String[SortList.size()];
		for(int i=0;i<SortList.size();i++){
			SortArray[i]=SortList.get(i);
		}
		Arrays.sort(SortArray);
		SortList=new ArrayList<String>();
		for(int i=0;i<SortArray.length;i++)
			SortList.add(SortArray[i]);
		return SortList;
	}
	public List<String> SortTimeParts(Map<String,Integer>TSMerge){//give <y1,t1>,<y2,t2>, order by y
		List<String>SortList=new ArrayList<String>();
		for(String time:TSMerge.keySet()){
			SortList.add(time);
		}
		String[]SortArray=new String[SortList.size()];
		for(int i=0;i<SortList.size();i++){
			SortArray[i]=SortList.get(i);
		}
		Arrays.sort(SortArray);
		SortList=new ArrayList<String>();
		for(int i=0;i<SortArray.length;i++)
			SortList.add(SortArray[i]);
		return SortList;
	}
	public List<Integer> SortTimeSpans(Map<String,Integer>TSMerge,String mark){
		List<Integer>SortList=new ArrayList<Integer>();
		if(mark=="T"){
			for(String time:TSMerge.keySet()){
				SortList.add(Integer.parseInt(time));
			}
		}
		else if(mark=="F"){
			int max=0;
			String maxTime="100";
			for(String time:TSMerge.keySet()){
				if(TSMerge.get(time)>max){
					max=TSMerge.get(time);
					maxTime=time;
				}
				else if(TSMerge.get(time)==max){
					if(Integer.parseInt(time)<Integer.parseInt(maxTime)){
						max=TSMerge.get(time);
						maxTime=time;
					}
				}
			}
			SortList.add(Integer.parseInt(maxTime));
		}
		int[]SortArray=new int[SortList.size()];
		for(int i=0;i<SortList.size();i++){
			SortArray[i]=SortList.get(i);
		}
		Arrays.sort(SortArray);
		SortList=new ArrayList<Integer>();
		for(int i=0;i<SortArray.length;i++)
			SortList.add(SortArray[i]);
		return SortList;
		
	}
	public List<SpanFrequency> CalculateTimeSpanYear(List<String>TimeParts,Map<String,Integer>TSMerge){//
		//given ordered year <y1,y2,y3...>, and <y1,t1>,<y2,t2>,return<sensitiviey1,t1>,<s2,t2>...
		List<SpanFrequency> SensitivitySeries= new ArrayList<SpanFrequency>();
		for(int i=0;i<TimeParts.size()-1;i++){
			int j=i+1;
				int FirstYear=0;
				int SecondYear=0;
				FirstYear=Integer.parseInt(TimeParts.get(i));
				SecondYear=Integer.parseInt(TimeParts.get(j));
				String span=Integer.toString(SecondYear-FirstYear);
				int frequency=TSMerge.get(TimeParts.get(i))<TSMerge.get(TimeParts.get(j))?TSMerge.get(TimeParts.get(i)):TSMerge.get(TimeParts.get(j));
				SpanFrequency sf=new SpanFrequency();
				sf.time=span;
				sf.count=frequency;
				SensitivitySeries.add(sf);
		}
		return SensitivitySeries;
	}
	public Map<String,Integer>TimePartsMerge(List<String>TimeParts){ //given <y1,y2,y3...>,return <year,frequency>
		Map<String,Integer>TPMerge=new HashMap<String,Integer>();
		for(int i=0;i<TimeParts.size();i++){
			if(TPMerge.containsKey(TimeParts.get(i))){
				int count=TPMerge.get(TimeParts.get(i))+1;
				TPMerge.put(TimeParts.get(i), count);
			}
			else{
				int count=1;
				TPMerge.put(TimeParts.get(i), count);
			}
		}
		return TPMerge;
	}
	public Map<String,Integer>TimeSpansMerge(List<SpanFrequency>TimeSpans){//given<s1,t1>,<s2,t2>,return merged <s1,t1>,<s2,t2>
		Map<String,Integer>TSMerge=new HashMap<String,Integer>();
		for(int i=0;i<TimeSpans.size();i++){
			if(TSMerge.containsKey(TimeSpans.get(i).time)){
				int count=TSMerge.get(TimeSpans.get(i).time)+TimeSpans.get(i).count;
				TSMerge.put(TimeSpans.get(i).time, count);
			}
			else{
				int count=TimeSpans.get(i).count;
				TSMerge.put(TimeSpans.get(i).time, count);
			}
		}
		return TSMerge;
	}
	public List<SpanFrequency> CalculateTimeSpan(List<String>TimeParts) throws Exception{
//		TimeParts=NormalizeTimeParts(TimeParts, "Y");
		TimeParts=NormalizeTimeParts(TimeParts);
		Map<String,Integer>TPMerge=TimePartsMerge(TimeParts);
		
		TimeParts=SortTimeParts(TPMerge);
		List<SpanFrequency>SensitivityS=CalculateTimeSpanYear(TimeParts,TPMerge);
		return SensitivityS;
	}
	public void OutPut(String outPath) throws IOException{
		FileWriter output = new FileWriter(outPath);
		for(String ClassID:ClassList.keySet()){
			String line="";
			line+=ClassID+"\t";
			line+=ClassList.get(ClassID).TopicParts.get(0)+"\t";
			for(int i=0;i<ClassList.get(ClassID).Sensitivity.length;i++){
				line+=ClassList.get(ClassID).Sensitivity[i]+"\t";
			}
			for(String time:ClassList.get(ClassID).TimeSpansMerges.keySet()){
				line+="("+time+","+ClassList.get(ClassID).TimeSpansMerges.get(time)+")";
			}
			line+="\t";
			for(String time:ClassList.get(ClassID).TimePartsMerges.keySet()){
				line+="("+time+","+ClassList.get(ClassID).TimePartsMerges.get(time)+")";
			}
			line+="\t";
			
			for(int i=0;i<ClassList.get(ClassID).TimeSpans.size();i++){
				line+="("+ClassList.get(ClassID).TimeSpans.get(i).time+","+ClassList.get(ClassID).TimeSpans.get(i).count+")";
			}
			line+="\t";
			line.trim();
			output.append(line+"\n");
		}
		output.flush();
		output.close();
	}
	public static void main(String[]args) throws Exception{
		String inPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/DBScan/ClusteringPqueryFilterAllNoises.txt";
		String outPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Sensitivity/PquerySensitivityMinMostFre.txt";
		TimeSpan tm=new TimeSpan();
		
		FileReader fr = new FileReader(inPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			if(lineArray.length<5)
				continue;
			String ClassID=lineArray[0];
			String TopicPart=lineArray[3];
			String TimePart=lineArray[4];
			if(tm.ClassList.containsKey(ClassID)){//the same classID 
				List<String>TopicParts=tm.ClassList.get(ClassID).TopicParts;
				List<String>TimeParts=tm.ClassList.get(ClassID).TimeParts;
				TopicParts.add(TopicPart);
				TimeParts.add(TimePart);
			}
			else{
				List<String>TopicParts=new ArrayList<String>();
				List<String>TimeParts=new ArrayList<String>();
				Map<String,Integer>TimePartsMerges=new HashMap<String,Integer>();
				List<SpanFrequency> TimeSpans=new ArrayList<SpanFrequency>();
				Map<String,Integer> TimeSpansMerges= new HashMap<String,Integer>();
				TopicParts.add(TopicPart);
				TimeParts.add(TimePart);
				Attribute att=new Attribute();
				att.ClassID=ClassID;
				att.TopicParts=TopicParts;
				att.TimeParts=TimeParts;
				att.TimePartsMerges=TimePartsMerges;
				att.TimeSpans=TimeSpans;
				att.TimeSpansMerges=TimeSpansMerges;
				tm.ClassList.put(ClassID, att);
			}
		}
		
		
		for(String ClassID:tm.ClassList.keySet()){
			//13 2006Y aprilT, aprilT, dailyT, 2006Y januaryT, 2006Y julyT
			List<String>TimeParts=tm.ClassList.get(ClassID).TimeParts;
			//[2006, 2006, 2006, 1999, 1999, 1999, 2005, 2006, 2004, 2006, 2006, 2006, 1974
			TimeParts=tm.NormalizeTimeParts(TimeParts);//////////////////////////ssy
			tm.ClassList.get(ClassID).TimeParts=TimeParts;
			//{2006=10, 2004=2, 1974=2, 2005=1, 1999=3}
			Map<String,Integer>TPMerge=tm.TimePartsMerge(TimeParts);//////////////////////////ssy
			tm.ClassList.get(ClassID).TimePartsMerges=TPMerge;
			//[1974, 1999, 2004, 2005, 2006]
			TimeParts=tm.SortTimeParts(TPMerge);//////////////////////////ssy
			//(25,2)(5,2)(1,1)(1,1)
			List<SpanFrequency>TimeSpans=tm.CalculateTimeSpanYear(TimeParts,TPMerge);//////////////////////////ssy
			tm.ClassList.get(ClassID).TimeSpans=TimeSpans;
			//(25,2)(5,2)(1,2)
			Map<String,Integer>TSMerge=tm.TimeSpansMerge(TimeSpans);//////////////////////////ssy
			tm.ClassList.get(ClassID).TimeSpansMerges=TSMerge;
			tm.ClassList.get(ClassID).Sensitivity=new String[k];
			try {
				tm.ClassList.get(ClassID).Sensitivity[0]=tm.SortTimeSpans(TSMerge, "T").get(0).toString();
				tm.ClassList.get(ClassID).Sensitivity[1]=tm.SortTimeSpans(TSMerge, "F").get(0).toString();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			
		}
		tm.OutPut(outPath);
	}
}
