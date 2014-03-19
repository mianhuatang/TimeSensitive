package Filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class ClassAttribute{
	List<String>QueryInfo;
	int year2;
	int year4;
}
public class ClusterFilterNoises {
	public Map<Integer,ClassAttribute> ClassInfo=new HashMap<Integer,ClassAttribute>();
	public void FilterNoises(String inFile,String outFile) throws Exception{
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		int count=0;
		while((line=input.readLine())!=null){
			count++;
			String []linearray=line.split("\t");
			if(linearray.length<6)
				System.out.println("error!");
			int ClassID=Integer.parseInt(linearray[0]);
			String query="";
			try {
				for(int i=1;i<linearray.length-1;i++){
					query+=linearray[i]+"\t";
				}
				query+=linearray[linearray.length-1];
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			if(ClassInfo.containsKey(ClassID)){
				ClassAttribute info=ClassInfo.get(ClassID);//list0:info except classid, list1:NO.2year,list2:NO.4year
				List<String> QueryInfo=info.QueryInfo;
				QueryInfo.add(query);
				int year2=info.year2;
				int year4=info.year4;
				try {
					if(linearray[4].length()==4)
					year2++;
				else if(linearray[4].length()==6)
					year4++;
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				info.QueryInfo=QueryInfo;
				info.year2=year2;
				info.year4=year4;
				ClassInfo.put(ClassID, info);
			}
			else{
				List<String>QueryInfo=new ArrayList<String>();
				int year2=0;
				int year4=0;
				QueryInfo.add(query);
				if(linearray[4].length()==4)
					year2++;
				else if(linearray[4].length()==6)
					year4++;
				ClassAttribute info=new ClassAttribute();
				info.QueryInfo=QueryInfo;
				info.year2=year2;
				info.year4=year4;
				ClassInfo.put(ClassID, info);
			}
			System.out.println("count:========"+count);
		}
		for(int key:ClassInfo.keySet()){
			String outline="";
			ClassAttribute info=ClassInfo.get(key);
			if((info.year4*1.0)/(info.year2+info.year4)>=0.5){
				for(String query:info.QueryInfo){
					outline=key+"\t"+query;
					output.append(outline+"\n");
				}
			}
		}
		output.flush();
		output.close();
		output = null;
		input.close();
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ClusterFilterNoises cfn=new ClusterFilterNoises();
		String inFile="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/DBScan/ClusteringURLFilterNoises.txt";
		String outFile="F:/research/111/time-sensitive query/MS Data Set/newqueryfilter/YearSensitive/DBScan/ClusteringURLFilterAllNoises.txt";
		cfn.FilterNoises(inFile, outFile);
	}

}
