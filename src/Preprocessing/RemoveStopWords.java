/**
 * 
 */
package Preprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rebecca
 *
 */
public class RemoveStopWords {

	/**
	 * @param args
	 */
	List<String>StopWordList=new ArrayList<String>();
	public void IniStopWordList(String FilePath) throws IOException{
		FileReader stopwordfr=new FileReader(FilePath);
		BufferedReader stopwordinput =new BufferedReader(stopwordfr, 20 * 1024 * 1024);
		String stopwordline="";
		while((stopwordline=stopwordinput.readLine())!=null){
			StopWordList.add(stopwordline);
		}
		stopwordinput.close();
	}
	public String RemoveStopWordsFunction(String query){
		query=query.replaceAll("\\,", " ");
		query=query.replaceAll("\\?", " ");
		query=query.replaceAll("\\.", " ");
		query=query.replaceAll("\\:", " ");
		query=query.replaceAll("\"", " ");
		query=query.replaceAll("\\+", " ");
		query=query.replaceAll("\\-", " ");
		query=query.replaceAll("\\/", " ");
		query=query.replaceAll("\\\\", " ");
		query=query.replaceAll("\\!", " ");
		query=query.replaceAll("\\~", " ");
		query=query.replaceAll("\\`", " ");
		query=query.replaceAll("\\@", " ");
		query=query.replaceAll("\\$", " ");
		query=query.replaceAll("\\%", " ");
		query=query.replaceAll("\\^", " ");
		query=query.replaceAll("\\&", " ");
		query=query.replaceAll("\\*", " ");
		query=query.replaceAll("\\(", " ");
		query=query.replaceAll("\\)", " ");
		query=query.replaceAll("\\_", " ");
		query=query.replaceAll("\\=", " ");
		query=query.replaceAll("\\[", " ");
		query=query.replaceAll("\\]", " ");
		query=query.replaceAll("\\{", " ");
		query=query.replaceAll("\\}", " ");
		query=query.replaceAll("\\|", " ");
		query=query.replaceAll("\\;", " ");
		query=query.replaceAll("\\<", " ");
		query=query.replaceAll("\\>", " ");
		query=query.replace("①", " ");
		query=query.replaceAll("  ", "");
		query=query.trim();
		String returnquery="";
		Boolean flag=false;
		String[] queryArray=query.split(" ");//һ�����ӷֳɵ���
		for(int i=0;i<queryArray.length;i++){//�ж�ÿ������
			for(String stopword:StopWordList){
				if(queryArray[i].toLowerCase().equals(stopword)){
					flag=true;
					continue;
				}
			}
			if(flag==false)
				returnquery+=queryArray[i].replaceAll("\\'", "")+" ";
			flag=false;
		}
		return returnquery.trim();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
