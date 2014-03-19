package Cluster;

public class DataObjectnew {
	int topicID;
	String pquery;
	String full;
	Boolean isVisited=false;
	int Cid=0;//��ֵΪ0��ʾδ����
	String queryID;
	public String getQueryID() {
		return queryID;
	}
	public void setQueryID(String queryID) {
		this.queryID = queryID;
	}
	public int getTopicID() {
		return topicID;
	}
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	public String getPquery() {
		return pquery;
	}
	public void setPquery(String pquery) {
		this.pquery = pquery;
	}
	public String getFull() {
		return full;
	}
	public void setFull(String full) {
		this.full = full;
	}
	public Boolean getIsVisited() {
		return isVisited;
	}
	public void setIsVisited(Boolean isVisited) {
		this.isVisited = isVisited;
	}
	public int getCid() {
		return Cid;
	}
	public void setCid(int cid) {
		Cid = cid;
	}
}

