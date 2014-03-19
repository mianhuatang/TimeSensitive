package Cluster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


public class DBSCANnew {
	double Eps = 0.000000001; // ����뾶
	int MinPts = 3; // �ܶ�
//	public static HashMap<Integer, HashMap<Integer, Double>>similarityMap=new HashMap<Integer,HashMap<Integer,Double>>();
	public static HashMap<String, HashMap<String, Double>>similarityMap=new HashMap<String,HashMap<String,Double>>();
//	public static Map <TopicObject,Double> similarityMap=new HashMap<TopicObject,Double>();
	public static ArrayList<DataObjectnew> objects= new ArrayList<DataObjectnew>();
//	public double getSimilarity(int topicID1, int topicID2) {
//		double sim = 0;
//		if(similarityMap.containsKey(topicID1)){
//			HashMap<Integer,Double> secondMap=similarityMap.get(topicID1);
//			if(secondMap.containsKey(topicID2))
//				sim=similarityMap.get(topicID1).get(topicID2);
//		}
////		TopicObject to=new TopicObject(topicID1, topicID2);
////		if(similarityMap.containsKey(to))
////			sim=similarityMap.get(to);
//		return sim;
//	}
	public double getSimilarity(String queryID1, String queryID2) {
		double sim = 0;
		if(similarityMap.containsKey(queryID1)){
			HashMap<String,Double> secondMap=similarityMap.get(queryID1);
			if(secondMap.containsKey(queryID2))
				sim=similarityMap.get(queryID1).get(queryID2);
		}
//		TopicObject to=new TopicObject(topicID1, topicID2);
//		if(similarityMap.containsKey(to))
//			sim=similarityMap.get(to);
		return sim;
	}
//	public static void putSimilarity() throws IOException{
////		String filename = "./similarity2.txt";
//		String filename = "./urlSim.txt";
////		String filename = "F:\\research\\111\\201307\\MS\\SimilarityMerge\\similarityTest.txt";
//		FileReader fr;
//		fr = new FileReader(filename);
//		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
//		String line="";
//		int i=0;
//		while(( line=input.readLine())!=null){
//			String []linearray=line.split("\t");
//			if(linearray.length<5)
//				continue;
//			try {
//				int topicID1=Integer.parseInt(linearray[3]);
//				int topicID2=Integer.parseInt(linearray[4]);
//				double sim=Double.parseDouble(linearray[0]);
//				if(!similarityMap.containsKey(topicID1)){
//					HashMap<Integer,Double> secondMap=new HashMap<Integer,Double>();
//					secondMap.put(topicID2, sim);
//					similarityMap.put(topicID1, secondMap);
//				}
//				else{
//					HashMap<Integer,Double> secondMap=similarityMap.get(topicID1);
//					secondMap.put(topicID2, sim);
//					similarityMap.put(topicID1, secondMap);
//				}
//					
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			i++;
//			System.out.println("i="+i);
//		}
//		
//	}

