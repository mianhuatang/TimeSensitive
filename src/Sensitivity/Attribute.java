package Sensitivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attribute {
	public String ClassID;
	public List<String> TopicParts=new ArrayList<String>();
	public List<String> TimeParts=new ArrayList<String>();
	public Map<String,Integer>TimePartsMerges= new HashMap<String,Integer>();
	public List<SpanFrequency> TimeSpans=new ArrayList<SpanFrequency>();
	public Map<String,Integer> TimeSpansMerges= new HashMap<String,Integer>();
	public String []Sensitivity;
}
