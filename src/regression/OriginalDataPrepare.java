package regression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Sensitivity.TimeCount;

class SensitivityFrequency{
	int sensitivity;
	int frequency;
}
public class OriginalDataPrepare {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public List<TimeCount> SortTimeParts(Map<Integer,Integer>TSMerge){//give <y1,t1>,<y2,t2>, order by y
		List<Integer>SortList=new ArrayList<Integer>();
		for(Integer time:TSMerge.keySet()){
			SortList.add(time);
		}
		Integer[]SortArray=new Integer[SortList.size()];
		for(int i=0;i<SortList.size();i++){
			SortArray[i]=SortList.get(i);
		}
		Arrays.sort(SortArray);
		List<TimeCount>timecountlist=new ArrayList<TimeCount>();
		
		for(int i=0;i<SortArray.length;i++){
			TimeCount tc=new TimeCount();
			tc.time=SortArray[i];
			tc.count=TSMerge.get(SortArray[i]);
			timecountlist.add(tc);
		}
		
		return timecountlist;
	}
	public double CalculateEntropy(Map<Integer,Integer>entropysublist){
		double result=0;
		for(Integer key:entropysublist.keySet()){
			double p=entropysublist.get(key)*1.0/entropysublist.size();
			result+=p*Math.log(p)/Math.log(2);
		}
		return result*-1;
	}
	public int MostFrequentSensitivity(Map<Integer,Integer>maxsublist){
		int sensitivity=0;
		int max=0;
		for(Integer key:maxsublist.keySet()){
			if(maxsublist.get(key)>max){
				sensitivity=key;
				max=maxsublist.get(key);
			}
		}
		return sensitivity;
	}
	public String Entropy(List<SensitivityFrequency>timecountlist,int rightsensitivity,int yearcount){
		String line="";
		for(int step=1;step<=timecountlist.size();step++){// for each length
			for(int i=0;i<timecountlist.size();i++){
				int j=i+step;
				if(j>timecountlist.size())
					continue;
				Map<Integer,Integer>entropysublist=new HashMap<Integer, Integer>();
				Map<Integer,Integer>maxsublist=new HashMap<Integer, Integer>();//most frequency sensitivity
				int minsensitivity=1000;
				int minsupport=100000;
				
				String sublistString="";
				
				for(int k=i;k<j;k++){//from i to j,length=step
					sublistString+="("+timecountlist.get(k).sensitivity+":"+timecountlist.get(k).frequency+")";
					if(timecountlist.get(k).sensitivity<minsensitivity){
						minsensitivity=timecountlist.get(k).sensitivity;
					}
					if(timecountlist.get(k).frequency<minsupport)//support
						minsupport=timecountlist.get(k).frequency;
					
					if(entropysublist.containsKey(timecountlist.get(k).sensitivity)){//build entropy subsequence
						int count=entropysublist.get(timecountlist.get(k).sensitivity);
						count++;
						entropysublist.put(timecountlist.get(k).sensitivity, count);
					}
					else
						entropysublist.put(timecountlist.get(k).sensitivity, 1);
					
					if(maxsublist.containsKey(timecountlist.get(k).sensitivity)){//build most frequency subsequence
						int frequency=maxsublist.get(timecountlist.get(k).sensitivity);
						frequency+=timecountlist.get(k).frequency;
						maxsublist.put(timecountlist.get(k).sensitivity, frequency);
					}
					else
						maxsublist.put(timecountlist.get(k).sensitivity, timecountlist.get(k).frequency);
				}
				int S=0;
				if(rightsensitivity==minsensitivity||rightsensitivity==MostFrequentSensitivity(maxsublist)){
					S=1;
				}
				if(minsensitivity==rightsensitivity)
					minsensitivity=1;
				else
					minsensitivity=0;
				//min,most frequency,length,entropy,support
//				line+=S+","+minsensitivity+","+MostFrequentSensitivity(maxsublist)+","+step*1.0/yearcount+","+CalculateEntropy(entropysublist)+","+minsupport*1.0/yearcount+";";
				
				
				line+=S+","+minsensitivity+","+MostFrequentSensitivity(maxsublist)+","+step*1.0/timecountlist.size()+","+CalculateEntropy(entropysublist)+","+minsupport*1.0+"\t"+sublistString+";";
			}
		}
		line=line.substring(0,line.length()-1);
		return line;
	}
	public void msExtractFromFile(String inPath,String outPath) throws IOException{
		FileReader fr = new FileReader(inPath);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter output=new FileWriter(outPath);
		
		int count=1;
		String line="";
		while((line=input.readLine())!=null){
			if(count==1){//sensitivity removed
				count++;
				continue;
			}
			count++;
			System.out.println("count:"+count);
			String []lineArray=line.split("\t");
			if((!lineArray[0].equals("Y")&&!lineArray[0].equals("y"))
					&&(!lineArray[1].equals("Y"))&&!lineArray[1].equals("y"))
				continue;
			else{
				if(lineArray.length<7)
					continue;
				String outline="";
				for(int i=0;i<=7;i++){
					if(i==5||i==6)
						continue;
					outline+=lineArray[i]+"\t";
				}
				String []lineArray7=lineArray[7].split("\\)\\(");
				
				Map<Integer,Integer>timecount=new HashMap<Integer, Integer>();
				int yearcount=0;
				for(int j=0;j<lineArray7.length;j++){
					if(j==0)
						lineArray7[j]=lineArray7[j].substring(1);
					if(j==lineArray7.length-1)
						lineArray7[j]=lineArray7[j].substring(0,lineArray7[j].length()-1);
					int y=Integer.parseInt(lineArray7[j].split(",")[0]);
					int f=Integer.parseInt(lineArray7[j].split(",")[1]);
					timecount.put(y, f);
					yearcount+=f;
				}
				List<TimeCount>timecountlist=SortTimeParts(timecount);
				List<SensitivityFrequency>sensitivitylist=new ArrayList<SensitivityFrequency>();
				for(int i=0;i<timecountlist.size()-1;i++){
					int j=i+1;
					int y=timecountlist.get(j).time-timecountlist.get(i).time;
					int f=timecountlist.get(j).count<timecountlist.get(i).count?timecountlist.get(j).count:timecountlist.get(i).count;
					SensitivityFrequency sf=new SensitivityFrequency();
					sf.sensitivity=y;
					sf.frequency=f;
					sensitivitylist.add(sf);
					outline+=y+","+f+";";
				}
				outline=outline.substring(0,outline.length()-1);
				int right=0;
				if(lineArray[4].equals("null")||lineArray[5].equals("null"))
					continue;
				if(lineArray[0].equals("Y")||lineArray[0].equals("y"))
					right=Integer.parseInt(lineArray[4]);
				else
					right=Integer.parseInt(lineArray[5]);
				String entropyline=Entropy(sensitivitylist,right,yearcount);
				String []entropylineArray=entropyline.split(";");
				for(String outline2:entropylineArray)
					output.append(outline+"\t"+outline2+"\n");
			}
		}
		output.flush();
		output.close();
	}
	public void aolExtractFromFile(String inPath,String outPath) throws IOException{
		FileReader fr = new FileReader(inPath);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter output=new FileWriter(outPath);
		
		int count=1;
		String line="";
		while((line=input.readLine())!=null&&count<=282){
			if(count==1){//sensitivity removed
				count++;
				continue;
			}
			count++;
			System.out.println("count:"+count);
			String []lineArray=line.split("\t");
			if((!lineArray[0].equals("Y")&&!lineArray[0].equals("y"))
					&&(!lineArray[1].equals("Y"))&&!lineArray[1].equals("y"))
				continue;
			else{
				if(lineArray.length<7)
					continue;
				String outline="";
				for(int i=0;i<=7;i++){
					if(i==5||i==6)
						continue;
					outline+=lineArray[i]+"\t";
				}
				String []lineArray7=lineArray[7].split("\\)\\(");
				
				Map<Integer,Integer>timecount=new HashMap<Integer, Integer>();
				int yearcount=0;
				for(int j=0;j<lineArray7.length;j++){
					if(j==0)
						lineArray7[j]=lineArray7[j].substring(1);
					if(j==lineArray7.length-1)
						lineArray7[j]=lineArray7[j].substring(0,lineArray7[j].length()-1);
					int y=Integer.parseInt(lineArray7[j].split(",")[0]);
					int f=Integer.parseInt(lineArray7[j].split(",")[1]);
					timecount.put(y, f);
					yearcount+=f;
				}
				List<TimeCount>timecountlist=SortTimeParts(timecount);
				List<SensitivityFrequency>sensitivitylist=new ArrayList<SensitivityFrequency>();
				for(int i=0;i<timecountlist.size()-1;i++){
					int j=i+1;
					int y=timecountlist.get(j).time-timecountlist.get(i).time;
					int f=timecountlist.get(j).count<timecountlist.get(i).count?timecountlist.get(j).count:timecountlist.get(i).count;
					SensitivityFrequency sf=new SensitivityFrequency();
					sf.sensitivity=y;
					sf.frequency=f;
					sensitivitylist.add(sf);
					outline+=y+","+f+";";
				}
				outline=outline.substring(0,outline.length()-1);
				int right=0;
				if(lineArray[4].equals("null")||lineArray[5].equals("null"))
					continue;
				if(lineArray[0].equals("Y")||lineArray[0].equals("y"))
					right=Integer.parseInt(lineArray[4]);
				else
					right=Integer.parseInt(lineArray[5]);
				String entropyline=Entropy(sensitivitylist,right,yearcount);
				String []entropylineArray=entropyline.split(";");
				for(String outline2:entropylineArray)
					output.append(outline+"\t"+outline2+"\n");
			}
		}
		output.flush();
		output.close();
	}
	public void NNExtractFromFile(String inPath,String outPath) throws IOException{
		FileReader fr = new FileReader(inPath);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter output=new FileWriter(outPath);
		
		int count=1;
		String line="";
		while((line=input.readLine())!=null){
			count++;
			System.out.println("count:"+count);
			String []lineArray=line.split("\t");
				if(lineArray.length<7)
					continue;
				String outline="";
				for(int i=0;i<=7;i++){
					if(i==5||i==6)
						continue;
					outline+=lineArray[i]+"\t";
				}
				String []lineArray7=lineArray[7].split("\\)\\(");
				
				Map<Integer,Integer>timecount=new HashMap<Integer, Integer>();
				int yearcount=0;
				for(int j=0;j<lineArray7.length;j++){
					if(j==0)
						lineArray7[j]=lineArray7[j].substring(1);
					if(j==lineArray7.length-1)
						lineArray7[j]=lineArray7[j].substring(0,lineArray7[j].length()-1);
					int y=Integer.parseInt(lineArray7[j].split(",")[0]);
					int f=Integer.parseInt(lineArray7[j].split(",")[1]);
					timecount.put(y, f);
					yearcount+=f;
				}
				List<TimeCount>timecountlist=SortTimeParts(timecount);
				List<SensitivityFrequency>sensitivitylist=new ArrayList<SensitivityFrequency>();
				for(int i=0;i<timecountlist.size()-1;i++){
					int j=i+1;
					int y=timecountlist.get(j).time-timecountlist.get(i).time;
					int f=timecountlist.get(j).count<timecountlist.get(i).count?timecountlist.get(j).count:timecountlist.get(i).count;
					SensitivityFrequency sf=new SensitivityFrequency();
					sf.sensitivity=y;
					sf.frequency=f;
					sensitivitylist.add(sf);
					outline+=y+","+f+";";
				}
				outline=outline.substring(0,outline.length()-1);
				int right=0;
				right=Integer.parseInt(lineArray[9]);
				String entropyline=Entropy(sensitivitylist,right,yearcount);
				String []entropylineArray=entropyline.split(";");
				for(String outline2:entropylineArray)
					output.append(outline+"\t"+outline2+"\n");
		}
		output.flush();
		output.close();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		OriginalDataPrepare dp=new OriginalDataPrepare();
		
		//AOL
//		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Sensitivity/SensitivityRemoved.txt";
//		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/AOLdata.txt";
//		dp.aolExtractFromFile(inPath, outPath);
		
		//MS
		String inPath="F:\\research\\111\\time-sensitive query\\MS Data Set\\queryfilter\\timaspan\\labled-timespan-yearquery少一列.txt";
		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/msdata.txt";
		dp.msExtractFromFile(inPath, outPath);
		//NN
//		String inPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/NN.txt";
//		String outPath="F:/research/111/time-sensitive query/AOL Data Set/All/Regression/purity&length/NNdata.txt";
//		dp.NNExtractFromFile(inPath, outPath);
	}

}
