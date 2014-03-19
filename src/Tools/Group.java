package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
	Map<String,List<String>> full=new HashMap<String,List<String>>();
	public List<String> SortTimeParts(Map<String,List<String>>TSMerge){
		List<String>SortList=new ArrayList<String>();
		for(String time:TSMerge.keySet()){
			SortList.add(time);
		}
		int[]SortArray=new int[SortList.size()];
		for(int i=0;i<SortList.size();i++){
			SortArray[i]=Integer.parseInt(SortList.get(i));
		}
		Arrays.sort(SortArray);
		SortList=new ArrayList<String>();
		for(int i=0;i<SortArray.length;i++)
			SortList.add(SortArray[i]+"");
		return SortList;
	}
	public static void main(String[]args) throws Exception{
		Group group=new Group();
		String inFile="F:\\time-sensitive query\\MS Data Set\\newqueryfilter\\toMeng\\DBScan1\\joinurl-group-dbscan1-filgerblank-preprocessing-filter-filter-newfilter1-srfp20060501-20060531.queries.txt";
		String outFile="F:\\time-sensitive query\\MS Data Set\\newqueryfilter\\toMeng\\DBScan1\\group-joinurl-group-dbscan1-filgerblank-preprocessing-filter-filter-newfilter1-srfp20060501-20060531.queries.txt";
		FileReader fr=new FileReader(inFile);
		BufferedReader input=new BufferedReader(fr, 20*1024*1024);
		FileWriter fw=new FileWriter(outFile);
		String line="";
		while((line=input.readLine())!=null){
			String[]lineArray=line.split("\t");
			if(group.full.containsKey(lineArray[0])){
				List<String>linelist=group.full.get(lineArray[0]);
				linelist.add(line);
				group.full.put(lineArray[0], linelist);
			}
			else{
				List<String>linelist=new ArrayList<String>();
				linelist.add(line);
				group.full.put(lineArray[0], linelist);
			}
		}
		List<String>SortList=group.SortTimeParts(group.full);
		for(int i=0;i<SortList.size();i++){
			List<String>linelist=group.full.get(SortList.get(i));
			for(int j=0;j<linelist.size();j++){
				fw.append(linelist.get(j)+"\n");
			}
		}
		fw.flush();
		fw.close();
	}
}
