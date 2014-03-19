package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Preprocessing.Stemmer;

public class UrlFenCi {
	public final static int urlPos=10;
	public String UrlRemoveHead(String url){
		int start=url.indexOf("://");
		url=url.substring(start+3);
		return url;
	}
	public String[] UrlFenCiByXieXian(String url){
		String []FenCiArray=url.split("/");
		return FenCiArray;
	}
	public String ReplaceSynonyms(String word){
		if(word.equals("U.S"))
			word="United_States";
		return word;
	}
	public String[]UrlFenCiByDian(String url){
		String []FenCiArray=url.split("\\.");
		return FenCiArray;
	}
	public String[]UrlFenCiByUnderLine(String url){
		String []FenCiArray=url.split("\\_");
		return FenCiArray;
	}
	public String[]UrlFenCiByHengXian(String url){
		String []FenCiArray=url.split("\\-");
		return FenCiArray;
	}
	public List<String>UrlFenCiByPercent(String url){
		List<String>FenCiList=new ArrayList<String>();
		String patterncontent="%[1-9][a-z,A-Z]";
		Pattern pattern = Pattern.compile(patterncontent,
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(url);
		int start=0;
		Boolean flag=false;
		while(matcher.find()){
			flag=true;
			int end=matcher.start();
			FenCiList.add(url.substring(start,end));
			start=start+3;
		}
		if(flag==false)
			FenCiList.add(url);
		return FenCiList;
	}
	public String[]UrlFenCiByPlus(String url){
		String []FenCiArray=url.split("\\+");
		return FenCiArray;
	}
	public String[]UrlFenCiByComma(String url){
		String []FenCiArray=url.split("\\,");
		return FenCiArray;
	}
	public String[]UrlFenCiByBoLang(String url){
		String []FenCiArray=url.split("\\~");
		return FenCiArray;
	}
	public String[]UrlFenCiByDengYu(String url){
		String []FenCiArray=url.split("\\=");
		return FenCiArray;
	}
	public String[]UrlFenCiByWenHao(String url){
		String []FenCiArray=url.split("\\?");
		return FenCiArray;
	}
	public String[]UrlFenCiByYu(String url){
		String []FenCiArray=url.split("\\&");
		return FenCiArray;
	}
	public List<String>UrlFenCiByCapitalLetter(String url){
		List<String>FenCiList=new ArrayList<String>();
		String patterncontent="[A-Z]";
		Pattern pattern = Pattern.compile(patterncontent,
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(url);
		List<Integer>filter=new ArrayList<Integer>();
		filter.add(0);
		while(matcher.find()){
			int start=matcher.start();
			if(start!=0)
				filter.add(start);
			
		}
		filter.add(url.length());
		for(int i=0;i<filter.size();i++){
			for(int j=i+1;j<filter.size();j++){
				if(filter.get(i)==filter.get(j)-1){
					continue;
				}
				else{
					String word=url.substring(filter.get(i), filter.get(j));
//					String patterncontentyear="[0-9]{4}|[0-9]{2}";
//					Pattern patternyear = Pattern.compile(patterncontentyear,
//							Pattern.DOTALL);
//					Matcher matcheryear = patternyear.matcher(word);
//					if(matcheryear.find())
//						continue;
					FenCiList.add(word);
				}
			}
		}
		
		return FenCiList;
	}
	public List<String> UrlFenCiFunction(String url) throws UnsupportedEncodingException{
		String[]FenCiArray=null;
		//url=URLDecoder.decode(url,"utf-8"); 
		url=UrlRemoveHead(url);
		List<String>FenCiList=new ArrayList<String>();
		
		FenCiArray=UrlFenCiByXieXian(url);
		for(int i=1;i<FenCiArray.length;i++){//��������
			FenCiArray[i]=ReplaceSynonyms(FenCiArray[i]);
			String[]FenCiArrayByDian=UrlFenCiByDian(FenCiArray[i]);
			for(String wordDian:FenCiArrayByDian){
				String[]FenCiArrayByUnderLine=UrlFenCiByUnderLine(wordDian);
				for(String wordUnderLine:FenCiArrayByUnderLine){
					String[]FenCiArrayByHengXian=UrlFenCiByHengXian(wordUnderLine);
					for(String wordHengXian:FenCiArrayByHengXian){
						String []FecCiArrayByBoLang=UrlFenCiByBoLang(wordHengXian);
						for(String wordByBoLang:FecCiArrayByBoLang){
							String []FenCiArrayByDengYu=UrlFenCiByDengYu(wordByBoLang);
							for(String wordDengYu:FenCiArrayByDengYu){
								String[]FenCiArrayByWenHao=UrlFenCiByWenHao(wordDengYu);
								for(String wordWenHao:FenCiArrayByWenHao){
									String []FenCiArrayByPlus=UrlFenCiByPlus(wordWenHao);
									for(String wordPlus:FenCiArrayByPlus){
										String []FenCiArrayByYu=UrlFenCiByYu(wordPlus);
										for(String wordYu:FenCiArrayByYu){
											List<String> FenCiListByPercent=UrlFenCiByPercent(wordYu);
											for(String wordPercent:FenCiListByPercent){
												FenCiList.addAll(UrlFenCiByCapitalLetter(wordPercent));
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return FenCiList;
	}
	public List<String>RemoveStopWord(List<String> FenCiList){
		String []StopWord={"www","www2","com","org","cn","a","an","of","the","net","html","htm","info","edu","gov","tel","asp"
				,"aspx","php","index","home","au","id","http","en","jpg"};
		for(int i=0;i<FenCiList.size();i++){
			String word=FenCiList.get(i);
			for(String stopword:StopWord){
				if(word.toLowerCase().equals(stopword))
					FenCiList.remove(i);
			}
		}
		return FenCiList;
	}
	public Boolean IsStopWord(String word){
		String []StopWord={"www","www2","com","org","cn","a","an","of","the","net","html","htm","info","edu","gov","tel","asp"
				,"aspx","php","index","home","au","id","http","en","jpg"};
		for(int i=0;i<StopWord.length;i++){
			if(word.equals(StopWord[i]))
				return true;
			else
				continue;
		}
		return false;
	}
	public String FenCi(String url) throws Exception{
		UrlFenCi fc=new UrlFenCi();
		List<String>FenCiList=fc.UrlFenCiFunction(url);
		FenCiList=fc.RemoveStopWord(FenCiList);
		String urlTime="";
		String urlWord="";
		for(int i=0;i<FenCiList.size();i++){
			String patterncontentyear="[0-9]+";
			Pattern patternyear = Pattern.compile(patterncontentyear,
					Pattern.DOTALL);
			String word=FenCiList.get(i);
			Matcher matcheryear = patternyear.matcher(word);
			Boolean flag=false;
			while(matcheryear.find()){
				flag=true;
				String time=matcheryear.group().trim();
				if(time.length()==4&&Integer.parseInt(time)>=1500&&Integer.parseInt(time)<=2020)
					urlTime+=time+" ";
				else
					continue;
				
			}
			if(flag==false){
				Stemmer s=new Stemmer();
				s.add(FenCiList.get(i).toLowerCase().toCharArray(),FenCiList.get(i).length());
				s.stem();
//				if(fc.IsStopWord(s.toString()))
//					continue;
				urlWord+=s.toString()+" ";
			}
		}
//		urlTime.substring(0,urlTime.length()-3);
		urlTime.trim();
		urlWord.trim();
		System.out.println(urlTime); 
		System.out.println(urlWord);
		System.out.println("OK");
		System.out.println(urlWord+"\t"+urlTime);
		return urlWord+"\t"+urlTime;
	}
	public static void main(String[]args) throws Exception{
		String inFile="F:\\research\\111\\201307\\MS\\Processing\\processing-url-frequentquery.txt";
		String outFile="F:\\research\\111\\201307\\MS\\Processing\\fenci-url-frequentquery.txt";
		FileReader fr = new FileReader(inFile);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outFile);
		String line = null;
		
		while((line=input.readLine())!=null){
			String []linearray=line.split("\t");
			String url=linearray[urlPos];
			System.out.println(url);
			UrlFenCi fc=new UrlFenCi();
			String outLine=line+"\t"+fc.FenCi(url)+"\n";
			output.append(line+"\t"+fc.FenCi(url)+"\n");
		}
//		UrlFenCi fc=new UrlFenCi();
////		fc.FenCi("http://msevents.microsoft.com/CUI/EventDetail.aspx?EventID=1032289815&Culture=en-US");
//		String url="http://search.msn.com/images/details.aspx?q=1977+cadillac+seville&color=both&size=1p&ht=333&wd=500&tht=85&twd=128&su=http%3a%2f%2fwww.californiaclassics.nl%2fcaliforniaclassics%2fcadillac%2fseville-1977-01-01a.htm&iu=http%3a%2f%2fwww.californiaclassics.nl%2fimages%2fcadillac%2fseville-1977-01-03.jpg&tu=http%3a%2f%2fimages.picsearch.com%2fis%3f7152338583759&sz=115";
//		fc.FenCi(url);
//		http://search.msn.com/images/details.aspx?q=1977+cadillac+seville&color=both&size=1p&ht=333&wd=500&tht=85&twd=128&su=http%3a%2f%2fwww.californiaclassics.nl%2fcaliforniaclassics%2fcadillac%2fseville-1977-01-01a.htm&iu=http%3a%2f%2fwww.californiaclassics.nl%2fimages%2fcadillac%2fseville-1977-01-03.jpg&tu=http%3a%2f%2fimages.picsearch.com%2fis%3f7152338583759&sz=115	
//		imag detail both&siz http californiaclass nl htm&iu http californiaclass nl jpg&tu http picsearch 	1977 1977 1977
	}
}
