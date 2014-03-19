package Extract;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YearSensitiveRules {
	public List<String> Filter8figure(String query){//��8λ���֣�mark��F
		List<String>FigureList=new ArrayList<String>();
		String patterncontent="\\s\\d{8}\\s";
		Pattern pattern = Pattern.compile(patterncontent,
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			String temp=matcher.group();
			temp=temp.trim();
			String removequery=matcher.group().trim()+"F";
			FigureList.add(removequery);
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		FigureList.add(query);
		return FigureList;
	}
	public List<String>FilterMonthDay4Year(String query){//�������Ϊ���֣�mark��Z
		List<String>MonthDayYearList=new ArrayList<String>();
		String patterncontent="(\\s(0?[1-9]|1[0-2])[^0-9]((0?[1-9])|((1|2)[0-9])|30|31)[^0-9]([0-9]{4})年?\\s)";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			String temp=matcher.group();
			temp=temp.trim();
			if(temp.charAt(temp.length()-1)!='年'){
				if(Integer.parseInt((String) temp.substring(temp.length()-3, temp.length()))<=2029&&
						Integer.parseInt((String) temp.substring(temp.length()-3, temp.length()))>=1500){
					MonthDayYearList.add(matcher.group().trim()+"Z");
					query=query.replaceAll(patterncontent, "①");
					System.out.println(query);
				}
			}
			else{
				if(Integer.parseInt((String) temp.substring(temp.length()-4, temp.length()-1))<=2029&&
						Integer.parseInt((String) temp.substring(temp.length()-3, temp.length()))>=1500){
					MonthDayYearList.add(matcher.group().trim()+"Z");
					query=query.replaceAll(patterncontent, "①");
					System.out.println(query);
				}
			}
		}
		MonthDayYearList.add(query);
		return MonthDayYearList;
	}
	public List<String>FilterMonthDay2Year(String query){//�������Ϊ���֣�mark��Z
		List<String>MonthDayYearList=new ArrayList<String>();
		String patterncontent="(\\s(0?[1-9]|1[0-2])[^0-9]((0?[1-9])|((1|2)[0-9])|30|31)[^0-9]([0-9]{2})年?\\s)";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			String temp=matcher.group();
			temp=temp.trim();
			MonthDayYearList.add(matcher.group().trim()+"z");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
//			if(temp.charAt(temp.length()-1)!='��'){
//				if(Integer.parseInt((String) temp.substring(temp.length()-3, temp.length()))<=2029){
//					MonthDayYearList.add(matcher.group().trim()+"z");
//					query=query.replaceAll(patterncontent, " ");
//					System.out.println(query);
//				}
//			}
//			else{
//				if(Integer.parseInt((String) temp.substring(temp.length()-4, temp.length()-1))<=2029){
//					MonthDayYearList.add(matcher.group().trim()+"z");
//					query=query.replaceAll(patterncontent, " ");
//					System.out.println(query);
//				}
//			}
		}
		MonthDayYearList.add(query);
		return MonthDayYearList;
	}
	public List<String>FilterLetterMonthDay4Year(String query){//��������У���Ϊ���ʣ�mark��L
		List<String>LetterMonthDayYearList=new ArrayList<String>();
		String patterncontent="\\s(january|february|march|april|may|" +
		"june|july|august|september|october|" +
		"november|december|jan|feb|mar|" +
		"apr|may|june|july|aug|sept|" +
		"oct|nov|dec)\\s.?((0?[1-9])|((1|2)[0-9])|30|31)(st|nd|rd|th)?[^0-9](\\d{4})";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			LetterMonthDayYearList.add(matcher.group().trim()+"W");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		LetterMonthDayYearList.add(query);
		return LetterMonthDayYearList;
	}
	public List<String>FilterLetterMonthDay2Year(String query){//��������У���Ϊ���ʣ�mark��L
		List<String>LetterMonthDayYearList=new ArrayList<String>();
		String patterncontent="\\s(january|february|march|april|may|" +
		"june|july|august|september|october|" +
		"november|december|jan|feb|mar|" +
		"apr|may|june|july|aug|sept|" +
		"oct|nov|dec)\\s.?((0?[1-9])|((1|2)[0-9])|30|31)(st|nd|rd|th)?[^0-9](\\d{2})";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			LetterMonthDayYearList.add(matcher.group().trim()+"w");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		LetterMonthDayYearList.add(query);
		return LetterMonthDayYearList;
	}
	public List<String>FilterLetterMonth4Year(String query){
		List<String>LetterMonthDayYearList=new ArrayList<String>();
		String patterncontent="\\s(january|february|march|april|may|" +
		"june|july|august|september|october|" +
		"november|december|jan|feb|mar|" +
		"apr|may|june|july|aug|sept|" +
		"oct|nov|dec)\\s.?(\\d{4})";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			LetterMonthDayYearList.add(matcher.group().trim()+"M");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		LetterMonthDayYearList.add(query);
		return LetterMonthDayYearList;
	}
	public List<String>FilterLetterMonth2Year(String query){
		List<String>LetterMonthDayYearList=new ArrayList<String>();
		String patterncontent="\\s(january|february|march|april|may|" +
		"june|july|august|september|october|" +
		"november|december|jan|feb|mar|" +
		"apr|may|june|july|aug|sept|" +
		"oct|nov|dec)\\s.?(\\d{2})";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			LetterMonthDayYearList.add(matcher.group().trim()+"m");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		LetterMonthDayYearList.add(query);
		return LetterMonthDayYearList;
	}
	public List<String>FilterLetterMonthDay(String query){
		List<String>LetterMonthDayYearList=new ArrayList<String>();
		String patterncontent="\\s(january|february|march|april|may|" +
		"june|july|august|september|october|" +
		"november|december|jan|feb|mar|" +
		"apr|may|june|july|aug|sept|" +
		"oct|nov|dec)\\s.?((0?[1-9])|((1|2)[0-9])|30|31)(st|nd|rd|th)?";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			LetterMonthDayYearList.add(matcher.group().trim()+"D");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		LetterMonthDayYearList.add(query);
		return LetterMonthDayYearList;
	}
	public List<String>Filter4Year(String query){
		List<String>LetterMonthDayYearList=new ArrayList<String>();
		String patterncontent="\\s\\d{4}\\s";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			int year=Integer.parseInt(matcher.group().trim());
			if(year<=2029&&year>=1500){
				LetterMonthDayYearList.add(matcher.group().trim()+"Y");
				query=query.replaceAll(patterncontent, "①");
				System.out.println(query);
			}
		}
		LetterMonthDayYearList.add(query);
		return LetterMonthDayYearList;
	}
	public List<String>Filter2Year(String query){
		List<String>LetterMonthDayYearList=new ArrayList<String>();
		String patterncontent="\\s\\d{2}\\s";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			LetterMonthDayYearList.add(matcher.group().trim()+"y");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		LetterMonthDayYearList.add(query);
		return LetterMonthDayYearList;
	}
	public List<String>FilterDictionary(String query){
		List<String>DictionaryList=new ArrayList<String>();
		String patterncontent="\\bday\\b|\\bweek\\b|\\bweekly\\b|\\bsennight\\b|" +
		"\\bsunday\\b|\\bmonday\\b|\\btuesday\\b|\\bwednesday\\b|\\bthursday\\b|\\bfriday\\b|\\bsaturday\\b|" +
		"\\bfortnight\\b|\\blunar month\\b|\\bmonth\\b|\\bjanuary\\b|\\bfebruary\\b|\\bmarch\\b|\\bapril\\b|" +
		"\\bmay\\b|\\bjune\\b|\\bjuly\\b|\\baugust\\b|\\bseptember\\b|\\boctober\\b|\\bnovember\\b|\\bdecember\\b|" +
		"\\bjan\\b|\\bfeb\\b|\\bmar\\b|\\bapi\\b|\\bmay\\b|\\bjul\\b|\\bjun\\b|\\baug\\b|\\bsep\\b|\\boct\\b|" +
		"\\bnov\\b|\\bdec\\b|\\bmon\\b|\\btue\\b|\\bwed\\b|\\bthur\\b|\\bfri\\b|\\bsat\\b|\\bsun\\b|" +
		"\\bquarter\\b|\\byear\\b|\\byearly\\b|\\bbiennium\\b|\\bOlympiad\\b|\\blustrum\\b|" +
		"\\bdecade\\b|\\bindiction\\b|\\bjubilee \\b|\\bbiblical\\b|\\bcentury\\b|\\bdaily\\b|\\bmonthly\\b|" +
		"\\bbi-monthly\\b|\\bquarterly\\b|\\bemi-annually\\b|\\bannually";
		Pattern pattern = Pattern.compile(patterncontent,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while(matcher.find()){
			DictionaryList.add(matcher.group().trim()+"T");
			query=query.replaceAll(patterncontent, "①");
			System.out.println(query);
		}
		DictionaryList.add(query);
		return DictionaryList;
	}
}
