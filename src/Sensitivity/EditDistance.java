package Sensitivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class EditDistance {
	public List<TimeCount>sortTimeCount(List<TimeCount> timeCountList)
	{
		TimeCount temp=new TimeCount();
		for(int i=0;i<timeCountList.size()-1;i++)
		{
			for(int j=0;j<timeCountList.size()-i-1;j++)
			{
				if(timeCountList.get(j).time>timeCountList.get(j+1).time)
				{
					temp=timeCountList.get(j);
					timeCountList.set(j, timeCountList.get(j+1));
					timeCountList.set(j+1, temp);
				}
			}
		}
		return timeCountList;
	}
	public int findMinED(List<SpanFrequency>TimeSpans){
		int sensitivity=0;
		double min=TimeSpans.get(0).wED;
		sensitivity=Integer.parseInt(TimeSpans.get(0).time);
		for(SpanFrequency ts:TimeSpans){
			if(ts.wED<min){
				min=ts.wED;
				sensitivity=Integer.parseInt(ts.time);
			}
		}
		return sensitivity;
	}
	public String unweightedED(List<TimeCount>TimeCountList,List<SpanFrequency>TimeSpans)
	{
		TimeCountList=sortTimeCount(TimeCountList);
		int sensitivity=0;
		
		for(int i=0;i<TimeSpans.size();i++)// for every sensitivity s, calculate its weighted ED
		{
			int S=0;//the number of full time
			int s=Integer.parseInt(TimeSpans.get(i).time);//s:sensitivity
			int edits=0;//the number of edits, equals the number of add adding the number of remove
			for(int j=0;j<TimeCountList.size()-1;j++)
			{
				int last=TimeCountList.get(TimeCountList.size()-1).time;
				int first=TimeCountList.get(0).time; 
				if((TimeCountList.get(j+1).time-TimeCountList.get(j).time)==Integer.parseInt(TimeSpans.get(i).time))//j+1-j=s
				{
					int timeJ=TimeCountList.get(j).time;
					int timeJ1=TimeCountList.get(j+1).time;
					//calculate S
					S+=(last-timeJ1)/s;
					S+=(timeJ-first)/s;
					S+=2;
					
					int k=j;
					int n=j+1;
					
					int kcount=0;
					int ncount=0;
					for(int time=timeJ;time>=first;time-=s)//to the left
					{
						if(time==TimeCountList.get(k).time)
						{
							kcount++;
							k--;
							continue;
						}
						boolean flag=false;
						while(time>TimeCountList.get(k).time)//the number of add
						{
							edits++;
							time-=s;
							flag=true;
						}
						if(flag)
							time+=s;
						boolean flag1=false;
						while(time<TimeCountList.get(k).time)//the number of remove
						{
							kcount++;
							edits++;
							k--;
							if(k<0)
							{
								edits++;
								break;
							}
							flag1=true;
						}
						if(flag1)
							time+=s;
					}
					if(kcount!=j+1)
						for(int p=0;p<j+1-kcount;p++)
							edits++;//remove 0
					for(int time=timeJ1;time<=last;time+=s)	
					{
						if(time==TimeCountList.get(n).time)
						{
							n++;
							ncount++;
							continue;
						}
						while(time>TimeCountList.get(n).time)//the number of remove
						{
							ncount++;
							edits++;
							n++;
							if(n>TimeCountList.size()-1)
							{
								edits++;
								break;
							}
						}
						boolean flag=false;
						while(time<TimeCountList.get(n).time)//the number of add
						{
							edits++;
							time+=s;
							flag=true;
						}
						if(flag)
							time-=s;
					}
					if(ncount!=TimeCountList.size()-j-1)
						for(int q=TimeCountList.size()-1;q>j+ncount;q--)
						edits++;//remove the last
					break;
				}
			}
			double wED=(double)edits/S;
			SpanFrequency t=TimeSpans.get(i);
			t.wED=wED;
			TimeSpans.set(i, t);
		}
		sensitivity=findMinED(TimeSpans);
		String line="";
		line+=sensitivity;
		line+="\t";
		line+=Print(TimeCountList, TimeSpans);
		return line;
	}
	public String weightedED(List<TimeCount>TimeCountList,List<SpanFrequency>TimeSpans)
	{
		TimeCountList=sortTimeCount(TimeCountList);
		int sensitivity=0;
		
		int weightAll=0;//the sum of all the frequency
		for(TimeCount tc:TimeCountList)
		{
			weightAll+=tc.count;
		}
		for(int i=0;i<TimeSpans.size();i++)// for every sensitivity s, calculate its weighted ED
		{
			int S=0;//the number of full time
			int s=Integer.parseInt(TimeSpans.get(i).time);//s:sensitivity
			int edits=0;//the number of edits, equals the number of add adding the number of remove
			for(int j=0;j<TimeCountList.size()-1;j++)
			{
				int last=TimeCountList.get(TimeCountList.size()-1).time;
				int first=TimeCountList.get(0).time; 
				if((TimeCountList.get(j+1).time-TimeCountList.get(j).time)==Integer.parseInt(TimeSpans.get(i).time))//j+1-j=s
				{
					int timeJ=TimeCountList.get(j).time;
					int timeJ1=TimeCountList.get(j+1).time;
					//calculate S
					S+=(last-timeJ1)/s;
					S+=(timeJ-first)/s;
					S+=2;
					
					int k=j;
					int n=j+1;
					
					int kcount=0;
					int ncount=0;
					for(int time=timeJ;time>=first;time-=s)//to the left
					{
						if(time==TimeCountList.get(k).time)
						{
							kcount++;
							k--;
							continue;
						}
						boolean flag=false;
						while(time>TimeCountList.get(k).time)//the number of add
						{
//							edits++;
//							edits+=0.5;
							edits+=1.5;
							time-=s;
							flag=true;
						}
						if(flag)
							time+=s;
						while(time<TimeCountList.get(k).time)//the number of remove
						{
							kcount++;
							edits+=TimeCountList.get(k).count;
							k--;
							if(k<0)
							{
//								edits++;
//								edits+=0.5;
								edits+=1.5;
								break;
							}
						}
					}
					if(kcount!=j+1)
						for(int p=0;p<j+1-kcount;p++)
						edits+=TimeCountList.get(p).count;//remove 0
					for(int time=timeJ1;time<=last;time+=s)	
					{
						if(time==TimeCountList.get(n).time)
						{
							n++;
							ncount++;
							continue;
						}
						while(time>TimeCountList.get(n).time)//the number of remove
						{
							ncount++;
							edits+=TimeCountList.get(n).count;
							n++;
							if(n>TimeCountList.size()-1)
							{
//								edits++;
//								edits+=0.5;
								edits+=1.5;
								break;
							}
						}
						boolean flag=false;
						while(time<TimeCountList.get(n).time)//the number of add
						{
//							edits++;
//							edits+=0.5;
							edits+=1.5;
							time+=s;
							flag=true;
						}
						if(flag)
							time-=s;
					}
					if(ncount!=TimeCountList.size()-j-1)
						for(int q=TimeCountList.size()-1;q>j+ncount;q--)
						edits+=TimeCountList.get(q).count;//remove the last
					break;
				}
			}
			double wED=((double)edits/weightAll)/S;
			SpanFrequency t=TimeSpans.get(i);
			t.wED=wED;
			TimeSpans.set(i, t);
		}
		sensitivity=findMinED(TimeSpans);
		String line="";
		line+=sensitivity;
		line+="\t";
		line+=Print(TimeCountList, TimeSpans);
		return line;
	}
	public String Print(List<TimeCount>TimeCountList,List<SpanFrequency>TimeSpans){
		String line="";
		for(int i=0;i<TimeSpans.size();i++){
			SpanFrequency sf=TimeSpans.get(i);
			line+="("+sf.time+","+sf.count+","+sf.wED+")";
		}
		return line;
	}
		
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Sensitivity/PquerySensitivityMinMostFre.txt";
		String outPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Sensitivity/15PquerySensitivityWED.txt";
//		String outPath="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/Sensitivity/PquerySensitivityUWED.txt";
		FileReader fr = new FileReader(inPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outPath);
		String line="";
		
		int linecount=0;
		while((line=input.readLine())!=null){
			//line="8706	 plymouth roadrunner 	1	1	(1,4)	(1970,2)(1971,7)(1969,19)	(1,2)(1,2)	";
			linecount++;
			String[]lineArray=line.split("\t");
			String sensitivityParts=lineArray[4];
			String timeParts=lineArray[5];
			String[]sensitivitys=sensitivityParts.split("\\)\\(");
			if(sensitivitys.length==1){
				output.append(line+"\n");
				System.out.println(linecount);
				continue;
			}
			List<TimeCount>TimeCountList=new ArrayList<TimeCount>();
			List<SpanFrequency>TimeSpans=new ArrayList<SpanFrequency>();
			for(int i=0;i<sensitivitys.length;i++){
				if(i==0){
					try {
						sensitivitys[i]=sensitivitys[i].substring(1);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(sensitivitys[i]);
					}
					
				}
				if(i==sensitivitys.length-1){
					try {
						sensitivitys[i]=sensitivitys[i].substring(0,sensitivitys[i].length()-1);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(sensitivitys[i]);
					}
					
				}
				String time=sensitivitys[i].split(",")[0];
				int count=Integer.parseInt(sensitivitys[i].split(",")[1]);
				SpanFrequency sf=new SpanFrequency();
				sf.time=time;
				sf.count=count;
				TimeSpans.add(sf);
			}
			String[]times=timeParts.split("\\)\\(");
			for(int i=0;i<times.length;i++){
				if(i==0){
					times[i]=times[i].substring(1);
				}
				if(i==times.length-1){
					times[i]=times[i].substring(0,times[i].length()-1);
				}
				int time=Integer.parseInt(times[i].split(",")[0]);
				int count=Integer.parseInt(times[i].split(",")[1]);
				TimeCount tc=new TimeCount();
				tc.time=time;
				tc.count=count;
				TimeCountList.add(tc);
			}
			EditDistance ed=new EditDistance();
			
			line=ed.weightedED(TimeCountList, TimeSpans)+"\t"+line;
//			line=ed.unweightedED(TimeCountList, TimeSpans)+"\t"+line;
			output.append(line+"\n");
			System.out.println(linecount);
		}
		output.flush();
		output.close();
	}

}
