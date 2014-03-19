package Cluster;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import net.didion.jwnl.JWNLException;

import Similarity.CalculateSimilarity;

public class DBSCAN {
	double Eps=0.6;   //����뾶
	int MinPts=3;   //�ܶ�
//�����Լ����Լ��ľ�����0,�����Լ�Ҳ���Լ���neighbor
	
	public Vector<DataObject> getNeighbors(DataObject p,ArrayList<DataObject> objects) throws JWNLException, Exception{
		Vector<DataObject> neighbors=new Vector<DataObject>();
		Iterator<DataObject> iter=objects.iterator();
		CalculateSimilarity sim=new CalculateSimilarity();
		while(iter.hasNext()){
			DataObject q=iter.next();
			String pquery=p.getPquery();
			String qquery=q.getPquery();
//			double d=sim.calSim(pquery,qquery);
//			d+=sim.CalCutUrlSim(p.getURL(),q.getURL());
//			d+=sim.CalSessionSim(p.getSessionID(),q.getSessionID());
//			d=d/3;
			double d=0.6*sim.CalQuerySim(pquery,qquery);
			d+=0.3*sim.CalUrlFenCiSim(p.getURL(),q.getURL());
			d+=0.1*sim.CalSessionSim(p.getSessionID(),q.getSessionID());
			if(d>=Eps)
				neighbors.add(q);
	        }
	        return neighbors;
	}
	
	public int dbscan(ArrayList<DataObject> objects) throws JWNLException, Exception{
		int clusterID=0;
		boolean AllVisited=false;
		int i=0;
		while(!AllVisited){
			Iterator<DataObject> iter=objects.iterator();
			while(iter.hasNext()){
				System.out.println("i:"+i++);
				DataObject p=iter.next();
				if(p.isVisited)
					continue;
				AllVisited=false;
				p.setIsVisited(true);     //��Ϊvisited����Ѿ�ȷ�������Ǻ��ĵ㻹�Ǳ߽��
				Vector<DataObject> neighbors=getNeighbors(p,objects);
				if(neighbors.size()<MinPts){
					if(p.getCid()<=0)
						p.setCid(-1);       //cid��ʼΪ0,��ʾδ���ࣻ���������Ϊһ����������Ϊ-1��ʾ����
				}else{
					System.out.println("the number of neighbors of "+","+neighbors.size());
					if(p.getCid()<=0){
						clusterID++;
						expandCluster(p,neighbors,clusterID,objects);
					}else{
						int iid=p.getCid();
						expandCluster(p,neighbors,iid,objects);
					}
				}
				AllVisited=true;
			}
		}
		return clusterID;
		}
	private void expandCluster(DataObject p, Vector<DataObject> neighbors,int clusterID,ArrayList<DataObject> objects) throws JWNLException, Exception {
		p.setCid(clusterID);
		Iterator<DataObject> iter=neighbors.iterator();
		while(iter.hasNext()){
			DataObject q=iter.next();
			if(!q.isVisited){
				q.setIsVisited(true);
				Vector<DataObject> qneighbors=getNeighbors(q,objects);
				if(qneighbors.size()>=MinPts){
					Iterator<DataObject> it=qneighbors.iterator();
					while(it.hasNext()){
						DataObject no=it.next();
						if(no.getCid()<=0)
							no.setCid(clusterID);
					}
				}
			}
			if(q.getCid()<=0){       //q�����κδصĳ�Ա
				q.setCid(clusterID);
			}
		}
	}
	public static void main(String [] args) throws JWNLException, Exception{
		DataSource ds=new DataSource();
		String filename="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\" +
				"YearSensitive\\Filter\\FilterBlank-PreProcessing-JoinUrls-YearSensitive.txt";
//		String filename="/date/sunshengyun/FilterBlank-PreProcessing-JoinUrls-YearSensitive.txt";
//		String outfile="/date/sunshengyun/DBScan-result.txt";
		ds.readFile(filename);
		DBSCAN dbs=new DBSCAN();
		dbs.dbscan(ds.objects);
		String outfile="F:\\research\\111\\time-sensitive query\\MS Data Set\\newqueryfilter\\" +
				"YearSensitive\\DBScan\\DBScan-FilterBlank-PreProcessing-JoinUrls-YearSensitive.txt";
		FileWriter output = new FileWriter(outfile);
		for(DataObject object:ds.objects){
			String line="";
			line+=object.getCid()+"\t"+object.getFull();
			output.append(line + "\n");
		}
		output.flush();
		output.close();
		output=null;
	}
}
