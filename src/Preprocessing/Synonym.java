package Preprocessing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;

public class Synonym {
	public Boolean IsSynonym(String KeyWord1,String KeyWord2) throws FileNotFoundException, JWNLException{
		String propsFile = "F:/research/111/time-sensitive query/代码/WordNet/jwnl14-rc2/config/file_properties.xml"; 
		JWNL.initialize(new FileInputStream(propsFile));
		IndexWord indexword = Dictionary.getInstance().getIndexWord(POS.NOUN, KeyWord1);
		Synset[] syn=indexword.getSenses();
		for(Synset s:syn){
			if(s.containsWord(KeyWord2)){
				System.out.println("true!");
				return true;
			}
		}
		return false;
	}
	public static void main(String[]args) throws FileNotFoundException, JWNLException{
		String propsFile = "F:/research/111/time-sensitive query/代码/WordNet/jwnl14-rc2/config/file_properties.xml"; 
		JWNL.initialize(new FileInputStream(propsFile));
		IndexWord DOG = Dictionary.getInstance().getIndexWord(POS.NOUN, "dogs");
		DOG = Dictionary.getInstance().lookupIndexWord(POS.NOUN, "U.S.");
		Synset[] syn=DOG.getSenses();
//		Dictionary.getInstance().getsSynsetIterator(POS.NOUN);
		System.out.println(DOG);
		for(Synset s:syn){
			IndexWord in=Dictionary.getInstance().lookupIndexWord(POS.NOUN, "United_States");
			System.out.println(in);
			if(s.containsWord(in.getLemma())){
				System.out.println("true!");
			}
			System.out.println(s);
		}
	}
	
}
