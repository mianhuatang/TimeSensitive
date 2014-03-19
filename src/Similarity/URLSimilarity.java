package Similarity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLSimilarity {
	public static Map<String,ArrayList<String>> queryURLsMap=new HashMap<String,ArrayList<String>>();
	public static Map<String,String> queryMap=new HashMap<String,String>();
	public static List<QueryURLs>queryURlsList=new ArrayList<QueryURLs>();
	public final static int queryIDPos=4;
	public final static int urlPos=10;
	public static void CalculateURLSimilarity(String infile) throws IOException{
		String outfile="./urlSim.txt";
		FileReader fr = new FileReader(infile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);	
		FileWriter output = new FileWriter(outfile);
		String line="";
		
		while(( line=input.readLine())!=null){
			//line="a9824abda15e4128	http://0.r.msn.com/?ld=2v9BjOUkPSkEHIgT6DO+PPe8E9Pks8QKy7PZBy/wemMxvSMBkmYoJzomEVrRwrTc2DDgJCfI9xWxPVWu7rb59WSqwo9aSSBx0CV0dyyBiGGtd3pcIrSFN94NbLCZq1GefD46z7fnt0CBxAWZCW63RJt/Mtdz2Zy3W0fo9DHz00dBfl1Lj7y7BMjoJXLD0xFI1YIcEIYCs+PGBH0W7WNaAWEiaXjMmsZ7ETbyZBnAE4zsrfU9p2hWYL	http://0.r.msn.com/?ld=2vg+Q7aBbVIYntmsqj5bEKzHweDeY4sXbLWhvkJ+zVqDQbsoksEZZpmMCjdpsgGT1qFoMLryHzzhuYL93EK5wrL6sVx2iXp3E/2p6segTvct+8GCN+QiyTNznFYjqNnEsY+8MJFgxuIl69TPtd47CT7ROjrtnPAUvDIDgc6aQIhs/9eZ9C172kNo8mXR82NAxZN3TJO1dfsj0dXlLqe7vTVs6sbAYA5HZ0a+odhnAU/qxnCUXLfJ8gaUGcHZdkY3P1XY0Guw7lILUI/b+HtVksE28mQZ2cZv4ycp3ZSWknCw==	http://www.wreckedbike.com/Honda_CBR_1000RR_Parts_Bike.htm	http://0.r.msn.com/?ld=2vg+Q7aBbVIYntmsqj5bEKzHweDeY4sXbLWhvkJ+zVqDQbsoksEZZpmMCjdpsgGT1qFoMLryHzzhuYL93EK5wrL6sVx2iXp3E/2p6segTvct+8GCN+QiyTNznFYjqNnEsY+8MJFgxuIl69TPtd47CT7ROjrtnPAUvDIDgc6aQIhs/9eZ9C172kNo8mXR82NAxZN3TJO1dfsj0dXlLqe7vTVs6sbAYA5HZ0a+odhnAU/qxnCUXLfJ8gaUGcHZdkY3P1XY0Guw7lILUI/b+HtVksE28mQZ2cZv4ycp3ZSWknCw==	http://0.r.msn.com/?ld=2vRcZTukJ5pShWn6qXnKHO3zrcGq/IRCaHIqb9JzQb5/iUYMuP/+yQU/qQyssmaZMhcPj04w58J5/yAsdN+kgUVywZt9hi8ReSCluTRGwQJ6X2ne7FSasw+V6lmlEDqNYsWbR9OppYr2gdkiJiqFsEf8omhvdHfARFzRYUk4SS2AbpPNMA0eDuXKHAPWu5055IBSx+JFmQcOdpRFbUVgqsG4aidfjbf7pgzHs5DhzsN7ZOiTITbyZB2BepOVxJEjxHYKkL";
			String []linearray=line.split("\t");
			QueryURLs qurls=new QueryURLs();
			qurls.queryID=linearray[0];
			for(int i=1;i<linearray.length;i++){
//				qurls.URLs.add(linearray[i]);
				qurls.urlsSet.add(linearray[i]);
			}
			queryURlsList.add(qurls);
		}
		for(int i=0;i<queryURlsList.size();i++){
			QueryURLs qurlsi=queryURlsList.get(i);
			for(int j=i;j<queryURlsList.size();j++){
				QueryURLs qurlsj=queryURlsList.get(j);
				int commonCount=0;
				for(String urli:qurlsi.urlsSet){
					for(String urlj:qurlsj.urlsSet){
						if(urli.equals(urlj))
							commonCount++;
					}
				}
//				for(String urli:qurlsi.URLs){
//					for(String urlj:qurlsj.URLs){
//						if(urli.equals(urlj))
//							commonCount++;
//					}
//				}
//				System.out.println((qurlsi.URLs.size()>qurlsj.URLs.size())?qurlsi.URLs.size():qurlsj.URLs.size());
				double urlSim=commonCount*1.0/((qurlsi.urlsSet.size()>qurlsj.urlsSet.size())?qurlsi.urlsSet.size():qurlsj.urlsSet.size());
				output.append(qurlsi.queryID+"\t"+qurlsj.queryID+"\t"+urlSim+"\n");
			}
		}
		output.flush();
		output.close();
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String filename = "./fenci-url-frequentquery.txt";
//		FileReader fr;
//		fr = new FileReader(filename);
//		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String outfile1 = "./queryURLs.txt";
//		String outfile1 ="F:\\research\\111\\201307\\MS\\URLCluster\\queryURLs.txt";
//		String outfile2="./frequent-query-clicked.txt";
//		FileWriter output1 = new FileWriter(outfile1);
//		FileWriter output2 = new FileWriter(outfile2);
//		
//		String line="";
//		while(( line=input.readLine())!=null){
//			String []linearray=line.split("\t");
//			String key=linearray[queryIDPos];
//			if(queryURLsMap.containsKey(key)){
//				ArrayList<String>lineList=queryURLsMap.get(key);
//				lineList.add(linearray[urlPos]);
//				queryURLsMap.put(key, lineList);
//			}
//			else{
//				ArrayList<String>lineList=new ArrayList<String>();
//				lineList.add(linearray[urlPos]);
//				queryURLsMap.put(key, lineList);
//			}
//			if(!queryMap.containsKey(key)){
//				queryMap.put(key, line);
//			}
//			
//		}
//		for(String key:queryURLsMap.keySet()){
//			ArrayList<String>urlList=queryURLsMap.get(key);
//			StringBuffer urls=new StringBuffer();
//			urls.append(key).append("\t");
//			for(int i=0;i<urlList.size()-1;i++)
//				urls.append(urlList.get(i)).append("\t");
//			urls.append(urlList.get(urlList.size()-1));
//			output1.append(urls+"\n");
//		}
//		for(String key:queryMap.keySet()){
//			output2.append(queryMap.get(key)+"\n");
//		}
//		output1.flush();
//		output1.close();
//		output2.flush();
//		input.close();
//		output2.close();
		CalculateURLSimilarity(outfile1);
	}

}
