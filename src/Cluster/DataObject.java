package Cluster;

public class DataObject {
	String Pquery;
	String URL;
	String SessionID;
	String full;
	Boolean isVisited=false;
	int Cid=0;//��ֵΪ0��ʾδ����
	public String getPquery() {
		return Pquery;
	}
	public void setPquery(String pquery) {
		Pquery = pquery;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
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
