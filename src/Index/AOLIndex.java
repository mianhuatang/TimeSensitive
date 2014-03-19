package Index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import net.didion.jwnl.JWNLException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import Similarity.CalculateSimilarity;

public class AOLIndex {
	public static int MaxQuery=100;
	public int CID=1;
	public static double MinSim=0.6;
	private Directory dir;
	public IndexWriter getWriter() throws Exception {  
        Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_40);  
        IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_40, analyzer);  
        return new IndexWriter(dir, iwc);  
    } 
	public void CreateIndex(String indexFilePath, String inputPath, String outputPath) throws Exception{
		FileReader fr = new FileReader(inputPath);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		FileWriter output = new FileWriter(outputPath);
		String line=null;
		
		AOLIndex index=new AOLIndex();
		
        dir=FSDirectory.open(new File(indexFilePath));  
        IndexWriter writer=getWriter();  
        int count=0;
        //wow lvl guid	2421	wow lvl 30 guide	2006-03-22 15:03:10	2	 wow lvl guide 	30y 	http://wow.allakhazam.com
        while((line=input.readLine())!=null){
        	String outline="";
        	count++;
        	String[]lineArray=line.split("\t");
        	Document doc=new Document();
        	doc.add(new TextField("Pquery",lineArray[0],Store.YES));
        	doc.add(new StringField("SessionID",lineArray[1],Store.YES));
        	doc.add(new TextField("URL",lineArray[7],Store.YES));
        	
        	if(count==1){
        		doc.add(new TextField("ClassID",CID+"",Store.YES));
        		outline+=CID+"\t"+line;
        	}
        	writer.commit();
        	if (count>1){
        		String ClassID=index.Search(lineArray[0], lineArray[1], lineArray[7]);
        		doc.add(new TextField("ClassID",ClassID,Store.YES));
        		outline+=ClassID+"\t"+line;
        	}
        	writer.addDocument(doc);
        	output.append(outline + "\n");
        	System.out.println("count:"+count);
        }
        System.out.println("init ok");  
        writer.close();  
        output.flush();
		output.close();
		output = null;
		input.close();
	}
	public String Search(String Pquery,String sessionID,String Url) throws JWNLException, Exception{
		double MaxSim=0;
		String ClassID=null;
		CalculateSimilarity cs=new CalculateSimilarity();
		String indexFilePath="F:/research/111/time-sensitive query/AOL Data Set/All/Index/index";
		dir=FSDirectory.open(new File(indexFilePath));
		IndexReader reader=DirectoryReader.open(dir);  
        IndexSearcher searcher=new IndexSearcher(reader); 
        String []PqueryArray=Pquery.split(" ");
        for(String pquery:PqueryArray){
        	Term term=new Term("Pquery",pquery);
        	TermQuery query=new TermQuery(term);  //单个字节查询
            TopDocs topdocs=searcher.search(query, MaxQuery);  
            ScoreDoc[] scoreDocs=topdocs.scoreDocs;  
            System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());  
            for(int i=0; i < scoreDocs.length; i++) {  
                int doc = scoreDocs[i].doc;  
                Document document = searcher.doc(doc);  
                String content=document.get("Pquery");
                String session=document.get("SessionID");
                String url=document.get("URL");
                double sim=0.6*cs.CalQuerySim(Pquery, content)+0.3*cs.CalUrlSim(url, Url)+0.1*cs.CalSessionSim(session, sessionID);
                if(sim>MaxSim){
                	MaxSim=sim;
                	ClassID=document.get("ClassID");
                }
                System.out.println("content===="+document.get("Pquery"));  
            }  
        } 
        reader.close();  
        if(MaxSim>=MinSim){
        	return ClassID;
        }
        else{
        	CID++;
        	return CID+"";
        }
	}
	public static void main(String []args) throws Exception{
		String indexFilePath="F:/research/111/time-sensitive query/AOL Data Set/All/Index/index";
		String inputPath="F:/research/111/time-sensitive query/AOL Data Set/All/PreprocessingExtractjoin.txt";
		String outputPath="F:/research/111/time-sensitive query/AOL Data Set/All/Cluster/Cluster.txt";
		AOLIndex index=new AOLIndex();
		index.CreateIndex(indexFilePath,inputPath,outputPath);
	}
}
