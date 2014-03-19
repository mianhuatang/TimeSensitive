package Similarity;

import java.io.UnsupportedEncodingException;
import java.util.List;

import net.didion.jwnl.JWNLException;

import Preprocessing.Synonym;
import Tools.CutUrl;
import Tools.UrlFenCi;

public class CalculateSimilarity {
	public double CalDiceSim(String pquery,String qquery){
		double sim=0;
		String []pqueryArray=pquery.split(" ");
		String []qqueryArray=qquery.split(" ");
		double samecount=0;
		for(int i=0;i<pqueryArray.length;i++){
			for(int j=0;j<qqueryArray.length;j++){
				if(pqueryArray[i].equals(qqueryArray[j]))
						samecount++;
			}
		}
		sim=2*samecount/(pqueryArray.length+qqueryArray.length);
		return sim;
	}
	public double CalSessionDocSim(String pquery,String query){
		double sim=0;
		return sim;
	}
	public double CalQuerySim(String pquery,String qquery) throws Exception, JWNLException {
		double sim=0;
		String []pqueryArray=pquery.split(" ");
		String []qqueryArray=qquery.split(" ");
		double samecount=0;
		Synonym sy=new Synonym();
		for(int i=0;i<pqueryArray.length;i++){
			for(int j=0;j<qqueryArray.length;j++){
//				if(pqueryArray[i].equals(qqueryArray[j])||sy.IsSynonym(pqueryArray[i], qqueryArray[j]))
				if(pqueryArray[i].equals(qqueryArray[j]))
						samecount++;
			}
		}
		sim=samecount/((pqueryArray.length>qqueryArray.length)?pqueryArray.length:qqueryArray.length);
		
		return sim;
	}
	public double CalUrlFenCiSim(String url1,String url2) throws UnsupportedEncodingException{
		double sim=0;
		double samecount=0;
		UrlFenCi fc=new UrlFenCi();
		List<String>url1List=fc.UrlFenCiFunction(url1);
		List<String>url2List=fc.UrlFenCiFunction(url2);
		for(int i=0;i<url1List.size();i++){
			for(int j=0;j<url2List.size();j++){
				if(url1List.get(i).equals(url2List.get(j)))
					samecount++;
			}
		}
		sim=samecount/((url1List.size()>url2List.size())?url1List.size():url2List.size());
		return sim;
	}
	public double CalUrlSim(String url1,String url2){
		if(url1.equals(url2))
			return 1;
		else
			return 0;
	}
	public double CalCutUrlSim(String url1,String url2){
		CutUrl cu=new CutUrl();
		url1=cu.CutUrlFunction(url1);
		url2=cu.CutUrlFunction(url2);
		if(url1.equals(url2))
			return 1;
		else
			return 0;
	}
	public double CalSessionSim(String session1,String session2){
		if(session1.equals(session2))
			return 1;
		else
			return 0;
	}
}
