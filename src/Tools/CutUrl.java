package Tools;

public class CutUrl {
	public String CutUrlFunction(String url){//url后半部分
		int start=url.indexOf("://");
		int end=url.indexOf("/",start+3);
		url=url.substring(start+3, end);
		return url;
	}
}