		public static void putSimilarity() throws IOException{
//			String filename = "./similarity2.txt";
			String filename = "./urlSim.txt";
//			String filename = "F:\\research\\111\\201307\\MS\\SimilarityMerge\\similarityTest.txt";
			FileReader fr;
			fr = new FileReader(filename);
			BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
			String line="";
			int i=0;
			String []linearray=null;
			while(( line=input.readLine())!=null){
				linearray=line.split("\t");
				if(linearray.length<3)
					continue;
				try {
//					String queryID1=linearray[0];
//					String queryID2=linearray[1];
					double sim=Double.parseDouble(linearray[2]);
					if(sim==0){
						System.out.println("continue");
						continue;
					}
					if(!similarityMap.containsKey(linearray[0])){
						HashMap<String,Double> secondMap=new HashMap<String,Double>();
						secondMap.put(linearray[1], sim);
						similarityMap.put(linearray[0], secondMap);
					}
					else{
						HashMap<String,Double> secondMap=similarityMap.get(linearray[0]);
						secondMap.put(linearray[1], sim);
						similarityMap.put(linearray[0], secondMap);
					}
						
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
				System.out.println("i="+i);
			}
			
		}
		
	public Vector<DataObjectnew> getNeighbors(DataObjectnew p,ArrayList<DataObjectnew> objects) throws  Exception {
		Vector<DataObjectnew> neighbors = new Vector<DataObjectnew>();
		Iterator<DataObjectnew> iter = objects.iterator();
		while (iter.hasNext()) {
			DataObjectnew q = iter.next();
			double d=0;
			if(p.queryID.equals(q.queryID))
				d=1;
			else 
				d=getSimilarity(p.queryID,q.queryID);
			if (d >= Eps)
				neighbors.add(q);
		}
		return neighbors;
	}

	public int dbscan(ArrayList<DataObjectnew> objects) throws 
			Exception {
		int clusterID = 0;
		boolean AllVisited = false;
		int i = 0;
		while (!AllVisited) {
			Iterator<DataObjectnew> iter = objects.iterator();
			while (iter.hasNext()) {
				System.out.println("i:" + i++);
				DataObjectnew p = iter.next();
				if (p.isVisited)
					continue;
				AllVisited = false;
				p.setIsVisited(true); // ��Ϊvisited����Ѿ�ȷ�������Ǻ��ĵ㻹�Ǳ߽��
				Vector<DataObjectnew> neighbors = getNeighbors(p, objects);
				if (neighbors.size() < MinPts) {
					if (p.getCid() <= 0)
						p.setCid(-1); // cid��ʼΪ0,��ʾδ���ࣻ���������Ϊһ����������Ϊ-1��ʾ����
				} else {
					System.out.println("the number of neighbors of " + ","
							+ neighbors.size());
					if (p.getCid() <= 0) {
						clusterID++;
						expandCluster(p, neighbors, clusterID, objects);
					} else {
						int iid = p.getCid();
						expandCluster(p, neighbors, iid, objects);
					}
				}
				AllVisited = true;
			}
		}
		return clusterID;
	}

	private void expandCluster(DataObjectnew p, Vector<DataObjectnew> neighbors,
			int clusterID, ArrayList<DataObjectnew> objects) throws 
			Exception {
		p.setCid(clusterID);
		Iterator<DataObjectnew> iter = neighbors.iterator();
		while (iter.hasNext()) {
			DataObjectnew q = iter.next();
			if (!q.isVisited) {
				q.setIsVisited(true);
				Vector<DataObjectnew> qneighbors = getNeighbors(q, objects);
				if (qneighbors.size() >= MinPts) {
					Iterator<DataObjectnew> it = qneighbors.iterator();
					while (it.hasNext()) {
						DataObjectnew no = it.next();
						if (no.getCid() <= 0)
							no.setCid(clusterID);
					}
				}
			}
			if (q.getCid() <= 0) { 
				q.setCid(clusterID);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		putSimilarity();
//		String filename = "F:\\research\\111\\201307\\MS\\Processing\\fenci-url-frequentquery.txt";
		String filename = "./frequent-query-clicked.txt";
		
		FileReader fr;
		fr = new FileReader(filename);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		
		while(( line=input.readLine())!=null){
			String []linearray=line.split("\t");
			DataObjectnew object=new DataObjectnew();
			object.topicID=Integer.parseInt(linearray[1]);
			object.pquery=linearray[0];
			object.queryID=linearray[4];
			object.full=line;
			objects.add(object);
		}
		DBSCANnew dbs = new DBSCANnew();
		dbs.dbscan(objects);
		
//		String outfile = "F:\\research\\111\\201307\\MS\\Cluster\\DBScanCluster.txt";
		String outfile = "./urlCluster.txt";
		FileWriter output = new FileWriter(outfile);
		for (DataObjectnew object : objects) {
			String outline = "";
			outline += object.getCid() + "\t" + object.getFull();
			output.append(outline + "\n");
		}
		output.flush();
		output.close();
		output = null;
		input.close();
	}
}
